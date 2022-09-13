package com.mindhub.marketplace.services;

import com.mindhub.marketplace.dtos.InvoiceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceServices {

    List<InvoiceDTO> getAllInvoices();

    ResponseEntity<Object> createInvoices(
            @RequestParam int payments, @RequestParam String typePayment, @RequestParam String numCard, @RequestParam LocalDate fechaVen, @RequestParam String cvv, Authentication authentication
    );
}
