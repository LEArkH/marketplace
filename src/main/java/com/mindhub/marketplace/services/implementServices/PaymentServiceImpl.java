package com.mindhub.marketplace.services.implementServices;

import com.mindhub.marketplace.dtos.PaymentDTO;
import com.mindhub.marketplace.repositories.PaymentRepository;
import com.mindhub.marketplace.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll().stream().map(PaymentDTO::new).collect(Collectors.toList());
    }
}
