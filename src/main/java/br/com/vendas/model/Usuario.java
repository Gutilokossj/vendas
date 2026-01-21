package br.com.vendas.model;

import br.com.vendas.model.base.EntidadeBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadeBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Nome é um campo obrigatório, não pode ser nulo!")
    @Size(min = 3, max = 40, message = "Nome deve ter entre 3 e 40 caracteres")
    @Column(nullable = false,length = 40)
    private String nome;

    @NotBlank(message = "Login do usuário é um campo obrigatório, não pode ser nulo!")
    @Size(min = 5, max = 10, message = "Login do usuário deve ter entre 5 e 10 caracteres")
    @Column(nullable = false,length = 10, unique = true)
    private String login;

    @NotBlank(message = "Senha do usuário é um campo obrigatório, não pode ser nulo!")
    @Size(min= 5, max = 20, message = "Senha do usuário deve ter entre 5 e 20 caracteres")
    @Column(nullable = false,length = 20)
    private String senha;

    @Column(nullable = false)
    private boolean admin = false;

    @Column(nullable = false)
    private boolean ativo = true;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
