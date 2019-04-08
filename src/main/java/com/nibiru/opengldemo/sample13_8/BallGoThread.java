package com.nibiru.opengldemo.sample13_8;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019-04-08
 * 描述:
 */
/*
 * 控制球运动的线程
 */
public class BallGoThread extends Thread {
    BallForControl ballForControl;//声明AllBalls的引用
    int timeSpan=12;
    private boolean flag=false;//循环标志位

    public BallGoThread(BallForControl ballForControl)//构造器
    {
        this.ballForControl=ballForControl;//成员变量赋值
    }

    @Override
    public void run()//重写run方法
    {
        while(flag)//while循环
        {
            ballForControl.go();//调用使所有球运动的方法

            try{
                Thread.sleep(timeSpan);//一段时间后再运动
            }
            catch(Exception e){
                e.printStackTrace();//打印异常
            }
        }
    }
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

