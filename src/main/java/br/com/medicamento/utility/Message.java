package br.com.medicamento.utility;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


//Classe utilitária de mensagem com todos os métodos static para não precisarmos ficar instânciando.

/*
	 * É uma classe utilitária para mostrar mensagens no JSF de forma rápida e simples.
	Você não precisa criar objeto, porque os métodos são static.
	
	O que ela faz
	
	info() → mostra mensagem de informação
	
	erro() → mostra mensagem de erro
	
	warning() → mostra aviso
 */
public class Message {

	public static void info(String text) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, text, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static void erro(String text) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public static void warning(String text) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, text, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
