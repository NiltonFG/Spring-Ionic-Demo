package com.example.demo;

import ch.qos.logback.core.net.server.Client;
import com.example.demo.domain.*;
import com.example.demo.domain.enums.EstadoPagamento;
import com.example.demo.domain.enums.TipoCLiente;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class SpringIonicDemoApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private ProdutoRepository repositoryP;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringIonicDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Categoria c1 = new Categoria(null,"Informatica");
        Categoria c2 = new Categoria(null,"Escritorio");

        Produto p1 = new Produto(null,"pc",2000.00);
        Produto p2 = new Produto(null,"impressora",500.00);
        Produto p3 = new Produto(null,"mouse",100.00);

        c1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
        c2.getProdutos().addAll(Arrays.asList(p2));

        p1.getCategorias().addAll(Arrays.asList(c1));
        p2.getCategorias().addAll(Arrays.asList(c1,c2));
        p3.getCategorias().addAll(Arrays.asList(c1));

        repository.saveAll(Arrays.asList(c1,c2));
        repositoryP.saveAll(Arrays.asList(p1,p2,p3));

        Estado est1 = new Estado(null,"Minas Gerais");
        Estado est2 = new Estado(null,"São Paulo");

        Cidade cid1 = new Cidade(null,"Uberlândia",est1);
        Cidade cid2 = new Cidade(null,"São Paulo",est2);
        Cidade cid3 = new Cidade(null,"Campinas",est2);

        est1.getCidades().addAll(Arrays.asList(cid1));
        est2.getCidades().addAll(Arrays.asList(cid2,cid3));

        estadoRepository.saveAll(Arrays.asList(est1,est2));
        cidadeRepository.saveAll(Arrays.asList(cid1,cid2,cid3));

        Cliente cli1 = new Cliente(null, "Maria Silva","maria@gmail.com","11122121222", TipoCLiente.PESSOAFISICA);
        cli1.getTelefones().addAll(Arrays.asList("555979797","1542365478"));

        Endereco e1 = new Endereco(null,"Rua Flores","300","Apto 231","Jardim","4444444",cid2,cli1);
        Endereco e2 = new Endereco(null,"Rua Torres","20","Apt 4","Mogi","44448888",cid1,cli1);

        cli1.getEnderecos().addAll(Arrays.asList(e1,e2));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1,e2));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido ped1 = new Pedido(null,sdf.parse("30/09/2020 10:32"),cli1,e1);
        Pedido ped2 = new Pedido(null,sdf.parse("20/10/2020 10:00"),cli1,e2);

        PagamentoCartao pagto1 = new PagamentoCartao(null, EstadoPagamento.QUITADO,ped1,6);
        ped1.setPagamento(pagto1);

        PagamentoBoleto pagto2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE,ped2,sdf.parse("20/07/2020 10:00"),null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
    }
}
