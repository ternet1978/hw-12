import java.util.Scanner;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) {
        Semaphore oxygenSem = new Semaphore(0);
        Semaphore hydrogenSem = new Semaphore(0);
        CyclicBarrier moleculePrinted = new CyclicBarrier(4);

        System.out.println("Please enter atoms (kind of 'H' or 'O') ");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        String temp = name.replace("H", "");
        int countH = name.length() - temp.length();
        int countO = temp.length() - (temp.replace("O", "")).length();
        System.out.println("Helius = " + countH);
        System.out.println("Oxigen = " + countO);
        int amountOfH2O = (countH / 2) <= countO ? countH / 2 : countO;
        System.out.println("We must get " + amountOfH2O + " mol. of water");

        CountDownLatch allAtomsUsed = new CountDownLatch(countH + countO);
        CountDownLatch allThreadsStarted = new CountDownLatch(countH + countO);

        for (int i = 1; i <= countH; i++) {
            new Thread(new Hydrogen(hydrogenSem, allThreadsStarted, allAtomsUsed, moleculePrinted)).start();
        }

        for (int i = 0; i < countO; i++) {
            new Thread(new Oxygen(oxygenSem, allThreadsStarted, allAtomsUsed, moleculePrinted)).start();

        }

        try {
            allThreadsStarted.await(); // waiting until all threads started
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while (true) {
            if (oxygenSem.getQueueLength() > 0 && hydrogenSem.getQueueLength() > 1) {
                oxygenSem.release();
                hydrogenSem.release(2);
                try {
                    moleculePrinted.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("\nAll possible amount of water molecules was collected");
                break;
            }

        }


        try {
            allAtomsUsed.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nTask number 2:");
        CustomThreadPoolExecutor customThreadPoolExecutor = new CustomThreadPoolExecutor(10);
        customThreadPoolExecutor.execute(new MyRunnable());
        customThreadPoolExecutor.shutdown();

    }
}
