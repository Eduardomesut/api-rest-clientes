package com.example.api_rest_clientes;

import com.example.api_rest_clientes.pojo.Habitaciones;
import com.example.api_rest_clientes.pojo.Hoteles;
import com.example.api_rest_clientes.pojo.Recompensa;
import com.example.api_rest_clientes.repositories.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApiRestClientesApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(ApiRestClientesApplication.class, args);
		ClientesRepository repository = context.getBean(ClientesRepository.class);
		ReservasRepository repository1 = context.getBean(ReservasRepository.class);
		HotelesRepository repository2 = context.getBean(HotelesRepository.class);
		HabitacionesRepository repository3 = context.getBean(HabitacionesRepository.class);
		RecompensaRepository repository4 = context.getBean(RecompensaRepository.class);


		System.out.println(repository.count());
		System.out.println(repository1.count());
		System.out.println(repository2.count());
		System.out.println(repository3.count());
		System.out.println(repository4.count());
	}

}


