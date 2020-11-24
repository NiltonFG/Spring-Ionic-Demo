package com.example.demo.domain.dto;

import com.example.demo.domain.Categoria;

import java.io.Serializable;
import java.util.Objects;

public class CategoriaDTO implements Serializable {

    private Integer id;
    private String name;

    public CategoriaDTO(){}

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.getId();
        this.name = categoria.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaDTO that = (CategoriaDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
