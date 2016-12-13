package browser.android.com.repothread;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Created by lizejun on 2016/11/16.
 */
public class ThreadUtils {

    private static final String TAG = "ThreadUtils";

    public static void createThread() {
        Thread thread = new MyThread();
        thread.start();
    }

    public static void createThreadRunnable() {
        Thread thread = new Thread(new MyRunnable());
        thread.start();
    }

    public static void createThreadCallable() {

        //Future用法。
        ExecutorService service1 = Executors.newCachedThreadPool();
        MyCallable futureCallable = new MyCallable("futureCallable");
        final Future<String> future = service1.submit(futureCallable); //传入Callable给ExecutorService返回Future。
        service1.shutdown();
        //FutureTask1
        ExecutorService service2 = Executors.newCachedThreadPool();
        Callable futureCallable1 = new MyCallable("futureCallable1");
        final FutureTask<String> futureTask1 = new FutureTask<String>(futureCallable1); //Callable生成FutureTask。
        service2.submit(futureTask1); //传入FutureTask。
        service2.shutdown();
        //FutureTask2
        Callable futureCallable2 = new MyCallable("futureCallable2");
        final FutureTask<String> futureTask2 = new FutureTask<String>(futureCallable2);
        Thread thread = new Thread(futureTask2);
        thread.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                print(future);
                print(futureTask1);
                print(futureTask2);
            }
        }, 300);
    }

    public static void createProduceConsume() {
        ConditionWareHouse wareHouse = new ConditionWareHouse();
        Thread producerThread = new Thread(new Producer(wareHouse, "producer"));
        Thread consumerThread = new Thread(new Consumer(wareHouse, "consumer"));
        producerThread.start();
        consumerThread.start();
    }

    public static void createTwinsLock() {
        final Lock lock = new TwinsLock();
        class TwinsLockThread extends Thread {

            @Override
            public void run() {
                Log.d(TAG, "TwinsLockThread, run=" + Thread.currentThread().getName());
                while (true) {
                    lock.lock();
                    try {
                        Thread.sleep(1000);
                        Log.d(TAG, "TwinsLockThread, name=" + Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Log.d(TAG, "TwinsLockThread, unlock=" + Thread.currentThread().getName());
                        lock.unlock();
                    }
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            Thread thread = new TwinsLockThread();
            thread.start();
        }
    }

    private static void print(Future<String> futureTask) {
        try {
            Log.d(TAG, "isDone=" + futureTask.isDone() + ",result=" + futureTask.get());
        } catch (Exception e) {
            Log.d(TAG, "Exception=" + e);
        }
    }

    public static class MyThread extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Log.d(TAG, "MyThread, i=" + i);
            }
        }
    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                Log.d(TAG, "MyRunnable, i=" + i);
            }
        }
    }

    public static class MyCallable implements Callable<String> {

        private String mCallableName;

        public MyCallable(String callableName) {
            this.mCallableName = callableName;
        }

        @Override
        public String call() throws Exception {
            return mCallableName;
        }
    }
}
