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

## CI e fluxo de entrega

O repositorio agora possui uma pipeline minima no GitHub Actions em `.github/workflows/ci.yml`.

Ela roda automaticamente em:

- `push` para `main`
- `push` para `develop`
- `push` para `codex/**`
- `pull_request` para `main`
- `pull_request` para `develop`

Checks atuais:

- `Backend tests`: executa `./mvnw -B test`
- `Frontend build`: executa `npm ci` e `npm run build` em `frontend/`

Fluxo recomendado:

1. trabalhar em branch dedicada
2. validar localmente backend e frontend
3. subir a branch
4. abrir PR para `develop`
5. usar a pipeline como gate minimo antes de merge

## Arquitetura de navegacao

### Camadas do produto

- area institucional:
  - `/`
  - `/sobre`
- area de conteudo:
  - `/conteudos`
  - `/receitas`
- area comercial:
  - `/planos`
  - `/contato`
- area editorial privada:
  - `/editor/acesso`
  - `/editor/**`

### Regras de separacao

- o site publico nao expõe links de CMS no header, na home ou no footer principal
- a tela `/editor/acesso` fica fora do shell publico
- a area editorial oferece caminho de retorno para o site publico
- a home organiza institucional, conversao e conteudo em blocos distintos

### Regra para `Rotina da Nutri`

`Rotina da Nutri` nao virou modulo novo.

Decisao adotada:

- continua dentro de `Post`
- funciona como colecao editorial
- a regra atual usa posts com categoria `Treino`
- na home aparece como destaque visual unico
- em `/conteudos` aparece como secao propria dentro da biblioteca

### Hierarquia atual da home

1. hero institucional com CTA principal
2. como funciona
3. abordagem da nutricionista
4. planos de atendimento
5. destaque `Rotina da Nutri`
6. biblioteca de conteudo
7. receitas
8. relatos
9. CTA final de contato

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

- `POST /api/v1/auth/login`
- `GET /api/v1/auth/me`

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

## Autenticacao e autorizacao editorial

Foi implementada autenticacao real com Spring Security + JWT para separar definitivamente a camada publica da camada privada editorial.

### Regras de acesso

- `/api/v1/public/**` -> publico
- `/api/v1/auth/login` -> publico para login
- `/api/v1/auth/me` -> autenticado
- `/api/v1/admin/**` -> exige role `ADMIN` ou `EDITOR`
- demais rotas privadas do backend continuam reservadas a `ADMIN`

### Sessao

- o login retorna `accessToken`, `tokenType`, `expiresAt` e o usuario autenticado
- o frontend guarda a sessao em `sessionStorage`
- as requisicoes editoriais usam `Authorization: Bearer <token>`
- quando o token expira ou fica invalido, o frontend limpa a sessao local e redireciona de volta para `/editor/acesso`

### Credenciais de desenvolvimento

Bootstrap local configurado por propriedades:

- nome: `Alberto Vilar`
- email: `albertovilar1@gmail.com`
- senha inicial: `132747`
- role: `ADMIN`

Configuracao atual:

- `application-test.properties` ativa o bootstrap automaticamente
- `application-dev.properties` ativa o bootstrap por padrao, com override por variaveis de ambiente

Para trocar depois:

- altere `APP_SECURITY_BOOTSTRAP_EMAIL`
- altere `APP_SECURITY_BOOTSTRAP_PASSWORD`
- altere `APP_SECURITY_BOOTSTRAP_ROLE`
- depois reinicie o backend

Observacao:

