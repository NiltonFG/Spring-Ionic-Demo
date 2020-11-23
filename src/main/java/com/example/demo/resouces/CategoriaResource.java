package com.example.demo.resouces;

import com.example.demo.domain.Categoria;
import com.example.demo.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoriaResource{

    @Autowired
    private CategoriaService service;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> find(@PathVariable Integer id) {
        Categoria obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insert(@RequestBody Categoria obj){
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Categoria obj, @PathVariable Integer id){
        obj.setId(id);
        service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
