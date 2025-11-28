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

	public static void info(String text) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, text, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static void erro(String text) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

    public void salvar(){
        info("Menagem de sucesso exibida!");
    }

    public void erro(){
        erro("Mensagem de erro exibida!");
    }

}