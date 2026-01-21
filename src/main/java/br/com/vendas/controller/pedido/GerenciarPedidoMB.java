package br.com.vendas.controller.pedido;

import br.com.vendas.enums.TipoBuscaPedido;
import br.com.vendas.model.PedidoVenda;
import br.com.vendas.service.PedidoVendaService;
import br.com.vendas.session.SessaoUsuario;
import br.com.vendas.util.Message;
import br.com.vendas.util.NegocioException;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Named("gerenciarPedidoMB")
@ViewScoped
public class GerenciarPedidoMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private PedidoVendaService pedidoVendaService;

    @Inject
    private SessaoUsuario sessaoUsuario;

    private List<PedidoVenda> pedidosVenda;
    private PedidoVenda pedidoVendaSelecionado;
    private String filtro;
    private TipoBuscaPedido tipoBusca = TipoBuscaPedido.ID;

    @PostConstruct
    public void init() {
        pedidosVenda = pedidoVendaService.buscarTodosPedidos();
    }

    public void buscarPedidos() {
        try {

            if (filtro == null || filtro.trim().isEmpty()) {
                Message.warning("Informe um valor para realizar a busca.");
                pedidosVenda = pedidoVendaService.buscarTodosPedidos();
                return;
            }

            switch (tipoBusca) {
                case ID -> pedidosVenda = pedidoVendaService.buscarPorIdConsulta(filtro);
                case NOME_CLIENTE -> pedidosVenda = pedidoVendaService.buscarPorNomeCliente(filtro);
            }

            if (pedidosVenda.isEmpty()) {
                if (tipoBusca == TipoBuscaPedido.ID) {
                    Message.warning("Nenhum pedido encontrado para o ID informado!");
                } else {
                    Message.warning("Nenhum pedido encontrado para o nome do cliente informado!");
                }
            }

        } catch (NegocioException e) {
            Message.error(e.getMessage());
            pedidosVenda = List.of();
        }
    }

    public TipoBuscaPedido[] getTiposBusca() {
        return TipoBuscaPedido.values();
    }

    public void excluirPedido() {
        try {
            String idPedidoExcluido = String.valueOf(pedidoVendaSelecionado.getId());
            pedidoVendaService.removerPedido(pedidoVendaSelecionado, sessaoUsuario.getUsuarioLogado());
            pedidosVenda.remove(pedidoVendaSelecionado);
            Message.info("Pedido:\n" + idPedidoExcluido + ", excluído com sucesso!");
        } catch (NegocioException e) {
            Message.error(e.getMessage());
        }
    }

    public void duplicarPedido() {

        if (pedidoVendaSelecionado == null || pedidoVendaSelecionado.getId() == null) {
            Message.error("Pedido não informado para duplicar!"); //Deixei aqui pro segurança por enquanto!
            return;
        }

        try {
            PedidoVenda pedidoDuplicado = pedidoVendaService.duplicarPedido(pedidoVendaSelecionado);
            pedidosVenda.add(0, pedidoDuplicado);
            Message.info("Pedido duplicado com sucesso! Novo ID: " + pedidoDuplicado.getId());
        } catch (NegocioException e) {
            Message.error(e.getMessage());
        }
    }

    public List<PedidoVenda> getPedidosVenda() {
        return pedidosVenda;
    }

    public PedidoVenda getPedidoVendaSelecionado() {
        return pedidoVendaSelecionado;
    }

    public void setPedidoVendaSelecionado(PedidoVenda pedidoVendaSelecionado) {
        this.pedidoVendaSelecionado = pedidoVendaSelecionado;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public TipoBuscaPedido getTipoBusca() {
        return tipoBusca;
    }

    public void setTipoBusca(TipoBuscaPedido tipoBusca) {
        this.tipoBusca = tipoBusca;
    }
}
