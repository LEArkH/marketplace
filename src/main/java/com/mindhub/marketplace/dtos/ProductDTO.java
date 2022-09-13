package com.mindhub.marketplace.dtos;

import com.mindhub.marketplace.models.Product;

import java.util.Set;
import java.util.stream.Collectors;

public class ProductDTO {
    private Long id;
    private String name;
    private Double amount;
    private int stock;
    private Set<PurchaseOrderDTO> purchaseOrders;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.amount= product.getAmount();
        this.stock = product.getStock();
        this.purchaseOrders = product.getPurchaseOrders().stream().map(PurchaseOrderDTO::new).collect(Collectors.toSet());
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

    public Set<PurchaseOrderDTO> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(Set<PurchaseOrderDTO> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }
}
