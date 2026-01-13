package br.com.vendas.controller;

import br.com.vendas.model.Produto;
import br.com.vendas.service.ProdutoService;
import br.com.vendas.session.SessaoUsuario;

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
}
