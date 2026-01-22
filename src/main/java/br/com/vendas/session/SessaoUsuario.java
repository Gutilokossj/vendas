package br.com.vendas.session;

import br.com.vendas.model.Usuario;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("sessaoUsuario")
@SessionScoped
public class SessaoUsuario implements Serializable {

    private Usuario usuarioLogado;

    public boolean isLogado(){
        return usuarioLogado != null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
