# Leluxe Cosméticos API

## Configuração de Variáveis de Ambiente

### 1. Configuração Inicial

1. Copie o arquivo `.env.example` para `.env`:

```bash
cp .env.example .env
```

2. Edite o arquivo `.env` com suas configurações específicas.

### 2. Variáveis de Ambiente Disponíveis

#### Banco de Dados PostgreSQL

- `POSTGRES_USER`: Usuário do banco de dados (padrão: postgres)
- `POSTGRES_PASSWORD`: Senha do banco de dados (padrão: 123456)
- `POSTGRES_DB`: Nome do banco de dados (padrão: leluxe-cosmeticos)
- `POSTGRES_PORT`: Porta do PostgreSQL no host (padrão: 5432)

#### Aplicação Spring Boot

- `APP_NAME`: Nome da aplicação (padrão: leluxe-cosmeticos-backend)
- `DB_URL`: URL de conexão com o banco de dados
- `DB_USERNAME`: Usuário para conexão com o banco
- `DB_PASSWORD`: Senha para conexão com o banco
- `SERVER_PORT`: Porta do servidor (padrão: 8080)

#### Segurança

- `DEFAULT_PASSWORD`: Senha padrão para usuários (padrão: 123456)
- `JWT_SECRET`: Chave secreta para JWT (deve ser alterada em produção)

#### Docker

- `POSTGRES_CONTAINER_NAME`: Nome do container PostgreSQL
- `POSTGRES_VOLUME_NAME`: Nome do volume de dados

### 3. Executando com Docker Compose

```bash
# Iniciar os serviços
docker-compose up -d

# Parar os serviços
docker-compose down

# Ver logs
docker-compose logs -f
```

### 4. Segurança

⚠️ **IMPORTANTE**: Em ambiente de produção, altere todas as senhas e chaves secretas padrão!

- Altere `POSTGRES_PASSWORD` para uma senha forte
- Altere `JWT_SECRET` para uma chave secreta única e complexa
- Altere `DEFAULT_PASSWORD` para uma senha segura
- Considere usar um gerenciador de segredos para produção

### 5. Desenvolvimento

Para desenvolvimento local, você pode usar os valores padrão do `.env.example`. O arquivo `.env` está no `.gitignore` para não ser commitado no repositório.
