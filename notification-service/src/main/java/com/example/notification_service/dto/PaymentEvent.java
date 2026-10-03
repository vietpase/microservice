package com.example.notification_service.dto;

public record PaymentEvent(Long orderId, String status) {}
