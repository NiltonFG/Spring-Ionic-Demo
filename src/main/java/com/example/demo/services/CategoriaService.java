package com.example.demo.services;

import com.example.demo.domain.Categoria;
import com.example.demo.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria find(Integer id){
        Optional<Categoria> obj = repository.findById(id);
        return obj.orElse(null);
    }

}
