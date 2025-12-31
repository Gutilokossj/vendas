package br.com.vendas.service;

import br.com.vendas.dao.GenericDao;
import br.com.vendas.model.PedidoVenda;
import br.com.vendas.model.Usuario;
import br.com.vendas.util.NegocioException;

import javax.inject.Inject;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PedidoVendaService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private GenericDao<PedidoVenda> daoGenerico;

    public void salvarPedido(PedidoVenda pedidovenda){
        daoGenerico.salvar(pedidovenda);
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
