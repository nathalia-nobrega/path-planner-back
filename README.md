# Planejador de viagem (Back-end)

Projeto feito como desafio prático de desenvolvimento de software do Laboratório Nexus.

Tabela de conteúdos
=================  
<!--ts-->  

* [Sobre](#sobre)
* [Tecnologias](#tecnologias-e-ferramentas)
* [Pré-requisitos](#pré-requisitos)
* [Como Executar](#como-executar)
* [Documentação](#documentação)

<!--te-->  

## Sobre

Este projeto tem como objetivo ajudar os usuários a planejar suas viagens de maneira eficiente e organizada. A aplicação permite que os usuários organizem atividades, criem links importantes, e organizem uma lista de itens para levar na viagem. A aplicação também conta com uma página de sugestão de destinos, gerada automaticamente pela [Google Places API](https://developers.google.com/maps/documentation/javascript/places) com base no destino do usuário.

### Tópicos escolhidos

* API RESTful para Gerenciamento de Recursos
* Autenticação e Autorização com JWT
* Criptografia e Hashing
* Cache de Dados ([Issue](https://github.com/nathalia-nobrega/path-planner-back/issues/1))
* [Front-end](https://github.com/nathalia-nobrega/path-planner-front) com APIs públicas




## Tecnologias e ferramentas

<p> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/></a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> <a href="https://maven.apache.org/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/actions/starter-workflows/main/icons/maven.svg" alt="git" width="40" height="40"/> </a> <a href="https://www.mysql.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> </a>
<a href="https://redis.io/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/redis/redis-original-wordmark.svg" alt="redis" width="40" height="40"/> </a>
<a href="https://www.docker.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/docker/docker-original-wordmark.svg" alt="docker" width="40" height="40"/> </a>
</p>  

## Pré-requisitos

Antes de começar, você precisará ter as seguintes ferramentas instaladas em sua máquina:

- [JDK 17+](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/get-started)


## Como Executar

O Redis é utilizado como armazenamento de dados em cache. O arquivo `docker-compose.yml` contém a configuração para levantar um contêiner Redis. Além disso, também possui a configuração para um Banco de Dados MySQL.


1. Construir a imagem Docker:

    ```bash
    docker build .
    ```

2. Iniciar os contêineres com Docker Compose:

    ```bash
    docker-compose up
    ```
3. Execute a aplicação Spring Boot com Maven: ```mvn spring-boot:run ```


4. A aplicação estará disponível em `http://localhost:8080`.

## Documentação

Este projeto está integrado com o Swagger API, uma ferramenta open-source que gera documentações de API's REST. A documentação estará disponível em `http://localhost:8080/swagger-ui.html`.
