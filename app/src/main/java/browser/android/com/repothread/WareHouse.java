package browser.android.com.repothread;

import android.util.Log;

/**
 * @author lizejun
 * @version 1.0 2016/11/17
 */
public class WareHouse {


    private static final String TAG = "WareHouse";

    public int base = 0;
    public int top = 0;

    private Product[] mProduct = new Product[10];

    public synchronized void produce(Product product) {
        notify(); //通知消费者有库存可以消费。
        while (top == mProduct.length) {
            try {
                System.out.println("The WareHouse is full");
                wait(); //由于仓库已经满了，因此需要释放。
            } catch (InterruptedException e) {
                Log.e(TAG, "InterruptedException e=" + e);
            }
        }
        mProduct[top] = product;
        top++;
    }

    public synchronized Product consume() {
        Product product = null;
        while (top == base) {
            notify(); //通知生产者已经没有库存。
            try {
                System.out.println("The WareHouse is Empty");
                wait(); //自己释放锁。
            } catch (InterruptedException e) {
                Log.e(TAG, "InterruptedException e=" + e);
            }
        }
        top--;
        product = mProduct[top];
        mProduct[top] = null;
        return product;
    }
}
