package com.gxd.handler;

import com.sun.istack.internal.Nullable;

/**
 * 重点是prepare()、ThreadLocal
 */
public class Looper {
    /**
     * 线程本地存储区（Thread Local Storage，简称为TLS）
     */
    private static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    /**
     * 这个是专为UI线程的吗？
     */
    private static Looper sMainLooper;  // guarded by Looper.class
    final MessageQueue messageQueue;
    final Thread mThread;

    /**
     * MessageQueue在Looper构造函数里实例化成为成员变量
     */
    private Looper(boolean quitAllowed) {
        messageQueue = new MessageQueue(quitAllowed);
        mThread = Thread.currentThread();
    }

    public static void prepare() {
        prepare(true);
    }

    /**
     * 重点方法
     */
    private static void prepare(boolean quitAllowed) {
        if (sThreadLocal.get() != null) {//每个线程只允许执行一次该方法，第二次执行时线程的ThreadLocal已有数据，则会抛出异常。
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

    /**
     * 用于获取ThreadLocal存储的Looper对象
     */
    @Nullable
    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    /**
     * loop()进入循环模式，不断重复下面的操作，直到没有消息时退出循环
     * 1、读取MessageQueue的下一条Message；
     * 2、把Message分发给相应的target；
     * 3、再把分发后的Message回收到消息池，以便重复利用。
     */
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
