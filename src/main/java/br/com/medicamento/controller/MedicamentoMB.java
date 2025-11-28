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
	private boolean editando;

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

			if (editando) {
				atualizar();
				return;
			}

			// --- NORMALIZAÇÃO (antes de validar, tirar os espaços do inico e fim)
			medicamento.setNome(medicamento.getNome().trim());
			medicamento.setApresentacao(medicamento.getApresentacao().trim());
			medicamento.setRegistro(medicamento.getRegistro().trim());

			// Validação de duplicidade
			if (registroDuplicado(medicamento)) {
				Message.erro("Já existe um medicamento cadastrado com este Registro MS.");
				return;
			}

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
			Message.warning("Item: " + nome + ", Foi removido!!");
		} catch (NegocioException e) {
			Message.erro(e.getMessage());
		}
	}

	public void atualizar() {
		try {

			medicamento.setNome(medicamento.getNome().trim());
			medicamento.setApresentacao(medicamento.getApresentacao().trim());
			medicamento.setRegistro(medicamento.getRegistro().trim());

			if (registroDuplicado(medicamento)) {
				Message.erro("Já existe outro medicamento usando este Registro MS.");
				return;
			}
			
			service.salvar(medicamento);
			carregar();
			medicamento = new Medicamento();
			editando = false; // Serve para mudar dinamicamente de cadastrar para salvar em tela!
			Message.info("Atualizado com sucesso!");
		} catch (Exception e) {
			Message.erro(e.getMessage());
		}
	}

	private boolean registroDuplicado(Medicamento med) {
		return medicamentos.stream()
				.anyMatch(m -> m.getRegistro().equals(med.getRegistro()) && m.getId() != med.getId());
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public List<Medicamento> getMedicamentos() {
		return medicamentos;
	}

}
