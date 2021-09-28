#!/usr/bin/env bash

source ./env.sh

cov-analyze --dir $TEMP/$1/idir --all --disable-fb --strip-path $TEMP/$1/$PROJECT
