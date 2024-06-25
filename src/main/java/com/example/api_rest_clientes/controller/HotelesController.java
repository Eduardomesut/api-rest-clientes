package com.example.api_rest_clientes.controller;


import com.example.api_rest_clientes.pojo.Hoteles;
import com.example.api_rest_clientes.repositories.HotelesRepository;
import com.example.api_rest_clientes.repositories.ReservasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api") // Añade un prefijo común para todas las rutas
public class HotelesController {
    private final HotelesRepository hotelesRepository;
    private final Logger log = LoggerFactory.getLogger(HotelesController.class);
    @Autowired
    public HotelesController(HotelesRepository hotelesRepository) {
        this.hotelesRepository = hotelesRepository;
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/hoteles")
    public ResponseEntity<List<Hoteles>> listaHoteles(){
        List<Hoteles>al = this.hotelesRepository.findAll();
        if (!al.isEmpty()){
            return ResponseEntity.ok(al);
        }else{
            log.warn("No hay hoteles registrados");
            return ResponseEntity.noContent().build();
        }

    }
    @CrossOrigin("http://127.0.0.1:3000")
    @PostMapping("/hoteles")
    public ResponseEntity<Hoteles> crearHotel(@RequestBody Hoteles hotel){
        if (hotel.getIdhotel() == null && hotel.getNombre() != null){
            this.hotelesRepository.save(hotel);
            return ResponseEntity.ok(hotel);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/hoteles/{nombre}")
    public ResponseEntity<List<Hoteles>> filtroHoteles(@PathVariable String nombre){
        List<Hoteles>al = this.hotelesRepository.findAll();
        List<Hoteles>alFinal = new ArrayList<>();
        for (Hoteles hotel:al) {
            if ((hotel.getNombre().toLowerCase().startsWith(nombre.toLowerCase())) || hotel.getUbicacion().toLowerCase().startsWith(nombre.toLowerCase())){
                alFinal.add(hotel);
            }
        }
        if (!alFinal.isEmpty()){
            return ResponseEntity.ok(alFinal);
        }else{
            log.warn("No hay hoteles con estos criterios");
            return ResponseEntity.noContent().build();
        }
    }

}
