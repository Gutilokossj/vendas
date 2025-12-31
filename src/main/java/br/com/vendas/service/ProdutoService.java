package br.com.vendas.service;

import br.com.vendas.dao.GenericDao;
import br.com.vendas.dao.ProdutoDao;
import br.com.vendas.model.Produto;
import br.com.vendas.model.Usuario;
import br.com.vendas.util.NegocioException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class ProdutoService  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private GenericDao<Produto> daoGenerico;

    @Inject
    private ProdutoDao produtoDao;

    @Transactional
    public void salvar(Produto produto){
        daoGenerico.salvar(produto);
    }

    public void remover(Produto produtoParaExcluir, Usuario usuarioLogado) throws NegocioException {

        if(!usuarioLogado.isAdmin()){
            throw new NegocioException("Usuario nao tem permissao para excluir produto!");
        }

        if(produtoDao.existePedidoParaProduto(produtoParaExcluir)){
            throw new NegocioException("Produto possui pedidos associados, não pode ser excluido!");
        }

        daoGenerico.remover(Produto.class, produtoParaExcluir.getId());
    }

    public List<Produto> buscarTodosProdutos(){
        return daoGenerico.buscarTodos
                (Produto.class, "SELECT p FROM Produto p ORDER BY p.id DESC");
    }
}
