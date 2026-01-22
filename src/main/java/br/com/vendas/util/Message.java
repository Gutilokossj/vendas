package br.com.vendas.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {

    public static void info(String text) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, text, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void error(String text) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void warning(String text) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, text, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
