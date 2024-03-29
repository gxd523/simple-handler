package com.gxd.demo.handler;

/**
 * Created by guoxiaodong on 2019/4/7 20:06
 */
public class Handler {
    private final Looper mLooper;
    private final MessageQueue messageQueue;

    public Handler() {
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException("Can't create handler inside thread that has not called Looper.prepare()");
        }
        messageQueue = mLooper.messageQueue;
    }

    public Handler(Looper looper) {
        mLooper = looper;
        messageQueue = mLooper.messageQueue;
    }

    public final void sendMessageDelayed(Message msg, long delayMillis) {
        msg.target = this;// 这也是个重点
        if (delayMillis < 0) {
            delayMillis = 0;
        }
        msg.when = System.currentTimeMillis() + delayMillis;
        messageQueue.enqueueMessage(msg);
    }

    public final void postDelayed(Runnable r, long delayMillis) {
        Message msg = Message.obtain();
        msg.callback = r;
        sendMessageDelayed(msg, delayMillis);
    }

    public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            msg.callback.run();
        } else {
            handleMessage(msg);
        }
    }

    /**
     * Subclasses must implement this to receive messages.
     */
    public void handleMessage(Message msg) {
    }
}
