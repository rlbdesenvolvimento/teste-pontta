# Cadastro de Clientes

Aplicação de CRUD de clientes (listar, cadastrar, editar e excluir), composta por uma API REST em Spring Boot e um front-end em Angular.

```
.
├── back/                # API REST (Spring Boot + PostgreSQL)
├── front/               # Front-end (Angular + Angular Material)
└── docker-compose.yml   # Banco de dados PostgreSQL para desenvolvimento
```

## Tecnologias

| Camada | Tecnologias |
| ------ | ----------- |
| Back   | Java 17, Spring Boot 4.1, Spring Data JPA, Bean Validation, Flyway, PostgreSQL, Lombok |
| Front  | Angular 22, Angular Material, RxJS, Vitest |
| Infra  | Docker / Docker Compose |

## Pré-requisitos

- [JDK 17+](https://adoptium.net/)
- [Node.js](https://nodejs.org/) `^22.22.3`, `^24.15.0` ou `>=26` (exigência do Angular 22) e npm
- [Docker](https://www.docker.com/) com Docker Compose (para o banco e para os testes do back)

O Maven não precisa estar instalado: o projeto usa o Maven Wrapper (`mvnw`).

## Como rodar

Execute os passos na ordem abaixo, a partir da raiz do projeto.

### 1. Banco de dados

```bash
docker compose up -d
```

Sobe um PostgreSQL 17 em `localhost:5432` com banco, usuário e senha `pontta`. Os dados ficam no volume `pontta-data`.

### 2. Back-end (API)

```bash
cd back
./mvnw spring-boot:run        # Linux / macOS / Git Bash
mvnw.cmd spring-boot:run      # Windows (cmd / PowerShell)
```

A API sobe em `http://localhost:8080`. Na inicialização, o Flyway cria a tabela `customer` e insere 10 clientes de exemplo (migrations em `back/src/main/resources/db/migration`).

Teste rápido:

```bash
curl http://localhost:8080/customers
```

### 3. Front-end

Em outro terminal:

```bash
cd front
npm install
npm start
```

Acesse `http://localhost:4200`.

## Endpoints da API

| Método | Rota              | Descrição                 |
| ------ | ----------------- | ------------------------- |
| GET    | `/customers`      | Lista todos os clientes   |
| GET    | `/customers/{id}` | Busca um cliente pelo id  |
| POST   | `/customers`      | Cadastra um cliente       |
| PUT    | `/customers/{id}` | Atualiza um cliente       |
| DELETE | `/customers/{id}` | Exclui um cliente         |

Exemplo de payload (POST / PUT):

```json
{
  "firstName": "João",
  "lastName": "Silva",
  "email": "joao.silva@email.com"
}
```

O email é único: cadastrar ou atualizar com um email já existente retorna erro.

## Testes

Back-end (requer Docker em execução, pois usa Testcontainers):

```bash
cd back
./mvnw test          # ou mvnw.cmd test no Windows
```

Front-end:

```bash
cd front
npm test -- --watch=false
```

## Build de produção do front

```bash
cd front
npm run build
```

Os arquivos são gerados em `front/dist/front`.

## Configuração

- **URL da API usada pelo front:** `front/src/environments/environment.ts` (produção) e `front/src/environments/environment.development.ts` (desenvolvimento).
- **Conexão com o banco:** `back/src/main/resources/application.yaml`. Se alterar usuário, senha ou nome do banco, ajuste também o `docker-compose.yml`.

Para parar o banco:

```bash
docker compose down        # mantém os dados
docker compose down -v     # apaga também o volume com os dados
```

## Problemas comuns

- **Porta em uso (4200, 8080 ou 5432):** encerre o processo que está usando a porta. No front é possível usar outra porta com `npm start -- --port 4300`.
- **Back não conecta ao banco:** confirme que o container está ativo com `docker compose ps`. Se já existir um PostgreSQL local na porta 5432, pare-o ou altere a porta no `docker-compose.yml` e no `application.yaml`.
- **Testes do back falham ao iniciar:** verifique se o Docker está em execução.
- **Erro de versão do Node ao rodar o front:** atualize o Node.js para uma das versões listadas em [Pré-requisitos](#pré-requisitos).
