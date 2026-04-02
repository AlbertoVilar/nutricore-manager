# Deploy de homologação com Railway + Vercel

Este fluxo é o caminho mais simples para publicar o NutriCore Manager em um ambiente de teste/aprovação:

- backend Spring Boot no Railway
- PostgreSQL no Railway
- volume persistente no Railway para uploads editoriais
- frontend React/Vite no Vercel

## Arquivos preparados no repositório

- [`railway.toml`](/C:/Dev/manager/railway.toml)
- [`Dockerfile`](/C:/Dev/manager/Dockerfile)
- [`frontend/vercel.json`](/C:/Dev/manager/frontend/vercel.json)
- [`frontend/.env.production.example`](/C:/Dev/manager/frontend/.env.production.example)

## 1. Backend no Railway

### Criar o projeto

1. Crie um projeto vazio no Railway.
2. Adicione um serviço PostgreSQL.
3. Conecte o repositório GitHub do NutriCore ao serviço backend.

O Railway usa o `Dockerfile` da raiz automaticamente. O [`railway.toml`](/C:/Dev/manager/railway.toml) fixa:

- builder `DOCKERFILE`
- healthcheck em `/api/v1/public/profile`
- restart policy `ON_FAILURE`

### Variáveis do backend

Defina no serviço backend do Railway:

- `SPRING_PROFILES_ACTIVE=homolog`
- `DB_URL=jdbc:postgresql://<HOST_INTERNO_POSTGRES>:5432/nutricore_manager`
- `DB_USERNAME=<USUARIO_POSTGRES>`
- `DB_PASSWORD=<SENHA_POSTGRES>`
- `JWT_SECRET=<SEGREDO_COM_32+_CARACTERES>`
- `JWT_ISSUER=nutricore-manager-homolog`
- `JWT_ACCESS_TOKEN_TTL=PT8H`
- `APP_CORS_ALLOWED_ORIGINS=https://<SEU_FRONTEND_VERCEL>.vercel.app`
- `MEDIA_STORAGE_PATH=/app/storage/editorial-media`

Bootstrap do primeiro admin:

- `APP_SECURITY_BOOTSTRAP_ENABLED=true`
- `APP_SECURITY_BOOTSTRAP_FULL_NAME=Admin Homolog`
- `APP_SECURITY_BOOTSTRAP_EMAIL=admin@seu-dominio.com`
- `APP_SECURITY_BOOTSTRAP_PASSWORD=<SENHA_FORTE>`
- `APP_SECURITY_BOOTSTRAP_ROLE=ADMIN`

Depois do primeiro login validado, volte:

- `APP_SECURITY_BOOTSTRAP_ENABLED=false`
- remova a senha temporária compartilhada do painel, se houver

### Volume persistente

Crie um volume no Railway e monte no backend em:

- `/app/storage`

Isso preserva as imagens enviadas pelo CMS.

## 2. Frontend no Vercel

### Criar o projeto

1. Importe o mesmo repositório no Vercel.
2. Defina o Root Directory como:
   - `frontend`
3. Use o preset de framework Vite.

O arquivo [`frontend/vercel.json`](/C:/Dev/manager/frontend/vercel.json) garante deep link das rotas SPA:

- `/sobre`
- `/conteudos`
- `/planos`
- `/editor/acesso`

### Variável do frontend

No Vercel, defina:

- `VITE_API_BASE_URL=https://<SEU-BACKEND-RAILWAY>.up.railway.app/api`

Use [`frontend/.env.production.example`](/C:/Dev/manager/frontend/.env.production.example) como referência local.

## 3. Ordem recomendada de subida

1. subir PostgreSQL no Railway
2. subir backend no Railway
3. confirmar healthcheck do backend
4. criar admin inicial via bootstrap temporário
5. desligar bootstrap
6. subir frontend no Vercel com `VITE_API_BASE_URL` apontando para o backend Railway
7. validar o fluxo completo

## 4. Checklist de validação

### Público

- home abre
- sobre abre
- conteúdos abre
- receitas abre
- planos abre
- contato abre

### Editorial

- login em `/editor/acesso`
- dashboard abre
- edição do perfil público funciona
- planos refletem no público
- upload de imagem persiste
- criação/edição de conteúdo funciona
- `DRAFT` não vaza no público

## 5. Observações

- O frontend no Vercel depende do backend Railway estar acessível publicamente.
- O backend precisa do volume montado para não perder imagens em novo deploy.
- Para a homologação atual, Railway + Vercel é suficiente. Produção depois pode ser consolidada em outra arquitetura, se necessário.
