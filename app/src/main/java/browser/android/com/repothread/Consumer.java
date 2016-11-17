package browser.android.com.repothread;

import android.util.Log;

/**
 * @author lizejun
 * @version 1.0 2016/11/17
 */
public class Consumer implements Runnable{

    private static final String TAG = "Consumer";
    private static final long MAX_SLEEP_GAP = 50;

    private WareHouse mWareHouse;
    private String mProducerName;

    public Consumer(WareHouse wareHouse, String producerName) {
        this.mWareHouse = wareHouse;
        this.mProducerName = producerName;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 10) {
            i++;
            Product product = mWareHouse.consume();
            System.out.println("consumer get product id=" + product.getProductId());
            try {
                Thread.sleep(MAX_SLEEP_GAP);
            } catch (Exception e) {
                Log.e(TAG, "Exception, e=" + e);
            }
        }
    }
}
