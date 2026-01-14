package br.com.vendas.controller;

import br.com.vendas.model.Produto;
import br.com.vendas.service.ProdutoService;
import br.com.vendas.session.SessaoUsuario;
import br.com.vendas.util.Message;
import br.com.vendas.util.NegocioException;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named("gerenciarProdutoMB")
@ViewScoped
public class GerenciarProdutoMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ProdutoService produtoService;

    @Inject
    private SessaoUsuario sessaoUsuario;

    private List<Produto> produtos;
    private Produto produtoSelecionado;

    @PostConstruct
    public void init(){
        produtos = produtoService.buscarTodosProdutos();
    }

    public void excluirProduto(){
        try {
            String nomeProdutoExcluido = produtoSelecionado.getNome();
            produtoService.remover(produtoSelecionado, sessaoUsuario.getUsuarioLogado());
            produtos.remove(produtoSelecionado);
            Message.info("Produto:\n" + nomeProdutoExcluido + ", excluído com sucesso!");
        } catch (NegocioException e){
            Message.error(e.getMessage());
        }
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }
}
