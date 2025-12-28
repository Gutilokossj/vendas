package br.com.vendas.dao;

import br.com.vendas.model.base.EntidadeBase;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class GenericDao <T extends EntidadeBase> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public T buscarPorId(Class<T> clazz, Long id){
        return manager.find(clazz, id);
    }

    public void salvar(T t){
        try {
            manager.getTransaction().begin();
            if (t.getId() == null){
                manager.persist(t);
            } else {
                manager.merge(t);
            }
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar entidade", e);
        }
    }

    public void remover(Class<T> clazz, Long id){
        T entidade = buscarPorId(clazz, id);
        if (entidade != null){
            try {
                manager.getTransaction().begin();
                manager.remove(entidade);
                manager.getTransaction().commit();
            } catch (Exception e) {
                manager.getTransaction().rollback();
                throw new RuntimeException("Erro ao remover entidade", e);
            }
        }
    }

    public Long buscarCount(String jpql, Object... params){
        var query = manager.createQuery(jpql, Long.class);

        for (int i = 0; i < params.length; i+= 2) {
            query.setParameter(params[i].toString(), params[i + 1]);
        }
        return query.getSingleResult();
    }

    public List<T> buscarTodos(Class<T> clazz, String jpql){
        return manager.createQuery(jpql, clazz).getResultList();
    }

}
