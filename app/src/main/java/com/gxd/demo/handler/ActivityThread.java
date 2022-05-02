package com.gxd.demo.handler;

/**
 * Created by guoxiaodong on 2019/4/7 20:39
 */
public class ActivityThread {
    public static void main(String[] args) {
        Looper.prepareMainLooper();
        Looper.loop();
        throw new RuntimeException("Main thread loop unexpectedly exited");
    }
}
