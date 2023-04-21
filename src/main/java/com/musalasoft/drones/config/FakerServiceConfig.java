package com.musalasoft.drones.config;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class FakerServiceConfig {
    @Bean
    FakeValuesService fakeValuesService() {
        return new FakeValuesService(
                new Locale("en-GB"), new RandomService());
    }

    @Bean
    Faker faker() {
        return new Faker();
    }
}
