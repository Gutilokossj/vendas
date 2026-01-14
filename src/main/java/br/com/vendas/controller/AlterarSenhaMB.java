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

@Named("alterarSenhaMB")
@ViewScoped
public class AlterarSenhaMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private SessaoUsuario sessaoUsuario;

    private String novaSenha;
    private String confirmarSenha;
    private Usuario usuario;

    @PostConstruct
    public void init() {
        usuario = sessaoUsuario.getUsuarioLogado();
    }

    public String salvarAlteracoes() {
        try {
            usuarioService.atualizarSenha(usuario, novaSenha, confirmarSenha);
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .setKeepMessages(true);
            Message.info("Senha atualizada com sucesso!");
            return "/venda/pages/DashboardVendas.xhtml?faces-redirect=true";
        } catch (NegocioException e) {
            Message.error(e.getMessage());
            return null;
        }
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }
}
