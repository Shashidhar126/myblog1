package com.myblog10;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication//starting point from where programme execution begins
public class Myblog10Application {

	public static void main(String[] args) {
		SpringApplication.run(Myblog10Application.class, args);
	}
	@Bean
	public ModelMapper modelmapper(){
		return new ModelMapper();
	}

}
