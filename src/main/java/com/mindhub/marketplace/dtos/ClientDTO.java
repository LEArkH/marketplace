package com.mindhub.marketplace.dtos;

import com.mindhub.marketplace.models.Client;
import com.mindhub.marketplace.models.ShoppingCart;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long idClient;
    private String firstName;
    private String lastName;
    private String email;
    private String address;

    private ShoppingCartDTO shoppingCart;
    private Set<ShoppingCartDTO> purchaseHistory;

    public ClientDTO(Client client) {
        this.idClient = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.address = client.getAddress();
        this.shoppingCart = client.getShoppingCarts().stream().filter(shoppingCart -> shoppingCart.isActive()).map(ShoppingCartDTO::new).collect(Collectors.toList()).get(0);
        this.purchaseHistory = client.getShoppingCarts().stream().filter(shoppingCart -> !shoppingCart.isActive()).map(ShoppingCartDTO::new).collect(Collectors.toSet());
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long id) {
        this.idClient = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<ShoppingCartDTO> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(Set<ShoppingCartDTO> shoppingCarts) {
        this.purchaseHistory = shoppingCarts;
    }

    public ShoppingCartDTO getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCartDTO shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
