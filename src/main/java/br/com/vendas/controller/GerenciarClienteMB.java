package br.com.vendas.controller;

import br.com.vendas.model.Cliente;
import br.com.vendas.service.ClienteService;
import br.com.vendas.session.SessaoUsuario;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named("gerenciarClienteMB")
@ViewScoped
public class GerenciarClienteMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ClienteService clienteService;
    private List<Cliente> clientes;
    private Cliente clienteSelecionado;

    private SessaoUsuario sessaoUsuario;

    @PostConstruct
    public void init() {
        clientes = clienteService.buscarTodos();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
