package com.gxd.handler;

/**
 * Created by guoxiaodong on 2019/4/7 20:39
 */
public class ActivityThread {
    public static void main(String[] args) {
        Looper.prepareMainLooper();
        Looper.loop();
    }
}
