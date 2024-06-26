package com.example.api_rest_clientes.controller;

import com.example.api_rest_clientes.pojo.Habitaciones;
import com.example.api_rest_clientes.pojo.Hoteles;
import com.example.api_rest_clientes.repositories.HabitacionesRepository;
import com.example.api_rest_clientes.repositories.HotelesRepository;
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
public class HabitacionesController {
    private final HotelesRepository hotelesRepository;
    private final HabitacionesRepository habitacionesRepository;

    private final Logger log = LoggerFactory.getLogger(HabitacionesController.class);
    @Autowired
    public HabitacionesController(HabitacionesRepository habitacionesRepository, HotelesRepository hotelesRepository) {
        this.habitacionesRepository = habitacionesRepository;
        this.hotelesRepository = hotelesRepository;
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @PostMapping("/habitaciones")
    public ResponseEntity<Habitaciones>addHabitacion(@RequestBody Habitaciones habitaciones){
        if (habitaciones.getIdHabitacion() == null){
            habitacionesRepository.save(habitaciones);
            return ResponseEntity.ok(habitaciones);
        }else{
            log.warn("Habitación no valida");
            return ResponseEntity.badRequest().build();
        }
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/habitaciones")
    public ResponseEntity<List<Habitaciones>> verHabitaciones(){
        List<Habitaciones>al = habitacionesRepository.findAll();
        if (!al.isEmpty()){
            return ResponseEntity.ok(al);
        }else {
            log.warn("No hay habitaciones");
            return ResponseEntity.noContent().build();
        }

    }
    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/habitaciones/{hotelId}")
    public ResponseEntity<List<Habitaciones>>habitacionesPorHotel(@PathVariable Long hotelId){
        List<Habitaciones>al = habitacionesRepository.findAll();
        List<Habitaciones>alFinal = new ArrayList<>();
        for (Habitaciones hab:al) {
            if (hab.getHotel_id().equals(hotelId)){
                alFinal.add(hab);
            }
        }
        if (!alFinal.isEmpty()){
            return ResponseEntity.ok(alFinal);
        }else{
            log.warn("No hay habitaciones en este hotel");
            return ResponseEntity.noContent().build();
        }
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/busqueda/{numHab}")
    public ResponseEntity<String>nombreHotel(@PathVariable String numHab){
       List<Habitaciones> al = habitacionesRepository.findAll();
       Long idHotel = -1L;
        for (Habitaciones hab:al) {
            if (hab.getNumHabitacion().equals(numHab)){
                idHotel = hab.getHotel_id();
            }
        }
        Optional<Hoteles> busqueda = hotelesRepository.findById(idHotel);
        if (busqueda.isPresent()){
            return ResponseEntity.ok(busqueda.get().getNombre());
        }else{
            return ResponseEntity.notFound().build();
        }

    }
}

