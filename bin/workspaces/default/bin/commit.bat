call env.bat

if "%2" =="http" goto http
if "%2" =="https" goto https

goto end

:http
	cov-commit-defects --dir ../%1/idir --host %3 --port %4 --user %5 --password %6 --stream %7
	goto end

:https
	cov-commit-defects --dir ../%1/idir --host %3 --https-port %4 --user %5 --password %6 --stream %7
	goto end	
		
:end