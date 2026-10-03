package com.example.inventory_service.dto;

public record OrderEvent(Long orderId, String productId, Integer quantity) {}