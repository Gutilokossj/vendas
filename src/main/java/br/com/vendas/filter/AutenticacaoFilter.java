package br.com.vendas.filter;

import br.com.vendas.session.SessaoUsuario;
import br.com.vendas.util.Message;

import javax.enterprise.inject.spi.CDI;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/venda/*")
public class AutenticacaoFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        SessaoUsuario sessaoUsuario =
               CDI.current().select(SessaoUsuario.class).get();

        boolean paginaPublica = req.getRequestURI().endsWith("Login.xhtml")
                || req.getRequestURI().endsWith("CadastroUsuario.xhtml");

        if (!paginaPublica && !sessaoUsuario.isLogado()){
            resp.sendRedirect(req.getContextPath() + "/venda/Login.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }
}
