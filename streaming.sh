#!/bin/bash

while ! ping -c 1 -n -w 1 setvideo &> /dev/null
do
    echo "server unreachable"
done

export DISPLAY=:0
export PATH=$PATH:./amazon-corretto-11.0.12.7.1-linux-armv7/bin