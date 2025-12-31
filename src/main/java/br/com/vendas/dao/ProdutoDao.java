package br.com.vendas.dao;

import br.com.vendas.model.Produto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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

}
