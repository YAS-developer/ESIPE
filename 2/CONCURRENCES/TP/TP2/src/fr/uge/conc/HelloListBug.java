package fr.uge.conc;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class HelloListBug {
    public static void main(String[] args) throws InterruptedException {
        var nbThreads = 4;
        var threads = new Thread[nbThreads];
        var list = new ArrayList<Integer>(5000 * nbThreads);

        IntStream.range(0, nbThreads).forEach(j -> {
            Runnable runnable = () -> {
                for (var i = 0; i < 5000; i++) {
                    list.add(i);
                }
            };

            threads[j] = Thread.ofPlatform().start(runnable);
        });

        for (var thread : threads) {
            thread.join();
        }

        System.out.println("Taille finale de la liste : " + list.size());
        System.out.println("Le programme est fini");
    }
}





/* 
2- Exécuter le programme plusieurs fois et noter les différents affichages. 
Résultat attendu :
Taille finale de la liste : 20000
Ce résultat est obtenu occasionnellement, comme dans votre troisième exécution. C'est le résultat correct si tous les ajouts à la liste se sont déroulés sans conflit.
Résultats observés inférieurs à 20000 :
Taille finale de la liste : 19277
Taille finale de la liste : 15888
Taille finale de la liste : 17216

Ces résultats montrent clairement le problème de concurrence. Voici pourquoi cela se produit :

Condition de course : Plusieurs threads essaient d'ajouter des éléments à la liste simultanément.
Opération non atomique : La méthode add() de ArrayList n'est pas atomique. Elle implique plusieurs étapes (vérification de la capacité, augmentation de la taille, ajout de l'élément) qui peuvent être interrompues.
Perte d'ajouts : Quand deux threads essaient d'ajouter un élément en même temps, il est possible qu'un des ajouts soit "perdu". Par exemple, si deux threads lisent la même taille de liste, augmentent cette taille, et ajoutent leur élément, un des ajouts écrasera l'autre.
Résultats incohérents : C'est pourquoi nous obtenons des tailles finales différentes et inférieures à 20000 à chaque exécution. Le nombre exact d'éléments perdus varie en fonction de la façon dont les threads sont ordonnancés par le système d'exploitation. 




3- Expliquer comment la taille de la liste peut être plus petite que le nombre total d'appels à la méthode 

Opération non atomique : L'ajout d'un élément à un ArrayList implique plusieurs étapes :

Lire la taille actuelle
Incrémenter la taille
Ajouter l'élément à l'index correspondant


Scénario de conflit :

Thread A et Thread B lisent simultanément la taille actuelle (par exemple, 10)
Les deux threads incrémentent la taille à 11
Les deux threads ajoutent leur élément à l'index 10


Résultat :

Deux appels à add() ont été effectués
La taille n'a augmenté que de 1 (de 10 à 11)
Un seul nouvel élément est effectivement dans la liste (le dernier écrase le premier)



Ce scénario se répétant de nombreuses fois avec plusieurs threads, le nombre final d'éléments dans la liste devient inférieur au nombre total d'appels à add().





 */