package esiee;

public class TurtleRace {
	public static void main(String[] args) throws InterruptedException{
		  System.out.println("On your mark!");
		  Thread.sleep(15000);
		  System.out.println("Go!");
		  int[] times = {25000, 10000, 20000, 5000, 50000, 60000};
		  for(var i = 0; i < times.length; i++) {
			  var currentI= i;
			  Thread.ofPlatform().start(() ->{
				  try {
						Thread.sleep(times[currentI]); 
					} catch (InterruptedException e) {
						throw new AssertionError(e);
					}
					System.out.println("Turtle "+currentI+" has finished");
			});
		  }
		}
}
