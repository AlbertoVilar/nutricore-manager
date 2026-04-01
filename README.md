# NutriCore Manager

Plataforma em evolucao para nutricionista, combinando:

- area publica/comercial para autoridade profissional, conteudo, receitas, planos e captacao
- area privada/editorial para administrar o que vai ao ar
- area clinica futura para pacientes, anamneses, avaliacoes, metas e acompanhamento

## Estado atual do MVP

Nesta etapa o projeto entrega:

- backend Spring Boot funcional, com testes verdes
- frontend React + TypeScript funcional em `frontend/`
- site publico navegavel
- camada editorial privada para posts, artigos e receitas
- suporte simples a imagem no backend
- suporte a video por URL
- publicacao controlada por status editorial

## Arquitetura resumida

### Backend

- Java 21
- Spring Boot 3.3.4
- PostgreSQL para dev/prod
- H2 em memoria para testes
- Flyway para migrations
- MapStruct + Lombok
- Swagger/OpenAPI

O backend continua na raiz do repositorio e usa:

- `server.servlet.context-path=/api`
- controllers estaveis em `/v1/...`
- rotas finais no padrao `/api/v1/...`

### Frontend

O frontend fica em `frontend/` e segue a organizacao:

```text
frontend/
  src/
    assets/
    components/
    hooks/
    layouts/
    pages/
    routes/
    services/
    types/
    utils/
```

Direcao adotada:

- layout publico e layout editorial separados
- rotas publicas e rotas privadas/editoriais isoladas
- `services` separados da UI
- formularios e tabelas editoriais componentizados
- integracao via client HTTP proprio

## Modelo editorial implementado

### Tipos de conteudo

- `Post`
- `Article`
- `Recipe`

### Status editorial

Todos os modulos trabalham com:

- `DRAFT`
- `PUBLISHED`
- `ARCHIVED`

Regras atuais:

- `DRAFT` nao aparece no publico
- `PUBLISHED` aparece no publico
- `ARCHIVED` nao aparece no publico
- publicar define `publishedAt`
- voltar para rascunho ou arquivar limpa `publishedAt`

### Slug e datas

Todos os modulos editoriais possuem:

- `slug` unico por tipo
- `createdAt`
- `updatedAt`
- `publishedAt`

Se o slug nao for informado, o backend gera automaticamente a partir do titulo e garante unicidade.

## API editorial

### API publica

- `GET /api/v1/public/profile`
- `GET /api/v1/public/plans`
- `GET /api/v1/public/posts`
- `GET /api/v1/public/posts/{slug}`
- `GET /api/v1/public/articles`
- `GET /api/v1/public/articles/{slug}`
- `GET /api/v1/public/recipes`
- `GET /api/v1/public/recipes/{slug}`

### API privada/editorial

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

## Protecao provisoria da area editorial

Autenticacao real ainda nao foi implementada.

Para nao deixar a area editorial solta, o backend usa uma protecao provisoria por header:

- header obrigatorio: `X-Editorial-Token`

Esse mecanismo:

- protege apenas `/api/v1/admin/**`
- existe para esta etapa do MVP
- sera substituido por autenticacao/autorizacao real no futuro

Configuracao atual:

- `application-test.properties` define `app.editorial.admin-token=nutricore-dev-editor`
- `application-dev.properties` usa `EDITORIAL_ADMIN_TOKEN` com fallback local

## Midia no MVP

### Imagens

Foi implementado upload simples de imagem para o backend:

- endpoint: `POST /api/v1/admin/media/images`
- retorno: URL publica da imagem no formato `/api/media/images/...`

No frontend editorial:

- a nutricionista pode subir imagem de capa
- a URL retornada e aplicada no formulario
- a imagem ja aparece no publico quando o conteudo e publicado

Armazenamento:

- em `test`, vai para `target/test-editorial-media`
- em `dev`, vai para `storage/editorial-media` por padrao

Conteudo visual reaproveitado:

- as imagens locais de `C:\Dev\landpage-nutri\public\img` foram copiadas para `frontend/public/images/`
- os arquivos passaram a ser usados como materiais publicos de treino e inspiracao na rotina editorial
- a sessao `Rotina da Nutri - Dando exemplo` usa esse acervo como base visual no MVP

### Videos

Para o MVP nao foi implementado upload de video.

Foi adotado suporte por `videoUrl`, com foco em:

- YouTube
- Vimeo
- links externos equivalentes

No site publico, quando possivel, o link vira embed.

## Rotas do frontend

### Publicas

- `/`
- `/sobre`
- `/conteudos`
- `/conteudos/posts/:slug`
- `/conteudos/artigos/:slug`
- `/receitas`
- `/receitas/:slug`
- `/planos`
- `/contato`

### Editoriais

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

## Como rodar

### Backend

Rodar testes:

```powershell
Set-Location C:\Dev\manager
.\mvnw.cmd test
```

Rodar com H2 em modo local:

```powershell
Set-Location C:\Dev\manager
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=test"
```

Rodar com PostgreSQL em dev:

```powershell
Set-Location C:\Dev\manager
$env:SPRING_PROFILES_ACTIVE='dev'
$env:DB_URL='jdbc:postgresql://localhost:5432/nutricore_manager'
$env:DB_USERNAME='postgres'
$env:DB_PASSWORD='postgres'
$env:EDITORIAL_ADMIN_TOKEN='defina-um-token-seguro'
.\mvnw.cmd spring-boot:run
```

### Frontend

```powershell
Set-Location C:\Dev\manager\frontend
npm install
npm run dev
```

Arquivo de ambiente:

```powershell
Set-Location C:\Dev\manager\frontend
Copy-Item .env.example .env
```

Valor padrao:

```text
VITE_API_BASE_URL=http://localhost:8080/api
```

## Como testar o fluxo editorial

1. Suba o backend em `test` ou `dev`.
2. Suba o frontend em `frontend/`.
3. Acesse `http://localhost:5173/editor/acesso`.
4. Informe o token editorial do ambiente.
5. Crie ou edite posts, artigos e receitas.
6. Salve como rascunho, publique ou arquive.
7. Valide no site publico que apenas conteudo `PUBLISHED` aparece.

Token local de demonstracao no perfil `test`:

```text
nutricore-dev-editor
```

## Validacao realizada nesta etapa

- `.\mvnw.cmd test`
- `npm run build` em `frontend/`
- validacao isolada de runtime com backend em `8081` usando perfil `test`
- respostas HTTP confirmadas:
  - `GET /api/v1/public/articles` -> `200`
  - `GET /api/v1/admin/posts` sem token -> `401`
  - `GET /api/v1/admin/posts` com token -> `200`
  - `GET http://127.0.0.1:5173/conteudos` -> `200`
  - `GET http://127.0.0.1:5173/editor/acesso` -> `200`

## O que ficou fora desta etapa

- autenticacao/JWT e autorizacao real
- area clinica privada
- fechamento de `MealPlan`
- fechamento da listagem de `NutritionGoal` por paciente
- upload real de video
- testes formais de frontend

## Proximos passos recomendados

1. Substituir o token provisorio por autenticacao real da nutricionista.
2. Ligar o perfil publico institucional a uma area privada de configuracao.
3. Criar a primeira camada privada clinica para pacientes e acompanhamento.
4. Retomar `MealPlan` e concluir a parte clinica pendente.
