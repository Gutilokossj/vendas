package br.com.vendas.controller;

import br.com.vendas.model.Usuario;
import br.com.vendas.service.UsuarioService;
import br.com.vendas.session.SessaoUsuario;
import br.com.vendas.util.Message;
import br.com.vendas.util.NegocioException;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named("gerenciarUsuariosMB")
@ViewScoped
public class GerenciarUsuariosMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private SessaoUsuario sessaoUsuario;

    private List<Usuario> usuarios;
    private Usuario usuarioSelecionado;
    private String senhaGerada;

    @PostConstruct
    public void init() {
        usuarios = usuarioService.buscarTodosUsuarios();
    }

    public void salvarAlteracoes() {
        try {
            usuarioService.atualizarUsuario(usuarioSelecionado);

            Message.info("Usuario:\n" + usuarioSelecionado.getLogin() + ", atualizado com sucesso!");
            PrimeFaces.current().ajax().addCallbackParam("salvo", true);
        } catch (NegocioException e) {
            Message.error(e.getMessage());
            PrimeFaces.current().ajax().addCallbackParam("salvo", false);
        }
    }

    public void excluirUsuario() {
        try {
            String nomeUsuarioExcluido = usuarioSelecionado.getLogin();
            usuarioService.remover(usuarioSelecionado, sessaoUsuario.getUsuarioLogado());
            usuarios.remove(usuarioSelecionado);
            Message.info("Usuario:\n" + nomeUsuarioExcluido + ", excluído com sucesso!");
        } catch (NegocioException e) {
            Message.error(e.getMessage());
        }
    }

    public void alterarAdmin() {
        try {
            boolean novoStatusAdmin = !usuarioSelecionado.isAdmin();
            usuarioService.alterarPermissaoAdmin(usuarioSelecionado, sessaoUsuario.getUsuarioLogado(), novoStatusAdmin);

            usuarioSelecionado.setAdmin(novoStatusAdmin);

            Message.info("Permissão de usuário: "+ getUsuarioSelecionado().getLogin() +", Alterada com sucesso!");
        } catch (NegocioException e) {
            Message.error(e.getMessage());
        }
    }

    public void resetarSenha(){
        try {
            senhaGerada = usuarioService.resetarSenha(usuarioSelecionado, sessaoUsuario.getUsuarioLogado());
            Message.info("Senha resetada com sucesso!");
            PrimeFaces.current().executeScript("PF('dlgSenhaGerada').show()");
        } catch (NegocioException e) {
            Message.error(e.getMessage());
        }
    }

    public void notificarSenhaCopiada(){
        Message.info("Senha copiada para a area de transferencia!");
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public String getSenhaGerada() {
        return senhaGerada;
    }
}
