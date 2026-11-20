package com.myx.fooddelivery.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.myx.fooddelivery.common.BusinessException;
import com.myx.fooddelivery.common.UserContext;
import com.myx.fooddelivery.dto.ShopCreateDTO;
import com.myx.fooddelivery.entity.Shop;
import com.myx.fooddelivery.enums.RoleEnum;
import com.myx.fooddelivery.mapper.ShopMapper;
import com.myx.fooddelivery.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Override
    public void createShop(ShopCreateDTO createDTO){
        //1、获取当前登录用户
        Long userId= UserContext.getUserId();
        Integer role=UserContext.getRole();

        //2、权限校验：商家开店
        if(!RoleEnum.MERCHANT.getCode().equals(role)){
            log.warn("用户{}尝试非法开店，其角色为：{}",userId,role);
            throw new BusinessException("只有商家角色才能申请开店！");
        }

        //3、业务校验：一个商家只能开一家店
        Long count=shopMapper.selectCount(new LambdaQueryWrapper<Shop>().eq(Shop::getOwnerId,userId));
        if(count>0){
            throw new BusinessException("您已经拥有一家店铺，请勿重复入驻！");
        }

        //4、组装Entity
        Shop shop=new Shop();
        BeanUtils.copyProperties(createDTO,shop);
        shop.setOwnerId(userId);
        shop.setStatus(0);//刚申请默认休息中
        shop.setAuditStatus(0);//默认待审核

        //5、入库（唯一索引兜底：若并发下两个线程都通过了selectCount，
        //   数据库层的唯一索引会拦住第二个，抛出DuplicateKeyException）
        try{
            shopMapper.insert(shop);
        }catch (org.springframework.dao.DuplicateKeyException e){
            throw new BusinessException("您已经拥有一家店铺，请勿重复入驻！");
        }
        log.info("商家{}成功申请开店：{}",userId,shop.getShopName());
    }

    public void auditShop(Long shopId,Integer auditStatus,String rejectReason){
        //1、校验当前用户是不是管理员
        Long userId=UserContext.getUserId();
        Integer role=UserContext.getRole();

        if(!RoleEnum.ADMIN.getCode().equals(role)){
            log.warn("用户{}尝试审核开店资质，其角色为：{}",userId,role);
            throw new BusinessException("只有管理员才拥有审核权力！");
        }

        //2、校验审核状态参数 + 查询店铺
        if(auditStatus==null||(auditStatus!=1&&auditStatus!=2)){
            throw new BusinessException("审核状态只能是 1通过 或 2拒绝！");
        }
        Shop shop=shopMapper.selectById(shopId);
        if(shop==null){
            throw new BusinessException("店铺不存在！");
        }
        if(shop.getAuditStatus()!=0){
            throw new BusinessException("该店铺已审核，请勿重复审核！");
        }

        //3、将店铺的auditStatus改为1（通过）或2（拒绝）
        shop.setAuditStatus(auditStatus);
        if(auditStatus==1){
            //审核通过：店铺改为营业中
            shop.setStatus(1);
        }

        //4、记录拒绝原因（存进shop表，不必新建审核表或消息表）
        if(auditStatus==2){
            if(rejectReason==null||rejectReason.isBlank()){
                throw new BusinessException("拒绝审核时必须填写拒绝原因！");
            }
            shop.setAuditReason(rejectReason);
            //“发消息”先用日志代替：记录已通知商家，后续可接入站内信/消息表
            log.info("店铺[{}]审核被拒，原因：{}，已通知商家userId={}",
                    shop.getShopName(), rejectReason, shop.getOwnerId());
        }

        shopMapper.updateById(shop);
    }
}
