#!/bin/bash

# Vérification des paramètres
if [ "$#" -lt 2 ]; then
  echo "Usage: $0 fichier_sortie repertoire1 [repertoire2 ...]"
  exit 1
fi

# Récupération du fichier de sortie
fichier_sortie="$1"
shift  # On passe au deuxième paramètre et suivants

# Réinitialisation du fichier de sortie
> "$fichier_sortie"

# Boucle sur les répertoires
while [ "$1" ]; do
  repertoire="$1"
  
  # Vérification de l'existence et de la lisibilité du répertoire
  if [ -d "$repertoire" ] && [ -r "$repertoire" ]; 
  then
    # Comptage des fichiers dans le répertoire et écriture dans le fichier de sortie
    nb_fichiers=$(find "$repertoire" -maxdepth 1 -type f | wc -l)
    echo "Il y a $nb_fichiers fichiers dans $repertoire" >> "$fichier_sortie"
  fi

  shift  # Passage au répertoire suivant
done
