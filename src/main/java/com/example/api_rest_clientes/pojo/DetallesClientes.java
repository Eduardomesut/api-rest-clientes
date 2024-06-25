package com.example.api_rest_clientes.pojo;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles")
public class DetallesClientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalles;
    private Long idCliente;
    private Integer puntosNH;
    private Double saldo;

    public DetallesClientes() {
    }

    public DetallesClientes(Long idDetalles, Long idCliente, Integer puntosNH, Double saldo) {
        this.idDetalles = idDetalles;
        this.idCliente = idCliente;
        this.puntosNH = puntosNH;
        this.saldo = saldo;
    }

    public Long getIdDetalles() {
        return idDetalles;
    }

    public void setIdDetalles(Long idDetalles) {
        this.idDetalles = idDetalles;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getPuntosNH() {
        return puntosNH;
    }

    public void setPuntosNH(Integer puntosNH) {
        this.puntosNH = puntosNH;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

}
