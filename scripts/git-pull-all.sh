#!/bin/bash

# User of this script is expected to execute from a directory containing many git directories

echo "#### starting git pulls ####"
for d in */ ; do
    cd $d
    git pull
    cd ..
done
echo "#### finished git pulls ####"