package com.gxd.handler;

/**
 * Created by guoxiaodong on 2019/4/7 20:05
 */
public class Message {
    private static final int MAX_POOL_SIZE = 50;
    private static final Object sPoolSync = new Object();
    /**
     * 类似栈结构
     */
    private static Message sPool;
    private static int sPoolSize = 0;
    public int what;
    public int arg1;
    public int arg2;
    public Object obj;
    Handler target;
    Runnable callback;
    Message next;

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
}
