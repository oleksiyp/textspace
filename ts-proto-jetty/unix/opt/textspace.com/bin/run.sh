#!/bin/sh

SCRIPT_PATH=$(dirname $(dirname $(readlink -f $0))/..)
cd $SCRIPT_PATH/..

JARS=`ls lib/*.jar | tr '\n' ':'`ts-proto-jetty-0.2.jar

java -cp $JARS com.textspace.App
