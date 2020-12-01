package com.example.demo.services;

import com.example.demo.domain.ItemPedido;
import com.example.demo.domain.PagamentoBoleto;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.enums.EstadoPagamento;
import com.example.demo.repositories.ItemPedidoRepository;
import com.example.demo.repositories.PagamentoRepository;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private boletoService boletoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido obj){
         obj.setId(null);
         obj.setInstantes(new Date());
         obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
         if(obj.getPagamento() instanceof PagamentoBoleto){
             PagamentoBoleto pgto =(PagamentoBoleto)obj.getPagamento();
             boletoService.preencherPagamentoBoleto(pgto,obj.getInstantes());
         }
         obj = repository.save(obj);
         pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido x : obj.getItens()) {
            x.setDesconto(0.00);
            x.setPreco(produtoService.find(x.getProduto().getId()).getPreco());
            x.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        return obj;

    }
}