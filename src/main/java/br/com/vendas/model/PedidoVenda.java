package br.com.vendas.model;

import br.com.vendas.model.base.EntidadeBase;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido_venda")
public class PedidoVenda extends EntidadeBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedidoVenda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoVendaItem> itens = new ArrayList<>();

    @Column(precision = 12, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    public void adicionarItem(PedidoVendaItem item){
        item.setPedidoVenda(this);
        this.itens.add(0, item);
    }

    public void removerItem(PedidoVendaItem item){
        item.setPedidoVenda(null);
        this.itens.remove(item);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<PedidoVendaItem> getItens() {
        return itens;
    }

    public void setItens(List<PedidoVendaItem> itens) {
        this.itens = itens;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
