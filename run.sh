#!/bin/sh

BASEDIR=`dirname $0`
#JAVA_OPTS="$JAVA_OPTS -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"

java $JAVA_OPTS -jar "$BASEDIR"/target/chemistry-shell-$VERSION.$BUILD_NUMBER.jar "$@"
