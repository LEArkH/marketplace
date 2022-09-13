package com.mindhub.marketplace.repositories;

import com.mindhub.marketplace.dtos.PaymentDTO;
import com.mindhub.marketplace.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Payment findByName(String typePayment);

}
