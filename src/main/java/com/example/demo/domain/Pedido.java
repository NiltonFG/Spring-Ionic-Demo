package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instantes;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "pedido")

    private Pagamento pagamento;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "endereço_entrega_id")
    private Endereco enderecoDeEntrega;

    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedido(){}

    public Pedido(Integer id, Date instantes, Cliente cliente, Endereco enderecoDeEntrega) {
        this.id = id;
        this.instantes = instantes;
        this.cliente = cliente;
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    public double getTotal(){
        double total = 0.00;
        for (ItemPedido x :  itens ){
            total += x.getSubtotal();
        }
        return total;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInstantes() {
        return instantes;
    }

    public void setInstantes(Date instantes) {
        this.instantes = instantes;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
        final StringBuffer sb = new StringBuffer();
        sb.append("Pedido número: ").append(id);
        sb.append(",Intante").append(sdf.format(getInstantes()));
        sb.append(",Cliente ").append(getCliente().getNome());
        sb.append(",Situação do pedido: ").append(getPagamento().getEstado().getDescricao());
        sb.append("\ndetalhes:\n");
        for (ItemPedido x: getItens()){
            sb.append(x.toString());
        }
        sb.append("Valor total").append(nf.format(getTotal()));
        return sb.toString();
    }
}
