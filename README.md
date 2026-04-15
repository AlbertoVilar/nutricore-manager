<div align="center">

# 🥗 NutriCore Manager

### Plataforma full-stack para presença digital, gestão editorial e base clínica em nutrição

[![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.x-brightgreen?style=for-the-badge&logo=spring)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19.x-61DAFB?style=for-the-badge&logo=react)](https://react.dev)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)](https://www.postgresql.org)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker)](https://www.docker.com)

[🌐 Site e Frontend](./frontend) • [📘 Homologação](./docs/HOMOLOGACAO.md) • [🚀 Deploy](./docs/DEPLOY_RAILWAY_VERCEL.md) • [📊 Swagger](http://localhost:8080/api/swagger-ui/index.html)

</div>

---

## 📖 Visão Geral

O **NutriCore Manager** é um monorepo full-stack que une três frentes complementares:

- **site público** para autoridade profissional, conteúdo, planos e conversão;
- **CMS editorial privado** para administrar perfil público, posts, artigos, receitas e usuários;
- **base clínica** com entidades e APIs de pacientes, nutricionista, anamnese clínica, antropometria e objetivos nutricionais.

Não é só um site institucional. O projeto já combina backend Spring Boot, frontend React, autenticação JWT, PostgreSQL, Flyway e Docker em um fluxo coerente para operação real.

---

## ✨ Principais Capacidades

### 🌍 Camada pública

- página inicial, sobre, conteúdos, receitas, planos e contato;
- renderização pública apenas de conteúdos `PUBLISHED`;
- perfil público administrável sem depender de deploy;
- vitrine comercial e posicionamento profissional.

### 📝 CMS editorial privado

- login editorial com JWT;
- gestão de posts, artigos e receitas;
- gestão do perfil público do site;
- gestão de planos públicos;
- upload de imagens;
- gestão de usuários editoriais com `ADMIN` e `EDITOR`.

### 🩺 Base clínica já presente no backend

- pacientes;
- anamnese clínica;
- avaliação antropométrica;
- objetivos nutricionais;
- entidade de nutricionista;
- base preparada para aprofundar a área clínica nas próximas etapas.

### 🔐 Segurança e operação

- autenticação stateless com Spring Security + JWT;
- controle por roles;
- bootstrap controlado do admin inicial;
- ambiente local com Docker Compose;
- CI com testes de backend, build do frontend e validação Docker.

---

## 🏗️ Arquitetura

O repositório está organizado como monorepo com backend e frontend separados:

```text
.
├── src/                    # Backend Spring Boot
├── frontend/               # Frontend React + TypeScript
├── docs/                   # Runbooks e deploy
├── docker-compose.yml      # Ambiente local completo
├── Dockerfile              # Backend
└── frontend/Dockerfile     # Frontend
```

### Backend

- `api/controllers`: endpoints públicos, editoriais e clínicos;
- `application/security`: autenticação editorial e administração de contas;
- `domain/entities`: núcleo de domínio;
- `domain/services`: regras de negócio por agregado;
- `infrastructure/security`: JWT, filtro, bootstrap e configuração;
- `infrastructure/db/repositories`: persistência JPA;
- `db/migration`: versionamento do banco com Flyway.

### Frontend

- `frontend/src/pages`: páginas públicas e editoriais;
- `frontend/src/components`: blocos de interface reutilizáveis;
- `frontend/src/services`: comunicação com backend;
- `frontend/src/routes`: guards e rotas;
- `frontend/src/hooks`: sessão editorial e dados públicos.

---

## 🧩 Módulos Principais

| Módulo | O que entrega |
|--------|---------------|
| **public site** | presença digital, páginas públicas, conteúdo e planos |
| **editorial cms** | administração de perfil, posts, artigos, receitas e mídia |
| **auth** | login editorial, sessão JWT e gestão de usuários |
| **clinical core** | pacientes, anamnese, antropometria, objetivos e nutricionista |
| **media** | upload e exposição controlada de imagens |

---

## 🛠️ Stack Técnica

### Backend

- Java 21
- Spring Boot 3.3.4
- Spring Security
- JWT
- Spring Data JPA
- PostgreSQL
- H2 para testes
- Flyway
- Lombok
- MapStruct
- Springdoc / OpenAPI

### Frontend

- React 19
- TypeScript
- Vite
- React Router

### Infraestrutura

- Docker
- Docker Compose
- Railway / Vercel previstos para homologação e deploy
- GitHub Actions

---

## 🚀 Como Rodar

### Pré-requisitos

- Java 21
- Node.js 20+
- Docker e Docker Compose

### 1️⃣ Clonar o repositório

```bash
git clone https://github.com/AlbertoVilar/nutricore-manager.git
cd nutricore-manager
```

### 2️⃣ Configurar ambiente

Copie o arquivo de exemplo:

```powershell
Copy-Item .env.example .env
```

### 3️⃣ Subir tudo com Docker

```powershell
docker compose up -d --build
```

### 4️⃣ Endereços locais padrão

- 🌐 Frontend: `http://localhost:4173`
- 🔙 Backend: `http://localhost:8080/api`
- 📊 Swagger: `http://localhost:8080/api/swagger-ui/index.html`

---

## 🔐 Segurança

- `/api/v1/public/**` é público;
- `/api/v1/auth/login` faz o login editorial;
- `/api/v1/auth/me` exige autenticação;
- `/api/v1/admin/**` exige `ADMIN` ou `EDITOR`;
- `/api/v1/admin/users/**` exige `ADMIN`.

No frontend:

- a sessão editorial usa JWT;
- as rotas `/editor/**` são protegidas;
- token inválido ou expirado encerra a sessão;
- o bootstrap do primeiro admin é controlado por variáveis de ambiente.

---

## 🗃️ Banco de Dados e Perfis

Perfis principais do backend:

- `test`: H2 em memória para a suíte de testes;
- `dev`: PostgreSQL local fora de containers;
- `docker`: PostgreSQL via Docker Compose;
- `homolog`: ambiente de teste/aprovação com variáveis de ambiente.

O banco é versionado com Flyway e a mídia editorial persiste em `./storage`.

---

## 🧪 Qualidade

O projeto já possui pipeline para:

- testes do backend;
- build do frontend;
- validação Docker.

Comandos úteis:

```powershell
.\mvnw.cmd test
```

```powershell
Set-Location .\frontend
npm install
npm run build
```

---

## 📚 Documentação

Pontos de entrada úteis:

- [HOMOLOGACAO.md](./docs/HOMOLOGACAO.md)
- [DEPLOY_RAILWAY_VERCEL.md](./docs/DEPLOY_RAILWAY_VERCEL.md)
- [frontend/.env.example](./frontend/.env.example)
- [docker-compose.yml](./docker-compose.yml)

---

## 🎯 Posicionamento do Projeto

Hoje o NutriCore já funciona bem como portfólio porque demonstra:

- backend Java/Spring com segurança, banco e migrations;
- frontend React integrado ao backend real;
- domínio editorial entregue de ponta a ponta;
- presença de base clínica pronta para evolução;
- ambiente executável local com Docker.

---

## 📄 Licença

Licença pública ainda não definida.

---

## 👤 Contato

**José Alberto Vilar Pereira**

- 📧 Email: [albertovilar1@gmail.com](mailto:albertovilar1@gmail.com)
- 💼 LinkedIn: [alberto-vilar-316725ab](https://www.linkedin.com/in/alberto-vilar-316725ab)
- 🐙 GitHub: [@AlbertoVilar](https://github.com/AlbertoVilar)
