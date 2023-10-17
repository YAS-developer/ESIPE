#!/bin/bash

usage() {
  echo "Usage: $0 [-c t] debut fin"
  echo "  -c t : Affiche les nombres debut, debut+t, debut+2*t, ..., sans dépasser fin."
  exit 1
}

# Vérification du nombre d'arguments
if [ "$#" -lt 2 ]; then
  usage
fi

# Traitement des options
increment=1
while getopts ":c:" opt; do
  case $opt in
    c)
      increment="$OPTARG"
      ;;
    \?)
      echo "Option invalide: -$OPTARG"
      usage
      ;;
  esac
done

# Suppression des options analysées
shift $((OPTIND - 1))

# Vérification des arguments restants
if [ "$#" -ne 2 ]; then
  usage
fi

debut=$1
fin=$2

# Boucle pour afficher les nombres
current=$debut
while [ "$current" -le "$fin" ]; do
  echo "$current"
  current=$((current + increment))
done