#!/bin/bash

if [ $# -lt 1 ]
    then
        echo "Veuillez rentrez un fichier comme ci-dessous:"$'\n'"$0 fichier.txt"
        exit 1
fi

txt=$(cat $1)



for mot in $txt
do
    firtChar=${mot:0:1}
    if [ "$firtChar" == "t" ] || [ "$firtChar" == "T" ]
        then
            echo $mot   
    fi 
done