- o bootstrap existe para ambiente local e deve evoluir no futuro para um fluxo administrativo controlado, sem depender de seed permanente em producao

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
.\mvnw.cmd -DskipTests package
java -jar target\manager-0.0.1-SNAPSHOT.jar --spring.profiles.active=test
```

Rodar com PostgreSQL em dev:

```powershell
Set-Location C:\Dev\manager
$env:DB_URL='jdbc:postgresql://localhost:5432/nutricore_manager'
$env:DB_USERNAME='postgres'
$env:DB_PASSWORD='postgres'
$env:APP_SECURITY_BOOTSTRAP_EMAIL='albertovilar1@gmail.com'
$env:APP_SECURITY_BOOTSTRAP_PASSWORD='132747'
.\mvnw.cmd -DskipTests package
java -jar target\manager-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
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

Se voce optar por rodar o frontend em uma porta diferente de `5173`, ajuste tambem o backend:

```powershell
$env:APP_CORS_ALLOWED_ORIGINS='http://localhost:5173,http://127.0.0.1:5173,http://127.0.0.1:4173'
```

## Como testar o fluxo editorial

1. Suba o backend em `test` ou `dev`.
2. Suba o frontend em `frontend/`.
3. Acesse `http://localhost:5173/editor/acesso`.
4. Faça login com email e senha.
5. Crie ou edite posts, artigos e receitas.
6. Salve como rascunho, publique ou arquive.
7. Valide no site publico que apenas conteudo `PUBLISHED` aparece.

Credenciais locais de demonstracao no perfil `test`:

```text
email: albertovilar1@gmail.com
senha: 132747
```

## Como validar a navegacao visualmente

Com backend e frontend rodando:

1. abra `http://localhost:5173/`
2. valide a ordem da home:
   - institucional
   - planos
   - rotina
   - conteudo
   - receitas
   - relatos
   - contato
3. abra `http://localhost:5173/conteudos`
4. confirme a separacao entre:
   - artigos
   - `Rotina da Nutri`
   - posts e bastidores
5. abra `http://localhost:5173/receitas`
6. abra `http://localhost:5173/planos`
7. abra `http://localhost:5173/contato`
8. confirme que o CMS nao aparece no menu publico nem no footer principal
9. abra `http://localhost:5173/editor/acesso`
10. confirme que o acesso editorial usa shell proprio, separado do site publico

## Validacao realizada nesta etapa

- `.\mvnw.cmd test`
- `.\mvnw.cmd -DskipTests package`
- `npm run build` em `frontend/`
- validacao isolada de runtime com backend em `8080` usando perfil `test`
- respostas HTTP confirmadas:
  - `GET /api/v1/public/articles` -> `200`
  - `POST /api/v1/auth/login` com credencial valida -> `200`
  - `POST /api/v1/auth/login` com credencial invalida -> `401`
  - `GET /api/v1/admin/posts` sem token -> `401`
  - `GET /api/v1/admin/posts` com bearer token -> `200`
  - `GET /api/v1/auth/me` com bearer token -> `200`
  - `GET /api/v1/public/posts/{slug}` para draft -> `404`
  - `GET /api/v1/public/posts/{slug}` para publicado -> `200`
  - `GET http://127.0.0.1:5173/conteudos` -> `200`
  - `GET http://127.0.0.1:5173/editor/acesso` -> `200`
- validacao visual real com Edge headless em:
  - `/`
  - `/conteudos`
  - `/receitas`
  - `/planos`
  - `/contato`
  - `/editor/acesso`
  - `/editor`
  - `/editor/posts`
- validacao de redirecionamento:
  - `/editor` sem sessao -> redireciona para `/editor/acesso`
  - login com credencial valida -> abre `/editor`
  - logout -> retorna para `/editor/acesso`

## O que ficou fora desta etapa

- area clinica privada
- fechamento de `MealPlan`
- fechamento da listagem de `NutritionGoal` por paciente
- upload real de video
- testes formais de frontend

## Proximos passos recomendados

1. Ligar perfil publico institucional e planos a configuracao privada autenticada.
2. Adicionar refresh token ou estrategia equivalente quando a area privada crescer.
3. Criar a primeira camada privada clinica para pacientes e acompanhamento.
4. Retomar `MealPlan` e concluir a parte clinica pendente.
