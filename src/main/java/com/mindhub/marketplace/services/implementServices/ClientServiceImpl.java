package com.mindhub.marketplace.services.implementServices;

import com.mindhub.marketplace.dtos.ClientDTO;
import com.mindhub.marketplace.dtos.PatchClientDTO;
import com.mindhub.marketplace.models.Client;
import com.mindhub.marketplace.models.Invoice;
import com.mindhub.marketplace.models.PurchaseOrder;
import com.mindhub.marketplace.models.ShoppingCart;
import com.mindhub.marketplace.repositories.ClientRepository;
import com.mindhub.marketplace.repositories.InvoiceRepository;
import com.mindhub.marketplace.repositories.PurchaseOrderRepository;
import com.mindhub.marketplace.repositories.ShoppingCartRepository;
import com.mindhub.marketplace.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImpl implements ClientServices {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }
    @Override
    public ClientDTO getClientById(Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }
    @Override
    public ClientDTO getClient(Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(client);
    }
    @Override
    public ResponseEntity<Object> register(String firstName, String lastName, String email, String address, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client1 = new Client (firstName, lastName, email, address, passwordEncoder.encode(password));
        clientRepository.save(client1);

        ShoppingCart newShoppingCart = new ShoppingCart(client1);
        shoppingCartRepository.save(newShoppingCart);


        return new ResponseEntity<>(HttpStatus.CREATED); //si pasó los anteriores requisitos, lo crea, lo guarda y envia un mensaje 201 creado

    }

    @Override
    public ResponseEntity<Object> patchClient(Long id, PatchClientDTO patchClientDTO) {
        Client clienteActualizar = clientRepository.findById(id).orElse(null);
        if (clienteActualizar == null) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.FORBIDDEN);
        } else {
            if (patchClientDTO.getFirstName() != null) {
                clienteActualizar.setFirstName(patchClientDTO.getFirstName());
            }

            if (patchClientDTO.getLastName() != null) {
                clienteActualizar.setLastName(patchClientDTO.getLastName());
            }

            if (patchClientDTO.getEmail() != null) {
                clienteActualizar.setEmail(patchClientDTO.getEmail());
            }

            if (patchClientDTO.getAddress() != null) {
                clienteActualizar.setAddress(patchClientDTO.getAddress());
            }

            if (patchClientDTO.getPassword() != null) {
                clienteActualizar.setPassword(patchClientDTO.getPassword());
            }

            this.clientRepository.save(clienteActualizar);
            return new ResponseEntity<>("Cliente actualizado", HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<Object> deleteClient(Long id) {
        Client clienteABorrar = clientRepository.findById(id).orElse(null);
        if (clienteABorrar == null) {
            return new ResponseEntity<>("El cliente indicado no existe", HttpStatus.FORBIDDEN);
        } else {
            ShoppingCart shoppingCartAsociado = shoppingCartRepository.findById(id).orElse(null);
            Set<Invoice> invoiceSet = shoppingCartAsociado.getInvoices();
            invoiceRepository.deleteAll(invoiceSet);
            Set<PurchaseOrder> purchaseOrderSet = shoppingCartAsociado.getPurchaseOrders();
            purchaseOrderRepository.deleteAll(purchaseOrderSet);
            Set<ShoppingCart> shoppingCartSet = clienteABorrar.getShoppingCarts();
            shoppingCartRepository.deleteAll(shoppingCartSet);
            //Para borrar un objeto, tenes que borrar los que tiene asociados, y los que tienen asociados esos tambien, sino van quedando vacíos (ej un carrito sin cliente no puede existir)


            clientRepository.deleteById(clienteABorrar.getId());
            return new ResponseEntity<>("Cliente eliminado", HttpStatus.OK);
        }
    }

}
