#!/usr/bin/env bash

echo "setup ..."

source ./env.sh

rm -rf "$TEMP/source" 
rm -rf "$TEMP/idir"
rm -rf "$TEMP/config"
rm -rf "$TEMP/$1"

mkdir $TEMP/$1 

case $2 in 
	git)
		echo -e "git clone $3\n"
		git clone $3 "$TEMP/$1" ;;
		
	zip)
		echo -e "unzip -o \"../$3\" -d \"$TEMP/$1\""
		unzip -o "../$3" -d "$TEMP/$1" ;;
	
	svn)
		echo -e "svn clone $3\n";;	
	
	*)
		echo -e "ERROR: unsupport $2\n";;
esac
		
