import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

    public CustomThreadPoolExecutor(int nThreads) {
        super(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    @Override
    public void execute(Runnable command) {
        int n = 1;
        Class<?> c = command.getClass();
        Repeat anno = c.getAnnotation(Repeat.class);
        if (anno != null) {
            n = anno.value();
        }

        for (int i = 1; i <= n; i++) {
            super.execute(command);

        }
    }


}
