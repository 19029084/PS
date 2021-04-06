call env.bat

pushd %cd% 

rd/s/q "../%1/idir"

cd ../%1
cov-build --dir ../%1/idir --config ../%1/config/config.xml %BUILD%
popd
