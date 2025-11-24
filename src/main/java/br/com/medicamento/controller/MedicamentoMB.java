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

@Named("medicamentoMB")
@ViewScoped
public class MedicamentoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Medicamento medicamento;

	private List<Medicamento> medicamentos;

	@Inject
	private MedicamentoService service;

	// PostConstruct diz que o método vai ser construido, assim que a classe for
	// criada
    @PostConstruct
    public void carregar() {
        medicamento = new Medicamento();
        medicamentos = service.todosOsMedicamentos();
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
			String nome = medicamento.getNome();
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
