package br.com.medicamento.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.medicamento.dao.DAO;
import br.com.medicamento.model.Medicamento;
import br.com.medicamento.utility.NegocioException;

/*
 * É a camada de regras de negócio do sistema.
   Ela fica entre o ManagedBean (tela) e o DAO (banco).
   
   Tela → Service → DAO → Banco
	
	O CDI injeta automaticamente o DAO pronto.

	→ A service não cria DAO
	→ Apenas usa
	→ Deixa o código limpo e organizado
 */

public class MedicamentoService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private DAO<Medicamento> dao;
	
	public void salvar(Medicamento m) throws NegocioException {
		
		if (m.getNome().length() <3) {
			throw new NegocioException("O nome do medicamento não pode ter menos que 3 caracteres");
		}
		dao.salvar(m);
	}
	
	public void remover(Medicamento m) throws NegocioException {
		dao.remover(Medicamento.class, m.getId());
	}
	
	public List<Medicamento> todosOsMedicamentos(){
		return dao.buscarTodos("select m from Medicamento m order by m.nome");
		//Trabalhando com objeto por isso Medicamente maiúsculo e não minúsculo como no banco
		/*
		 * Usa JPQL (consulta por objeto):

			Medicamento é o nome da Classe, não da tabela
			
			retorna lista ordenada por nome
		 */
	}

}
