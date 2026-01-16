package br.com.vendas.controller;

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

    @PostConstruct
    public void init(){
        pedidosVenda = pedidoVendaService.buscarTodosPedidos();
    }

    public void excluirPedido(){
        try {
            String idPedidoExcluido = String.valueOf(pedidoVendaSelecionado.getId());
            pedidoVendaService.removerPedido(pedidoVendaSelecionado, sessaoUsuario.getUsuarioLogado());
            pedidosVenda.remove(pedidoVendaSelecionado);
            Message.info("Pedido:\n" +  idPedidoExcluido + ", excluído com sucesso!");
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
}
