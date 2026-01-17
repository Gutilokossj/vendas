package br.com.vendas.converter;

import br.com.vendas.model.Produto;
import br.com.vendas.service.ProdutoService;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named("produtoConverter")
@RequestScoped
public class ProdutoConverter implements Converter<Object> {

    @Inject
    private ProdutoService produtoService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return produtoService.buscarPorId(Long.valueOf(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Produto) {
            return ((Produto) value).getId() != null ? ((Produto) value).getId().toString() : "";
        } else if (value instanceof Long) {
            return value.toString();
        } else {
            return "";
        }
    }
}
