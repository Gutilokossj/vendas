package br.com.dominio.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.dominio.model.Pessoa;

@Named("bean")
@SessionScoped
public class PessoaMB implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private List<Pessoa> pessoas = new ArrayList<>();
	private int contador = 0;
	
	public void adicionar() {
		pessoa.setId(++contador);
		pessoas.add(pessoa);
		limpar();
		//return "paginas/Sucesso";
	}
	
	public void deletar() {
		//System.out.println("Removendo: " + pessoa.getNome());
		pessoas.remove(pessoa);
		limpar();
	}
	
	private void limpar() {
		pessoa = new Pessoa();
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	

}
