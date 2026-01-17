package br.com.vendas.converter;

import br.com.vendas.model.Cliente;
import br.com.vendas.service.ClienteService;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named("clienteConverter")
@RequestScoped
public class ClienteConverter implements Converter<Object> {

    @Inject
    private ClienteService clienteService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
            return clienteService.buscarPorId(Long.valueOf(value));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof Cliente) {
            Cliente c = (Cliente) value;
            return c.getId() != null ? c.getId().toString() : "";
        }

        if (value instanceof Long) {
            return value.toString();
        }

        return "";
    }
}
