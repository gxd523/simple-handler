package com.gxd.handler;

/**
 * Created by guoxiaodong on 2019/4/7 20:06
 */
public class MessageQueue {
    /**
     * True if the message queue can be quit.
     */
    private final boolean mQuitAllowed;
    Message mMessages;

    public MessageQueue(boolean quitAllowed) {
        this.mQuitAllowed = quitAllowed;
    }

    /**
     * 出队列
     */
    Message next() {
        return new Message();
    }

    /**
     * 入队列
     */
    boolean enqueueMessage(Message msg) {
        mMessages = msg;
        return true;
    }
}
