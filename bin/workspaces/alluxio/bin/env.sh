#!/usr/bin/env bash

export PATH=~/Projects/cov-analysis-linux64-2020.03/bin:$PATH

export PATH=~/Projects/jdk1.8.0_301/bin:$PATH

export TEMP=~/tmp

export PROJECT=

export BUILD="mvn -T 2C clean install -DskipTests -Dmaven.javadoc.skip -Dfindbugs.skip -Dcheckstyle.skip -Dlicense.skip"


