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
- administração privada do perfil público do site;
- administração privada dos planos comerciais públicos;
- gestão privada de usuários editoriais com roles e ativação/desativação;
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
- `/editor/site`
- `/editor/planos`
- `/editor/planos/novo`
- `/editor/planos/:id/editar`
- `/editor/posts`
- `/editor/posts/novo`
- `/editor/posts/:id/editar`
- `/editor/articles`
- `/editor/articles/novo`
- `/editor/articles/:id/editar`
- `/editor/recipes`
- `/editor/recipes/novo`
- `/editor/recipes/:id/editar`
- `/editor/usuarios`
- `/editor/usuarios/novo`
- `/editor/usuarios/:id/editar`

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
- `GET /api/v1/admin/public-profile`
- `PUT /api/v1/admin/public-profile`
- `GET /api/v1/admin/public-plans`
- `GET /api/v1/admin/public-plans/{id}`
- `POST /api/v1/admin/public-plans`
- `PUT /api/v1/admin/public-plans/{id}`
- `PATCH /api/v1/admin/public-plans/{id}/activate`
- `PATCH /api/v1/admin/public-plans/{id}/deactivate`
- `DELETE /api/v1/admin/public-plans/{id}`
- `GET /api/v1/admin/users`
- `GET /api/v1/admin/users/{id}`
- `POST /api/v1/admin/users`
- `PUT /api/v1/admin/users/{id}`
- `PATCH /api/v1/admin/users/{id}/activate`
- `PATCH /api/v1/admin/users/{id}/deactivate`
- `PATCH /api/v1/admin/users/{id}/password`
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
- `homolog`
  - PostgreSQL para ambiente de teste/aprovação
  - sem bootstrap automático por padrão
  - configuração orientada por variáveis de ambiente

## Ambiente local com Docker

### Serviços do Compose

- `postgres`
  - banco principal de desenvolvimento
  - volume nomeado `postgres-data`
- `backend`
  - build da aplicação Spring Boot
  - profile `docker`
  - Flyway habilitado
  - healthcheck em `/api/v1/public/profile`
- `frontend`
  - build Vite
  - servido por Nginx
  - proxy de `/api` para o backend
  - healthcheck HTTP na raiz `/`

### Variáveis de ambiente

Copie o arquivo de exemplo:

```powershell
Set-Location C:\Dev\manager
Copy-Item .env.example .env
```

Principais variáveis:

- `SPRING_PROFILES_ACTIVE`
- `POSTGRES_DB`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`
- `POSTGRES_PORT`
- `BACKEND_PORT`
- `FRONTEND_PORT`
- `JWT_SECRET`
- `JWT_ISSUER`
- `JWT_ACCESS_TOKEN_TTL`
- `APP_CORS_ALLOWED_ORIGINS`
- `MEDIA_STORAGE_PATH`
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

## Homologação

Para preparar um ambiente de teste/aprovação:

1. copie [`.env.homolog.example`](/C:/Dev/manager/.env.homolog.example) para `.env.homolog`
2. preencha senha do PostgreSQL, `JWT_SECRET` e origem pública em `APP_CORS_ALLOWED_ORIGINS`
3. habilite o bootstrap apenas no primeiro boot se precisar criar o admin inicial
4. suba com:

```powershell
Set-Location C:\Dev\manager
docker compose --env-file .env.homolog up -d --build
```

Runbook detalhado:

- [HOMOLOGACAO.md](/C:/Dev/manager/docs/HOMOLOGACAO.md)

## Deploy recomendado para teste

Para um deploy rápido de homologação sem reestruturar a aplicação:

- backend + PostgreSQL + storage no Railway
- frontend no Vercel

Guia preparado no repositório:

- [DEPLOY_RAILWAY_VERCEL.md](/C:/Dev/manager/docs/DEPLOY_RAILWAY_VERCEL.md)

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
- `/api/v1/admin/users/**` exige `ADMIN`.

### Sessão no frontend

- login via e-mail e senha;
- token JWT em `sessionStorage`;
- rotas `/editor/**` protegidas por guard;
- token inválido ou expirado encerra a sessão e retorna para `/editor/acesso`.

### Credenciais de desenvolvimento

- profile `test` cria um admin fixo apenas para testes automatizados
- `dev`, `docker` e `homolog` não criam admin por padrão
- para criar o primeiro admin fora de `test`, habilite temporariamente:
  - `APP_SECURITY_BOOTSTRAP_ENABLED=true`
  - `APP_SECURITY_BOOTSTRAP_FULL_NAME`
  - `APP_SECURITY_BOOTSTRAP_EMAIL`
  - `APP_SECURITY_BOOTSTRAP_PASSWORD`
  - `APP_SECURITY_BOOTSTRAP_ROLE=ADMIN`

Depois do primeiro login validado, volte `APP_SECURITY_BOOTSTRAP_ENABLED=false`.

As credenciais de bootstrap vêm das variáveis de ambiente. Para trocar:

- altere `APP_SECURITY_BOOTSTRAP_EMAIL`;
- altere `APP_SECURITY_BOOTSTRAP_PASSWORD`;
- altere `APP_SECURITY_BOOTSTRAP_ROLE`;
- reinicie o backend.

## Administração privada da camada pública

### Perfil público

O admin consegue editar sem depender de código:

- nome profissional e subtítulo;
- hero do site;
- descrição institucional;
- CTAs principais;
- contato e redes;
- blocos estruturados da home;
- depoimentos;
- imagem da hero;
- imagem da seção Sobre.

Fluxo:

1. entrar em `/editor/site`;
2. editar textos e imagens;
3. salvar;
4. confirmar o reflexo imediato em `/`, `/sobre`, `/planos` e `/contato`.

### Planos públicos

O admin consegue:

- listar planos;
- criar plano;
- editar plano;
- ativar e desativar;
- excluir;
- definir ordem de exibição;
- controlar CTA e destaque.

Somente planos ativos aparecem no site público.

### Usuários editoriais

O admin consegue:

- listar contas editoriais;
- criar novo usuário;
- editar nome, e-mail, role e status;
- redefinir senha;
- ativar e desativar acesso.

Regras atuais:

- `ADMIN` enxerga e gerencia usuários;
- `EDITOR` acessa o CMS, mas não gerencia usuários;
- usuário desativado não consegue login;
- o admin não pode se desativar pela própria tela.

## Mídia no MVP

### Imagens

- upload por `POST /api/v1/admin/media/images`;
- retorno em URL pública no formato `/api/media/images/...`;
- persistência em:
  - `target/test-editorial-media` no profile `test`;
  - `storage/editorial-media` em `dev`;
  - `./storage/editorial-media` no Docker.

Fluxo no admin:

1. clicar em `Enviar imagem`;
2. aguardar o upload;
3. o campo recebe a URL pública retornada;
4. salvar o formulário;
5. validar a imagem no site público.

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
8. edite o perfil público em `/editor/site`;
9. altere uma imagem institucional e confirme no site;
10. crie ou edite planos em `/editor/planos`;
11. desative um plano e confirme que ele some do público;
12. crie um usuário em `/editor/usuarios`;
13. redefina a senha desse usuário;
14. desative a conta e confirme o bloqueio de login;
15. publique, despublique, arquive e exclua conteúdos no CMS;
16. confirme que apenas conteúdo `PUBLISHED` aparece no público.

## CI

O repositório possui GitHub Actions em `.github/workflows/ci.yml`.

Checks atuais:

- `Backend tests`
- `Frontend build`
- `Docker validation`

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
