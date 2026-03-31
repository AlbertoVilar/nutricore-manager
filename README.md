# NutriCore Manager

Sistema de gestao nutricional em evolucao para uma plataforma com:

- area publica/comercial para autoridade profissional, conteudo, planos e captacao
- area privada/de gestao para pacientes, anamneses, avaliacoes, metas e planejamento alimentar

## Base estavel atual

- O backend Spring Boot permanece na raiz do repositorio.
- O contexto base da API e `/api`.
- Os controllers estaveis usam `/v1/...`, formando rotas finais no padrao `/api/v1/...`.
- A base estavel atual cobre:
  - nutritionists
  - patients
  - clinical anamnesis
  - anthropometric assessments
  - nutrition goals (create, update, findById, delete)

## Fora da base estavel

- `MealPlan` continua em WIP na branch `feature/meal-planning-structure`.
- A listagem de `NutritionGoal` por paciente segue pendente e nao faz parte da base estavel.
- Seguranca, autenticacao e autorizacao entram depois do saneamento da base.

## Referencias locais

- `nutricore-landing/` e `DOCUMENTACAO_TECNICA.md` sao referencias locais e nao compoem a base backend versionada.
- O frontend React + TypeScript deve entrar futuramente em pasta propria, por exemplo `frontend/`, com suporte a rotas publicas e protegidas.

## Como rodar

### Testes

```powershell
.\mvnw.cmd test
```

### Aplicacao

```powershell
.\mvnw.cmd spring-boot:run
```

## Ambiente atual

- Java 21
- Spring Boot 3.3.4
- PostgreSQL para dev/prod
- H2 em memoria para testes
- Flyway para migrations
- MapStruct e Lombok
- Swagger/OpenAPI
