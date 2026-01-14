package br.com.vendas.controller;

import br.com.vendas.model.Usuario;
import br.com.vendas.service.UsuarioService;
import br.com.vendas.session.SessaoUsuario;
import br.com.vendas.util.Message;
import br.com.vendas.util.NegocioException;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;

@Named("loginMB")
@ViewScoped
public class LoginMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private SessaoUsuario sessaoUsuario;

    private String login;
    private String senha;

    public String logar() {
        try {
            Usuario usuario = usuarioService.logar(login, senha);
            sessaoUsuario.setUsuarioLogado(usuario);
            return "/venda/pages/DashboardVendas.xhtml?faces-redirect=true";
        } catch (NegocioException e) {
            Message.error(e.getMessage());
            return null;
        }
    }

    public String deslogar() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().getFlash().setKeepMessages(true);

        Message.warning("Atenção:\n Usuário deslogado!");

        sessaoUsuario.setUsuarioLogado(null);
        facesContext.getExternalContext().invalidateSession();

        return "/venda/Login.xhtml?faces-redirect=true";
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
