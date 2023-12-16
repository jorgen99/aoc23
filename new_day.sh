#!/bin/bash

case $1 in
    "")     
        DATE=$(date +"%d")
        ;;
    *)
        DATE=$1
        ;;
esac
YEAR=$(date +"%y")

[[ ! -d src/jorgen/aoc${YEAR} ]] && mkdir -p {src,test}/jorgen/aoc${YEAR}
NEW_SRC=src/jorgen/aoc${YEAR}/dec${DATE}.clj
NEW_TEST=test/jorgen/aoc${YEAR}/dec${DATE}_test.clj

cp src/jorgen/aoc${YEAR}/dec_template.txt $NEW_SRC
sed -i '' -e "s/__DAY__/$DATE/g" $NEW_SRC

cp test/jorgen/aoc${YEAR}/dec_template_test.txt $NEW_TEST
sed -i '' -e "s/__DAY__/$DATE/g" $NEW_TEST

touch resources/dec${DATE}{_sample,_input}.txt
