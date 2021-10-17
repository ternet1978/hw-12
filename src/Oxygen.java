import java.util.concurrent.*;


public class Oxygen implements Runnable {
    Semaphore oxygenSem;
    CountDownLatch allThreadsStarted;
    CountDownLatch allAtomsUsed;
    CyclicBarrier moleculePrinted;
    private boolean oxygenSemPassed;

    Oxygen(Semaphore oxygenSem, CountDownLatch allThreadsStarted, CountDownLatch allAtomsUsed, CyclicBarrier moleculePrinted) {
        this.oxygenSem = oxygenSem;
        this.allThreadsStarted = allThreadsStarted;
        this.allAtomsUsed = allAtomsUsed;
        this.moleculePrinted = moleculePrinted;
    }

    public void run() {
        allThreadsStarted.countDown();
        try {
            oxygenSemPassed = oxygenSem.tryAcquire(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (oxygenSemPassed) {
            releaseOxygen();

            try {
                moleculePrinted.await();

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        allAtomsUsed.countDown();
    }

    private void releaseOxygen() {
        System.out.print("O");
    }
}
