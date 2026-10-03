package com.example.payment_service.dto;

public record InventoryEvent(Long orderId, String status) {}