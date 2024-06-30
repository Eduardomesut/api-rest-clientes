package com.example.api_rest_clientes.controller;

import com.example.api_rest_clientes.pojo.Clientes;
import com.example.api_rest_clientes.pojo.DetallesClientes;
import com.example.api_rest_clientes.repositories.ClientesRepository;
import com.example.api_rest_clientes.repositories.DetallesClientesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DetallesClientesController {
    private final DetallesClientesRepository detallesClientesRepository;
    private final ClientesRepository clientesRepository;
    private final Logger log = LoggerFactory.getLogger(DetallesClientesController.class);
    @Autowired
    public DetallesClientesController(DetallesClientesRepository detallesClientesRepository, ClientesRepository clientesRepository) {
        this.detallesClientesRepository = detallesClientesRepository;
        this.clientesRepository = clientesRepository;
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/detalles/{id}")
    public ResponseEntity<DetallesClientes> detallesporId(@PathVariable Long id){
        if (detallesClientesRepository.existsById(id)){
            Optional<DetallesClientes>detalles = detallesClientesRepository.findById(id);
            return ResponseEntity.ok(detalles.get());

        }else{
            log.warn("No existe el cliente");
            return ResponseEntity.notFound().build();
        }

    }
    @CrossOrigin("http://127.0.0.1:3000")
    @PostMapping("/detalles/{correo}")
    public ResponseEntity<DetallesClientes> nuevosDetalles(@PathVariable String correo){
        List<Clientes> al = clientesRepository.findAll();
        Long id = null;
        for (Clientes cli:al) {
            if (cli.getMail().equals(correo)){
                id = cli.getId();
            }
        }
        if (id == null){
            return ResponseEntity.badRequest().build();
        }else {
            DetallesClientes nuevo = new DetallesClientes(null, id, 0, 0.0);
            detallesClientesRepository.save(nuevo);
            return ResponseEntity.ok(nuevo);
        }
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @PutMapping("/detalles/{id}/{nuevoSaldo}")
    public ResponseEntity<DetallesClientes> cambiarSaldo(@PathVariable Long id, @PathVariable Double nuevoSaldo) {
        Optional<DetallesClientes> detallesOpt = detallesClientesRepository.findById(id);
        if (detallesOpt.isPresent()) {
            DetallesClientes detalles = detallesOpt.get();
            if (detalles.getSaldo() + nuevoSaldo > 10000){
                log.warn("Demasiado dinero en saldo");
                return ResponseEntity.badRequest().build();
            }else{
                detalles.setSaldo(detalles.getSaldo() + nuevoSaldo);
                detallesClientesRepository.save(detalles);
                return ResponseEntity.ok(detalles);
            }

        } else {
            log.warn("No existe el cliente con id: " + id);
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @PutMapping("/puntos/{id}/{nuevoPuntos}")
    public ResponseEntity<DetallesClientes> cambiarPuntos(@PathVariable Long id, @PathVariable Integer nuevoPuntos) {
        Optional<DetallesClientes> detallesOpt = detallesClientesRepository.findById(id);
        if (detallesOpt.isPresent()) {
            DetallesClientes detalles = detallesOpt.get();
            if (detalles.getSaldo() + nuevoPuntos > 1000000){
                log.warn("Demasiado dinero en puntos");
                return ResponseEntity.badRequest().build();
            }else{
                detalles.setPuntosNH(detalles.getPuntosNH() + nuevoPuntos);
                detallesClientesRepository.save(detalles);
                return ResponseEntity.ok(detalles);
            }

        } else {
            log.warn("No existe el cliente con id: " + id);
            return ResponseEntity.notFound().build();
        }
    }
}
