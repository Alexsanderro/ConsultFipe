package br.com.alura.consultfipe;

import br.com.alura.consultfipe.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsultfipeApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsultfipeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
