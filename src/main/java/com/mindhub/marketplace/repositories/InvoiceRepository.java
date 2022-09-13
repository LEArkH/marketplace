package com.mindhub.marketplace.repositories;

import com.mindhub.marketplace.models.Invoice;
import com.mindhub.marketplace.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Invoice findByShoppingCart(ShoppingCart shoppingCart);

}
