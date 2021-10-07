#!/bin/sh

help() {
  echo 
  echo "Usage: ./runserver.sh [options]"
  echo "  Options:"
  echo "    -b                   Update runnning server with rebuilding"
  echo "    -m                   Run on M1 Architecture"
  echo "    -s [service-name]    Run only specific service. Select one among maria_rdb, common_api_server"
}

# ERROR CODE
INVALID_ARGUMENT=3


# resolve option
while getopts "bhms:" opt
do
case $opt in
  b) build=true ;;
  h) help && exit 0 ;;
  m) m1=true ;;
  s) service=$OPTARG ;;
  esac
done

filename=docker-compose.dev.yaml

command="docker-compose -f ${filename}"


# m1 architecture case
if [ $m1 ] 
then
  command="${command} -f docker-compose.dev-m1.yaml"
fi

command="${command} up -d"


# rebuild case
if [ $build ]
then
  command="${command} --build"
fi


# select service
case $service in
  rdb) service=maria_rdb ;;
  server) service=common_api_server ;;
esac

if [ $service ]
then
  command="${command} ${service}"
fi

echo "COMMAND: $command"
$command

