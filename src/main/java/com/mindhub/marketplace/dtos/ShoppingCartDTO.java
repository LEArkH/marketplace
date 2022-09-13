package com.mindhub.marketplace.dtos;

import com.mindhub.marketplace.models.PurchaseOrder;
import com.mindhub.marketplace.models.ShoppingCart;

import java.util.Set;
import java.util.stream.Collectors;

public class ShoppingCartDTO {
    private Long idCompra;
    private Set<InvoiceDTO> invoices;

    private Set<PurchaseOrderDTO> compras;

    public ShoppingCartDTO(ShoppingCart shoppingCart) {
        this.idCompra = shoppingCart.getId();
        this.invoices = shoppingCart.getInvoices().stream().map(InvoiceDTO::new).collect(Collectors.toSet());
        this.compras = shoppingCart.getPurchaseOrders().stream().map(PurchaseOrderDTO::new).collect(Collectors.toSet());
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Set<PurchaseOrderDTO> getCompras() {
        return compras;
    }

    public void setCompras(Set<PurchaseOrderDTO> compras) {
        this.compras = compras;
    }

    public Set<InvoiceDTO> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<InvoiceDTO> invoices) {
        this.invoices = invoices;
    }

    public Set<PurchaseOrderDTO> getPurchaseOrders() {
        return compras;
    }

    public void setPurchaseOrders(Set<PurchaseOrderDTO> purchaseOrders) {
        this.compras = purchaseOrders;
    }
}
