@echo off
echo Starting Locker...
java -jar Locker.jar localhost:1234
echo Stopped.
timeout 1
tasklist /FI "IMAGENAME eq explorer.exe" 2>NUL | find /I /N "explorer.exe">NUL
if "%ERRORLEVEL%"=="0" (
	echo explorer.exe is running
) else (
	echo explorer.exe not running, starting it...
	start /b explorer.exe
	echo Done.
)
exit