package browser.android.com.repothread;

import android.support.annotation.NonNull;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author lizejun
 * @version 1.0 2016/12/13
 */
public class TwinsLock implements Lock {

    private final Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer {

        Sync(int count) {
            //初始值为2.
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for(;;) {
                //1.获得当前的状态.
                int current = getState();
                //2.newCount表示剩余可获取同步状态的线程数
                int newCount = current - arg;
                //3.如果小于0,那么返回获取同步状态失败;否则通过CAS确保设置的正确性.
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    //4.当返回值大于等于0表示获取同步状态成功.
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;) {
                int current = getState();
                //将可获取同步状态的线程数加1.
                int newCount = current + current;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
    }

    @Override
    public void lock() {
        sync.tryAcquireShared(1);
    }

    @Override
    public void unlock() {
        sync.tryReleaseShared(1);
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, @NonNull TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @NonNull
    @Override
    public Condition newCondition() {
        return null;
    }
}
