#!/bin/sh

cd src

rm -r ./sc/*

echo "Lecture de grammaireL.sablecc"
java -jar ./../sablecc.jar ./grammaireL.sablecc

echo "Compilation du compilateur"
javac Compiler.java

FILES="./../test/input/*.l"
for f in $FILES
do
	echo "Processing $f"
	java Compiler $f
done

