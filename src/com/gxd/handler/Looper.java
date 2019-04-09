package com.gxd.handler;

import com.sun.istack.internal.Nullable;

/**
 * Created by guoxiaodong on 2019/4/7 20:06
 */
public class Looper {
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    private static Looper sMainLooper;  // guarded by Looper.class
    final MessageQueue messageQueue;
    final Thread mThread;

    private Looper(boolean quitAllowed) {
        messageQueue = new MessageQueue(quitAllowed);
        mThread = Thread.currentThread();
    }

    public static void prepare() {
        prepare(true);
    }

    private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper(quitAllowed));
    }

    public static void prepareMainLooper() {
        prepare(false);
        synchronized (Looper.class) {
            if (sMainLooper != null) {
                throw new IllegalStateException("The main Looper has already been prepared.");
            }
            sMainLooper = myLooper();
        }
    }

    @Nullable
    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        for (; ; ) {
            Message msg = me.messageQueue.next(); // might block
            if (msg == null) {// No message indicates that the message queue is quitting.
                return;
            }
            msg.target.dispatchMessage(msg);
            msg.recycle();
        }
    }

    public void quit() {
        messageQueue.quit();
    }
}
