package com.mindhub.marketplace.repositories;


import com.mindhub.marketplace.models.Client;
import com.mindhub.marketplace.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    //ShoppingCart findByClient(Client client);

    ShoppingCart findByClientAndActive(Client client, Boolean active);

}
