#!/bin/bash

while true
do
	echo "Veuillez saisir un nombre supérieur à 0"
	read -r b

	if [ $b -ge 1 ]
		then
			break
	fi
done



result=$((b/8))
rest=$((b%8))


if [ $rest -gt 0 ]
	then
		result=$((result+1))
fi

if [ $b -le 8 ]
	then
		if [ $b -eq 1 ]
			then
				echo "Dans $b bit il ya $result octet."
			else
				echo "Dans $b bits il ya $result octet."
		fi	
	else
		echo "Dans $b bits il ya $result octets."
fi





