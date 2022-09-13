package com.mindhub.marketplace.controllers;


import com.mindhub.marketplace.dtos.PaymentDTO;
import com.mindhub.marketplace.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payment")
    public List<PaymentDTO> getAllPayments(){
        return paymentService.getAllPayments();
    }
}
