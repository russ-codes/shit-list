#!/bin/bash

totalTests=0
totalIgnored=0
disabledTests=0
disabledSonar=0

countTests() {
   # search all java files for all @Test annotations and count them
   testCount=`find . -iname '*.java' | xargs grep -o \@Test | wc -l`
   #echo "counted $testCount tests"
   totalTests=$(($totalTests + $testCount))
   return $testCount
}

ignoredTests() {
   # search all java files for all @Test annotations and count them
   ignoredCount=`find . -iname '*.java' | xargs grep -o \@Ignore | wc -l`
   #echo "counted $ignoredCount ignored tests"
   totalIgnored=$(($totalIgnored + $ignoredCount))
   return $ignoredCount
}

~/.config/bash/git-pull-all.sh

for d in */ ; do
   cd $d

   countTests
   ignoredTests

   cd ..
done

echo "Total tests: $totalTests"
echo "Total ignored: $totalIgnored"