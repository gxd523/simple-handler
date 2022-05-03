package com.gxd.demo.handler

import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.os.MessageQueue
import android.util.Log

object SyncBarrierObj {
    private val handlerThread by lazy {
        HandlerThread("test").also { it.start() }
    }
    private val threadHandler by lazy {
        object : Handler(handlerThread.looper) {
            override fun handleMessage(msg: Message) {
                Log.d("gxd", "收到..." + msg.obj)
            }
        }
    }

    /**
     * 往消息队列插入同步屏障
     */
    @Throws(java.lang.Exception::class)
    fun sendSyncBarrier(): Int {
        Log.d("gxd", "插入同步屏障")
        val method = MessageQueue::class.java.getDeclaredMethod("postSyncBarrier")
        method.isAccessible = true
        return method.invoke(threadHandler.looper.queue) as Int
    }

    /**
     * 移除同步屏障
     */
    @Throws(Exception::class)
    fun removeSyncBarrier(token: Int) {
        val method = MessageQueue::class.java.getDeclaredMethod("removeSyncBarrier", Int::class.javaPrimitiveType)
        method.isAccessible = true
        method.invoke(threadHandler.looper.queue, token)
        Log.d("gxd", "移除屏障")
    }

    /**
     * 往消息队列插入普通消息
     */
    fun sendSyncMessage() {
        val msg = Message.obtain().also { it.obj = "同步消息" }
        threadHandler.sendMessageDelayed(msg, 1000)
        Log.d("gxd", "插入普通消息")
    }

    /**
     * 往消息队列插入异步消息
     */
    fun sendAsyncMessage() {
        val msg = Message.obtain().also { it.obj = "异步消息" }
        msg.isAsynchronous = true
        threadHandler.sendMessageDelayed(msg, 1000)
        Log.d("gxd", "插入异步消息")
    }
}