package com.mindhub.marketplace.services.implementServices;

import com.mindhub.marketplace.dtos.PatchPurchaseItemDTO;
import com.mindhub.marketplace.dtos.PurchaseOrderDTO;
import com.mindhub.marketplace.models.*;
import com.mindhub.marketplace.repositories.*;
import com.mindhub.marketplace.services.PurchaseOrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class PurchaseOrderImpl implements PurchaseOrderServices {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<PurchaseOrderDTO> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll().stream().map(PurchaseOrderDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> createPurchaseOrder(String nameProduct, int cant, Authentication authentication) {

        Client clientConected=clientRepository.findByEmail(authentication.getName());
        Product product= productRepository.findByName(nameProduct);
        ShoppingCart shoppingCartNow= shoppingCartRepository.findByClientAndActive(clientConected,true);

        if (shoppingCartNow == null){
            return new ResponseEntity<>("No existe el carrito", HttpStatus.FORBIDDEN);
        }

        if (nameProduct.isEmpty() || cant==0) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (product ==  null) {
            return new ResponseEntity<>("No vendemos ese producto", HttpStatus.FORBIDDEN);
        }

        if (product.getStock() <  cant) {
            return new ResponseEntity<>("No hay suficiente stock para ese pedido", HttpStatus.FORBIDDEN);
        }

        //Crear PurchaseOrder
        PurchaseOrder purchaseOrder=new PurchaseOrder(product,cant,product.getAmount()*cant, LocalDateTime.now(),shoppingCartNow);
        purchaseOrderRepository.save(purchaseOrder);

        int stock = product.getStock()-cant;
        product.setStock(stock);
        productRepository.save(product);



        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> deleteItem(Long id) {
        PurchaseOrder itemABorrar = purchaseOrderRepository.findById(id).orElse(null);
        if (itemABorrar == null) {
            return new ResponseEntity<>("El item no está en el carrito", HttpStatus.FORBIDDEN);

        }

        purchaseOrderRepository.deleteById(itemABorrar.getId());

        return new ResponseEntity<>("Item eliminado del carrito", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> patchItem(Long id, PatchPurchaseItemDTO patchPurchaseItemDTO) {
        PurchaseOrder itemActualizar = purchaseOrderRepository.findById(id).orElse(null);
        Product producto = productRepository.findById(itemActualizar.getProduct().getId()).orElse(null);
        double precioActualizado = 0;
        if(itemActualizar == null) {
            return new ResponseEntity<>("No encontrado", HttpStatus.FORBIDDEN);
        }
        if (patchPurchaseItemDTO.getQuantity() != 0){
            itemActualizar.setQuantity(patchPurchaseItemDTO.getQuantity());
            precioActualizado = itemActualizar.getProduct().getAmount() * itemActualizar.getQuantity();
            itemActualizar.setPrice(precioActualizado);
            producto.setStock(producto.getStock() - itemActualizar.getQuantity());
        }
        productRepository.save(producto);
        purchaseOrderRepository.save(itemActualizar);

        return new ResponseEntity<>("Cantidad del item actualizado", HttpStatus.CREATED);
    }
}

