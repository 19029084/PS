#!/usr/bin/env bash

echo "Setup environment...\n"

export PATH=$PATH:~/Projects/cov-analysis-linux64-2020.03/bin
export PATH=$PATH:/usr/bin

export TEMP=~/tmp

export PROJECT=apache-shardingsphere-5.0.0-beta-src-release

export BUILD="mvn clean install -Prelease"







