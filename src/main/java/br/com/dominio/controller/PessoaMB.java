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
		try {
			String cpfFormatado = pessoa.getDocumento().replaceAll("[^0-9]", "");
			
			// VALIDAÇÃO: Se CPF do cliente já existe
			boolean existeCPF = pessoas.stream().anyMatch(p -> p.getDocumento().equals(cpfFormatado));

			if (existeCPF) {
				MessagesMB.erro("Já existe uma pessoa cadastrada com este CPF.");
				return;
			}

			pessoa.setId(++contador);
			pessoa.setDocumento(cpfFormatado);
			
			pessoas.add(pessoa);
			limpar();

		} catch (Exception e) {
			MessagesMB.erro("Erro ao adicionar pessoa.");
		}
	}

	public void deletar() {
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
