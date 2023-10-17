package pe.isil.cliente_2978;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Cliente2978Application {

	public static void main(String[] args) {
		SpringApplication.run(Cliente2978Application.class, args);
	}
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
