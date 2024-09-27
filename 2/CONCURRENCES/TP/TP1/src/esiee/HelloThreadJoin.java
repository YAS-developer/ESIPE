package esiee;

public class HelloThreadJoin {
	private static int N = 5000;
	public static void main(String[] args) throws InterruptedException {
		var th1 = Thread.ofPlatform().start(() ->{
				for(var i= 0; i <= N; i++) {
					System.out.println("Hello 1 "+i);
				}
		});
		
		var th2 = Thread.ofPlatform().start(() ->{
			for(var i= 0; i <= N; i++) {
				System.out.println("Hello 2 "+i);
			}
		});
		
		var th3 = Thread.ofPlatform().start(() ->{
			for(var i= 0; i <= N; i++) {
				System.out.println("Hello 3 "+i);
			}
		});
		var th4 = Thread.ofPlatform().start(() ->{
			for(var i= 0; i <= N; i++) {
				System.out.println("Hello 4 "+i);
			}
		});
		
		th1.join();
		th2.join();
		th3.join();
		th4.join();
//		for(;;) {
//			System.out.println("Le programme est fini");
//			Thread.sleep(10000);
//		}
		
		System.out.println("Le programme est fini");
	}
	
	
	
/**
3. Observer l'évolution du nombre de threads. Que devient le thread main ? Quand est-ce que la JVM s'éteint ?


Le thread main : Le thread main exécute ses instructions, à savoir attendre 15 secondes avec Thread.sleep(15_000), puis afficher "Go!", créer les threads pour chaque tortue, et enfin se terminer. Une fois que toutes ces actions sont terminées, le thread main meurt, car il a terminé l'exécution de son code.

La JVM s'éteint : La JVM ne s'éteint pas immédiatement lorsque le thread main meurt. La JVM attend que tous les threads non-daemon (c'est-à-dire les threads utilisateurs) aient terminé leur exécution. Dans ce cas, chaque thread Turtle doit exécuter son sleep() et afficher son message avant que la JVM ne puisse s'arrêter. Une fois que tous ces threads ont terminé, la JVM s'éteint.
 * **/	
	
}



