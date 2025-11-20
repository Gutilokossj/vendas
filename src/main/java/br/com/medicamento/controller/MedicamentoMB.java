package br.com.medicamento.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.medicamento.model.Medicamento;
import br.com.medicamento.service.MedicamentoService;
import br.com.medicamento.utility.Message;
import br.com.medicamento.utility.NegocioException;

@Named
@ViewScoped
public class MedicamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Medicamento medicamento;

	@Inject
	private MedicamentoService service;

	private List<Medicamento> medicamentos;

	@PostConstruct
	// PostConstruct diz que o método vai ser construido, assim que a classe for
	// criada
	public void carregar() {
		medicamentos = service.todosOsMedicamentos(); // Método lá do server pra buscar todos os medicamentos do banco
	}

	public void adicionar() {
		try {
			service.salvar(medicamento);
			medicamento = new Medicamento();
			carregar(); // Recarrega a lista após inserir o item novo, o mesmo vale no método excluir
						// ali

			Message.info("Salvo com sucesso");
		} catch (NegocioException e) {
			Message.erro(e.getMessage()); // Pega mensagem do MedicamentoService, validado!
		}
	}

	public void excluir() {
		try {
			service.remover(medicamento);
			carregar();

			Message.info(medicamento.getNome() + " Foi removido!!");
		} catch (NegocioException e) {
			Message.erro(e.getMessage());
		}
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public List<Medicamento> getMedicamentos() {
		return medicamentos;
	}

}
