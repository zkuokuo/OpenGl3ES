package com.nibiru.opengldemo.sample16.util.screenscale;

public class ScreenScaleResult {

    enum ScreenOrien {
        HP,
        SP
    }

    public int lucX;
    public int lucY;
    public float ratio;
    ScreenOrien so;

    public ScreenScaleResult(int lucX, int lucY, float ratio, ScreenOrien so) {
        this.lucX = lucX;
        this.lucY = lucY;
        this.ratio = ratio;
        this.so = so;
    }

    public String toString() {
        return "lucX=" + lucX + ", lucY=" + lucY + ", ratio=" + ratio + ", " + so;
    }
}