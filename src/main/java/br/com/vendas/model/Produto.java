package br.com.vendas.model;

import br.com.vendas.model.base.EntidadeBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "produto")
public class Produto extends EntidadeBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Column(nullable = false, length = 60)
    private String nome;

    @NotNull(message = "Valor de custo do produto é obrigatório")
    @PositiveOrZero(message = "Valor de custo não pode ser negativo")
    @Column(nullable = false, precision = 14, scale = 4)
    private BigDecimal valorCusto;

    @NotNull(message = "Valor de venda do produto é obrigatório")
    @PositiveOrZero(message = "Valor de venda não pode ser negativo") //Fiz isso para permitir bonificação no pedido.
    @Column(nullable = false, precision = 14, scale = 4)
    private BigDecimal valorVenda;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorCusto() {
        return valorCusto;
    }

    public void setValorCusto(BigDecimal valorCusto) {
        this.valorCusto = valorCusto;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }
}
