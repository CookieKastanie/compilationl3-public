#!/bin/sh

FILES="./../input/*.sa"

echo "\n\n\n############################ START ###########################\n"

for f in $FILES
do
   	name=$(basename "$f" ".sa")
	echo "Processing $name"
	./compare_arbres_xml $f "./../sa-ref/$name.sa"
	echo "\n##############################################################\n"
done

