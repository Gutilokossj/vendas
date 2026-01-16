package br.com.vendas.converter;

import br.com.vendas.model.Produto;
import br.com.vendas.service.ProdutoService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "produtoConverter", forClass = Produto.class, managed = true)
public class ProdutoConverter implements Converter<Object> {

    @Inject
    private ProdutoService produtoService;

    @Override
    public Produto getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return produtoService.buscarPorId(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null){
            return "";
        }

        if (value instanceof Produto produto){
            return produto.getId() != null ? produto.getId().toString() : "";
        }

        if (value instanceof Long id) {
            return id.toString();
        }

        return "";
    }
}
