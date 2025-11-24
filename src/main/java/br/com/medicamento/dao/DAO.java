package br.com.medicamento.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

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
            manager.getTransaction().begin();
            if (t.getId() == null) {
                manager.persist(t);
            } else {
                manager.merge(t);
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw e;
        }
    }
    
    public void remover(Class<T> clazz, Long id) {
        T entidade = buscarPorId(clazz, id);
        if (entidade != null) {
            try {
                manager.getTransaction().begin();
                manager.remove(entidade);
                manager.getTransaction().commit();
            } catch (Exception e) {
                manager.getTransaction().rollback();
                throw e;
            }
        }
    }

    public List<T> buscarTodos(Class<T> clazz, String jpql) {
        return manager.createQuery(jpql, clazz).getResultList();
    }
}

	// Através desse manager que poderemos utilizar os métodos da JPA, buscar,
	// salvar, etc...
	/*
	 * 🎉 Resumo final (super curto)
	 * 
	 * buscarPorId: encontra objeto pelo ID.
	 * 
	 * salvar: decide entre persistir ou atualizar.
	 * 
	 * remover: busca e deleta.
	 * 
	 * buscarTodos: executa JPQL e retorna lista.
	 * 
	 * tudo usando um único EntityManager.
	 */