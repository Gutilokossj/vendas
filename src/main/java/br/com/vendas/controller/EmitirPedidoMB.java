package br.com.vendas.controller;

import br.com.vendas.model.Cliente;
import br.com.vendas.model.PedidoVenda;
import br.com.vendas.model.PedidoVendaItem;
import br.com.vendas.model.Produto;
import br.com.vendas.service.ClienteService;
import br.com.vendas.service.PedidoVendaService;
import br.com.vendas.service.ProdutoService;
import br.com.vendas.util.Message;
import br.com.vendas.util.NegocioException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("emitirPedidoMB")
@ViewScoped
public class EmitirPedidoMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private PedidoVendaService pedidoVendaService;

    @Inject
    private ClienteService clienteService;

    @Inject
    private ProdutoService produtoService;

    private Cliente clienteSelecionado;
    private Produto produtoSelecionado;
    private BigDecimal quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotalItem;
    private PedidoVendaItem itemSelecionado;
    private List<Produto> produtos;

    private Cliente novoCliente = new Cliente();
    private Produto novoProduto = new Produto();

    private final PedidoVenda pedidoVenda = new PedidoVenda();

    public List<Cliente> buscarClientes(String filtro) {
        return clienteService.buscarPorNomeOuDocumento(filtro);
    }

    public void selecionarCliente() {
        PedidoVendaService.inserirCliente(pedidoVenda, clienteSelecionado);
    }

    public void selecionarProduto() {

        if (produtoSelecionado == null || produtoSelecionado.getId() == null) {
            return;
        }

        try {
            Produto produtoCompleto = produtoService.buscarPorId(produtoSelecionado.getId());

            if (produtoCompleto != null) {
                prepararProdutoSelecionado(produtoSelecionado);
            }
        } catch (Exception e) {
            Message.error(e.getMessage());
        }
    }

    public void recalcularTotalItem() {
        if (produtoSelecionado != null
                && valorUnitario != null
                && quantidade != null
                && quantidade.compareTo(BigDecimal.ZERO) > 0) {

            valorTotalItem = valorUnitario.multiply(quantidade);
        } else {
            valorTotalItem = BigDecimal.ZERO;
        }
    }

    public void adicionarItem() {
        try {
            recalcularTotalItem();
            pedidoVendaService.adicionarItem(pedidoVenda, produtoSelecionado, quantidade, valorUnitario);

            limparItemAtual();

        } catch (NegocioException e) {
            Message.error(e.getMessage());
        }
    }

    public void onRowEdit(RowEditEvent<PedidoVendaItem> event){
        PedidoVendaItem item = event.getObject();

        try {
            pedidoVendaService.atualizarItem(pedidoVenda, item);
            Message.info("Item atualizado com sucesso!");
        } catch (NegocioException e){
            FacesContext.getCurrentInstance().validationFailed();
            Message.error(e.getMessage());
        }
    }

    private void limparItemAtual(){
        produtoSelecionado = null;
        quantidade = null;
        valorUnitario = null;
        valorTotalItem = null;
    }

    public void removerItem() {
        try {
            pedidoVendaService.removerItem(pedidoVenda, itemSelecionado);
            itemSelecionado = null;
            recalcularTotalItem();
        } catch (NegocioException e) {
            Message.error(e.getMessage());
        }
    }

    public String emitirPedidoVenda() {
        try {
            pedidoVendaService.salvarPedido(pedidoVenda);
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getFlash()
                    .setKeepMessages(true);
            Message.info("Pedido registrado com sucesso!");
            return "/venda/pages/pedido/GerenciarPedidos.xhtml?faces-redirect=true";
        } catch (NegocioException e) {
            Message.error(e.getMessage());
            return null;
        }
    }

    public void salvarNovoCliente() {
        try {
            clienteService.salvar(novoCliente);

            clienteSelecionado = novoCliente;

            pedidoVenda.setCliente(novoCliente);

            novoCliente = new Cliente();
            PrimeFaces.current().ajax().addCallbackParam("clienteSalvo", true);
            Message.info("Cliente cadastado com sucesso!");
        } catch (NegocioException e) {
            FacesContext.getCurrentInstance().validationFailed();
            PrimeFaces.current().ajax().addCallbackParam("clienteSalvo", false);
            Message.error(e.getMessage());
        }
    }

    public void salvarNovoProduto() {
        try {
            produtoService.salvar(novoProduto);

            produtoSelecionado = novoProduto;

            prepararProdutoSelecionado(produtoSelecionado);

            produtos = null;

            novoProduto = new Produto();
            PrimeFaces.current().ajax().addCallbackParam("produtoSalvo", true);
            Message.info("Produto cadastrado com sucesso!");
        } catch (NegocioException e) {
            FacesContext.getCurrentInstance().validationFailed();
            PrimeFaces.current().ajax().addCallbackParam("produtoSalvo", false);
            Message.error(e.getMessage());
        }
    }

    private void prepararProdutoSelecionado(Produto produto){
        this.produtoSelecionado = produto;

        if (quantidade == null){
            quantidade = BigDecimal.ONE;
        }

        if (produto.getValorVenda() != null){
            valorUnitario = produto.getValorVenda();
        } else {
            valorUnitario = BigDecimal.ZERO;
        }

        recalcularTotalItem();
    }

    public List<Produto> getProdutos() {
        if (produtos == null) {
            produtos = produtoService.buscarTodosProdutos();
        }

        return produtos;
    }

    public PedidoVenda getPedidoVenda() {
        return pedidoVenda;
    }

    public Cliente getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(Cliente clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public PedidoVendaItem getItemSelecionado() {
        return itemSelecionado;
    }

    public void setItemSelecionado(PedidoVendaItem itemSelecionado) {
        this.itemSelecionado = itemSelecionado;
    }

    public Cliente getNovoCliente() {
        return novoCliente;
    }

    public void setNovoCliente(Cliente novoCliente) {
        this.novoCliente = novoCliente;
    }

    public Produto getNovoProduto() {
        return novoProduto;
    }

    public void setNovoProduto(Produto novoProduto) {
        this.novoProduto = novoProduto;
    }

    public BigDecimal getValorTotalItem() {
        return valorTotalItem;
    }

    public void setValorTotalItem(BigDecimal valorTotalItem) {
        this.valorTotalItem = valorTotalItem;
    }
}
