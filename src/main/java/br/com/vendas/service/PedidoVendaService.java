package br.com.vendas.service;

import br.com.vendas.dao.GenericDao;
import br.com.vendas.model.*;
import br.com.vendas.util.NegocioException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class PedidoVendaService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private GenericDao<PedidoVenda> daoGenerico;

    public void salvarPedido(PedidoVenda pedidovenda) throws NegocioException {
        validarPedido(pedidovenda);
        calcularTotalPedido(pedidovenda);
        daoGenerico.salvar(pedidovenda);
    }

    private void validarPedido(PedidoVenda pedidoVenda) throws NegocioException {

        if(pedidoVenda.getCliente() == null){
            throw new NegocioException("Pedido deve possuir um cliente vinculado!");
        }

        if(pedidoVenda.getItens().isEmpty()){
            throw new NegocioException("Pedido deve possuir pelo menos um item!");
        }
    }

    public void inserirCliente(PedidoVenda pedidoVenda, Cliente cliente){
        pedidoVenda.setCliente(cliente);
    }

    public void adicionarItem(PedidoVenda pedidoVenda, Produto produto, BigDecimal quantidade) throws NegocioException {

        if (produto == null) {
            throw new NegocioException("Produto deve ser informado!");
        }

        if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0){
            throw new NegocioException("Quantidade deve ser informada e maior que zero!");
        }

        PedidoVendaItem item = new PedidoVendaItem();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorUnitario(produto.getValorVenda());

        BigDecimal totalItem = produto.getValorVenda().multiply(quantidade);
        item.setValorTotal(totalItem);

        pedidoVenda.adicionarItem(item);
        calcularTotalPedido(pedidoVenda);
    }

    private void calcularTotalPedido(PedidoVenda pedidoVenda){
        BigDecimal total = BigDecimal.ZERO;

        for(PedidoVendaItem item : pedidoVenda.getItens()){
            total = total.add(item.getValorTotal());
        }

        pedidoVenda.setValorTotal(total);
    }

    public void removerItem(PedidoVenda pedidoVenda, PedidoVendaItem item) throws NegocioException {
        if (item == null) {
            throw new NegocioException("Selecione ao menos um item para exclusão!");
        }

        pedidoVenda.removerItem(item);
        calcularTotalPedido(pedidoVenda);
    }

    public void removerPedido(PedidoVenda pedidoParaExcluir, Usuario usuarioLogado) throws NegocioException {
        if (!usuarioLogado.isAdmin()){
            throw new NegocioException("Usuario nao tem permissao para excluir pedido!");
        }

        daoGenerico.remover(PedidoVenda.class, pedidoParaExcluir.getId());
    }

    public List<PedidoVenda> buscarTodosPedidos(){
        return daoGenerico.buscarTodos
                (PedidoVenda.class, "SELECT p FROM PedidoVenda p ORDER BY p.id DESC");
    }

}
