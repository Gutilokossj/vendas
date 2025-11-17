package br.com.dominio.model;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Objects;

public class Pessoa implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String email;
	private String documento;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()){
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", "Nome é um campo obrigatório");
                    return;
        }
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", "Email é um campo obrigatório");
            return;
        }
		this.email = email;
	}

	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
        if (documento == null || documento.trim().isEmpty()){
            new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro", "Documento é um campo obrigatório");
            return;
        }
		this.documento = documento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		return Objects.equals(id, other.id);
	}
	
}
