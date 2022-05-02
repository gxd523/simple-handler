package com.gxd.demo.handler

fun main() {
    val handlerThread = HandlerThread("gxd-thread")
    handlerThread.start()
    val handler: Handler = object : Handler(handlerThread.looper) {
        override fun handleMessage(msg: Message) {
            println(msg.obj)
        }
    }
    val msg = Message.obtain()
    msg.obj = "这是一条测试消息!"
    handler.sendMessageDelayed(msg, 2000)

    handler.postDelayed({ println("延迟5000毫秒的callback") }, 5000)
}