package com.tripstore.cartitemsmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableEurekaClient
public class CartItemsMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartItemsMicroserviceApplication.class, args);
	}

}
