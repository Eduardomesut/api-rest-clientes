package com.example.api_rest_clientes.pojo;

import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "clientes")
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String password;
    private String surname;
    private String mail;
    private String phone;
    private String address;
    private String address2;
    private String state;
    private String country;
    private String zipCode;
    private String areaCode;

    public Clientes() {
    }

    public Clientes(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public Clientes(Long id, String name, String password, String surname, String mail, String phone, String address, String address2, String state, String country, String zipCode, String areaCode) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.surname = surname;
        this.mail = mail;
        this.phone = phone;
        this.address = address;
        this.address2 = address2;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.areaCode = areaCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
