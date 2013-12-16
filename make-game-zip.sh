#!/bin/bash
rm -f one.zip
dir=`mktemp -d lolwatXXXXXX`
curdir=`pwd`
echo $dir
mkdir $dir/ld28-gdx
cp -r ld28-gdx* $dir/ld28-gdx/
cp -r *.bat $dir/ld28-gdx/
cp -r *.command $dir/ld28-gdx/
cd $dir
zip -r one.zip ld28-gdx
mv one.zip $curdir
cd $curdir
rm -rf $dir
