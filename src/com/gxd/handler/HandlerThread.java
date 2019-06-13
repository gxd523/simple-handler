package com.gxd.handler;

/**
 * Created by guoxiaodong on 2019/4/9 19:54
 */
public class HandlerThread extends Thread {
    private Looper mLooper;
    private Handler mHandler;

    public HandlerThread(String name) {
        super(name);
    }

    public Looper getLooper() {
        if (!isAlive()) {
            return null;
        }
        synchronized (this) {// 假如Looper.prepare();执行时间很长，那么主线程先拿到锁
            while (isAlive() && mLooper == null) {
                try {
                    wait();// 调用wait()之后会释放锁
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return mLooper;
    }

    public boolean quit() {
        Looper looper = getLooper();
        if (looper != null) {
            looper.quit();
            return true;
        }
        return false;
    }

    protected void onLooperPrepared() {
    }

    @Override
    public void run() {
        Looper.prepare();
        synchronized (this) {// getLooper()中调用wait()会释放锁，所以这里也拿得到锁
            mLooper = Looper.myLooper();
            notifyAll();
        }
        onLooperPrepared();
        Looper.loop();
    }
}