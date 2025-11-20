package br.com.medicamento.utility;

public class NegocioException extends Exception{
	
	/*
	 * É uma exceção personalizada usada para regras de negócio, por exemplo:

		não pode excluir um registro que está sendo usado
		
		não pode salvar cliente sem CPF
		
		não pode cadastrar produto sem preço
		
		🧩 Por que criar uma exceção própria?
		
		para diferenciar erros de negócio de erros técnicos
		
		para organizar melhor o código
		
		para tratar mensagens com Message.erro() de forma personalizada
	 */

	private static final long serialVersionUID = 1L;
	
	public NegocioException(String message) {
		super(message);
	}

}
