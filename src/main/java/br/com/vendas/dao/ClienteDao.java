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

    public List<Cliente> buscarPorNomeOuDocumento(String filtro){
        String jpql = "SELECT c FROM Cliente c WHERE LOWER (c.nome) LIKE LOWER (:filtro) OR LOWER (c.documento) LIKE LOWER (:filtro) ORDER BY c.nome";
        return em.createQuery(jpql, Cliente.class)
                .setParameter("filtro", "%" + filtro + "%")
                .setMaxResults(10)
                .getResultList();
    }

    public Cliente buscarPorId(Long id){
        return em.find(Cliente.class, id);
    }

    public boolean existeClienteComDocumento(String documento){
        String jpql = "SELECT COUNT(c) FROM Cliente c WHERE c.documento = :documento";

        Long count = em.createQuery(jpql, Long.class)
                .setParameter("documento", documento)
                .getSingleResult();

        return count > 0;
    }
}
