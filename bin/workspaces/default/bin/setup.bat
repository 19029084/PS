rem setup id type src

call env.bat

rd/s/q "../source" 
rd/s/q "../idir"
rd/s/q "../config"
rm -rf "../%1"
mkdir "../%1"

if "%2"=="git" goto git
if "%2"=="svn" goto svn
if "%2"=="zip" goto zip
if "%2"=="rar" goto rar

goto end

:git
git clone %3 "../%1" 
goto end

:svn
svn checkout %3 "../%1"
goto end

:zip
7z x -tzip -y -o../%1 ../%3 
goto end

:rar

goto end

:end
echo "successful setup!"
