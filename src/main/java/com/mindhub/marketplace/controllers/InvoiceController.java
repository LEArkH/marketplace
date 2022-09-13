package com.mindhub.marketplace.controllers;


import com.mindhub.marketplace.dtos.InvoiceDTO;
import com.mindhub.marketplace.services.InvoiceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceServices invoiceServices;

    @GetMapping("/invoices")
    public List<InvoiceDTO> getAllInvoices(){
        return invoiceServices.getAllInvoices();
    }

    @PostMapping("/pay")
    public ResponseEntity<Object> createInvoices(
            @RequestParam int payments, @RequestParam String typePayment, @RequestParam String numCard, @RequestParam String fechaVen, @RequestParam String cvv, Authentication authentication
    ) {
        LocalDate thruDate = LocalDate.parse(fechaVen);
        return invoiceServices.createInvoices(payments, typePayment,numCard,thruDate,cvv, authentication);
    }
}
