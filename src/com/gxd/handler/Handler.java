package com.gxd.handler;

/**
 * Created by guoxiaodong on 2019/4/7 20:06
 */
public class Handler {
    final Looper mLooper;
    final MessageQueue mQueue;
    final Callback mCallback;

    public Handler(Callback callback) {
        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException("Can't create handler inside thread that has not called Looper.prepare()");
        }
        mQueue = mLooper.mQueue;
        mCallback = callback;
    }

    public Handler(Looper looper, Callback callback) {
        mLooper = looper;
        mQueue = mLooper.mQueue;
        mCallback = callback;
    }

    private static void handleCallback(Message message) {
        message.callback.run();
    }

    public final boolean sendMessage(Message msg) {
        return sendMessageAtTime(msg);
    }

    public boolean sendMessageAtTime(Message msg) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            RuntimeException e = new RuntimeException(this + " sendMessageAtTime() called with no mQueue");
            System.out.println(e.getMessage());
            return false;
        }
        return enqueueMessage(queue, msg);
    }

    private boolean enqueueMessage(MessageQueue queue, Message msg) {
        msg.target = this;
        return queue.enqueueMessage(msg);
    }

    public void dispatchMessage(Message msg) {
        if (msg.callback != null) {
            handleCallback(msg);
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(msg)) {
                    return;
                }
            }
            handleMessage(msg);
        }
    }

    /**
     * Subclasses must implement this to receive messages.
     */
    public void handleMessage(Message msg) {
    }

    public interface Callback {
        boolean handleMessage(Message msg);
    }
}
