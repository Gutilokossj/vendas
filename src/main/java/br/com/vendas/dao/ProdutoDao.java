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

    public List<Produto> buscarPorNomeExato(String nome){
        return em.createQuery(
                "SELECT p FROM Produto p WHERE p.nome = :nome ORDER BY p.id",
                        Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public List<Produto> buscarPorNomeParcial(String filtro){
        String jpql = "SELECT p FROM Produto p WHERE LOWER (p.nome) LIKE LOWER (:filtro) ORDER BY p.nome";
        return em.createQuery(jpql, Produto.class)
                .setParameter("filtro", "%" + filtro + "%")
                .setMaxResults(10)
                .getResultList();
    }

    public Produto buscarPorId(Long id){
        return em.find(Produto.class, id);
    }
}
