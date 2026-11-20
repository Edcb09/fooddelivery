package com.myx.fooddelivery.service;

/**
 * 通知服务抽象。
 * 当前实现为日志通知；后续接入站内信时只需新增一个实现类，业务代码无需改动。
 */
public interface NotificationService {
    /**
     * 向指定用户发送通知
     * @param userId  接收人用户ID
     * @param title   通知标题
     * @param content 通知内容
     */
    void notify(Long userId, String title, String content);
}
