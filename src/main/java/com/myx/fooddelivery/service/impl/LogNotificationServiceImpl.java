package com.myx.fooddelivery.service.impl;

import com.myx.fooddelivery.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 通知服务的日志实现：当前只打印日志，后续可替换为站内信/短信实现。
 */
@Slf4j
@Service
public class LogNotificationServiceImpl implements NotificationService {
    @Override
    public void notify(Long userId, String title, String content) {
        log.info("【通知】用户[{}] - {}：{}", userId, title, content);
    }
}
