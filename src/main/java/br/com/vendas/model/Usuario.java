package br.com.vendas.model;

import br.com.vendas.model.base.EntidadeBase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadeBase implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Nome do usuário não pode ser nulo")
    @Column(nullable = false,length = 40)
    private String nome;

    @NotBlank(message = "Login do usuário não pode ser nulo")
    @Column(nullable = false,length = 10, unique = true)
    private String login;

    @NotBlank(message = "Senha do usuário não pode ser nula")
    @Column(nullable = false,length = 20)
    private String senha;

    @Column(nullable = false)
    private Boolean admin;

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

    public Boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }
}
