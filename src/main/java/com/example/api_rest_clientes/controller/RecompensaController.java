package com.example.api_rest_clientes.controller;

import com.example.api_rest_clientes.pojo.Recompensa;
import com.example.api_rest_clientes.repositories.RecompensaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RecompensaController {

    private final RecompensaRepository recompensaRepository;
    private final Logger log = LoggerFactory.getLogger(DetallesClientesController.class);
    @Autowired
    public RecompensaController(RecompensaRepository recompensaRepository) {
        this.recompensaRepository = recompensaRepository;
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @PostMapping("/recompensas")
    public ResponseEntity<Recompensa>guardarRecompensa(@RequestBody Recompensa recompensa){
        if (recompensa.getId() == null){
            recompensaRepository.save(recompensa);
            return ResponseEntity.ok(recompensa);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/recompensas")
    public ResponseEntity<List<Recompensa>> listaRecompensas(){
        List<Recompensa> al = new ArrayList<>();
        if (recompensaRepository.count() > 0){
            al = recompensaRepository.findAll();
            return ResponseEntity.ok(al);
        }else{
            log.warn("No hay recompensas");
            return ResponseEntity.noContent().build();
        }
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/recompensas/{id}")
    public ResponseEntity<Recompensa> elegirRecompensa (@PathVariable Long id){
        Optional<Recompensa>elegida;
        if (recompensaRepository.existsById(id)){
            elegida = recompensaRepository.findById(id);
            return ResponseEntity.ok(elegida.get());
        }else {
            log.warn("Recompensa no encontrada");
            return ResponseEntity.notFound().build();
        }
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @PutMapping("/recompensas/{id}")
    public ResponseEntity<Recompensa> comprarRecompensa (@PathVariable Long id){
        Optional<Recompensa>elegida;
        if (recompensaRepository.existsById(id)){
            elegida = recompensaRepository.findById(id);
            elegida.get().setStock(elegida.get().getStock() - 1);
            recompensaRepository.save(elegida.get());
            return ResponseEntity.ok(elegida.get());
        }else {
            log.warn("Recompensa no encontrada");
            return ResponseEntity.notFound().build();
        }
    }
}
