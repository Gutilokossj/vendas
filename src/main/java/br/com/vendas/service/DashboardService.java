package br.com.vendas.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@ApplicationScoped
public class DashboardService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    public Long totalClientes() {
        return em.createQuery(
                "SELECT COUNT(c) FROM Cliente c",
                Long.class
        ).getSingleResult();
    }

    public Long totalProdutos() {
        return em.createQuery(
                "SELECT COUNT(p) FROM Produto p",
                Long.class
        ).getSingleResult();
    }

    public Long totalUsuariosAtivos() {
        return em.createQuery(
                "SELECT COUNT(u) FROM Usuario u WHERE u.ativo = true",
                Long.class
        ).getSingleResult();
    }

    public BigDecimal totalVendas() {
        return em.createQuery(
                "SELECT COALESCE(SUM(p.valorTotal), 0) FROM PedidoVenda p",
                BigDecimal.class
        ).getSingleResult();
    }
}
