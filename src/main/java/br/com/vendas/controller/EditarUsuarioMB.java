package br.com.vendas.controller;

import br.com.vendas.model.Usuario;
import br.com.vendas.service.UsuarioService;
import br.com.vendas.session.SessaoUsuario;
import br.com.vendas.util.Message;
import br.com.vendas.util.NegocioException;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;

@Named("editarUsuarioMB")
@ViewScoped
public class EditarUsuarioMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private SessaoUsuario sessaoUsuario;

    private Usuario usuario;

    @PostConstruct
    public void init() {
        usuario = sessaoUsuario.getUsuarioLogado();
    }

    public String salvarAlteracoes() {
        try {
            usuarioService.atualizarUsuario(usuario);
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .setKeepMessages(true);
            Message.info("Usuario:\n" + usuario.getLogin() + ", atualizado com sucesso!");
            return "/venda/pages/DashboardVendas?faces-redirect=true";
        } catch (NegocioException e) {
            Message.error(e.getMessage());
            return null;
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
