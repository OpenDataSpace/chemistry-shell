@echo off

set ABS_PATH=%~dp0
set CMD_LINE_ARGS=
:setArgs
if "%1"=="" goto doneSetArgs
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto setArgs
:doneSetArgs

java %JAVA_OPTS% -jar "%ABS_PATH%chemistry-shell-${project.version}.jar" %CMD_LINE_ARGS%

