package br.com.medicamento.controller;

import br.com.medicamento.model.TipoLaboratorios;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("tipoLaboratorioBean")
@ApplicationScoped
public class TipoLaboratorioBean {

    public TipoLaboratorios[] getLista(){
        return TipoLaboratorios.values();
    }
}
