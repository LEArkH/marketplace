package com.mindhub.marketplace.services.implementServices;


import com.mindhub.marketplace.dtos.InvoiceDTO;
import com.mindhub.marketplace.models.*;
import com.mindhub.marketplace.repositories.*;
import com.mindhub.marketplace.services.InvoiceServices;
import com.mindhub.marketplace.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class InvoiceServiceImpl implements InvoiceServices {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private MailService mailService;

    @Override
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream().map(InvoiceDTO::new).collect(toList());
    }

    @Transactional
    @Override
    public ResponseEntity<Object> createInvoices(int payments, String typePayment, String numCard, LocalDate fechaVen, String cvv, Authentication authentication) {

        Client clientConected=clientRepository.findByEmail(authentication.getName());
        ShoppingCart shoppingCartNow= shoppingCartRepository.findByClientAndActive(clientConected,true);
        Payment paymentType= paymentRepository.findByName(typePayment);

        if (shoppingCartNow == null){
            return new ResponseEntity<>("No existe el carrito", HttpStatus.FORBIDDEN);
        }

        if (payments==0 || typePayment.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (shoppingCartNow.getPurchaseOrders().isEmpty()){
            return new ResponseEntity<>("No hay ninguna compra", HttpStatus.FORBIDDEN);
        }

        if (paymentType == null){
            return new ResponseEntity<>("No existe el medio de pago", HttpStatus.FORBIDDEN);
        }

        if (numCard.length()!=16){
            return new ResponseEntity<>("El número de la tarjeta no es válido", HttpStatus.FORBIDDEN);
        }

        if (cvv.length()!=3){
            return new ResponseEntity<>("El número de seguridad no es válido", HttpStatus.FORBIDDEN);
        }

        if (fechaVen.isBefore(LocalDate.now())){
            return new ResponseEntity<>("La tarjeta ya expiró", HttpStatus.FORBIDDEN);
        }

        if (!paymentType.getPayments().contains(payments)){
            return new ResponseEntity<>("La cantidad de cuotas no es valida para ese medio de pago", HttpStatus.FORBIDDEN);
        }

        List<PurchaseOrder> purchaseOrders= purchaseOrderRepository.findByShoppingCart(shoppingCartNow);
        Double totalPrice = 0.0;
        for (int x=0;x<shoppingCartNow.getPurchaseOrders().size();x++){
            totalPrice+=purchaseOrders.get(x).getPrice();
        }

        Invoice invoice = new Invoice(totalPrice, LocalDate.now(),payments,shoppingCartNow,paymentType);
        invoiceRepository.save(invoice);

        shoppingCartNow.setActive(false);
        shoppingCartRepository.save(shoppingCartNow);
        ShoppingCart newShoppingCart = new ShoppingCart(clientConected);
        shoppingCartRepository.save(newShoppingCart);

        String message="Tu compra se realizó exitosamente" + "\n" + "N° compra: "+invoice.getId() + "\n" + "Monto total: "+invoice.getTotalPrice() + "\n" + "Tipo de pago: " + invoice.getPayment().getName() + ". En: "+invoice.getPayments()+" cuotas." + "\n" + "Compraste: "+shoppingCartNow.getPurchaseOrders().stream().map(purchaseOrder -> purchaseOrder.getProduct().getName()).collect(Collectors.toSet());
        mailService.sendMail("cositosinc1@gmail.com", clientConected.getEmail(),"Compra realizada :)", message);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
