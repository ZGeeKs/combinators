#!/bin/bash

# see https://coderwall.com/p/9b_lfq

set -e -u

function mvn_deploy() {
  mvn clean source:jar javadoc:jar deploy \
    --settings="$(dirname $0)/settings.xml" -DskipTests=true "$@"
}

if [ "$CIRCLE_PROJECT_USERNAME/$CIRCLE_PROJECT_REPONAME" == "ZGeeKs/combinators" ] && \
   [ "$CIRCLE_BRANCH" == "master" ]; then
  echo "Publishing Maven snapshot..."

  mvn_deploy

  echo "Maven snapshot published."
fi