# NutriCore Manager

Sistema de gestao nutricional em evolucao para uma plataforma com duas camadas integradas:

- area publica/comercial para autoridade profissional, conteudo, planos e captacao
- area privada/de gestao para pacientes, anamneses, avaliacoes, metas e planejamento alimentar

## MVP atual

Esta etapa entrega um MVP minimo funcional com:

- backend Spring Boot funcional e com build verde
- frontend React + TypeScript em `frontend/`
- rotas publicas navegaveis para home, sobre, conteudos, receitas, planos e contato
- integracao real do frontend com a API publica
- reaproveitamento visual da landing de referencia com assets e linguagem visual adaptados

## Backend estavel atual

- O backend Spring Boot permanece na raiz do repositorio.
- O contexto base da API e `/api`.
- Os controllers estaveis usam `/v1/...`, formando rotas finais no padrao `/api/v1/...`.
- A base estavel atual cobre:
  - nutritionists
  - patients
  - clinical anamnesis
  - anthropometric assessments
  - nutrition goals (create, update, findById, delete)
  - public profile
  - public plans
  - public posts
  - public recipes

## Endpoints publicos usados pelo frontend

- `GET /api/v1/public/profile`
- `GET /api/v1/public/plans`
- `GET /api/v1/public/posts`
- `GET /api/v1/public/recipes`

Os endpoints acima sustentam a camada publica/comercial do MVP. Eles sao servidos por tabelas proprias e dados seedados via Flyway.

## Fora da base estavel

- `MealPlan` continua em WIP na branch `feature/meal-planning-structure`.
- A listagem de `NutritionGoal` por paciente segue pendente e nao faz parte da base estavel.
- Seguranca, autenticacao e autorizacao continuam fora desta etapa.
- A camada privada/de gestao ainda nao tem frontend implementado.

## Referencias locais

- `nutricore-landing/` e `DOCUMENTACAO_TECNICA.md` sao referencias locais e nao compoem a base backend versionada.
- A referencia visual principal desta etapa veio da landing existente no workspace e foi portada para `frontend/` em uma estrutura React componentizada.

## Estrutura do frontend

```text
frontend/
  src/
    components/
    hooks/
    layouts/
    pages/
    routes/
    services/
    types/
    utils/
    data/
    assets/styles/
```

Decisoes desta etapa:

- layout e navegacao publica separados por responsabilidade
- provider unico para carregar os dados publicos da API
- camada `services` isolando o client HTTP e os contratos consumidos
- componentes reutilizaveis para cards, hero, estados de carregamento e CTA
- dados locais mantidos apenas para secoes editoriais/presentacionais que ainda nao possuem administracao via backend

## Como rodar

### Backend

Para rodar testes:

```powershell
.\mvnw.cmd test
```

Para subir a API em modo demonstracao local usando H2:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=test"
```

Para desenvolvimento com PostgreSQL:

```powershell
$env:SPRING_PROFILES_ACTIVE='dev'
$env:DB_URL='jdbc:postgresql://localhost:5432/nutricore_manager'
$env:DB_USERNAME='postgres'
$env:DB_PASSWORD='postgres'
.\mvnw.cmd spring-boot:run
```

### Frontend

```powershell
Set-Location .\frontend
npm install
npm run dev
```

Se necessario, configure a URL da API:

```powershell
Copy-Item .env.example .env
```

Valor padrao:

```text
VITE_API_BASE_URL=http://localhost:8080/api
```

## URLs para teste visual

- Frontend: `http://localhost:5173`
- Home: `http://localhost:5173/`
- Sobre: `http://localhost:5173/sobre`
- Conteudos: `http://localhost:5173/conteudos`
- Receitas: `http://localhost:5173/receitas`
- Planos: `http://localhost:5173/planos`
- Contato: `http://localhost:5173/contato`
- Swagger/OpenAPI: `http://localhost:8080/api/swagger-ui/index.html`

## Validacao realizada nesta etapa

- `.\mvnw.cmd test`
- `npm run build` em `frontend/`
- subida da API em `test`
- subida do frontend Vite em `5173`
- validacao HTTP de:
  - `http://127.0.0.1:8080/api/v1/public/profile`
  - `http://127.0.0.1:8080/api/v1/public/posts`
  - `http://127.0.0.1:5173`
  - `http://127.0.0.1:5173/sobre`

## Proximos passos recomendados

1. Fechar a area privada inicial do frontend para pacientes e visao clinica.
2. Concluir `NutritionGoal` por paciente e retomar `MealPlan` sobre a base saneada.
3. Introduzir autenticacao/autorizacao e rotas protegidas.
4. Evoluir o conteudo publico para administracao via painel.

## Ambiente atual

- Java 21
- Spring Boot 3.3.4
- PostgreSQL para dev/prod
- H2 em memoria para testes
- Flyway para migrations
- MapStruct e Lombok
- Swagger/OpenAPI
- React 19 + TypeScript + Vite no frontend
```

## Ambiente atual

- Java 21
- Spring Boot 3.3.4
- PostgreSQL para dev/prod
- H2 em memoria para testes
- Flyway para migrations
- MapStruct e Lombok
- Swagger/OpenAPI
