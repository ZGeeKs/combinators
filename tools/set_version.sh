#!/bin/bash

set -eu

if (( $# != 1 )); then
  echo "Usage: set_versions.sh <version>" >&2
  exit 1
fi

version="$1"

mvn versions:set versions:commit -DnewVersion="${version}"
git commit -am "Set version numbers to ${version}"