package br.com.dominio.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("msg")
@SessionScoped
public class MessagesMB implements Serializable {

	private static final long serialVersionUID = 1L;

	public void salvar() {
		FacesMessage message = new FacesMessage("Mensagem de sucesso teste"); // Por padrão ele usa SERVERITY_WARN
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void erro() {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Mensagem de erro teste");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}