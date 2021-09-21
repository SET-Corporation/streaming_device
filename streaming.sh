#!/bin/bash

while ! ping -c 1 -n -w 1 setvideo &> /dev/null
do
    echo "server unreachable"
done

export DISPLAY=:0
