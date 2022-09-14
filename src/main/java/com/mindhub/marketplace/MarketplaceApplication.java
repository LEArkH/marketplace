package com.mindhub.marketplace;

import com.mindhub.marketplace.models.*;
import com.mindhub.marketplace.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MarketplaceApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, ProductRepository productRepository,
									  PurchaseOrderRepository purchaseOrderRepository,
									  ShoppingCartRepository shoppingCartRepository,
									  InvoiceRepository invoiceRepository,
									  PaymentRepository paymentRepository){
		return (args) -> {
			//Guardamos los datos en H2


			Client client1 = new Client("Homero", "Simpson", "homero@gmail.com", "Av. Siempre Viva 123", passwordEncoder.encode("1234"));
			clientRepository.save(client1);
			Client client2 = new Client("Mark", "Spector", "monknight@gmail.com", "Av. Siempre Viva 123", passwordEncoder.encode("1234"));
			clientRepository.save(client1);
			Client admin = new Client("Admin", "Blabla", "admin@gmail.com", "Av. Falsa 1414", passwordEncoder.encode("admin"));
			clientRepository.save(admin);

			Product product1 = new Product("Cerveza Duff", 200.00, 500);
			productRepository.save(product1);

			ShoppingCart cart1 = new ShoppingCart(client1);
			shoppingCartRepository.save(cart1);

			ShoppingCart cartAdmin = new ShoppingCart(admin);
			shoppingCartRepository.save(cartAdmin);

			PurchaseOrder order1= new PurchaseOrder(product1,3, 600.00, LocalDateTime.now(),cart1);
			purchaseOrderRepository.save(order1);

			List<Integer> paymentsDebitCard = List.of(1);
			List<Integer> paymentsCreditCard = List.of(1, 3, 6);
			List<Integer> paymentsCash = List.of(1);


			Payment payment1 = new Payment("Cash",paymentsCash);
			Payment payment2 = new Payment("Credit",paymentsCreditCard);
			Payment payment3 = new Payment("Debit",paymentsDebitCard);

			paymentRepository.save(payment1);
			paymentRepository.save(payment2);
			paymentRepository.save(payment3);
		};
	}
}
