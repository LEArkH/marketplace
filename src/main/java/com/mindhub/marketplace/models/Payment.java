package com.mindhub.marketplace.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String name;

    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    @OneToMany (mappedBy="payment", fetch=FetchType.EAGER)
    Set<Invoice> invoices = new HashSet<>();

    public Payment() {
    }

    public Payment(String name, List<Integer> payments) {
        this.name = name;
        this.payments = payments;
    }

    public void addInvoice(Invoice invoice) {
        invoice.setPayment(this);
        invoices.add(invoice);
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

    public void setPayment(List<Integer> payment) {
        this.payments = payment;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }
}
