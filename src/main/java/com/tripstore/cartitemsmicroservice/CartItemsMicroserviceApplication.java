package com.tripstore.cartitemsmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class CartItemsMicroserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CartItemsMicroserviceApplication.class, args);
	}

}
