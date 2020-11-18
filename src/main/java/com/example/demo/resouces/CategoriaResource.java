package com.example.demo.resouces;

import com.example.demo.domain.Categoria;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoriaResource{

    @RequestMapping(method = RequestMethod.GET)
    public List<Categoria> listar() {
        Categoria cat1 = new Categoria(1,"informatica");
        Categoria cat2 = new Categoria(2,"informatica");
        List<Categoria> list = new ArrayList();
        list.add(cat1);
        list.add(cat2);
        return list;
    }
}
