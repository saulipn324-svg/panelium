$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $projectRoot

docker compose up --build --detach --wait
if ($LASTEXITCODE -ne 0) { throw 'No se pudo iniciar Panelium con Docker Compose.' }

docker compose ps
Write-Host ''
Write-Host 'Panelium esta disponible en http://localhost:3006' -ForegroundColor Green
