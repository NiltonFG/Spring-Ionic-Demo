package com.example.demo.services;

import com.example.demo.domain.Cidade;
import com.example.demo.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService{

    @Autowired
    private CidadeRepository repository;

    public List<Cidade> findByEstado(Integer estadoID){
        return repository.findCidade(estadoID);
    }
}
