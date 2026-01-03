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

@Named("cadastroUsuarioMB")
@ViewScoped
public class CadastroUsuarioMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    private final Usuario usuario = new Usuario(); //Lembre-se manter o objeto como atributo da classe e não local!

    private String confirmarSenha;

    @Inject
    private SessaoUsuario sessaoUsuario;

    public String cadastrarUsuario() {
        try {
            usuarioService.salvar(usuario, confirmarSenha);
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .setKeepMessages(true);
            Message.info("Usuario cadastrado com sucesso!");
            return "Login.xhtml?faces-redirect=true";
        } catch (NegocioException e) {
            Message.error(e.getMessage());
            return null;
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public void excluirUsuario(Usuario usuarioSelecionado) {
        try {
            usuarioService.remover(usuarioSelecionado, sessaoUsuario.getUsuarioLogado());
            Message.info("Usuario excluido com sucesso!");
        } catch (NegocioException e) {
            Message.error(e.getMessage());
        }
    }
}
