package com.example.inventory_service.service;

import com.example.inventory_service.dto.InventoryEvent;
import com.example.inventory_service.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-events", groupId = "inventory-group",
            properties = "spring.json.value.default.type=com.example.inventory_service.dto.OrderEvent")
    public void handleOrderCreated(OrderEvent event) {
        log.info("Kiểm tra tồn kho cho Order: {}", event.orderId());

        // Giả lập logic check DB: Luôn đủ hàng nếu số lượng < 100
        boolean inStock = event.quantity() < 100;

        String status = inStock ? "RESERVED" : "FAILED";
        log.info("Trạng thái tồn kho của Order {}: {}", event.orderId(), status);

        InventoryEvent inventoryEvent = new InventoryEvent(event.orderId(), status);
        kafkaTemplate.send("inventory-events", String.valueOf(event.orderId()), inventoryEvent);
    }
}