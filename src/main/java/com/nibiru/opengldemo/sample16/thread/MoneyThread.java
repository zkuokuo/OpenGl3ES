package com.nibiru.opengldemo.sample16.thread;

import com.nibiru.opengldemo.sample16.view.GameView;

import static com.nibiru.opengldemo.sample16.constant.SourceConstant.moneycount;

public class MoneyThread extends Thread //¼àÌý¼üÅÌ×´Ì¬µÄÏß³Ì
{
    GameView gv;
    long currtime;
    long pretime;

    public MoneyThread(GameView mv) {
        this.gv = mv;
        pretime = System.currentTimeMillis();

    }

    public void run() {

        try {
            while (true) {
                currtime = System.currentTimeMillis();
                if (moneycount == 20) {
                    pretime = currtime;
                } else if (moneycount < 20 && (currtime - pretime > 3000 * 60)) {
                    moneycount++;
                    pretime = currtime;
                }
                if (moneycount >= 1) {
                    gv.ismoneyout = false;
                }
                Thread.sleep(20);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

	
