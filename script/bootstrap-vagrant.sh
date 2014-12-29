#!/usr/bin/env bash

sudo apt-get update
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 36A1D7869245C8950F966E92D8576A8BA88D21E9
sudo apt-get install -y openjdk-7-jdk
sudo apt-get install -y curl
curl -sSL https://get.docker.com/ubuntu/ | sudo sh
sudo wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
sudo cp lein /usr/local/bin
sudo chmod a+x /usr/local/bin/lein
