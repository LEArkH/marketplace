package com.mindhub.marketplace.repositories;

import com.mindhub.marketplace.models.PurchaseOrder;
import com.mindhub.marketplace.models.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RepositoryRestResource
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findByShoppingCart(ShoppingCart shoppingCart);

}
