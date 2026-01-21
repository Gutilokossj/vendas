package br.com.vendas.controller.dashboard;

import br.com.vendas.service.DashboardService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Named("homeMB")
@ViewScoped
public class HomeMB implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Inject
    private DashboardService dashboardService;

    private boolean dashboardCarregado = false;

    private Long totalClientes;
    private Long totalProdutos;
    private Long totalUsuariosAtivos;
    private BigDecimal totalVendas;

    public void carregarDashboard() {
        totalClientes = dashboardService.totalClientes();
        totalProdutos = dashboardService.totalProdutos();
        totalUsuariosAtivos = dashboardService.totalUsuariosAtivos();
        totalVendas = dashboardService.totalVendas();
    }

    public void alternarDashboard() {
        if (!dashboardCarregado) {
            carregarDashboard();
            dashboardCarregado = true;
        } else {
            dashboardCarregado = false;
        }
    }

    public Long getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(Long totalClientes) {
        this.totalClientes = totalClientes;
    }

    public Long getTotalProdutos() {
        return totalProdutos;
    }

    public void setTotalProdutos(Long totalProdutos) {
        this.totalProdutos = totalProdutos;
    }

    public boolean isDashboardCarregado() {
        return dashboardCarregado;
    }

    public void setDashboardCarregado(boolean dashboardCarregado) {
        this.dashboardCarregado = dashboardCarregado;
    }

    public Long getTotalUsuariosAtivos() {
        return totalUsuariosAtivos;
    }

    public void setTotalUsuariosAtivos(Long totalUsuariosAtivos) {
        this.totalUsuariosAtivos = totalUsuariosAtivos;
    }

    public BigDecimal getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(BigDecimal totalVendas) {
        this.totalVendas = totalVendas;
    }
}
