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

        /*
    Significa: “Quem manda nesse relacionamento é o atributo pedidoVenda que está em PedidoVendaItem”
    Ou seja:
    PedidoVenda → só enxerga
    PedidoVendaItem → possui a FK

    🔹 cascade = CascadeType.ALL
    Significa:
    Salvou pedido → salva itens
    Removeu pedido → remove itens

    🔹 orphanRemoval = true
    Se remover um item da lista:

    pedido.getItens().remove(item);

    O item some do banco também.

    🧱 Modelo mental definitivo (guarda isso)

    Pedido NÃO tem produto
    Pedido tem itens
    Item tem produto
    Item tem quantidade
    Item tem valor
    Sempre pense assim.
     */

    @Column(precision = 12, scale = 2)
    private BigDecimal valorTotal;

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
