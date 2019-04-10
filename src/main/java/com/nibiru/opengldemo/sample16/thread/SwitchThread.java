package com.nibiru.opengldemo.sample16.thread;

import static com.nibiru.opengldemo.sample16.constant.SourceConstant.AngleSpng;
import static com.nibiru.opengldemo.sample16.constant.SourceConstant.step;

public class SwitchThread extends Thread {
    boolean ismin = false;

    public SwitchThread() {
    }

    public void run() {
        while (true) {//循环定时移动炮弹
            try {
                if (ismin) {
                    step -= 1;
                    if (step < 5) {
                        ismin = false;
                    }
                }
                if (!ismin) {
                    step += 1;
                    if (step > 30) {
                        ismin = true;
                    }
                }
                AngleSpng += 1;
                if (AngleSpng > 360) {
                    AngleSpng = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
