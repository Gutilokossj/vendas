package br.com.vendas.controller;

import br.com.vendas.model.Cliente;
import br.com.vendas.service.ClienteService;
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

@Named("gerenciarClienteMB")
@ViewScoped
public class GerenciarClienteMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ClienteService clienteService;
    private List<Cliente> clientes;
    private Cliente clienteSelecionado;

    @Inject
    private SessaoUsuario sessaoUsuario;

    @PostConstruct
    public void init() {
        clientes = clienteService.buscarTodosClientes();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void excluirCliente(){
        try{
            clienteService.remover(clienteSelecionado, sessaoUsuario.getUsuarioLogado());
            clientes.remove(clienteSelecionado);
            Message.info("Cliente excluido com sucesso!");
        } catch (NegocioException e){
            Message.error(e.getMessage());
        }
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }
}
