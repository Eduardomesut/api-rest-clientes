package com.example.api_rest_clientes.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "hoteles")
public class Hoteles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idhotel;
    private String nombre;
    private String ubicacion;
    private Integer numEstrellas;

    public Hoteles() {
    }

    public Hoteles(Long idhotel, String nombre, String ubicacion, Integer numEstrellas) {
        this.idhotel = idhotel;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.numEstrellas = numEstrellas;
    }

    public Long getIdhotel() {
        return idhotel;
    }

    public void setIdhotel(Long idhotel) {
        this.idhotel = idhotel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getNumEstrellas() {
        return numEstrellas;
    }

    public void setNumEstrellas(Integer numEstrellas) {
        this.numEstrellas = numEstrellas;
    }
}
