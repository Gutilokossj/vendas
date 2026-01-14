package br.com.vendas.controller;

import br.com.vendas.model.Produto;
import br.com.vendas.service.ProdutoService;
import br.com.vendas.util.Message;
import br.com.vendas.util.NegocioException;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;

@Named("editarProdutoMB")
@ViewScoped
public class EditarProdutoMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ProdutoService produtoService;

    private Produto produto;
    private Long id;

    @PostConstruct
    public void init() {
        if (id != null) {
            produto = produtoService.buscarPorId(id);
        } else {
            produto = new Produto();
        }
    }

    public String salvarAlteracoes() {
        try {
            produtoService.atualizar(produto);
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .setKeepMessages(true);
            Message.info("Produto:\n" + produto.getNome() + ", atualizado com sucesso!");
            return "/venda/pages/produto/GerenciarProdutos.xhtml?faces-redirect=true";
        } catch (NegocioException e) {
            Message.error(e.getMessage());
            return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
