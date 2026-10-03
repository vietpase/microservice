package com.example.payment_service.service;

import com.example.payment_service.dto.InventoryEvent;
import com.example.payment_service.dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "inventory-events", groupId = "payment-group",
            properties = "spring.json.value.default.type=com.example.payment_service.dto.InventoryEvent")
    public void handleInventoryEvent(InventoryEvent event) {
        if ("RESERVED".equals(event.status())) {
            log.info("Kho đã giữ chỗ. Tiến hành trừ tiền cho Order: {}", event.orderId());

            // Giả lập xử lý thanh toán thành công
            PaymentEvent paymentEvent = new PaymentEvent(event.orderId(), "SUCCESS");
            kafkaTemplate.send("payment-events", String.valueOf(event.orderId()), paymentEvent);
        } else {
            log.info("Kho hết hàng, huỷ bỏ thanh toán cho Order: {}", event.orderId());
        }
    }
}