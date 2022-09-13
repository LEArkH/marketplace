package com.mindhub.marketplace.services.implementServices;

import com.mindhub.marketplace.dtos.ClientDTO;
import com.mindhub.marketplace.dtos.PatchProductDTO;
import com.mindhub.marketplace.dtos.ProductDTO;
import com.mindhub.marketplace.models.Client;
import com.mindhub.marketplace.models.Product;
import com.mindhub.marketplace.models.PurchaseOrder;
import com.mindhub.marketplace.repositories.ClientRepository;
import com.mindhub.marketplace.repositories.ProductRepository;
import com.mindhub.marketplace.repositories.PurchaseOrderRepository;
import com.mindhub.marketplace.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class ProductServiceImpl implements ProductServices {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductDTO::new).collect(toList());
    }

    @Override
    public ResponseEntity<Object> createProduct(String name, double price, int stock) {
        if (name.isEmpty() || price == 0 || stock == 0) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (productRepository.findByName(name) != null) {
            return new ResponseEntity<>("Ya existe ese producto", HttpStatus.FORBIDDEN);
        }

        Product product = new Product(name, price, stock);
        productRepository.save(product);

        return new ResponseEntity<>(HttpStatus.CREATED); //si pasó los anteriores requisitos, lo crea, lo guarda y envia un mensaje 201 creado
    }


    //Si uso el metodo post, me va a crear algo nuevo si o si, en cambio con put actualizo los que yo quiera, de a uno o varios
    @Override //Buscamos el prod por ID, no por nombre
    public ResponseEntity<Object> updateProduct(Long id, String name, double price, int stock) { //todo lo que estamos ingresando
        Optional<Product> productoActualizado = productRepository.findById(id); //no pongo el orElsenull (si pongo el orElseNull saco el optional)
        if (productoActualizado == null) {
            return new ResponseEntity<>("No existe ese producto", HttpStatus.FORBIDDEN);
        }
        productoActualizado.get().setName(name);
        productoActualizado.get().setAmount(price);
        int newStock = productoActualizado.get().getStock() + stock;
        productoActualizado.get().setStock(newStock);
        productRepository.save(productoActualizado.get());

        return new ResponseEntity<>("Producto actualizado", HttpStatus.CREATED);


    }

    @Override
    public ResponseEntity<Object> deleteProduct(Long id) {
        Product productoABorrar = productRepository.findById(id).orElse(null); //primero verificar que el prod a borrar no sea nulo
       // Set<PurchaseOrder> purchaseOrdersList = productoABorrar.getPurchaseOrders(); //La lista está ordenada con objs repetidos; el set viene desordenado y sin repetir datos
        if (productoABorrar == null) {
            return new ResponseEntity<>("No existe", HttpStatus.FORBIDDEN);

        }
        //Primero borrar el asociado:
        Set<PurchaseOrder> purchaseOrdersList = productoABorrar.getPurchaseOrders(); //La lista está ordenada con objs repetidos; el set viene desordenado y sin repetir datos
        purchaseOrderRepository.deleteAll(purchaseOrdersList);
        productRepository.deleteById(productoABorrar.getId());

        return new ResponseEntity<>("Producto borrado", HttpStatus.CREATED);

    }

    @Override
    //Lo pruebo en postman en Body y en formato JSON ( entre corchetes el atributo)
    public ResponseEntity<Object> patchProduct(Long id, PatchProductDTO patchProductDTO) {
        Product productoaActualizar = productRepository.findById(id).orElse(null);

        if(productoaActualizar == null) {
            return new ResponseEntity<>("No encontrado", HttpStatus.FORBIDDEN);
        }
        if (patchProductDTO.getAmount() != null){
            productoaActualizar.setAmount(patchProductDTO.getAmount());

        }
        if (patchProductDTO.getStock() != 0) {
            productoaActualizar.setStock(patchProductDTO.getStock());
        }

        if (patchProductDTO.getName() != null){
            productoaActualizar.setName(patchProductDTO.getName());

        }
        productRepository.save(productoaActualizar);
        return new ResponseEntity<>("Producto actualizado", HttpStatus.CREATED);
    }
}
