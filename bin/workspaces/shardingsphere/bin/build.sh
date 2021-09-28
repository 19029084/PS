#!/usr/bin/env bash

source ./env.sh

pushd $PWD

rm -rf "$TEMP/$1/idir"

cd $TEMP/$1/$PROJECT

echo "$PWD"

echo -e "cov-build --dir $TEMP/$1/idir --config $TEMP/$1/config/config.xml $BUILD"

cov-build --dir $TEMP/$1/idir --config $TEMP/$1/config/config.xml $BUILD

popd
