#!/bin/bash

case "$1" in
  ^[0-9]+$) 
    echo "OK"
    ;;
  *)
    echo "Erreur : La chaîne doit avoir exactement 3 caractères."
    ;;
esac