package com.mindhub.marketplace.services.implementServices;

import com.mindhub.marketplace.dtos.ClientDTO;
import com.mindhub.marketplace.dtos.ShoppingCartDTO;
import com.mindhub.marketplace.models.Client;
import com.mindhub.marketplace.models.ShoppingCart;
import com.mindhub.marketplace.repositories.ClientRepository;
import com.mindhub.marketplace.repositories.PaymentRepository;
import com.mindhub.marketplace.repositories.ProductRepository;
import com.mindhub.marketplace.repositories.ShoppingCartRepository;
import com.mindhub.marketplace.services.ShoppingCartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ShoppingCartImpl implements ShoppingCartServices {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ShoppingCartDTO> getAllShoppingCarts() {
        return shoppingCartRepository.findAll().stream().map(ShoppingCartDTO::new).collect(toList());
    }

    @Override
    public ResponseEntity<Object> createCart(Authentication authentication) {

        Client clientConected=clientRepository.findByEmail(authentication.getName());
        ShoppingCart shoppingCartNow = new ShoppingCart(clientConected);
        shoppingCartNow.setActive(true);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
