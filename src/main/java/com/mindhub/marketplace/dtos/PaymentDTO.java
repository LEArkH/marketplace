package com.mindhub.marketplace.dtos;

import com.mindhub.marketplace.models.Payment;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PaymentDTO {

    private Long id;
    private String name;
    private List<Integer> payments;

    private Set<InvoiceDTO> invoices;

    public PaymentDTO(Payment payment) {
        this.id = payment.getId();
        this.name = payment.getName();
        this.payments = payment.getPayments();
        this.invoices = payment.getInvoices().stream().map(InvoiceDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }
}
