#!/usr/bin/env bash

source ./env.sh

zip -r $TEMP/log_$1.zip $TEMP/$1/idir

rm -rf  "$TEMP/$1"

