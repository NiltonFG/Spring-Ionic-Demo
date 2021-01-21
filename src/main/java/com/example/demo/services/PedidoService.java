package com.example.demo.services;

import com.example.demo.domain.*;
import com.example.demo.domain.enums.EstadoPagamento;
import com.example.demo.repositories.ItemPedidoRepository;
import com.example.demo.repositories.PagamentoRepository;
import com.example.demo.repositories.PedidoRepository;
import com.example.demo.security.UserSS;
import com.example.demo.services.exception.AuthorizationException;
import com.example.demo.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido obj){
         obj.setId(null);
         obj.setInstantes(new Date());
         obj.setCliente(clienteService.find(obj.getCliente().getId()));
         obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
         if(obj.getPagamento() instanceof PagamentoBoleto){
             PagamentoBoleto pgto =(PagamentoBoleto)obj.getPagamento();
             boletoService.preencherPagamentoBoleto(pgto,obj.getInstantes());
         }
         obj = repository.save(obj);
         pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido x : obj.getItens()) {
            x.setDesconto(0.00);
            x.setProduto(produtoService.find(x.getProduto().getId()));
            x.setPreco(x.getProduto().getPreco());
            x.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        UserSS user = UserService.authenticated();
        if(user == null){
            throw new AuthorizationException("Acesso negado");
        }
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        Cliente cliente = clienteService.find(user.getId());
        return repository.findByCliente(cliente,pageRequest);
    }
}