package com.example.payment_service.dto;

public record PaymentEvent(Long orderId, String status) {}