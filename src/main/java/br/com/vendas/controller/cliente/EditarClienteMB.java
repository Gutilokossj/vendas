package br.com.vendas.controller.cliente;

import br.com.vendas.model.Cliente;
import br.com.vendas.service.ClienteService;
import br.com.vendas.util.Message;
import br.com.vendas.util.NegocioException;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;

@Named("editarClienteMB")
@ViewScoped
public class EditarClienteMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private ClienteService clienteService;

    private Cliente cliente;
    private Long id;

    @PostConstruct
    public void init(){
        if(id != null){
            cliente = clienteService.buscarPorId(id);
        } else {
            cliente = new Cliente();
        }
    }

    public String salvarAlteracoes(){
        try{
            clienteService.atualizar(cliente);
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .setKeepMessages(true);
            Message.info("Cliente:\n" + cliente.getNome() + ", atualizado com sucesso!");
            return "/venda/pages/cliente/GerenciarClientes.xhtml?faces-redirect=true";
        } catch (NegocioException e){
            Message.error(e.getMessage());
            return null;
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
