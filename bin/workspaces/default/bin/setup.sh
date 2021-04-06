call env.bat

rd/s/q "../source" 
rd/s/q "../idir"
rd/s/q "../config"

mkdir ../%1 

7z x -tzip -y -o../%1 ../%2

