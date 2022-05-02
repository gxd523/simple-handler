package com.gxd.demo.handler;

import java.util.concurrent.DelayQueue;

/**
 * MessageQueue是消息机制的Java层和C++层的连接纽带，大部分核心方法都交给native层来处理，这里用DelayQueue替代
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
