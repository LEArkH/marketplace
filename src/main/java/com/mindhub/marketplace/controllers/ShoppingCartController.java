package com.mindhub.marketplace.controllers;

import com.mindhub.marketplace.dtos.ClientDTO;
import com.mindhub.marketplace.dtos.ShoppingCartDTO;
import com.mindhub.marketplace.services.ShoppingCartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartServices shoppingCartServices;

    @GetMapping("/shoppingCarts")
    public List<ShoppingCartDTO> getAllShoppingCarts(){
        return shoppingCartServices.getAllShoppingCarts();
    }

    @PostMapping("/shoppingCarts")
    public ResponseEntity<Object> createCart(
            Authentication authentication) {
        return shoppingCartServices.createCart(authentication);
    }

}
