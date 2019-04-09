package com.gxd.handler;

/**
 * Created by guoxiaodong on 2019/4/9 20:10
 */
public class Main {
    public static void main(String[] args) {
        HandlerThread handlerThread = new HandlerThread("gxd-thread");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                System.out.println(msg.obj);
            }
        };
        Message msg = Message.obtain();
        msg.obj = "这是一条测试消息!";
        handler.sendMessageDelayed(msg, 2000);

        handler.postDelayed(() -> System.out.println("延迟5000毫秒的callback"), 5000);
    }
}
