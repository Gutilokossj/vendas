package br.com.medicamento.model;

public enum TipoLaboratorios {
    BAYER("Bayer"), PRATIDONADUZZI("Pratidonaduzzi"), EMS("E-MS"), LEGRAND("Legrand"), EUROFARMA("Eurofarma"),
    CIMED("Cimed"), TEUTO("Teuto"), NEOQUIMICA("Neo Química"), ACTAVIS("Actavis"), NOVAQUIMICA("Nova Química"),
    BIOSSINTETICA("Biossintética");

    private final String nomeLaboratorio;

    TipoLaboratorios(String nomeLaboratorio) {
        this.nomeLaboratorio = nomeLaboratorio;
    }

    public String getNomeLaboratorio() {
        return nomeLaboratorio;
    }
}
