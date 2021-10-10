import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 10.10.21
 * Time: 8:50
 * To change this template use File | Settings | File Templates.
 */
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
            super.execute(command);    //To change body of overridden methods use File | Settings | File Templates.

        }
    }


}
