package br.com.vendas.dao;

import br.com.vendas.model.base.EntidadeBase;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class GenericDao<T extends EntidadeBase> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public T buscarPorId(Class<T> clazz, Long id) {
        return manager.find(clazz, id);
    }

    public void salvar(T t) {
        var tx = manager.getTransaction();
        try {
            if (!tx.isActive()) {
                tx.begin();
            }

            if (t.getId() == null) {
                manager.persist(t);
            } else {
                manager.merge(t);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void remover(Class<T> clazz, Long id) {
        T entidade = buscarPorId(clazz, id);
        if (entidade != null) {
            var tx = manager.getTransaction();
            try {
                if (!tx.isActive()) {
                    tx.begin();
                }
                manager.remove(entidade);
                tx.commit();
            } catch (Exception e) {
                if (tx.isActive()) {
                    tx.rollback();
                }
                throw e;
            }
        }
    }

    public List<T> buscarTodos(Class<T> clazz, String jpql) {
        return manager.createQuery(jpql, clazz).getResultList();
    }

}
