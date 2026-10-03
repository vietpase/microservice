package com.example.notification_service.dto;

public record OrderEvent(Long orderId, String productId, Integer quantity) {}