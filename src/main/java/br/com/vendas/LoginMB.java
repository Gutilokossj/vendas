package br.com.vendas;

import br.com.vendas.model.Usuario;
import br.com.vendas.service.UsuarioService;
import br.com.vendas.util.Message;
import br.com.vendas.util.NegocioException;

import javax.annotation.PostConstruct;
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

    private String login;
    private String senha;

    @Inject
    private UsuarioService usuarioService;

    public void logar(){
        try{
            usuarioService.logar(login, senha);
            Message.info("Login realizado com sucesso!");
        } catch (NegocioException e) {
            Message.error(e.getMessage());
        }
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
