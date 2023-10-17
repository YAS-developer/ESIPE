#!/bin/bash

echo "Entrez une suite de mots :"
read input

IFS=' ' read -ra words <<< "$input"

if [ "${#words[@]}" -ge 3 ] 
then
    # Afficher le premier et le troisième mot
    echo "${words[0]} ${words[2]}"
else
    echo "Veuillez saisir au moins trois mots."
fi
