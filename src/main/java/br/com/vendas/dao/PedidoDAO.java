package br.com.vendas.dao;

import br.com.vendas.model.PedidoVenda;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@ApplicationScoped
public class PedidoDAO {

    @Inject
    private EntityManager em;

    public PedidoVenda buscarPedidoPorId(Long id) {
        return em.createQuery(
                        "SELECT p FROM PedidoVenda p " +
                                "LEFT JOIN FETCH p.itens " +
                                "WHERE p.id = :id",
                        PedidoVenda.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
