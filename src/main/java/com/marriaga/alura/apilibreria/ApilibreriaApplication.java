package com.marriaga.alura.apilibreria;

import com.marriaga.alura.apilibreria.principal.Principal;
import com.marriaga.alura.apilibreria.service.ConsumoAPI;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApilibreriaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApilibreriaApplication.class, args);
    }

    Principal principal = new Principal();

    @Override
    public void run(String... args) throws Exception {
        principal.muestraInformacion();

    }
}
