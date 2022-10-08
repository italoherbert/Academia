@echo off

SET CAMINHO=%cd:\=/%/BACKUP/%date:~6,4%/%date:~3,2%

if not exist %CAMINHO:/=\% ( mkdir %CAMINHO:/=\% )

SET H=
SET M=
SET S=

for /f "tokens=1 delims=:, " %%a in ("%time%") do SET H=%%a
for /f "tokens=2 delims=:, " %%a in ("%time%") do SET M=%%a
for /f "tokens=3 delims=:, " %%a in ("%time%") do SET S=%%a


SET ARQ=%CAMINHO%/%date:~6,4%-%date:~3,2%-%date:~0,2%___%H%-%M%-%S%.SQL

pg_dump -U postgres academia > %ARQ%

echo.

echo Caminho do arquivo: %ARQ%

echo.

SET /p X=Pressione alguma tecla para finalizar...