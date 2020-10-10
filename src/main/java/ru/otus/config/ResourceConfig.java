package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
public class ResourceConfig {

    @Bean
    public PrintStream outputStream(){
        return System.out;
    }

}
