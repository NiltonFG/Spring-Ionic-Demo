package com.example.demo;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Produto;
import com.example.demo.repositories.CategoriaRepository;
import com.example.demo.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class SpringIonicDemoApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ProdutoRepository repositoryP;

    public static void main(String[] args) {
        SpringApplication.run(SpringIonicDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria c1 = new Categoria(null,"Informatica");
        Categoria c2 = new Categoria(null,"Escritorio");

        Produto p1 = new Produto(null,"pc",2000.00);
        Produto p2 = new Produto(null,"impressora",500.00);
        Produto p3 = new Produto(null,"mouse",100.00);

        c1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        c2.getProdutos().addAll(Arrays.asList(p2));

        p1.getCategorias().addAll(Arrays.asList(c1));
        p2.getCategorias().addAll(Arrays.asList(c1,c2));
        p3.getCategorias().addAll(Arrays.asList(c1));

        repository.saveAll(Arrays.asList(c1,c2));
        repositoryP.saveAll(Arrays.asList(p1,p2,p3));
    }
}
