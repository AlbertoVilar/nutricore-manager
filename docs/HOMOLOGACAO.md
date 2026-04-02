# Homologação

Este documento descreve o fluxo mínimo para subir o NutriCore Manager em um ambiente de teste/aprovação com segurança operacional melhor que a configuração local de desenvolvimento.

## Objetivo

Subir:

- PostgreSQL persistente
- backend Spring Boot com profile `homolog`
- frontend servido por Nginx
- mídia persistente para uploads editoriais
- autenticação JWT com secret próprio

## Arquivos de base

- exemplo de ambiente: [`.env.homolog.example`](/C:/Dev/manager/.env.homolog.example)
- compose principal: [`docker-compose.yml`](/C:/Dev/manager/docker-compose.yml)
- profile backend: [`application-homolog.properties`](/C:/Dev/manager/src/main/resources/application-homolog.properties)

## Passo 1. Preparar variáveis

Copie o arquivo de exemplo:

```powershell
Set-Location C:\Dev\manager
Copy-Item .env.homolog.example .env.homolog
```

Preencha no mínimo:

- `POSTGRES_PASSWORD`
- `JWT_SECRET`
- `APP_CORS_ALLOWED_ORIGINS`

Se o frontend e o backend ficarem no mesmo host e o frontend for servido pelo Nginx do próprio compose, use a URL pública do frontend em `APP_CORS_ALLOWED_ORIGINS`.

## Passo 2. Primeiro boot com admin inicial

O bootstrap de usuário vem desligado por padrão. Para criar o primeiro admin, habilite temporariamente:

```text
APP_SECURITY_BOOTSTRAP_ENABLED=true
APP_SECURITY_BOOTSTRAP_FULL_NAME=Admin Homolog
APP_SECURITY_BOOTSTRAP_EMAIL=admin@seu-dominio.com
APP_SECURITY_BOOTSTRAP_PASSWORD=troque-esta-senha
APP_SECURITY_BOOTSTRAP_ROLE=ADMIN
```

## Passo 3. Subir o ambiente

```powershell
Set-Location C:\Dev\manager
docker compose --env-file .env.homolog up -d --build
```

## Passo 4. Validar bootstrap

Valide:

- frontend carregando
- backend respondendo em `/api/v1/public/profile`
- login editorial funcionando com o admin criado
- `docker compose ps` mostrando `backend`, `frontend` e `postgres` como `healthy`

## Passo 5. Desligar bootstrap

Depois do primeiro login validado:

1. volte `APP_SECURITY_BOOTSTRAP_ENABLED=false`
2. remova a senha temporária do arquivo compartilhado
3. rode novamente:

```powershell
Set-Location C:\Dev\manager
docker compose --env-file .env.homolog up -d
```

Isso evita recriação automática de credenciais em todo restart.

## Passo 6. Checklist funcional

Validar no ambiente:

1. home pública
2. página Sobre
3. Conteúdo
4. Receitas
5. Planos
6. Contato
7. login em `/editor/acesso`
8. edição do perfil público
9. upload de imagem institucional
10. criação/edição/ativação de plano
11. criação/edição/desativação de usuário editorial
12. publicação de post e reflexo no público
13. conteúdo `DRAFT` fora da API pública

## Persistência

- banco: volume `postgres-data`
- mídia: bind mount em `./storage`

Em servidor real, esse diretório deve apontar para disco persistente do host.

## Observações

- `test` continua usando H2 e bootstrap fixo para a suíte automatizada
- `dev`, `docker` e `homolog` não devem subir com admin implícito
- JWT precisa ser próprio do ambiente
- não use o `.env.example` como arquivo final de homologação
