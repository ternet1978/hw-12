/**
 * Created with IntelliJ IDEA.
 * User: User
 * Date: 10.10.21
 * Time: 8:43
 * To change this template use File | Settings | File Templates.
 */
@Repeat(3)
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello!");
    }
}
