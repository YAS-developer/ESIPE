#!/bin/bash

# Vérification du nombre d'arguments
if [ "$#" -ne 1 ]; then
  echo "Usage: $0 repertoire"
  exit 1
fi

repertoire="$1"

# Boucle pour traiter chaque fichier du répertoire
for fichier in "$repertoire"/*; do
  if [ -f "$fichier" ]; then
    base=$(basename "$fichier")
    extension=$(echo "$base" | awk -F . '{if (NF>1) {print $NF}}')

    if [ "$extension" = "jpg" ]; then
      echo "Le fichier $base est OK, rien à lancer"
    else
      newfile="${fichier%.*}.jpg"
      echo "mv $fichier $newfile"
    fi
  fi
done
