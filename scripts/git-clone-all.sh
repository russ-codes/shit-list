#!/bin/bash

# User of this script is expected to create a file called repos.config
# first line of repos.config should contain the clone base url for all the projects
# the rest of the lines of the file should just be a copy/paste of all the repositories from the bitbucket project page


# Get the git clone URL base
cloneBase=$(head -n 1 repos.config)
echo "this is the base clone url: $cloneBase"

# For all Repository lines
cat repos.config | while read line 
do
   if [[ $line == Repository* ]]
   then
    #Remove the prefix Repository from the line
    repoName=${line#"Repository"}

    cloneUrl="$cloneBase$repoName.git"
    git clone $cloneUrl
   fi
done