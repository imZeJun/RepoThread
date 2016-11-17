package browser.android.com.repothread;

import android.util.Log;

import java.util.Random;

/**
 * @author lizejun
 * @version 1.0 2016/11/17
 */
public class Producer implements Runnable {

    private static final String TAG = "Producer";
    private static final long MAX_SLEEP_GAP = 100;

    private WareHouse mWareHouse;
    private String mProducerName;

    public Producer(WareHouse wareHouse, String producerName) {
        this.mWareHouse = wareHouse;
        this.mProducerName = producerName;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 10) {
            i++;
            mWareHouse.produce(new Product(i));
            System.out.println("producer produce id=" + i);
            try {
                Thread.sleep(MAX_SLEEP_GAP);
            } catch (Exception e) {
                Log.e(TAG, "Exception, e=" + e);
            }
        }
    }
}
