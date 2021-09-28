#!/usr/bin/env bash

source ./env.sh


case $2 in
	https)
		cov-commit-defects --dir $TEMP/$1/idir --host $3 --https-port $4 --user $5 --password $6 --stream "$7";;
	http)
		cov-commit-defects --dir $TEMP/$1/idir --host $3 --port $4 --user $5 --password $6 --stream "$7";;
	*)
		cov-commit-defects --dir $TEMP/$1/idir --host $3 --port $4 --user $5 --password $6 --stream "$7";;
esac
