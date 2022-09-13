package com.mindhub.marketplace.services;

import com.mindhub.marketplace.dtos.PaymentDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getAllPayments();
}
