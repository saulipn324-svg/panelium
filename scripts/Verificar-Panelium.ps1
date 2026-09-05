$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $projectRoot

function Assert-True([bool]$Condition, [string]$Message) {
  if (-not $Condition) { throw $Message }
}

function Send-MultipartImages([string]$Url, [string]$Token, [string]$Field, [string[]]$Paths) {
  Add-Type -AssemblyName System.Net.Http
  $client = [System.Net.Http.HttpClient]::new()
  $content = [System.Net.Http.MultipartFormDataContent]::new()
  try {
    $client.DefaultRequestHeaders.Authorization = [System.Net.Http.Headers.AuthenticationHeaderValue]::new('Bearer', $Token)
    foreach ($path in $Paths) {
      $bytes = [System.IO.File]::ReadAllBytes($path)
      $fileContent = [System.Net.Http.ByteArrayContent]::new($bytes)
      $fileContent.Headers.ContentType = [System.Net.Http.Headers.MediaTypeHeaderValue]::new('image/png')
      $content.Add($fileContent, $Field, [System.IO.Path]::GetFileName($path))
    }
    $response = $client.PostAsync($Url, $content).GetAwaiter().GetResult()
    $responseBody = $response.Content.ReadAsStringAsync().GetAwaiter().GetResult()
    if (-not $response.IsSuccessStatusCode) { throw "Carga multipart rechazada ($([int]$response.StatusCode)): $responseBody" }
    return $responseBody | ConvertFrom-Json
  }
  finally { $content.Dispose(); $client.Dispose() }
}

docker compose up --build --detach --wait
if ($LASTEXITCODE -ne 0) { throw 'Docker Compose no pudo iniciar los servicios.' }

$health = Invoke-RestMethod 'http://localhost:8086/actuator/health'
Assert-True ($health.status -eq 'UP') 'El backend no reporto estado UP.'

$works = Invoke-RestMethod 'http://localhost:8086/api/works'
Assert-True ($works.Count -ge 3) 'El catalogo no contiene las obras iniciales.'

$detail = Invoke-RestMethod 'http://localhost:8086/api/works/neon-ronin'
Assert-True ($detail.chapters.Count -ge 1) 'Neon Ronin no contiene capitulos.'

$manifest = Invoke-RestMethod 'http://localhost:8086/api/chapters/1/manifest'
Assert-True ($manifest.pages.Count -eq 6) 'El manifiesto del lector no contiene seis paginas.'

$settings = @{}
Get-Content '.env' | Where-Object { $_ -match '^[A-Z_]+=' } | ForEach-Object { $key,$value = $_ -split '=',2; $settings[$key] = $value }
$loginBody = @{ email = $settings.ADMIN_EMAIL; password = $settings.ADMIN_PASSWORD } | ConvertTo-Json
$session = Invoke-RestMethod 'http://localhost:8086/api/auth/login' -Method Post -ContentType 'application/json' -Body $loginBody
Assert-True ($session.user.role -eq 'ADMIN') 'No fue posible autenticar al administrador.'
$headers = @{ Authorization = "Bearer $($session.token)" }

$suffix = [DateTimeOffset]::UtcNow.ToUnixTimeSeconds()
$workBody = @{ slug = "prueba-$suffix"; title = "Prueba editorial $suffix"; author = 'Panelium QA'; synopsis = 'Obra temporal creada por la verificacion automatizada.'; format = 'COMIC'; status = 'Borrador'; accent = '#ff4d8d' } | ConvertTo-Json
$createdWork = Invoke-RestMethod 'http://localhost:8086/api/admin/works' -Method Post -Headers $headers -ContentType 'application/json' -Body $workBody
Assert-True ($createdWork.id -gt 0) 'El administrador no pudo crear una obra.'

$coverPath = Join-Path $projectRoot 'frontend\public\covers\neon-ronin.png'
$cover = Send-MultipartImages "http://localhost:8086/api/admin/works/$($createdWork.id)/cover" $session.token 'file' @($coverPath)
$asset = Invoke-WebRequest "http://localhost:8086$($cover.url)" -UseBasicParsing
Assert-True ($asset.StatusCode -eq 200) 'La portada almacenada no se pudo recuperar.'

$chapterBody = @{ number = 1; title = 'Capitulo de prueba'; pageCount = 1; direction = 'LTR' } | ConvertTo-Json
$createdChapter = Invoke-RestMethod "http://localhost:8086/api/admin/works/$($createdWork.id)/chapters" -Method Post -Headers $headers -ContentType 'application/json' -Body $chapterBody
$pageOne = Join-Path $projectRoot 'frontend\public\reader\page-1.png'
$pageTwo = Join-Path $projectRoot 'frontend\public\reader\page-6.png'
$uploadedPages = Send-MultipartImages "http://localhost:8086/api/admin/chapters/$($createdChapter.id)/pages" $session.token 'files' @($pageOne,$pageTwo)
Assert-True ($uploadedPages.Count -eq 2) 'La carga no devolvio las dos paginas.'

$body = @{ chapterId = 1; pageNumber = 4 } | ConvertTo-Json
$saved = Invoke-RestMethod 'http://localhost:8086/api/progress/1' -Method Put -Headers $headers -ContentType 'application/json' -Body $body
Assert-True ($saved.pageNumber -eq 4) 'No se guardo el progreso de lectura.'

docker compose restart
docker compose up --detach --wait

$persisted = Invoke-RestMethod 'http://localhost:8086/api/progress/1' -Headers $headers
Assert-True ($persisted.pageNumber -eq 4) 'El progreso no persistio despues del reinicio.'

Write-Host 'APROBADO: catalogo, lector, autenticacion, roles, progreso y persistencia.' -ForegroundColor Green
Write-Host 'Abre http://localhost:3006' -ForegroundColor Green
