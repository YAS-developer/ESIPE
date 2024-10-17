public class ThreadDeschedulingDemo {
    private static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            long counter = 0;
            while (!flag) {
                counter++;
            }
            System.out.println("Thread terminé, compteur : " + counter);
        });

        thread.start();
        
        Thread.sleep(1000); // Donne du temps au thread pour s'exécuter
        
        flag = true;
        thread.join();
        
        System.out.println("Programme terminé");
    }
}