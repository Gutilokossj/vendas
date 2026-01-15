package br.com.vendas.service;

import br.com.vendas.dao.GenericDao;
import br.com.vendas.dao.ProdutoDao;
import br.com.vendas.model.Produto;
import br.com.vendas.model.Usuario;
import br.com.vendas.util.NegocioException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@ApplicationScoped
public class ProdutoService  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private GenericDao<Produto> daoGenerico;

    @Inject
    private ProdutoDao produtoDao;

    public void salvar(Produto produto) throws NegocioException {
        validarProduto(produto);
        normalizarProduto(produto);
        daoGenerico.salvar(produto);
    }

    public void atualizar(Produto produto) throws NegocioException {
        Produto produtoBanco = produtoDao.buscarPorId(produto.getId());

        if (produtoBanco == null){
            throw new NegocioException("Produto não encontrado!");
        }

        validarProduto(produto);
        normalizarProduto(produto);
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

    public void validarProduto(Produto produto) throws NegocioException{
        if (produto.getNome() == null || produto.getNome().length() < 2){
            throw new NegocioException("Nome deve ter pelo menos 2 caracteres!");
        }

        if (produto.getValorCusto().compareTo(produto.getValorVenda()) > 0) {
            throw new NegocioException(("Preço de custo não pode ser maior que o valor de venda!"));
                    // > 0 é maior, == 0 é igual e < 0 é menor. (só pra eu lembrar)
        }
    }

    public void normalizarProduto(Produto produto){
        produto.setNome(produto.getNome().trim());
    }

    public List<Produto> buscarTodosProdutos(){
        return daoGenerico.buscarTodos
                (Produto.class, "SELECT p FROM Produto p ORDER BY p.id DESC");
    }

    public List<Produto> buscarPorNome(String nome) throws NegocioException{

        List<Produto> produtos = produtoDao.buscarPorNome(nome);

        if (produtos.isEmpty()){
            throw new NegocioException("Nenhum produto encontrado para o nome informado!");
        }

        return produtos;
    }

    public Produto buscarPorId(Long id){
        return produtoDao.buscarPorId(id);
    }
}
