#!/usr/bin/env bash

set -e -x

git pull -r
./gradlew build
git push