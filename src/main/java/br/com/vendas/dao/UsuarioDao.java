package br.com.vendas.dao;

import br.com.vendas.model.Usuario;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class UsuarioDao {

    @Inject
    private EntityManager em;

    public Usuario buscarPorLogin(String login){
        String jpql = "SELECT u FROM Usuario u WHERE u.login = :login";
        TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
        query.setParameter("login", login);

        List<Usuario> resultado = query.getResultList();
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public boolean existeLogin(String login){
        String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.login = :login";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("login", login)
                .getSingleResult();

        return count > 0;
    }
}
