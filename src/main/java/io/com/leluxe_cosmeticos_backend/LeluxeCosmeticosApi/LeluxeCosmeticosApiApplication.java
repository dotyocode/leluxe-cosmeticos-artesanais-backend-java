package io.com.leluxe_cosmeticos_backend.LeluxeCosmeticosApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LeluxeCosmeticosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeluxeCosmeticosApiApplication.class, args);
	}

}
