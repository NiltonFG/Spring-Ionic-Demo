package com.example.demo;

import com.example.demo.domain.Categoria;
import com.example.demo.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class SpringIonicDemoApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(SpringIonicDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria c1 = new Categoria(null,"Informatica");
        Categoria c2 = new Categoria(null,"Escritorio");
        repository.saveAll(Arrays.asList(c1,c2));
    }
}
