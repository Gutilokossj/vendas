**📦 Capsule Corp Vendas** <br>
-----------------------
📝 Descrição do Projeto

Este projeto tem por objetivo entender, analisar e praticar um CRUD real, simulando um fluxo básico de vendas, com funcionalidades de cadastro, atualização, busca e remoção de usuários, clientes e produtos, além do gerenciamento dessas entidades.

O foco principal é o aprendizado, utilizando as tecnologias: <br>
Java e os frameworks JSF, PrimeFaces e Hibernate.

-----------------------
🛠️ Tecnologias Utilizadas
* Java 17 (Amazon Corretto)
* Apache Tomcat 9.0.111
* Hibernate 5.6.15.Final
* PrimeFaces 15.0.6
* Banco de Dados: MariaDB
* Versão do driver: 3.3.3
* JPA
* Maven
* Outras bibliotecas auxiliares configuradas no pom.xml
-----------------------
📋 Pré-requisitos

Antes de executar o projeto, é necessário:
* Java 17 instalado
* Apache Tomcat 9 configurado
* MariaDB em execução
* IDE Java (IntelliJ, Eclipse ou similar)
-----------------------
⚙️ Configuração do Banco de Dados

* Criar o banco de dados desta forma:
CREATE DATABASE vendas;

* Configurar o persistence.xml
* Configure a conexão com o MariaDB e Hibernate no arquivo persistence.xml
-----------------------
⚠️Importante⚠️ <br>
Apesar do arquivo já estar no projeto! <br>
Ajuste porta, usuário e senha conforme seu ambiente local.
-----------------------
👤 Necessário criar - Usuário Administrador Padrão

Após rodar o projeto pela primeira vez, execute o SQL abaixo para criar o usuário ADMIN:

```sql
INSERT INTO vendas.usuario (nome, login, senha, admin, ativo)
SELECT
    'administrador',
    'admin',
    '@Admin2020',
    TRUE,
    TRUE
FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM vendas.usuario WHERE login = 'admin'
);
```
-----------------------
▶️ Como Executar o Projeto

* Clone o repositório
* Importe o projeto na IDE como Maven Project
* Configure o Tomcat 9
* Crie o banco "vendas" conforme descrito
* Execute a SQL para criação do admin
* Execute a aplicação
* Acesse pelo navegador conforme a configuração do servidor
-----------------------
📁 Estrutura do Projeto

Backend (Java): <br>
* src/main/java/br.com/vendas

Frontend (JSF / PrimeFaces): <br>
* src/main/webapp/venda/ <br>

Utiliza também:
* src/main/webapp/resources/css
* src/main/webapp/resources/imagens
-----------------------
ℹ️ Observações Finais <br>

Este projeto tem por finalidade, estudos, aprendizado da melhor maneira, com a prática, com foco no desenvolvimento backend em Java. <br>
O frontend foi mantido mais de forma mais simples, sem grande foco em estilização. <br>
Existem pastas e arquivos não utilizados, que serviram apenas como referência de estudos anteriores e podem ser desconsiderados. <br>

**Eu não levo em consideração todas as regras de interface do PDF, pois utilizei aquilo apenas como ideia, como exemplo.**

* Curiosidade, tem 67 commits, mas muitos deles são do outro projeto que estava estudando, esse aqui começou neste commit: <br>
<img width="1348" height="134" alt="image" src="https://github.com/user-attachments/assets/2e5a5a60-3fe1-4196-be88-e939b12970b6" />


