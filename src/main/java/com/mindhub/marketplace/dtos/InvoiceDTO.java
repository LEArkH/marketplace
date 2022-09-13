package com.mindhub.marketplace.dtos;

import com.mindhub.marketplace.models.Invoice;
import com.mindhub.marketplace.models.Payment;

import javax.persistence.ElementCollection;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class InvoiceDTO {

    private Long ticket;
    private String totalPrice;
    private LocalDate date;
    private String namePayment;
    private int payments;


    public InvoiceDTO(Invoice invoice) {
        this.ticket = invoice.getId();
        this.totalPrice = "$"+invoice.getTotalPrice();
        this.date = invoice.getDate();
        this.payments = invoice.getPayments();
        this.namePayment = invoice.getPayment().getName();
    }

    public Long getTicket() {
        return ticket;
    }

    public void setTicket(Long id) {
        this.ticket = id;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getNamePayment() {
        return namePayment;
    }

    public void setNamePayment(String namePayment) {
        this.namePayment = namePayment;
    }
}
