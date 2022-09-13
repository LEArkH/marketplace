package com.mindhub.marketplace.controllers;

import com.mindhub.marketplace.dtos.ClientDTO;
import com.mindhub.marketplace.dtos.PatchProductDTO;
import com.mindhub.marketplace.dtos.ProductDTO;
import com.mindhub.marketplace.models.Product;
import com.mindhub.marketplace.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        return productServices.getAllProducts();
    }

    @PostMapping("/products")
    public ResponseEntity<Object> createProduct(
            @RequestParam String name, @RequestParam double price, //recibo estos parametros por el formulario de frontend
            @RequestParam int stock) {
        return productServices.createProduct(name, price, stock);
    }

    @PutMapping("/products/update/{id}") //si son metodos diferentes puedo ponerle la misma ruta
                                                            //Lo pongo para identificar a que parametro se refiere si tengo mas de uno en la ruta, ej /{nombre}
    public ResponseEntity<Object> updateProduct(@PathVariable("id")Long id, @RequestParam String name, @RequestParam double price, @RequestParam int stock) {
        return productServices.updateProduct(id, name, price, stock); //PathVariable se usa para los id

    }

    @PatchMapping("/products/update/{id}")
    public ResponseEntity<Object> patchProduct(@RequestBody PatchProductDTO patchProductDTO,  @PathVariable ("id") Long id){
        return productServices.patchProduct(id, patchProductDTO); //con requestBody mandamos un objeto, va a usar un solo dato y va a construir un objeto con un solo dato, por ende necesita un contructor para cada atributo
    }

    @DeleteMapping("products/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id")Long id){
        return productServices.deleteProduct(id);
    }

}