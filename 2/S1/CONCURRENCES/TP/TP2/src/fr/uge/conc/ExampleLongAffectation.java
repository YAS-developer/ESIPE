package fr.uge.conc;

class ExampleLongAffectation {
  long l = -1L;

  public static void main(String[] args) {
    var e = new ExampleLongAffectation();
    Thread.ofPlatform().start(() -> {
      System.out.println("l = " + e.l);
    });
    e.l = 0;
  }
}


/*
Pour ces deux exemples, nous allons examiner les différents affichages possibles en raison des problèmes de concurrence et de réordonnancement des instructions.

Pour ExempleReordering :

Les affichages possibles sont :
a) a = 0  b = 0 : Le thread d'affichage s'exécute avant que les affectations dans le thread principal ne soient visibles.
b) a = 1  b = 0 : Le thread d'affichage s'exécute après l'affectation de a mais avant celle de b.
c) a = 0  b = 2 : En raison du réordonnancement des instructions, b pourrait être affecté avant a.
d) a = 1  b = 2 : Le thread d'affichage s'exécute après que les deux affectations sont visibles.
Il est important de noter que sans synchronisation ou barrières de mémoire, l'ordre des affectations n'est pas garanti d'être vu dans le même ordre par différents threads.

Pour ExampleLongAffectation :

Les affichages possibles sont :
a) l = -1 : Le thread d'affichage s'exécute avant que l'affectation dans le thread principal ne soit visible.
b) l = 0 : Le thread d'affichage s'exécute après que l'affectation est complètement visible.
c) Une valeur "étrange" entre -1 et 0 : Ceci est dû au fait que l'affectation d'un long (64 bits) n'est pas atomique sur les architectures 32 bits. Le thread d'affichage pourrait voir une valeur partiellement mise à jour.
Ce dernier cas est particulièrement problématique et illustre pourquoi les opérations sur les long et double doivent être considérées avec attention dans un contexte multithread.
Dans les deux exemples, l'absence de synchronisation ou de mots-clés comme volatile peut conduire à des comportements imprévisibles dus à la visibilité des modifications entre threads et au réordonnancement des instructions par le compilateur ou le processeur.
Pour garantir un comportement cohérent, il faudrait utiliser des mécanismes de synchronisation appropriés, comme synchronized, volatile, ou les classes atomiques de java.util.concurrent.
 
*/
