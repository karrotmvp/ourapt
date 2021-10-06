#!/bin/sh



command="docker-compose -f docker-compose.dev.yaml down"

while getopts "v" opt
do
case $opt in
  v) command="${command} -v" ;;
  esac
done

echo "COMMAND: $command"
$command