package com.gxd.demo.handler;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Message采用单链表数据结构,这里实现Delayed，配合DelayQueue实现消息队列
 */
public class Message implements Delayed {
    private static final int MAX_POOL_SIZE = 50;
    private static final Object sPoolSync = new Object();
    private static int sPoolSize = 0;
    private static Message sPool;
    private Message next;
    
    /**
     * 多久后触发
     */
    public long when;
    /**
     * 消息内容
     */
    public Object obj;
    /**
     * 参数2
     */
    public int arg2;
    /**
     * 参数1
     */
    public int arg1;
    /**
     * 消息类型
     */
    public int what;
    Runnable callback;
    /**
     * Looper.loop()循环拿到消息后，调用handler实例继续分发消息
     */
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

    /**
     * Message回收进消息池
     */
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
