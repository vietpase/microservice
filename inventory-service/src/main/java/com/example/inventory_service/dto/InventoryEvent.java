package com.example.inventory_service.dto;

public record InventoryEvent(Long orderId, String status) {}