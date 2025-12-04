# InventoryLite

Um sistema simples de gerenciamento de inventário construído com Spring Boot.

## Recursos

- Autenticação e gerenciamento de usuários
- Gerenciamento de categorias (CRUD)
- Gerenciamento de produtos (CRUD)
- Rastreamento de movimentos de inventário
- Segurança baseada em JWT

## Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security (JWT)
- PostgreSQL
- Lombok
- SpringDoc OpenAPI

## Instalação

1. Clone o repositório:
   ```bash
   git clone https://github.com/Carlossnogueira/inventorylite-Spring.git
   cd inventorylite
   ```

2. Inicie o banco de dados PostgreSQL:
   ```bash
   docker-compose up -d
   ```

4. Em application.properties:
    Adicione as credenciais do banco de dados e gere uma secret Key para o funcionamento do sistema.

3. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ```

A aplicação iniciará em `http://localhost:8080`.

## Documentação da API

Uma vez executando, acesse a documentação da API em `http://localhost:8080/swagger-ui.html`.
