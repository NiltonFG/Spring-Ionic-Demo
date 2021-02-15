package com.example.demo.resouces;

import com.example.demo.domain.Cidade;
import com.example.demo.domain.dto.CidadeDTO;
import com.example.demo.domain.dto.EstadoDTO;
import com.example.demo.services.CidadeService;
import com.example.demo.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource{

    @Autowired
    private EstadoService service;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDTO>> findAll(){
        List<EstadoDTO> list = service.findAll().stream().map(x -> new EstadoDTO(x))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(list);

    }

    @RequestMapping(value = "/cidades/{estadoId}/cidades",method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDTO>> findCidade(@PathVariable Integer id){
        List<CidadeDTO> list = cidadeService.findByEstado(id).stream()
                .map(x -> new CidadeDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }
}
