package com.tripstore.cartitemsmicroservice.cartitems.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration("behaviourMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public CartItemMapper tripMapper() {
        return new CartItemMapper();
    }
}
