#!/bin/sh

help() {
  echo 
  echo "Usage: ./runserver.sh -p [profile-name] [options]"
  echo "  Arguments"
  echo "    [profile-name]       The name of profile to run (ex. dev, staging, production)\n"
  echo "  Options:"
  echo "    -b                   Update runnning server with rebuilding"
  echo "    -m                   Run on M1 Architecture"
  echo "    -s [service-name]    Run only specific service. Select one among maria_rdb, common_api_server"
}

#ERROR CODE
INVALID_ARGUMENT=3


while getopts "bp:hms:" opt
do
case $opt in
  p) profile=$OPTARG ;;
  b) build=true ;;
  h) help && exit 0 ;;
  m) m1=true ;;
  s) service=$OPTARG ;;
  esac
done

case $profile in
  dev)
    filename=docker-compose.dev.yaml ;;
  staging)
    filename=docker-compose.staging.yaml ;;
  production)
    filename=docker-compose.production.yaml ;;
  *)
    RED='\033[0;31m'
    NC='\033[0m'
    echo "${RED}ERROR: Invalid argument, you must set aleast one profile${NC}"
    help
    exit $INVALID_ARGUMENT
    ;;
  esac


command="docker-compose -f ${filename}"

if [[ $profile = "dev" && $m1 ]] 
then
  command="${command} -f docker-compose.dev-m1.yaml"
fi

command="${command} up -d"


if [ $build ]
then
  command="${command} --build"
fi



if [ $service ]
then
  command="${command} ${service}"
fi

echo "COMMAND: $command"
$command

