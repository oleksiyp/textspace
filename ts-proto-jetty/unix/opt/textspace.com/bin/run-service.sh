#!/bin/sh

SCRIPT_PATH=$(dirname $(dirname $(readlink -f $0))/..)
cd $SCRIPT_PATH/..

mkdir -p logs

JARS=`ls lib/*.jar | tr '\n' ':'`ts-proto-jetty-0.2.jar


nohup java -Xmx64M -cp $JARS com.textspace.App >> logs/textspace.out 2>> logs/textspace.err  &
echo $! > /var/run/textspace.pid
