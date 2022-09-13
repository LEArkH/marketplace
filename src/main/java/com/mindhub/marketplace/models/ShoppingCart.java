package com.mindhub.marketplace.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private boolean active;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy="shoppingCart", fetch=FetchType.EAGER)
    Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy="shoppingCart", fetch=FetchType.EAGER)
    Set<PurchaseOrder> purchaseOrders = new HashSet<>();



    public ShoppingCart() {
    }

    public ShoppingCart(Client client) {
        this.client = client;
        this.active = true;
    }

    public void addInvoice(Invoice invoice){
        invoice.setShoppingCart(this);
        invoices.add(invoice);
    }

    public void addPurOrder(PurchaseOrder purchaseOrder){
        purchaseOrder.setShoppingCart(this);
        purchaseOrders.add(purchaseOrder);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public Set<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", client=" + client +
                ", invoices=" + invoices +
                ", purchaseOrders=" + purchaseOrders +
                '}';
    }
}
