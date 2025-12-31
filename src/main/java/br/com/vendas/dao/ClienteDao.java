package br.com.vendas.dao;

import br.com.vendas.model.Cliente;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class ClienteDao {

    @Inject
    private EntityManager em;

    public boolean existePedidoParaCliente(Cliente cliente){
        String jpql = "SELECT COUNT(c) FROM PedidoVenda c WHERE c.cliente.id = :clienteId";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("clienteId", cliente.getId())
                .getSingleResult();

        return count > 0;
    }

    public List<Cliente> buscarPorNome(String nome){
        return em.createQuery(
                "SELECT c FROM Cliente c WHERE c.nome = :nome ORDER BY c.id",
                        Cliente.class)
                .setParameter("nome", nome)
                .getResultList();
    }
}
