package com.mindhub.marketplace.repositories;

import com.mindhub.marketplace.dtos.ProductDTO;
import com.mindhub.marketplace.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);

}
