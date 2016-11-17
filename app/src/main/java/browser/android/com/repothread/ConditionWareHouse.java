package browser.android.com.repothread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lizejun
 * @version 1.0 2016/11/17
 */
public class ConditionWareHouse {

    private static final int MAX_SIZE = 10;
    private LinkedList<Product> mProducts = new LinkedList<>();
    private Lock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public void produce(Product product) {
        lock.lock();
        try {
            while (mProducts.size() == MAX_SIZE) {
                System.out.println("ConditionWareHouse is full");
                notFull.await();
            }
            mProducts.add(product);
            notEmpty.signal();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException e=" + e);
        } finally {
            lock.unlock();
        }
    }

    public Product consume() {
        lock.lock();
        Product product = null;
        try {
            while (mProducts.size() == 0) {
                System.out.println("ConditionWareHouse is empty");
                notEmpty.await();
            }
            product = mProducts.removeLast();
            notFull.signal();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException e=" + e);
        } finally {
            lock.unlock();
        }
        return product;
    }

}
