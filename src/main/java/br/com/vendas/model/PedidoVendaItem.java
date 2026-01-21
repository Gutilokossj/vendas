package br.com.vendas.model;

import br.com.vendas.model.base.EntidadeBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "pedido_venda_item")
public class PedidoVendaItem extends EntidadeBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(optional = false)
    private PedidoVenda pedidoVenda;

    @ManyToOne(optional = false)
    private Produto produto;

    @NotNull(message = "Quantidade deve ser informada")
    @PositiveOrZero(message = "Quantidade deve ser maior ou igual a zero")
    @Digits(integer = 10, fraction = 4, message = "Quantidade máxima permitida é 9.999.999.999,9999")
    @Column(nullable = false, precision = 14, scale = 4)
    private BigDecimal quantidade = BigDecimal.ZERO;

    @NotNull(message = "Valor unitário deve ser informado")
    @PositiveOrZero(message = "Valor unitário não pode ser negativo")
    @Digits(integer = 10, fraction = 4, message = "Valor unitário máximo permitido é 9.999.999.999,9999")
    @Column(nullable = false, precision = 14, scale = 4)
    private BigDecimal valorUnitario = BigDecimal.ZERO;

    @NotNull(message = "Valor total deve ser informado")
    @PositiveOrZero(message = "Valor total não pode ser negativo")
    @Digits(integer = 10, fraction = 2, message = "Valor total máximo permitido é 9.999.999.999,99")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    public PedidoVenda getPedidoVenda() {
        return pedidoVenda;
    }

    public void setPedidoVenda(PedidoVenda pedidoVenda) {
        this.pedidoVenda = pedidoVenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
