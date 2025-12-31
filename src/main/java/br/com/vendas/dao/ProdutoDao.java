package br.com.vendas.dao;

import br.com.vendas.model.Produto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ProdutoDao {

    @Inject
    private EntityManager em;

    public boolean existePedidoParaProduto(Produto produto){
        String jpql = "SELECT COUNT(i) FROM PedidoVendaItem i WHERE i.produto.id = :produtoId";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("produtoId", produto.getId())
                .getSingleResult();

        return count > 0;
    }

    public List<Produto> buscarPorNome(String nome){
        return em.createQuery(
                "SELECT p FROM Produto p WHERE p.nome = :nome ORDER BY p.id",
                        Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }
}
