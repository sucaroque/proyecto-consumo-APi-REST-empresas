package com.examen.ms_roque_sucari;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients()
public class MsRoqueSucariApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRoqueSucariApplication.class, args);
	}

}
