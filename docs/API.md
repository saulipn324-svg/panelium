# API de Panelium

Base local: `http://localhost:8086/api`

## Rutas públicas

- `POST /auth/register`: crea una cuenta con rol `READER`.
- `POST /auth/login`: devuelve un JWT de ocho horas y el perfil.
- `GET /works`: lista y filtra el catálogo con `q` y `format`.
- `GET /works/{slug}`: devuelve una obra con sus capítulos.
- `GET /chapters/{id}/manifest`: devuelve dirección y páginas del lector.
- `GET /assets/{key}`: transmite una imagen almacenada en MinIO.

## Rutas autenticadas

Incluyen `Authorization: Bearer <token>`.

- `GET /progress/{workId}`: consulta el avance del usuario autenticado.
- `PUT /progress/{workId}`: guarda `{ "chapterId": 1, "pageNumber": 4 }`.

## Rutas administrativas

Requieren un JWT con rol `ADMIN`.

- `POST /admin/works`: crea una obra.
- `POST /admin/works/{id}/cover`: carga una portada como multipart `file`.
- `POST /admin/works/{id}/chapters`: crea un capítulo.
- `POST /admin/chapters/{id}/pages`: reemplaza y ordena las páginas recibidas como multipart `files`.

Las cargas aceptan imágenes de hasta 15 MB por archivo y 200 MB por solicitud.
