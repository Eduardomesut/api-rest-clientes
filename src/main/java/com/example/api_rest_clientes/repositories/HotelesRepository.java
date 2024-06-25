package com.example.api_rest_clientes.repositories;

import com.example.api_rest_clientes.pojo.Hoteles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelesRepository extends JpaRepository<Hoteles, Long> {

}
