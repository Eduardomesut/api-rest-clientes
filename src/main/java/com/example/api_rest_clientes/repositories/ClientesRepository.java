package com.example.api_rest_clientes.repositories;

import com.example.api_rest_clientes.pojo.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {

    boolean existsByMail(String mail);
    boolean existsByPhone(String phone);
}
