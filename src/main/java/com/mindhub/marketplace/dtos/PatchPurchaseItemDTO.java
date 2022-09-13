package com.mindhub.marketplace.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatchPurchaseItemDTO {
    private Long id;
    private int quantity;
    private LocalDate date;


    public PatchPurchaseItemDTO(Long id, int quantity, Double price, LocalDate date) {
        this.id = id;
        this.quantity = quantity;
        this.date = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
