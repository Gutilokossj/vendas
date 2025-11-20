package br.com.medicamento.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("CursoPU");
	
	public static EntityManager getEntityManager(){
		return factory.createEntityManager();
	}
	
	/*
	 * Esse código é uma fábrica de conexões com o banco para o JPA/Hibernate.

		Ele centraliza toda a responsabilidade de criar EntityManager em um único lugar.
		
		O JPA usa EntityManagerFactory para conectar ao banco.

		Ele é criado usando o persistence.xml.
		
		"CursoPU" é o nome da persistence-unit que está no arquivo:
		
		Ou seja:

		📌 “Crie uma fábrica usando as configurações do persistence.xml.”
		
		Por que é static?
		
		Porque:
		
		só deve existir uma fábrica para o sistema inteiro
		
		criar várias é pesado
		
		economiza memória e melhora desempenho
		
		RESUMO DIDÁTICO (pode copiar para seu caderno)

			ConnectionFactory cria uma única fábrica de conexões (EntityManagerFactory) usando o persistence.xml.
			
			O método getEntityManager() retorna um EntityManager novo, que é a conexão para realizar operações no banco (inserir, atualizar, deletar, buscar).
			
			O uso de static evita criar várias fábricas e garante que tudo use a mesma configuração.
			
			É um padrão clássico da época do professor (muito comum em projetos Hibernate/JPA antigos).
	 */

}
