1- Pourquoi n'est il pas possible d’arrêter un thread de façon non coopérative ?


En Java, il n'est pas possible d'arrêter un thread de façon non coopérative (avec stop(), suspend(), resume()) car cela pose plusieurs problèmes critiques :

Risque de corruption des données :


Le thread peut être interrompu au milieu d'une opération critique
Les objets peuvent rester dans un état incohérent
Les verrous ne sont pas libérés correctement


Problèmes de sécurité :


Le thread peut être stoppé pendant qu'il manipule des ressources sensibles
Les mécanismes de sécurité peuvent être contournés

La solution recommandée est d'utiliser un mécanisme coopératif via un drapeau d'interruption (interrupt()) qui permet au thread de s'arrêter proprement en :

Vérifiant périodiquement s'il doit s'arrêter
Nettoyant ses ressources
Relâchant ses verrous


2- Rappeler ce qu'est un appel de méthode bloquant.


Un appel de méthode bloquant est une opération qui suspend l'exécution du thread courant jusqu'à ce qu'un certain événement se produise. Le thread entre alors dans l'état "WAITING" ou "TIMED_WAITING".

Exemples typiques :

    socket.accept() : attend une connexion cliente
    inputStream.read() : attend des données disponibles
    thread.join() : attend la fin d'un autre thread
    lock.lock() : attend qu'un verrou soit libéré
    object.wait() : attend une notification

Ces appels permettent d'éviter la consommation inutile de CPU par rapport à une attente active (busy waiting), car le thread libère le processeur pendant qu'il est bloqué.


3- À quoi sert la méthode d'instance interrupt() de la classe Thread?


La méthode interrupt() sert à signaler à un thread qu'il devrait s'arrêter, mais c'est une demande coopérative, pas un arrêt forcé.
Son fonctionnement :

Si le thread est bloqué (dans des appels comme sleep(), wait(), join()) :

L'appel bloquant est interrompu
Une InterruptedException est levée


Si le thread n'est pas bloqué :

Le drapeau d'interruption est activé
Le thread peut vérifier ce drapeau via Thread.interrupted() ou isInterrupted()


Exemple typique d'utilisation :

```java
	class MonThread extends Thread {
	    public void run() {
	        while (!isInterrupted()) {
	            try {
	                // travail
	                sleep(1000);
	            } catch (InterruptedException e) {
	                // réagir à l'interruption
	                interrupt(); // réactiver le drapeau
	                break;
	            }
	        }
	    }
	}
```

4 Expliquer comment interrompre un thread en train d'effectuer un appel de méthode bloquant et le faire sur l'exemple suivant : le thread main attend 5 secondes avant d'interrompre le thread qui dort et ce dernier affiche son nom.

```java

public static void main(String[] args) {
    // Crée et démarre le thread, en gardant sa référence
    Thread sleepingThread = Thread.ofPlatform().start(() -> {
        for (var i = 1;; i++) {
            try {
                Thread.sleep(1_000);
                System.out.println("Thread slept " + i + " seconds.");
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + " was interrupted!");
                return; // Sort de la boucle et termine le thread
            }
        }
    });

    try {
        Thread.sleep(5_000); // Main thread attend 5 secondes
        sleepingThread.interrupt(); // Interrompt le thread qui dort
    } catch (InterruptedException e) {
        // Gère l'interruption du thread principal si nécessaire
    }
}
```

5- 



```java

public static boolean isPrime(long candidate) {
    if (candidate <= 1) {
        return false;
    }
    for (var i = 2; i <= Math.sqrt(candidate); i++) {
        if (candidate % i == 0) {
            return false;
        }
    }
    return true;
}

public static OptionalLong findPrime() {
    var generator = ThreadLocalRandom.current();
    while (!Thread.interrupted()) {  // Vérifie le drapeau d'interruption
        var candidate = generator.nextLong();
        if (isPrime(candidate)) {
            return OptionalLong.of(candidate);
        }
    }
    return OptionalLong.empty();  // Retourne vide si interrompu
}

public static void main(String[] args) throws InterruptedException {
    Thread primeThread = Thread.ofPlatform().start(() -> {
        var result = findPrime();
        if (result.isPresent()) {
            System.out.println("Found a random prime : " + result.getAsLong());
        }
    });

    Thread.sleep(3000);  // Main attend 3 secondes
    primeThread.interrupt();  // Interrompt le thread de calcul
    System.out.println("STOP");
}

```

Les modifications :

Dans findPrime() :

Remplacé la boucle infinie for(;;) par while(!Thread.interrupted())
Ajout d'un return OptionalLong.empty() si interrompu


Dans main() :

Garde la référence du thread créé
Attend 3 secondes
Interrompt le thread de calcul
Affiche "STOP"


Dans le lambda :

Vérifie si un nombre a été trouvé avant d'afficher


Note : Thread.interrupted() vérifie ET efface le drapeau d'interruption, ce qui est approprié ici car on arrête le calcul de toute façon.





6- Expliquer la (trop) subtile différence entre les méthodes Thread.interrupted et thread.isInterrupted de la classe Thread.

La différence clé entre ces deux méthodes est leur effet sur le drapeau d'interruption :

Thread.interrupted() :

Méthode statique
Retourne l'état du drapeau du thread COURANT
EFFACE le drapeau (le met à false) après la lecture





-9 Pouvez-vous garantir que le programme afichera soit un nombre, soit "STOP", mais pas les deux ? 


Non, il est impossible de garantir cela avec ce code car il y a une condition de course (race condition) :

Cas problématique :


Le thread de calcul trouve un nombre premier
Juste avant l'affichage, le main thread atteint les 3 secondes
Les deux messages peuvent s'afficher dans n'importe quel ordre