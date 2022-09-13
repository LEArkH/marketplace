package com.mindhub.marketplace.dtos;

public class PatchProductDTO {
    private Long id;
    private String name;
    private Double amount;
    private int stock;

    public PatchProductDTO(Long id, String name, Double amount, int stock) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.stock = stock;
    }


/*

    public PatchProductDTO(String name) {
        this.name = name;
    }

    public PatchProductDTO(Double amount) {
        this.amount = amount;
    }

    public PatchProductDTO(int stock) {
        this.stock = stock;
    }

    public PatchProductDTO(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    public PatchProductDTO(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public PatchProductDTO(Double amount, int stock) {
        this.amount = amount;
        this.stock = stock;
    }
*/


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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
