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
	
}



