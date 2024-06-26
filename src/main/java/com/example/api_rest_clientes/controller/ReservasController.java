package com.example.api_rest_clientes.controller;

import com.example.api_rest_clientes.pojo.Reservas;
import com.example.api_rest_clientes.repositories.ReservasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api") // Añade un prefijo común para todas las rutas
public class ReservasController {

    private final ReservasRepository reservasRepository;
    private final Logger log = LoggerFactory.getLogger(ReservasController.class);
    @Autowired
    public ReservasController(ReservasRepository reservasRepository) {
        this.reservasRepository = reservasRepository;
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @GetMapping("/reservas/{idCliente}")
    public ResponseEntity<List<Reservas>> getReservasById(@PathVariable Long idCliente) {
        List<Reservas> al = reservasRepository.findAll();
        List<Reservas>reservasUser = new ArrayList<>();
        for (Reservas reser:al) {
            if (reser.getIdCliente() == idCliente){
                reservasUser.add(reser);
            }
        }
        if (!reservasUser.isEmpty()) {
            return ResponseEntity.ok(reservasUser);
        } else {
            log.warn("No hay reservas en la base de datos");
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin("http://127.0.0.1:3000")
    @PostMapping("/reservas")
    public  ResponseEntity<Reservas>hacerReserva(@RequestBody Reservas reservas){
        if (reservas.getIdReserva() == null && reservas.getFechaEntrada().isBefore(reservas.getFechaSalida())){
            reservasRepository.save(reservas);
            return ResponseEntity.ok(reservas);
        }else {
            return  ResponseEntity.badRequest().build();
        }
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @PostMapping("/disponibilidad")
    public ResponseEntity<Boolean> verificarReserva(@RequestBody Reservas reserva) {
        // Verifica si los datos de la reserva son válidos
        if (reserva.getIdReserva() == null && reserva.getFechaEntrada().isBefore(reserva.getFechaSalida()) && reserva.getIdHabitacion() != null) {

            Long idHabitacion = reserva.getIdHabitacion();
            LocalDate fechaEntrada = reserva.getFechaEntrada();
            LocalDate fechaSalida = reserva.getFechaSalida();

            // Consultar las reservas existentes para esa habitación
            List<Reservas> reservasExist = reservasRepository.findAll();

            // Verificar si hay alguna superposición de fechas
            for (Reservas r : reservasExist) {
                LocalDate rFechaEntrada = r.getFechaEntrada();
                LocalDate rFechaSalida = r.getFechaSalida();

                // Si hay alguna superposición de fechas, la habitación no está disponible
                if (!(fechaEntrada.isAfter(rFechaSalida) || fechaSalida.isBefore(rFechaEntrada)) && r.getIdHabitacion().equals(idHabitacion)) {
                    return ResponseEntity.ok(false);
                }
            }
            return ResponseEntity.ok(true);
        } else {
            // Datos de reserva no válidos
            return ResponseEntity.badRequest().build();
        }
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @PostMapping("/precio")
    public ResponseEntity<Double>dineroAPagar(@RequestBody Reservas reservas){
        //1 noche cuesta 115$ en todos los hoteles
        double dinero = 0;
        int dias = 0;
        if (reservas.getIdReserva() == null && reservas.getFechaEntrada().isBefore(reservas.getFechaSalida())){
            dias = (int) ChronoUnit.DAYS.between(reservas.getFechaEntrada(), reservas.getFechaSalida());
            dinero = 115 * dias;
            return ResponseEntity.ok(dinero);
        }else {
            return  ResponseEntity.badRequest().build();
        }
    }
    @CrossOrigin("http://127.0.0.1:3000")
    @PostMapping("/puntos")
    public ResponseEntity<Integer>puntosNuevos(@RequestBody Reservas reservas){
        //1 noche cuesta 115$ en todos los hoteles
        double dinero = 0;
        int dias = 0;
        int puntos = 0;
        if (reservas.getIdReserva() == null && reservas.getFechaEntrada().isBefore(reservas.getFechaSalida())){
            dias = (int) ChronoUnit.DAYS.between(reservas.getFechaEntrada(), reservas.getFechaSalida());
            dinero = 115 * dias;
            puntos = (int) (dinero * 10);
            return ResponseEntity.ok(puntos);
        }else {
            return  ResponseEntity.badRequest().build();
        }
    }
}
