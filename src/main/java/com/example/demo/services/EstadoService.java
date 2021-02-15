package com.example.demo.services;

import com.example.demo.domain.Estado;
import com.example.demo.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService{

    @Autowired
    private EstadoRepository repository;

    public List<Estado> findAll(){
        return repository.findAllByOrderByNome();
    }
}
