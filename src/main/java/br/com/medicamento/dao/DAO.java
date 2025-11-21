package br.com.medicamento.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.medicamento.model.Base;

//Este é o DAO genérico
/*
 * É um DAO genérico, que funciona para qualquer entidade que implemente Base (ou seja, que tenha getId()).

  Assim, você não precisa criar um DAO para cada modelo. Um só resolve tudo.
 */
public class DAO<T extends Base> implements Serializable {

	private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;


	public T buscarPorId(Class<T> clazz, Long id) {
		return manager.find(clazz, id);
	}

	public void salvar(T t) {
		try {

			manager.getTransaction().begin(); // Feito início da transação
			if (t.getId() == null) {
				manager.persist(t);
			} else { // Verifico se é uma entidade nova ou já existente, verifica se ID é null, se
						// for null é nova, se não já existe!
				manager.merge(t);
			}
			manager.getTransaction().commit(); // Feito o encerramento da transação

		} catch (Exception e) {
			manager.getTransaction().rollback();
		}
	}

	public void remover(Class<T> clazz, Long id) {
		T t = buscarPorId(clazz, id); // Assim conseguimos buscar e remover a entidade!
		try {

			manager.getTransaction().begin(); // Feito início da transação
			manager.remove(t);                 //Feito a remoção da entidade
			manager.getTransaction().commit(); // Feito o encerramento da transação

		} catch (Exception e) {
			manager.getTransaction().rollback();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscarTodos(String jpql){
		Query query = manager.createQuery(jpql); //jpql é semelhante a sql, mas é voltada para uso com objetos no Java (JPA)
		return query.getResultList();
	}

	// Através desse manager que poderemos utilizar os métodos da JPA, buscar,
	// salvar, etc...
	/*
	 * 🎉 Resumo final (super curto)

		buscarPorId: encontra objeto pelo ID.
		
		salvar: decide entre persistir ou atualizar.
		
		remover: busca e deleta.
		
		buscarTodos: executa JPQL e retorna lista.
		
		tudo usando um único EntityManager.
	 */
}
