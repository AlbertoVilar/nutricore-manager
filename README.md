# NutriCore Manager

Plataforma em evolução para nutricionista, unindo:

- site público para autoridade profissional, conteúdo, receitas, planos e conversão;
- área editorial privada para publicar e administrar o que vai ao ar;
- base preparada para a futura camada clínica.

## Estado atual

Nesta etapa, o projeto entrega:

- backend Spring Boot funcional;
- frontend React + TypeScript funcional;
- autenticação editorial real com Spring Security + JWT;
- CMS privado para posts, artigos e receitas;
- upload simples de imagem;
- conteúdo público filtrando apenas itens `PUBLISHED`;
- ambiente local com Docker, PostgreSQL e persistência para desenvolvimento.

## Stack

### Backend

- Java 21
- Spring Boot 3.3.4
- Spring Security
- JWT stateless
- PostgreSQL para desenvolvimento e uso containerizado
- H2 em memória para testes
- Flyway para migrations
- Lombok + MapStruct
- Springdoc / OpenAPI

### Frontend

- React 19
- TypeScript
- Vite
- React Router

## Estrutura do repositório

```text
.
├── frontend/
│   ├── src/
│   │   ├── assets/
│   │   ├── components/
│   │   ├── hooks/
│   │   ├── layouts/
│   │   ├── pages/
│   │   ├── routes/
│   │   ├── services/
│   │   ├── types/
│   │   └── utils/
│   ├── Dockerfile
│   └── nginx.conf
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── Dockerfile
├── docker-compose.yml
└── .env.example
```

## Arquitetura funcional

### Rotas públicas

- `/`
- `/sobre`
- `/conteudos`
- `/conteudos/posts/:slug`
- `/conteudos/artigos/:slug`
- `/receitas`
- `/receitas/:slug`
- `/planos`
- `/contato`
- `/acessos`

### Rotas privadas editoriais

- `/editor/acesso`
- `/editor`
- `/editor/posts`
- `/editor/posts/novo`
- `/editor/posts/:id/editar`
- `/editor/articles`
- `/editor/articles/novo`
- `/editor/articles/:id/editar`
- `/editor/recipes`
- `/editor/recipes/novo`
- `/editor/recipes/:id/editar`

### APIs

#### Públicas

- `GET /api/v1/public/profile`
- `GET /api/v1/public/plans`
- `GET /api/v1/public/posts`
- `GET /api/v1/public/posts/{slug}`
- `GET /api/v1/public/articles`
- `GET /api/v1/public/articles/{slug}`
- `GET /api/v1/public/recipes`
- `GET /api/v1/public/recipes/{slug}`

#### Autenticação

- `POST /api/v1/auth/login`
- `GET /api/v1/auth/me`

#### Editoriais

- `GET /api/v1/admin/posts`
- `GET /api/v1/admin/posts/{id}`
- `POST /api/v1/admin/posts`
- `PUT /api/v1/admin/posts/{id}`
- `PATCH /api/v1/admin/posts/{id}/publish`
- `PATCH /api/v1/admin/posts/{id}/draft`
- `PATCH /api/v1/admin/posts/{id}/archive`
- `DELETE /api/v1/admin/posts/{id}`
- `GET /api/v1/admin/articles`
- `GET /api/v1/admin/articles/{id}`
- `POST /api/v1/admin/articles`
- `PUT /api/v1/admin/articles/{id}`
- `PATCH /api/v1/admin/articles/{id}/publish`
- `PATCH /api/v1/admin/articles/{id}/draft`
- `PATCH /api/v1/admin/articles/{id}/archive`
- `DELETE /api/v1/admin/articles/{id}`
- `GET /api/v1/admin/recipes`
- `GET /api/v1/admin/recipes/{id}`
- `POST /api/v1/admin/recipes`
- `PUT /api/v1/admin/recipes/{id}`
- `PATCH /api/v1/admin/recipes/{id}/publish`
- `PATCH /api/v1/admin/recipes/{id}/draft`
- `PATCH /api/v1/admin/recipes/{id}/archive`
- `DELETE /api/v1/admin/recipes/{id}`
- `POST /api/v1/admin/media/images`

## Perfis do backend

- `test`
  - H2 em memória
  - usado na suíte de testes
- `dev`
  - PostgreSQL local
  - usado fora de containers
- `docker`
  - PostgreSQL via Docker Compose
  - mídia persistida em `./storage`

## Ambiente local com Docker

### Serviços do Compose

- `postgres`
  - banco principal de desenvolvimento
  - volume nomeado `postgres-data`
- `backend`
  - build da aplicação Spring Boot
  - profile `docker`
  - Flyway habilitado
- `frontend`
  - build Vite
  - servido por Nginx
  - proxy de `/api` para o backend

### Variáveis de ambiente

Copie o arquivo de exemplo:

```powershell
Set-Location C:\Dev\manager
Copy-Item .env.example .env
```

Principais variáveis:

- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`
- `POSTGRES_PORT`
- `BACKEND_PORT`
- `FRONTEND_PORT`
- `JWT_SECRET`
- `APP_SECURITY_BOOTSTRAP_ENABLED`
- `APP_SECURITY_BOOTSTRAP_FULL_NAME`
- `APP_SECURITY_BOOTSTRAP_EMAIL`
- `APP_SECURITY_BOOTSTRAP_PASSWORD`
- `APP_SECURITY_BOOTSTRAP_ROLE`

### Subir tudo com Docker

```powershell
Set-Location C:\Dev\manager
docker compose up -d --build
```

### Parar o ambiente

```powershell
Set-Location C:\Dev\manager
docker compose down
```

### Parar e limpar volumes do banco

```powershell
Set-Location C:\Dev\manager
docker compose down -v
```

### Endereços padrão com Docker

- frontend: `http://localhost:4173`
- backend: `http://localhost:8080/api`
- Swagger: `http://localhost:8080/api/swagger-ui/index.html`

## Execução local sem Docker

### Backend com PostgreSQL local

```powershell
Set-Location C:\Dev\manager
$env:DB_URL='jdbc:postgresql://localhost:5432/nutricore_manager'
$env:DB_USERNAME='postgres'
$env:DB_PASSWORD='postgres'
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

### Backend com H2 para testes rápidos

```powershell
Set-Location C:\Dev\manager
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=test"
```

### Frontend

```powershell
Set-Location C:\Dev\manager\frontend
Copy-Item .env.example .env
npm install
npm run dev
```

Arquivo `frontend/.env.example`:

```text
VITE_API_BASE_URL=http://localhost:8080/api
```

## Banco de dados e persistência

- o fluxo principal de desenvolvimento usa PostgreSQL;
- o H2 continua apenas para testes;
- o Flyway roda automaticamente em `dev` e `docker`;
- no Docker, o banco persiste no volume `postgres-data`;
- a mídia enviada pelo CMS persiste em `./storage`, ignorado no Git.

## Autenticação editorial

### Regras de acesso

- `/api/v1/public/**` é público;
- `/api/v1/auth/login` é público para login;
- `/api/v1/auth/me` exige autenticação;
- `/api/v1/admin/**` exige `ADMIN` ou `EDITOR`.

### Sessão no frontend

- login via e-mail e senha;
- token JWT em `sessionStorage`;
- rotas `/editor/**` protegidas por guard;
- token inválido ou expirado encerra a sessão e retorna para `/editor/acesso`.

### Credenciais de desenvolvimento

- nome: `Alberto Vilar`
- e-mail: `albertovilar1@gmail.com`
- senha inicial: `132747`
- role: `ADMIN`

Essas credenciais vêm do bootstrap configurado por propriedades e variáveis de ambiente. Para trocar:

- altere `APP_SECURITY_BOOTSTRAP_EMAIL`;
- altere `APP_SECURITY_BOOTSTRAP_PASSWORD`;
- altere `APP_SECURITY_BOOTSTRAP_ROLE`;
- reinicie o backend.

## Mídia no MVP

### Imagens

- upload por `POST /api/v1/admin/media/images`;
- retorno em URL pública no formato `/api/media/images/...`;
- persistência em:
  - `target/test-editorial-media` no profile `test`;
  - `storage/editorial-media` em `dev`;
  - `./storage/editorial-media` no Docker.

### Vídeos

- não há upload de vídeo nesta etapa;
- o CMS aceita `videoUrl`;
- o site público faz embed de YouTube e Vimeo quando possível.

## Revisão textual em PT-BR

Nesta etapa foi feita uma varredura de pré-homologação para:

- corrigir acentuação e cedilha;
- remover microcopy técnica exposta ao usuário;
- revisar textos públicos, editoriais e mensagens de erro;
- normalizar os seeds públicos/editoriais por migration nova, sem reescrever histórico antigo do Flyway.

## Validação recomendada

### Técnica

```powershell
Set-Location C:\Dev\manager
.\mvnw.cmd test
```

```powershell
Set-Location C:\Dev\manager\frontend
npm run build
```

```powershell
Set-Location C:\Dev\manager
docker compose up -d --build
```

### Funcional

Com frontend e backend em execução:

1. abra `/`;
2. abra `/conteudos`;
3. abra `/receitas`;
4. abra `/planos`;
5. abra `/contato`;
6. abra `/acessos`;
7. faça login em `/editor/acesso`;
8. publique, despublique, arquive e exclua conteúdos no CMS;
9. confirme que apenas conteúdo `PUBLISHED` aparece no público.

## CI

O repositório possui GitHub Actions em `.github/workflows/ci.yml`.

Checks atuais:

- `Backend tests`
- `Frontend build`

Branches monitoradas:

- `main`
- `develop`
- `nutri-manage/**`

## O que continua fora desta etapa

- área clínica privada;
- pacientes, anamneses e avaliações;
- `MealPlan`;
- fechamento da listagem de `NutritionGoal` por paciente;
- refresh token;
- suíte formal de testes do frontend.

## Próximos passos recomendados

1. finalizar o ambiente de homologação;
2. configurar CI/CD com deploy automatizado;
3. adicionar observabilidade mínima;
4. abrir a próxima frente funcional somente depois da homologação do CMS e do site público.
