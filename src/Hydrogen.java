import java.util.concurrent.*;


public class Hydrogen implements Runnable {
    Semaphore hydrogenSem;
    CountDownLatch allThreadsStarted;
    CountDownLatch allAtomsUsed;
    CyclicBarrier moleculePrinted;
    private boolean hydrogenSemPassed;

    Hydrogen(Semaphore hydrogenSem, CountDownLatch allThreadsStarted, CountDownLatch allAtomsUsed, CyclicBarrier moleculePrinted) {
        this.hydrogenSem = hydrogenSem;
        this.allThreadsStarted = allThreadsStarted;
        this.allAtomsUsed = allAtomsUsed;
        this.moleculePrinted = moleculePrinted;
    }

    public void run() {
        allThreadsStarted.countDown();


        try {
            hydrogenSemPassed = hydrogenSem.tryAcquire(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (hydrogenSemPassed) {
            releaseHydrogen();


            try {
                moleculePrinted.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        allAtomsUsed.countDown();

    }

    private void releaseHydrogen() {
        System.out.print("H");
    }


}
