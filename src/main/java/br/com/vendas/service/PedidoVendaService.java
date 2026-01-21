package br.com.vendas.service;

import br.com.vendas.dao.GenericDao;
import br.com.vendas.dao.PedidoDAO;
import br.com.vendas.model.*;
import br.com.vendas.util.NegocioException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@ApplicationScoped
public class PedidoVendaService implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private GenericDao<PedidoVenda> daoGenerico;

    @Inject
    private PedidoDAO pedidoDAO;

    public void salvarPedido(PedidoVenda pedidovenda) throws NegocioException {
        validarPedido(pedidovenda);
        calcularTotalPedido(pedidovenda);
        daoGenerico.salvar(pedidovenda);
    }

    public void atualizarPedido(PedidoVenda pedidoVenda) throws NegocioException {
        PedidoVenda pedidoBanco = buscarPorId(pedidoVenda.getId());

        if (pedidoBanco == null) {
            throw new NegocioException("Pedido nao encontrado!");
        }

        validarPedido(pedidoVenda);
        calcularTotalPedido(pedidoVenda);
        daoGenerico.salvar(pedidoVenda);
    }

    private void validarPedido(PedidoVenda pedidoVenda) throws NegocioException {

        if (pedidoVenda.getCliente() == null) {
            throw new NegocioException("Pedido deve possuir um cliente vinculado!");
        }

        if (pedidoVenda.getItens().isEmpty()) {
            throw new NegocioException("Pedido deve possuir pelo menos um item inserido!");
        }
    }

    public static void inserirCliente(PedidoVenda pedidoVenda, Cliente cliente) {
        pedidoVenda.setCliente(cliente);
    }

    public void adicionarItem(PedidoVenda pedidoVenda, Produto produto, BigDecimal quantidade, BigDecimal valorUnitario) throws NegocioException {

        if (produto == null) {
            throw new NegocioException("Algum produto deve ser selecionado!");
        }

        if (quantidade == null || quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegocioException("Quantidade deve ser informada e maior que zero!");
        }

        if (valorUnitario == null) {
            throw new NegocioException("Valor unitário deve ser informado!");
        }

        PedidoVendaItem item = new PedidoVendaItem();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorUnitario(valorUnitario);

        BigDecimal totalItem = valorUnitario.multiply(quantidade).setScale(2, RoundingMode.HALF_UP);
        item.setValorTotal(totalItem);

        pedidoVenda.adicionarItem(item);
        calcularTotalPedido(pedidoVenda);
    }

    public void atualizarItem(PedidoVenda pedidoVenda, PedidoVendaItem item) throws NegocioException {
        if (item.getQuantidade() == null || item.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegocioException("Quantidade deve ser informada e maior que zero!");
        }

        // Bonificação = ZERO
        if (item.getValorUnitario() == null) {
            item.setValorUnitario(BigDecimal.ZERO);
        }

        BigDecimal totalItem = item.getValorUnitario()
                .multiply(item.getQuantidade())
                .setScale(2, RoundingMode.HALF_UP);

        item.setValorTotal(totalItem);
        calcularTotalPedido(pedidoVenda);
    }

    private void calcularTotalPedido(PedidoVenda pedidoVenda) {
        BigDecimal total = BigDecimal.ZERO;

        for (PedidoVendaItem item : pedidoVenda.getItens()) {
            total = total.add(item.getValorTotal().setScale(2, RoundingMode.HALF_UP));
        }

        pedidoVenda.setValorTotal(total);
    }

    public void removerItem(PedidoVenda pedidoVenda, PedidoVendaItem item) throws NegocioException {
        if (item == null) {
            throw new NegocioException("Selecione ao menos um item para exclusão!");
        }

        pedidoVenda.removerItem(item);
        calcularTotalPedido(pedidoVenda);
    }

    public void removerPedido(PedidoVenda pedidoParaExcluir, Usuario usuarioLogado) throws NegocioException {
        if (!usuarioLogado.isAdmin()) {
            throw new NegocioException("Usuario nao tem permissao para excluir pedido!");
        }

        pedidoDAO.removerPedido(pedidoParaExcluir.getId());
    }

    public PedidoVenda duplicarPedido(PedidoVenda pedidoVendaOriginal) throws NegocioException {

        if (pedidoVendaOriginal == null || pedidoVendaOriginal.getId() == null) {
            throw new NegocioException("Pedido não informado para duplicar!");
        }

        PedidoVenda pedidoCompleto = pedidoDAO.buscarPedidoPorId(pedidoVendaOriginal.getId());

        PedidoVenda novoPedido = new PedidoVenda();

        novoPedido.setCliente(pedidoCompleto.getCliente());

        for (PedidoVendaItem itemOriginal : pedidoCompleto.getItens()) {

            PedidoVendaItem novoItem = new PedidoVendaItem();
            novoItem.setProduto(itemOriginal.getProduto());
            novoItem.setQuantidade(itemOriginal.getQuantidade());
            novoItem.setValorUnitario(itemOriginal.getValorUnitario());

            BigDecimal totalItem = itemOriginal.getValorUnitario()
                    .multiply(itemOriginal.getQuantidade())
                    .setScale(2, RoundingMode.HALF_UP);

            novoItem.setValorTotal(totalItem);
            novoPedido.adicionarItem(novoItem);
        }

        calcularTotalPedido(novoPedido);
        daoGenerico.salvar(novoPedido);
        return novoPedido;
    }

    public List<PedidoVenda> buscarTodosPedidos() {
        return daoGenerico.buscarTodos
                (PedidoVenda.class, "SELECT p FROM PedidoVenda p ORDER BY p.id DESC");
    }

    public PedidoVenda buscarPorId(Long id) throws NegocioException {

        if (id == null) {
            throw new NegocioException("Id do pedido não informado!");
        }
        return pedidoDAO.buscarPedidoPorId(id);
    }

    public List<PedidoVenda> buscarPorIdConsulta(String filtro) throws NegocioException {
        if (filtro == null || filtro.trim().isEmpty()) {
            return List.of();
        }
        try {
            Long id = Long.valueOf(filtro.trim());
            PedidoVenda pedido = pedidoDAO.buscarPedidoPorIdConsulta(id);

            return pedido != null ? List.of(pedido) : List.of();
        } catch (NumberFormatException e) {
            throw new NegocioException("Informe um ID de pedido válido");
        }
    }

    public List<PedidoVenda> buscarPorNomeCliente(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return List.of();
        }
        return pedidoDAO.buscarPorNomeCliente(nome);
    }

}
