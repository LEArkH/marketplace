package com.mindhub.marketplace.services;


import com.mindhub.marketplace.dtos.ShoppingCartDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ShoppingCartServices {

    List<ShoppingCartDTO> getAllShoppingCarts();

    ResponseEntity<Object> createCart(Authentication authentication);

}
