package com.example.demo.domain.dto;

import com.example.demo.domain.Estado;

import java.io.Serializable;
import java.util.Objects;

public class EstadoDTO implements Serializable {

    private Integer id;
    private String nome;

    public EstadoDTO() {
    }

    public EstadoDTO(Estado estado) {
        this.id = estado.getId();
        this.nome = estado.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstadoDTO estadoDTO = (EstadoDTO) o;
        return Objects.equals(id, estadoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
