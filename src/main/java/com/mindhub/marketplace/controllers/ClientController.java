package com.mindhub.marketplace.controllers;

import com.mindhub.marketplace.dtos.ClientDTO;
import com.mindhub.marketplace.dtos.PatchClientDTO;
import com.mindhub.marketplace.models.Client;
import com.mindhub.marketplace.repositories.ClientRepository;
import com.mindhub.marketplace.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientServices clientServices;

    @GetMapping("/clients")
    public List<ClientDTO> getAllClients(){
        return clientServices.getAllClients();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        return clientServices.getClientById(id);
    }

    @GetMapping("/clients/current") //Para que ya no me muestre el "1" sino el actual, la info del cliente con el que me loguee. Por mas que aparezca el nombre del cliente que logueo, la info va a ser del otro.
    public ClientDTO getClient(Authentication authentication){
        return clientServices.getClient(authentication);
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName, //recibo estos parametros por el formulario de frontend
            @RequestParam String email, @RequestParam String address, @RequestParam String password) {
        return clientServices.register(firstName, lastName, email, address, password);
    }

    @PatchMapping({"/clients/update/{id}"})
    public ResponseEntity<Object> patchClient(@PathVariable("id") Long id, @RequestBody PatchClientDTO patchClientDTO) {
        return this.clientServices.patchClient(id, patchClientDTO);
    }

    @DeleteMapping({"/clients/delete/{id}"})
    public ResponseEntity<Object> deleteClient(@PathVariable("id") Long id) {
        return this.clientServices.deleteClient(id);
    }

}
