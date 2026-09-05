# Panelium

Lector web de cómics, manga y webtoons con catálogo, capítulos y progreso persistente.

## Stack

- Java 21 y Spring Boot 3.5 con monolito modular por dominio
- PostgreSQL 17 y migraciones Flyway
- Next.js 16, React 19 y TypeScript
- MinIO preparado para una siguiente fase de carga editorial
- Docker Compose y GitHub Actions

## Funciones del MVP

- Catálogo con tres obras originales de demostración
- Ficha de obra y listado de capítulos
- Lector vertical con navegación por página
- Registro e inicio de sesión con JWT y BCrypt
- Roles de lector y administrador
- Panel editorial para crear obras y capítulos
- Carga de portadas y páginas en MinIO
- Persistencia de la última página leída por usuario
- Interfaz adaptable a escritorio y móvil
- Cuatro portadas comerciales disponibles solo en la copia local

## Iniciar con Docker

Abre Docker Desktop y ejecuta desde PowerShell:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\Iniciar-Panelium.ps1
```

Abre [http://localhost:3006](http://localhost:3006). La API queda disponible localmente en `http://localhost:8086`.

El acceso administrativo inicial usa los valores `ADMIN_EMAIL` y `ADMIN_PASSWORD` de `.env`. Cámbialos antes de publicar el proyecto.

## Verificar el flujo completo

El siguiente script construye los contenedores y comprueba catálogo, manifiesto del lector, guardado del progreso y persistencia después de reiniciar:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\Verificar-Panelium.ps1
```

## Desarrollo sin Docker

```powershell
cd backend
.\mvnw.cmd test

cd ..\frontend
npm install
npm test
npm run dev
```

Configura `PANELIUM_API_URL=http://localhost:8080` al ejecutar el frontend localmente.

## Contenido y derechos

La portada de **Neon Ronin** es un recurso original de demostración. Las portadas proporcionadas por el propietario del proyecto se usan únicamente en su biblioteca local y están excluidas de Git para evitar redistribuir material comercial.

Consulta [Arquitectura](docs/ARCHITECTURE.md) y [API](docs/API.md).
