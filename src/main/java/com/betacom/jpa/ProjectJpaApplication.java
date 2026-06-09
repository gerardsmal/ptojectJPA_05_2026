package com.betacom.jpa;

import java.beans.BeanProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.betacom.jpa.process.ProcessJPA;

@SpringBootApplication
public class ProjectJpaApplication {
	
	@Autowired
	private ProcessJPA pro;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectJpaApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			pro.execute();
		};
		
	}
}
