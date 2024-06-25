package com.example.api_rest_clientes.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "habitaciones")
public class Habitaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idHabitacion;
    String numHabitacion;
    String tipo;
    Long hotel_id;

    public Habitaciones() {
    }

    public Habitaciones(Long idHabitacion, String numHabitacion, String tipo, Long hotel_id) {
        this.idHabitacion = idHabitacion;
        this.numHabitacion = numHabitacion;
        this.tipo = tipo;
        this.hotel_id = hotel_id;
    }

    public Long getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(Long idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public String getNumHabitacion() {
        return numHabitacion;
    }

    public void setNumHabitacion(String numHabitacion) {
        this.numHabitacion = numHabitacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(Long hotel_id) {
        this.hotel_id = hotel_id;
    }
}
