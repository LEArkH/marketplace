package com.mindhub.marketplace.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private Double totalPrice;
    private LocalDate date;
    private int payments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shoppingCart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    public Invoice() {
    }

    public Invoice(Double totalPrice, LocalDate date, int payments, ShoppingCart shoppingCart, Payment payment) {
        this.totalPrice = totalPrice;
        this.date = date;
        this.payments = payments;
        this.shoppingCart = shoppingCart;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
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

    public void setPaymentMethod(int payments) {
        this.payments = payments;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                ", paymentMethod=" + payments +
                ", shoppingCart=" + shoppingCart +
                '}';
    }
}
