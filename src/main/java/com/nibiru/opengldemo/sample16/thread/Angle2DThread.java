package com.nibiru.opengldemo.sample16.thread;

import static com.nibiru.opengldemo.sample16.constant.SourceConstant.Angle2D;

public class Angle2DThread extends Thread {
    boolean isST = false;

    public Angle2DThread() {
    }

    public void run() {
        while (true) {//循环定时移动炮弹
            try {
                if (!isST) {
                    Angle2D += 1;
                    if (Angle2D > 2) {
                        isST = true;
                    }
                }
                if (isST) {
                    Angle2D -= 1;
                    if (Angle2D < -2) {
                        isST = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
