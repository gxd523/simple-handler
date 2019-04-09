package com.gxd.handler;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by guoxiaodong on 2019/4/7 20:05
 */
public class Message implements Delayed {
    private static final int MAX_POOL_SIZE = 50;
    private static final Object sPoolSync = new Object();
    private static int sPoolSize = 0;
    /**
     * 类似栈结构
     */
    private static Message sPool;
    public long when;
    public Object obj;
    public int arg2;
    public int arg1;
    public int what;
    Message next;
    Runnable callback;
    Handler target;

    /**
     * Return a new Message instance from the global pool. Allows us to
     * avoid allocating new objects in many cases.
     */
    public static Message obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
                sPoolSize--;
                return m;
            }
        }
        return new Message();
    }

    void recycle() {
        // Mark the message as in use while it remains in the recycled object pool.
        // Clear out all other details.
        what = 0;
        arg1 = 0;
        arg2 = 0;
        obj = null;
        target = null;
        callback = null;

        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    @Override
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(when - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed delayed) {
        if (delayed instanceof Message) {
            return (int) (when - ((Message) delayed).when);
        } else {
            return (int) (getDelay(TimeUnit.MILLISECONDS) - delayed.getDelay(TimeUnit.MILLISECONDS));
        }
    }
}
