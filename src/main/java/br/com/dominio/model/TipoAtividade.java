package br.com.dominio.model;

public enum TipoAtividade {
	APRESENTACAO("Apresentação"), CURSO("Curso"), MINICURSO("MiniCurso"), PALESTRA("Palestra"), SEMINARIO("Seminário"), 
	SIMPOSIO("Simpósio"), OUTRA("Outras");
	
	private final String descricao;
	
	TipoAtividade(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
}