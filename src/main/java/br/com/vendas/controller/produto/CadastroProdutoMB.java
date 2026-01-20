package br.com.vendas.controller.produto;

import br.com.vendas.model.Produto;
import br.com.vendas.service.ProdutoService;
import br.com.vendas.util.Message;
import br.com.vendas.util.NegocioException;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;

@Named("cadastroProdutoMB")
@ViewScoped
public class CadastroProdutoMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ProdutoService produtoService;

    private final Produto produto = new Produto();

    public String cadastrarProduto(){
        try {
            produtoService.salvar(produto);
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .setKeepMessages(true);
            Message.info("Produto:\n" + produto.getNome() + ", cadastrado com sucesso!");
            return "/venda/pages/produto/GerenciarProdutos.xhtml?faces-redirect=true";
        } catch (NegocioException e){
            Message.error(e.getMessage());
            return null;
        }
    }

    public Produto getProduto() {
        return produto;
    }
}
