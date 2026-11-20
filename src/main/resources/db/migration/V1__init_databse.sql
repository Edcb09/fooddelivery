-- ============================================================
-- 1. 用户表
-- ============================================================
CREATE TABLE `user` (
                        `id`          bigint NOT NULL AUTO_INCREMENT,
                        `username`    varchar(50)  NOT NULL COMMENT '用户名（登录用）',
                        `password`    varchar(100) NOT NULL COMMENT '加密密码，建议 BCrypt',
                        `phone`       varchar(20)  DEFAULT NULL COMMENT '手机号',
                        `nickname`    varchar(50)  DEFAULT NULL COMMENT '昵称',
                        `avatar`      varchar(255) DEFAULT NULL COMMENT '头像URL',
                        `gender`      tinyint      DEFAULT 0 COMMENT '0未知 1男 2女',
                        `role`        tinyint      DEFAULT 0 COMMENT '0普通用户 1商家 2平台管理员 3骑手',
                        `status`      tinyint      DEFAULT 1 COMMENT '1正常 0禁用',
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_username` (`username`),
                        UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ============================================================
-- 2. 收货地址表
-- ============================================================
CREATE TABLE `user_address` (
                                `id`             bigint NOT NULL AUTO_INCREMENT,
                                `user_id`        bigint NOT NULL COMMENT '所属用户',
                                `receiver_name`  varchar(50)  NOT NULL COMMENT '收货人姓名',
                                `receiver_phone` varchar(20)  NOT NULL COMMENT '收货人电话',
                                `province`       varchar(50)  DEFAULT NULL,
                                `city`           varchar(50)  DEFAULT NULL,
                                `district`       varchar(50)  DEFAULT NULL,
                                `detail_address` varchar(255) NOT NULL COMMENT '详细地址',
                                `is_default`     tinyint DEFAULT 0 COMMENT '是否默认地址',
                                `create_time`    datetime DEFAULT CURRENT_TIMESTAMP,
                                `update_time`    datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`),
                                KEY `idx_user` (`user_id`),
                                CONSTRAINT `fk_addr_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

-- ============================================================
-- 3. 店铺表
-- ============================================================
CREATE TABLE `shop` (
                        `id`               bigint NOT NULL AUTO_INCREMENT,
                        `owner_id`         bigint NOT NULL COMMENT '店主用户ID',
                        `shop_name`        varchar(100)  NOT NULL COMMENT '店铺名称',
                        `category`         varchar(50)   DEFAULT NULL COMMENT '店铺品类：快餐/奶茶/火锅等',
                        `logo`             varchar(255)  DEFAULT NULL COMMENT '店铺Logo',
                        `description`      varchar(500)  DEFAULT NULL COMMENT '店铺简介',
                        `address`          varchar(255)  DEFAULT NULL COMMENT '店铺地址',
                        `phone`            varchar(20)   DEFAULT NULL COMMENT '店铺电话',
                        `business_hours`   varchar(100)  DEFAULT NULL COMMENT '营业时间，如 09:00-22:00',
                        `min_order_amount` decimal(10,2) NOT NULL DEFAULT 0 COMMENT '起送价',
                        `delivery_fee`     decimal(10,2) NOT NULL DEFAULT 0 COMMENT '配送费',
                        `pack_fee`         decimal(10,2) NOT NULL DEFAULT 0 COMMENT '包装费',
                        `rating`           decimal(2,1)  NOT NULL DEFAULT 5.0 COMMENT '店铺评分',
                        `monthly_sales`    int NOT NULL DEFAULT 0 COMMENT '月售单数',
                        `longitude`        decimal(10,7) DEFAULT NULL COMMENT '经度',
                        `latitude`         decimal(10,7) DEFAULT NULL COMMENT '纬度',
                        `status`           tinyint DEFAULT 1 COMMENT '1营业中 0休息中',
                        `audit_status`     tinyint DEFAULT 0 COMMENT '0待审核 1通过 2拒绝',
                        `audit_reason`     varchar(255)  DEFAULT NULL COMMENT '审核拒绝原因',
                        `is_deleted`       tinyint DEFAULT 0 COMMENT '软删除标记',
                        `create_time`      datetime DEFAULT CURRENT_TIMESTAMP,
                        `update_time`      datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_owner` (`owner_id`),
                        KEY `idx_status_audit` (`status`, `audit_status`),
                        KEY `idx_category` (`category`),
                        CONSTRAINT `fk_shop_owner` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺表';

