package br.com.vendas.converter;

import br.com.vendas.model.Cliente;
import br.com.vendas.service.ClienteService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = Cliente.class, managed = true)
public class ClienteConverter implements Converter<Cliente> {

    @Inject
    private ClienteService clienteService;

    @Override
    public Cliente getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        return clienteService.buscarPorId(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Cliente cliente) {
        if (cliente == null || cliente.getId() == null) {
            return "";
        }

        return cliente.getId().toString();
    }
}
