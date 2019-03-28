package com.nibiru.opengldemo.sample3_1;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 作者:zkk
 * 公司:nibiru
 * 描述:创建GLSurfaceView类
 */

public class MyTDView extends GLSurfaceView {
    final float ANGLE_SPAN = 0.375f;  //每次三角形旋转的角度
    RotateThread rthread;          //自定义线程类的引用
    SceneRenderer mRenderer;       //自定义渲染器的引用

    public MyTDView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);//使用opengl es 3.0 需要设置该值为3
        mRenderer = new SceneRenderer();   //创建render
        this.setRenderer(mRenderer);        //设置渲染器
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    /**
     * 20毫秒角度增加
     */
    public class RotateThread extends Thread {
        public boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                mRenderer.tle.xAngle = mRenderer.tle.xAngle + ANGLE_SPAN;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SceneRenderer implements GLSurfaceView.Renderer {
        Triangle tle;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glClearColor(0, 0, 0, 1);//设置屏幕背景为黑色
            tle = new Triangle(MyTDView.this);  //创建Triangle类的对象
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);//打开深度检测
            rthread = new RotateThread();
            rthread.start();                  //开启线程
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);//设置视口 视口也就是显示屏上指定的矩形区域.

            float ratio = (float) width / height;  //计算屏幕的宽高比例

            //投影矩阵
            Matrix.frustumM(
                    Triangle.mProjMatrix,
                    0,
                    -ratio, ratio,
                    -1, 1, 1, 10
            );
            //设置摄像机
            Matrix.setLookAtM(
                    Triangle.mVMatrix,  //存储生成矩阵元素的float[]类型数组
                    0,                  //填充起始偏移量
                    0, 0, 3,            //摄像机位置的x,y,z坐标
                    0, 0, 0,            //观察目标点x,y,z坐标
                    0, 1, 0             //up向量在x,y,z轴上的分量
            );
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清除颜色缓存和深度缓存
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //通过Triangle的对象调用drawSelf绘制三角形
            tle.drawSelf();
        }
    }
}
