# GitHub User Profile Find And Keeper

### Este projeto destina-se à encontrar, armazenar e retornar perfis de usuários do github.

## Arquitetura

Eu escolhi uma interpretação do modelo de arquitetura em camadas chamado Arquitetura Limpa. Além de ser um design arquitetural
pelo qual tenho me interessado cada vez mais, acredito que utilizarei essa aplicação para testes com outras técnologias,
o que a obrigará a crescer. Para que isso ocorra de forma organizada, segui, desde o começo da implementação, as ideias 
do Uncle Bob, autor do livro "Arquitetura Limpa", que estabelece, principalmente, mas não somente, insights de como deve 
ser a arquitetura de uma aplicação manutenível o suficiente para durar e não elevar de mais os seus custos com o tempo.

Como pode ser visto ao se examinar o projeto, criei duas camadas, "domain" e "infraestructure": 

A camada domain contém todas  as regras de negócio enquanto na camada infraestructure se encontram todas as 
implementações correspondentes à comunicação do projeto com o mundo externo: consumo da api do GitHub,
 operações relacionadas ao banco de dados e disponibilização de uma api Rest.

### Como as camadas conversam entre si no projeto?

* As camadas se comunicam através de interfaces, ferramentas que utilizo para aplicar o princípio da inversão de
dependência — fundamental para esse design arquitetural.

* Qualquer classe de uma camada pode estabelecer dependência com classes da camada da qual pertencem.

* Não existem dependências das classes da camada de domínio com as classes da camada de infrastructure, mas
o contrário é permitido.


## Testes

Implementei testes unitários de integração em todas as classes que implementam negócio, entram em contato com o banco de
dados ou com o API do GitGub.

## Princípios S.O.L.I.D

* Durante todo o processo de implementação, estive atento em manter a coesão do código, tentando aplicar os princípios
"Single Reason to Change" e "Open close" que dizem, respectivamente, que uma classe deve ter somente um motivo para
mudar e deve estar sempre que possível fechada para alterações e aberta para extensão;
* O princípio da Substituição de Liskov é imposto ao desenvolvimento java por todos os compiladores, então com este eu
não me preocupei;
* O princípio da segregação das interfaces foi seguido quando as criei somente com as assinaturas de funções que suas
implementações devessem implementar.
* A inversão de dependência, como já foi dito lá em cima, foi utilizada para estabelecer a comunicação entre as camadas
que, desta forma, conseguem seguir o fluxo de comunicação estabelecido pelo design arquitetural.

## Tratamento de exceções

Foi implementada uma exceção que envelopa as possíveis causas de erro de negócio da aplicação. Ela é lançada sempre que 
ocorrem erros por conta da interação do usuário com a aplicação e é interceptada por uma classe capaz de retornar respostas
padronizadas que indicam o motivo do erro.

As outras possíveis fontes de erros como uma possível indisponibilidade da Api do GitHub ou do serviço de banco de dados
estão sendo interceptadas de forma genérica e uma resposta padrão é retornada ao usuário quando isso ocorre.

## O que foi implementado exatamente?

* A consulta ao perfil de usuário do GitHub;
* o salvamento do perfil do usuário que se originou dessa consulta à API;
* a alteração do perfil do usuário salvo na base este tenha sido persistido anteriormente
* e o retorno do perfil do usuário para o cliente http.

## Como utilizar

Verifique se você possui Docker instalado e rodando na sua máquina, clone o projeto, 
abra um terminal na pasta onde o projeto foi baixado e digite no terminal:

    docker-compose -f .\Docker\docker-compose.yaml up -d

A aplicação rodará utilizando os valores para as variáveis de ambientes presentes no arquivo .\Docker\.env:

    POSTGRESQL_DB=github-user-profile-find-and-keeper
    POSTGRESQL_PASSWORD=postgres
    POSTGRESQL_PORT=5432
    POSTGRESQL_USERNAME=postgres

Verifique se a aplicação está disponível utilizando a seguinte url:

    localhost:8080/actuator/health

A resposta deve ser:

    {
       "status": "UP"
    }

A documentação da api pode ser acessada através da url:

    http://localhost:8080/webjars/swagger-ui/index.html

### Tecnologias utilizadas:

* Java 17;
* Docker;
* Spring Boot (Webflux, R2DBC);
* Postgresql;
* Liquibase;
* Lombok;
* Junit 5;
* MockServer;
* OpenApi 3.
