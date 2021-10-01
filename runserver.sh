#!/bin/sh

help() {
  echo 
  echo "Usage: ./runserver.sh -p [profile-name] [options]"
  echo "  Arguments"
  echo "    [profile-name]       The name of profile to run (ex. dev, staging, production)\n"
  echo "  Options:"
  echo "    -u                   Update runnning server container to edited code"
  echo "    -m                   Run on M1 Architecture"
}

#ERROR CODE
INVALID_ARGUMENT=3


while getopts "up:hm" opt
do
case $opt in
  p) profile=$OPTARG ;;
  u) build=true ;;
  h) help && exit 0 ;;
  m) m1=true ;;
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


echo "COMMAND: $command"
$command

