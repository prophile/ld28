#!/bin/bash
rm -f one.zip
dir=`mktemp -d lolwatXXXXXX`
curdir=`pwd`
echo $dir
mkdir $dir/monorail
cp -r ld28-gdx* $dir/monorail/
cp -r *.bat $dir/monorail/
cp -r *.command $dir/monorail/
cd $dir
zip -r one.zip monorail
mv one.zip $curdir
cd $curdir
rm -rf $dir
