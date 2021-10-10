import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 21.09.21
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public class Oxygen implements Runnable {
    Semaphore oxygenSem;
    CountDownLatch allThreadsStarted;
    CountDownLatch allAtomsUsed;
    CyclicBarrier moleculePrinted;
    private boolean b;

    Oxygen(Semaphore oxygenSem, CountDownLatch allThreadsStarted, CountDownLatch allAtomsUsed, CyclicBarrier moleculePrinted) {
        this.oxygenSem = oxygenSem;
        this.allThreadsStarted = allThreadsStarted;
        this.allAtomsUsed = allAtomsUsed;
        this.moleculePrinted = moleculePrinted;
    }

    public void run() {
        allThreadsStarted.countDown();
        try {
            b = oxygenSem.tryAcquire(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if (b) {
            releaseOxygen();

            try {
                moleculePrinted.await();

            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (BrokenBarrierException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        allAtomsUsed.countDown();
    }

    private void releaseOxygen() {
        System.out.print("O");
    }
}
