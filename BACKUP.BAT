@echo off

SET PGPASSWORD=root

SET /p UNIDADE=Informe a unidade: 
SET CAMINHO=%UNIDADE%:/SCA_BACKUP/%date:~6,4%/%date:~3,2%

IF NOT EXIST %CAMINHO:/=\% (
        MKDIR %CAMINHO:/=\%
)


SET H=
SET M=
SET S=

for /f "tokens=1 delims=:, " %%a in ("%time%") do SET H=%%a
for /f "tokens=2 delims=:, " %%a in ("%time%") do SET M=%%a
for /f "tokens=3 delims=:, " %%a in ("%time%") do SET S=%%a


SET ARQ=%CAMINHO%/%date:~6,4%-%date:~3,2%-%date:~0,2%___%H%-%M%-%S%.SQL

pg_dump -U postgres academia > %ARQ%

if ERRORLEVEL 0 (
	echo.

	echo Caminho do arquivo: %ARQ%

	echo.
)

SET /p X=Pressione alguma tecla para finalizar...