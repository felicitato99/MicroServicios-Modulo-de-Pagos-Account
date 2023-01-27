package com.accenture.modulosPago;

import com.accenture.modulosPago.entities.Account;
import com.accenture.modulosPago.repositorys.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EnableFeignClients
@SpringBootApplication
public class ModulosPagoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModulosPagoApplication.class, args);
	}


}
