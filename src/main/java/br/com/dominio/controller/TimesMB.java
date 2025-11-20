package br.com.dominio.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("ligaBBVA")
@SessionScoped
public class TimesMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> timesEspanhois;
	
	public void selecionar() {  //ISSO SERVER PARA PERCORRER A LISTA, PRINTAR TODOS OS TIMES!
		for (String times : timesEspanhois) {
			System.out.println(times);
		}
	}

	public List<String> getTimesEspanhois() {
		return timesEspanhois;
	}

	public void setTimesEspanhois(List<String> timesEspanhois) {
		this.timesEspanhois = timesEspanhois;
	}
	
	
}