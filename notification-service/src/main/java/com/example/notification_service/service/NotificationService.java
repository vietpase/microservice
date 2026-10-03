package com.example.notification_service.service;

import com.example.notification_service.dto.OrderEvent;
import com.example.notification_service.dto.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

// --- Service ---
@Service
@Slf4j
public class NotificationService {

    @KafkaListener(topics = "order-events", groupId = "notification-group",
            properties = "spring.json.value.default.type=com.example.notification_service.dto.OrderEvent")
    public void notifyOrderCreated(OrderEvent event) {
        log.info("[EMAIL] - Bạn vừa tạo thành công đơn hàng số #{}, sản phẩm: {}",
                event.orderId(), event.productId());
    }

    @KafkaListener(topics = "payment-events", groupId = "notification-group",
            properties = "spring.json.value.default.type=com.example.notification_service.dto.PaymentEvent")
    public void notifyPaymentStatus(PaymentEvent event) {
        if ("SUCCESS".equals(event.status())) {
            log.info("[EMAIL] - Thanh toán thành công cho đơn hàng #{}. Hàng đang được chuẩn bị!",
                    event.orderId());
        } else {
            log.info("[EMAIL] - Thanh toán thất bại cho đơn hàng #{}. Vui lòng thử lại.",
                    event.orderId());
        }
    }
}