#!/bin/bash
set -e

lein uberjar
sudo docker build --rm=true -t orionsbelt/obb-rules-api .
sudo docker push orionsbelt/obb-rules-api
