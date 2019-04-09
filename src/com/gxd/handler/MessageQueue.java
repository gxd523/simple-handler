package com.gxd.handler;

import java.util.concurrent.DelayQueue;

/**
 * Created by guoxiaodong on 2019/4/7 20:06
 */
public class MessageQueue {
    /**
     * True if the message queue can be quit.
     */
    private final boolean mQuitAllowed;
    private DelayQueue<Message> queue = new DelayQueue<>();

    public MessageQueue(boolean quitAllowed) {
        this.mQuitAllowed = quitAllowed;
    }

    /**
     * 出队列
     */
    public Message next() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 入队列
     */
    public void enqueueMessage(Message msg) {
        queue.add(msg);
    }

    public void quit() {
        queue.clear();
    }
}