-- ============================================================
-- 4. 商品分类表
-- ============================================================
CREATE TABLE `category` (
                            `id`          bigint NOT NULL AUTO_INCREMENT,
                            `shop_id`     bigint NOT NULL COMMENT '所属店铺',
                            `name`        varchar(50) NOT NULL COMMENT '分类名称',
                            `sort`        int DEFAULT 0 COMMENT '排序值',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_shop_name` (`shop_id`, `name`),
                            CONSTRAINT `fk_cat_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- ============================================================
-- 5. 菜品表（无 stock，库存只在 product_sku）
-- ============================================================
CREATE TABLE `product` (
                           `id`            bigint NOT NULL AUTO_INCREMENT,
                           `shop_id`       bigint NOT NULL COMMENT '所属店铺',
                           `category_id`   bigint NOT NULL COMMENT '所属分类',
                           `name`          varchar(100)  NOT NULL COMMENT '菜品名称',
                           `description`   varchar(500)  DEFAULT NULL COMMENT '菜品描述',
                           `image`         varchar(255)  DEFAULT NULL COMMENT '菜品图片URL',
                           `price`         decimal(10,2) NOT NULL COMMENT '售价',
                           `monthly_sales` int NOT NULL DEFAULT 0 COMMENT '月售',
                           `status`        tinyint DEFAULT 1 COMMENT '1上架 0下架',
                           `version`       int NOT NULL DEFAULT 0 COMMENT '乐观锁版本号，防超卖',
                           `create_time`   datetime DEFAULT CURRENT_TIMESTAMP,
                           `update_time`   datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`),
                           KEY `idx_shop_status` (`shop_id`, `status`),
                           KEY `idx_category` (`category_id`),
                           CONSTRAINT `fk_prod_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`),
                           CONSTRAINT `fk_prod_cat`  FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品表';

-- ============================================================
-- 6. 菜品规格(SKU)表
-- 无规格的菜品也要建一条默认 SKU，保证 cart 中 sku_id 非空
-- ============================================================
CREATE TABLE `product_sku` (
                               `id`          bigint NOT NULL AUTO_INCREMENT,
                               `product_id`  bigint NOT NULL,
                               `sku_name`    varchar(100)  NOT NULL COMMENT '规格名，如 大份/微辣/默认',
                               `price`       decimal(10,2) NOT NULL COMMENT '该规格价格',
                               `stock`       int NOT NULL DEFAULT 0 COMMENT '该规格库存',
                               `version`     int NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               KEY `idx_product` (`product_id`),
                               CONSTRAINT `fk_sku_prod` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品规格表';

-- ============================================================
-- 7. 购物车表（同一用户按 SKU 去重，限定同一店铺）
-- ============================================================
CREATE TABLE `cart` (
                        `id`          bigint NOT NULL AUTO_INCREMENT,
                        `user_id`     bigint NOT NULL,
                        `shop_id`     bigint NOT NULL COMMENT '所属店铺，换店需清空购物车',
                        `product_id`  bigint NOT NULL,
                        `sku_id`      bigint NOT NULL COMMENT '规格ID',
                        `quantity`    int NOT NULL DEFAULT 1,
                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_user_sku` (`user_id`, `sku_id`),
                        KEY `idx_user_shop` (`user_id`, `shop_id`),
                        CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`)    REFERENCES `user` (`id`),
                        CONSTRAINT `fk_cart_shop` FOREIGN KEY (`shop_id`)    REFERENCES `shop` (`id`),
                        CONSTRAINT `fk_cart_sku`  FOREIGN KEY (`sku_id`)     REFERENCES `product_sku` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- ============================================================
-- 8. 订单主表
-- ============================================================
CREATE TABLE `orders` (
                          `id`               bigint NOT NULL AUTO_INCREMENT,
                          `order_no`         varchar(50) NOT NULL COMMENT '订单号',
                          `user_id`          bigint NOT NULL COMMENT '下单用户',
                          `shop_id`          bigint NOT NULL COMMENT '接单店铺',
                          `receiver_name`    varchar(50)  NOT NULL COMMENT '收货人快照',
                          `receiver_phone`   varchar(20)  NOT NULL COMMENT '收货电话快照',
                          `receiver_address` varchar(255) NOT NULL COMMENT '收货地址快照',
                          `goods_amount`     decimal(10,2) NOT NULL DEFAULT 0 COMMENT '商品总额',
                          `pack_fee`         decimal(10,2) NOT NULL DEFAULT 0 COMMENT '包装费',
                          `delivery_fee`     decimal(10,2) NOT NULL DEFAULT 0 COMMENT '配送费',
                          `discount_amount`  decimal(10,2) NOT NULL DEFAULT 0 COMMENT '优惠金额',
                          `pay_amount`       decimal(10,2) NOT NULL DEFAULT 0 COMMENT '实付金额',
                          `pay_method`       tinyint DEFAULT NULL COMMENT '1微信 2支付宝',
                          `pay_time`         datetime DEFAULT NULL,
                          `status`           tinyint NOT NULL DEFAULT 0
                              COMMENT '0待支付 1已支付 2商家已接单 3配送中 4已完成 5已取消 6退款中 7已退款',
                          `cancel_reason`    varchar(255) DEFAULT NULL,
                          `remark`           varchar(255) DEFAULT NULL COMMENT '订单备注',
                          `accept_time`      datetime DEFAULT NULL COMMENT '商家接单时间',
                          `deliver_time`     datetime DEFAULT NULL COMMENT '开始配送时间',
                          `receive_time`     datetime DEFAULT NULL COMMENT '送达时间',
                          `create_time`      datetime DEFAULT CURRENT_TIMESTAMP,
                          `update_time`      datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `uk_order_no` (`order_no`),
                          KEY `idx_user` (`user_id`),
                          KEY `idx_shop_status` (`shop_id`, `status`),
                          KEY `idx_create_time` (`create_time`),
                          CONSTRAINT `fk_ord_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                          CONSTRAINT `fk_ord_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';

-- ============================================================
-- 9. 订单明细表（不建 product_id / sku_id 外键，历史订单可独立展示）
-- ============================================================
CREATE TABLE `order_item` (
                              `id`           bigint NOT NULL AUTO_INCREMENT,
                              `order_id`     bigint NOT NULL,
                              `product_id`   bigint DEFAULT NULL COMMENT '菜品ID，仅作记录',
                              `sku_id`       bigint DEFAULT NULL COMMENT '规格ID，仅作记录',
                              `product_name` varchar(100)  NOT NULL COMMENT '菜品名称快照',
                              `spec_name`    varchar(100)  DEFAULT NULL COMMENT '规格名称快照',
                              `image`        varchar(255)  DEFAULT NULL COMMENT '图片快照',
                              `price`        decimal(10,2) NOT NULL COMMENT '成交单价快照',
                              `quantity`     int NOT NULL,
                              `item_amount`  decimal(10,2) NOT NULL COMMENT '小计 = price × quantity',
                              PRIMARY KEY (`id`),
                              KEY `idx_order` (`order_id`),
                              CONSTRAINT `fk_item_ord` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- ============================================================
-- 10. 评价表
-- ============================================================
CREATE TABLE `review` (
                          `id`          bigint NOT NULL AUTO_INCREMENT,
                          `order_id`    bigint NOT NULL,
                          `user_id`     bigint NOT NULL,
                          `shop_id`     bigint NOT NULL,
                          `rating`      tinyint NOT NULL COMMENT '1~5星',
                          `content`     varchar(500)  DEFAULT NULL,
                          `images`      varchar(1000) DEFAULT NULL COMMENT '图片URL，逗号分隔',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `uk_order` (`order_id`) COMMENT '一单只能评价一次',
                          KEY `idx_shop` (`shop_id`),
                          CONSTRAINT `fk_rev_ord`  FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
                          CONSTRAINT `fk_rev_user` FOREIGN KEY (`user_id`)  REFERENCES `user` (`id`),
                          CONSTRAINT `fk_rev_shop` FOREIGN KEY (`shop_id`)  REFERENCES `shop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- ============================================================
-- 11. 优惠券定义表
-- ============================================================
CREATE TABLE `coupon` (
                          `id`          bigint NOT NULL AUTO_INCREMENT,
                          `shop_id`     bigint DEFAULT NULL COMMENT 'NULL=平台通用券',
                          `name`        varchar(100)  NOT NULL,
                          `type`        tinyint NOT NULL COMMENT '1满减 2折扣',
                          `threshold`   decimal(10,2) NOT NULL DEFAULT 0 COMMENT '使用门槛',
                          `value`       decimal(10,2) NOT NULL COMMENT '满减金额或折扣值',
                          `valid_days`  int NOT NULL DEFAULT 7 COMMENT '领取后有效天数',
                          `status`      tinyint DEFAULT 1 COMMENT '1启用 0停用',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`),
                          KEY `idx_shop` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券定义表';

-- ============================================================
-- 12. 用户优惠券表
-- ============================================================
CREATE TABLE `user_coupon` (
                               `id`          bigint NOT NULL AUTO_INCREMENT,
                               `user_id`     bigint NOT NULL,
                               `coupon_id`   bigint NOT NULL,
                               `order_id`    bigint DEFAULT NULL COMMENT '核销订单ID',
                               `status`      tinyint DEFAULT 0 COMMENT '0未使用 1已使用 2已过期',
                               `expire_time` datetime NOT NULL,
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               KEY `idx_user_status` (`user_id`, `status`),
                               CONSTRAINT `fk_uc_user`   FOREIGN KEY (`user_id`)   REFERENCES `user` (`id`),
                               CONSTRAINT `fk_uc_cpn`    FOREIGN KEY (`coupon_id`) REFERENCES `coupon` (`id`),
                               CONSTRAINT `fk_uc_order`  FOREIGN KEY (`order_id`)  REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户优惠券表';

-- ============================================================
-- 13. 支付流水表
-- ============================================================
CREATE TABLE `payment` (
                           `id`          bigint NOT NULL AUTO_INCREMENT,
                           `order_no`    varchar(50)  NOT NULL COMMENT '关联订单号',
                           `trade_no`    varchar(100) NOT NULL COMMENT '第三方交易流水号',
                           `pay_method`  tinyint NOT NULL COMMENT '1微信 2支付宝',
                           `amount`      decimal(10,2) NOT NULL,
                           `pay_status`  tinyint DEFAULT 1 COMMENT '1成功 0失败',
                           `pay_time`    datetime DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `uk_trade_no` (`trade_no`),
                           KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付流水表';

-- ============================================================
-- 14. 退款表
-- ============================================================
CREATE TABLE `refund` (
                          `id`          bigint NOT NULL AUTO_INCREMENT,
                          `order_id`    bigint NOT NULL,
                          `amount`      decimal(10,2) NOT NULL,
                          `reason`      varchar(255) DEFAULT NULL,
                          `status`      tinyint DEFAULT 0 COMMENT '0申请中 1同意 2拒绝 3已退款',
                          `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                          `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`),
                          KEY `idx_order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退款表';

-- ============================================================
-- 15. 骑手表（含资质审核状态）
-- ============================================================
CREATE TABLE `rider` (
                         `id`            bigint NOT NULL AUTO_INCREMENT,
                         `user_id`       bigint NOT NULL COMMENT '关联用户账号',
                         `name`          varchar(50) NOT NULL,
                         `phone`         varchar(20) NOT NULL,
                         `status`        tinyint DEFAULT 1 COMMENT '1空闲 2配送中 3休息',
                         `audit_status`  tinyint NOT NULL DEFAULT 0 COMMENT '0待审核 1通过 2拒绝',
                         `create_time`   datetime DEFAULT CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `uk_user` (`user_id`),
                         CONSTRAINT `fk_rider_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='骑手表';

-- ============================================================
-- 16. 配送单表
-- ============================================================
CREATE TABLE `delivery_order` (
                                  `id`           bigint NOT NULL AUTO_INCREMENT,
                                  `order_id`     bigint NOT NULL,
                                  `rider_id`     bigint DEFAULT NULL,
                                  `status`       tinyint DEFAULT 0 COMMENT '0待分配 1已分配 2取餐中 3配送中 4已送达',
                                  `pickup_time`  datetime DEFAULT NULL COMMENT '取餐时间',
                                  `deliver_time` datetime DEFAULT NULL COMMENT '送达时间',
                                  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`id`),
                                  UNIQUE KEY `uk_order` (`order_id`),
                                  KEY `idx_rider` (`rider_id`),
                                  CONSTRAINT `fk_do_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
                                  CONSTRAINT `fk_do_rider` FOREIGN KEY (`rider_id`) REFERENCES `rider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配送单表';
