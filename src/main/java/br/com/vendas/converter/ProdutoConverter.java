package br.com.vendas.converter;

import br.com.vendas.model.Produto;
import br.com.vendas.service.ProdutoService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Produto.class, managed = true)
public class ProdutoConverter implements Converter<Produto> {

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
    public String getAsString(FacesContext context, UIComponent component, Produto produto) {
        if (produto == null || produto.getId() == null) {
            return "";
        }
        return produto.getId().toString();
    }
}
