package com.example.api_rest_clientes.controller;


import com.example.api_rest_clientes.correo.GestorEmail;
import com.example.api_rest_clientes.exception.EmailAlreadyExistsException;
import com.example.api_rest_clientes.exception.PhoneAlreadyExistsException;
import com.example.api_rest_clientes.pojo.DetallesClientes;
import com.example.api_rest_clientes.pojo.Reservas;
import com.example.api_rest_clientes.repositories.ClientesRepository;
import com.example.api_rest_clientes.pojo.Clientes;
import com.example.api_rest_clientes.repositories.DetallesClientesRepository;
import com.example.api_rest_clientes.repositories.ReservasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api") // Añade un prefijo común para todas las rutas
public class ClientesController {
    private final ClientesRepository clientesRepository;
    private final DetallesClientesRepository detallesClientesRepository;
    private final ReservasRepository reservasRepository;


    private final Logger log = LoggerFactory.getLogger(ClientesController.class);

    @Autowired
    public ClientesController(ClientesRepository clientesRepository, DetallesClientesRepository detallesClientesRepository, ReservasRepository reservasRepository) {
        this.clientesRepository = clientesRepository;
        this.detallesClientesRepository = detallesClientesRepository;
        this.reservasRepository = reservasRepository;
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/clientes")
    public ResponseEntity<List<Clientes>> getClientes() {
        List<Clientes> al = clientesRepository.findAll();
        if (!al.isEmpty()) {
            return ResponseEntity.ok(al);
        } else {
            log.warn("No hay clientes en la base de datos");
            return ResponseEntity.noContent().build();
        }
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/clientes/{nombre}")
    public ResponseEntity<List<Clientes>> filtroClientes(@PathVariable String nombre){
        List<Clientes> al = clientesRepository.findAll();
        List<Clientes>alFinal = new ArrayList<>();
        for (Clientes cli:al) {
            if (cli.getName().toLowerCase().startsWith(nombre.toLowerCase())){
                alFinal.add(cli);
            }
        }
        if (!alFinal.isEmpty()){
            return ResponseEntity.ok(alFinal);
        }else {
            log.warn("No hay clientes con este nombre");
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @PostMapping("/register")
    public ResponseEntity<Clientes> guardarCliente(@RequestBody Clientes clientes) {
        GestorEmail gestorEmail = new GestorEmail();
        if (clientes.getId() != null) {
            return ResponseEntity.badRequest().build();
        } else if (clientesRepository.existsByMail(clientes.getMail())) {
            throw new EmailAlreadyExistsException("Ya existe el mail!!");
        } else if (clientesRepository.existsByPhone(clientes.getPhone())) {
            throw new PhoneAlreadyExistsException("Teléfono ya en uso!!");
        } else {
            clientesRepository.save(clientes);
            gestorEmail.mandarCorreo(clientes.getMail());
            return ResponseEntity.ok(clientes);
        }
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Clientes clientes) {
        List<Clientes> al = clientesRepository.findAll();
        for (Clientes cli : al) {
            if (cli.getMail().equals(clientes.getMail()) && cli.getPassword().equals(clientes.getPassword())) {
                Map<String, Object> response = new HashMap<>();
                log.warn("Si");
                response.put("clienteId", cli.getId());  // Asegúrate de que 'getId()' devuelva el ID del cliente
                response.put("message", "Login successful");

                return ResponseEntity.ok(response);
            }
        }
        log.warn("no");
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Correo electrónico no encontrado. Inténtalo de nuevo.");

        // Devolver la respuesta de error con código de estado 404 (Not Found)
        return ResponseEntity.notFound().build();
    }


    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/cliente/{id}")
    public ResponseEntity<Clientes> findById(@PathVariable Long id) {
        Optional<Clientes> cliente = clientesRepository.findById(id);
        if (cliente.isPresent()) {
            log.warn("Yes client");
            return ResponseEntity.ok(cliente.get());
        } else {
            log.warn("No client");
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @PutMapping("/cliente/{id}/{nombre}/{apellido}")
    public ResponseEntity<Clientes>cambiarNombre(@PathVariable Long id, @PathVariable String nombre, @PathVariable String apellido){
        Optional<Clientes> cliente = clientesRepository.findById(id);
        if (cliente.isPresent()) {
            cliente.get().setName(nombre);
            cliente.get().setSurname(apellido);
            clientesRepository.save(cliente.get());
            return ResponseEntity.ok(cliente.get());
        } else {
            log.warn("No client");
            return ResponseEntity.notFound().build();
        }
    }


    //Todas las cosas eliminadas relativas a user
    @CrossOrigin("http://127.0.0.1:3000")
    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Clientes>deleteById(@PathVariable Long id){
        if (clientesRepository.existsById(id)){
            List<Reservas>alRe = reservasRepository.findAll();
            List<DetallesClientes>alDe = detallesClientesRepository.findAll();
            for (Reservas re:alRe) {
                if (re.getIdCliente() == id){
                    reservasRepository.delete(re);
                }
            }
            for (DetallesClientes de:alDe) {
                if (de.getIdCliente() == id){
                    detallesClientesRepository.delete(de);
                }
            }
            clientesRepository.deleteById(id);
            return ResponseEntity.accepted().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}