package br.com.vendas.dao;

import br.com.vendas.model.PedidoVenda;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

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

    public PedidoVenda buscarPedidoPorIdConsulta(Long id) {
        List<PedidoVenda> resultado = em.createQuery(
                        "SELECT p FROM PedidoVenda p " +
                                "LEFT JOIN FETCH p.itens " +
                                "WHERE p.id = :id",
                        PedidoVenda.class)
                .setParameter("id", id)
                .getResultList();

        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public List<PedidoVenda> buscarPorNomeCliente(String nome) {

        if (nome == null || nome.trim().isEmpty()) {
            return List.of();
        }

        String jpql = """
                    SELECT p FROM PedidoVenda p
                    JOIN p.cliente c
                    WHERE LOWER(c.nome) LIKE :nome
                    ORDER BY p.id DESC
                """;

        return em.createQuery(jpql, PedidoVenda.class)
                .setParameter("nome", "%" + nome.trim().toLowerCase() + "%")
                .setMaxResults(20)
                .getResultList();
    }

}
