package cundi.edu.co.demo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;


@SpringBootApplication
public class PrimerApiApplication {
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(PrimerApiApplication.class, args);
	}

}
