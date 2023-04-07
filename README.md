# Challenge Back Vote

API REST para votação em Assembleia

Descrição do Desafio Técnico:

   No cooperativismo, cada associado possui o direito de participar das decisões da mesma através do Voto. 
   Imagine que você deve criar uma solução Backend para gerenciar essas sessões de
votação.

   Considerando que o Cooperativismo é administrado de forma democrática, onde seus cooperados tem participação
na sua Gestão. Segue um modelo de Solução para votação Eletrônica de Pautas.

   A Solução compõe apenas o Backend, que pode ser utilizado em diversos tipos de Interfaces Gráfica,
como Frontend Web ou Mobile, por exemplo.

   Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de
uma API REST:

- Um CRUD completo para cadastro de pautas, chamado de "Agenda"
- Um CRUD completo para cadastro de Seções, chamado de "Section"
- Uma solução para cadastrar os votos dos Associados.
- Uma solução de controle dos votos dos Associados, para que um mesmo Associado não vote mais de uma vez
  em uma mesma Pauta.
- Um controle de tempo de duração de cada Seção. Com isso, pode ser determinado o tempo em que cada seção
  estará ápita a receber votos dos Associados.
- Um Approach para envio do resultado da votação após o encerramento da Seção por tempo de expiração. Tal solução
  permite enviar o Resultado de forma Assíncrona para uma ou vários destinos.

## Stack utilizada

- Java 11
- Spring 2.7.10
- Maven 3.9.1
- MongoDB latest
- Swagger 2.9.2
- JUnit 5.8.2
- Mockito 4.6.1
- Kafka 3.1
- Docker 20.10.23

## Requisitos
- Maven 3.6+
- Java 11
- Docker e Docker Compose instalado na máquina

## Rodar a aplicação

O Docker deve estar rodando na maquina.

Clone o projeto e navegue até a pasta root do mesmo por um terminal.

Então, execute os comandos na sequencia abaixo para compilar, rodar os testes unitarios da aplicação e gerar as imagens docker:

- mvn clean
- mvn package
- docker build .
- docker-compose up

Caso deseje rodar apenas os testes:

- mvn test

Há uma arquivo criado no Postman na pasta raiz do projeto com exemplos de chamadas nas API's

## Acessar a API

Após subir os containers do docker, a api ficará disponivel em:
- http://localhost:8080/api/v1/

Para visualizar a documentação da api:
- http://localhost:8080/swagger-ui.html

OBS: Por default, o endereço para acesso ao Container da Aplicação é o localhost. Mas pode sofrer alteração
     dependendo das configurações da máquina e da forma como foi configurado o Doccker.

#### Tarefa Bônus 1 - Integração com sistemas externos
    Foi implementada a integração com o Serviço Externo informado no desafio: 
- GET https://user-info.herokuapp.com/users/{cpf}

#### Tarefa Bônus 2 - Mensageria e filas
      O processamento Assincrono do resultado foi implementado com o Kafka, por permitir uma configuração 
    de multiplos consumidores de forma independente, com o uso de Partições por Tópico.
      Para possibilitar a validação dos resultado coletados, a principio foi inserido como destino do resultado
    uma tabela chamada "messagingResult", que o armazenará após o encerramento da Seção.

#### Tarefa Bônus 3 - Performance
    Foi implementado um teste de performance desenvolvido com o JMetter 5.5.
	Além do arquivo de projeto do JMetter, foi incluso um arquivo de dados com uma lista de CPF's para execução do teste.
	Caso a URL da aplicação seja alterada, o parâmetro BASE_URL deve ser atualizado com o novo valor.
	Considerando que a aplicação possui uma Feature para emisão do resultado da Pauta processada de forma Assíncrona, após a execução do Teste de Carga,
	talvés seja necessário aguardar alguns instantes até que o Job seja processado, inserindo os dados na tabela "messaging_result", o que simula o envio do resultado.
	Foi disponibilizada também uma API para consulta do resultado de forma Síncrona.

#### Tarefa Bônus 4 - Versionamento da API
    O versionamento da API foi devinido por URL. Por ser uma padrão de mercado e de fácil visualização.
    Portanto, a versão da API segue o modelo: /api/v1/ .
