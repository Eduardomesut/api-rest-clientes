package com.example.api_rest_clientes.pojo;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reservas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idReserva;
    Long idCliente;
    LocalDate fechaEntrada;
    LocalDate fechaSalida;
    Long idHabitacion;

    public Reservas(Long idReserva, Long idCliente, LocalDate fechaEntrada, LocalDate fechaSalida, Long idHabitacion) {
        this.idReserva = idReserva;
        this.idCliente = idCliente;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.idHabitacion = idHabitacion;
    }

    public Reservas() {
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Long getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Long idHabitacion) {
        this.idHabitacion = idHabitacion;
    }
}
