package br.com.vendas.controller;

import br.com.vendas.model.Cliente;
import br.com.vendas.service.ClienteService;
import br.com.vendas.util.Message;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;

@Named("cadastroClienteMB")
@ViewScoped
public class CadastroClienteMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ClienteService clienteService;

    private final Cliente cliente = new Cliente();

    public String cadastrarCliente(){
        try{
            clienteService.salvar(cliente);
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .setKeepMessages(true);
            Message.info("Cliente:\n" + cliente.getNome() + ", cadastrado com sucesso!");
            return "/venda/pages/cliente/GerenciarClientes.xhtml?faces-redirect=true";
        }catch (Exception e){
            Message.error(e.getMessage());
            return null;
        }
    }

    public Cliente getCliente() {
        return cliente;
    }
}
