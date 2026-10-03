package com.example.order_service.dto;

public record OrderEvent(Long orderId, String productId, Integer quantity) {}