package com.nibiru.opengldemo.sample14.sample14_1;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019-04-08
 * 描述:
 */

public class GL2JNIView extends GLSurfaceView {
    MyRenderer renderer;//自定义渲染器的引用

    public GL2JNIView(Context context) {
        super(context);
        //使用OPengl ES 3.0需设置该参数为3
        this.setEGLContextClientVersion(3);
        renderer = new MyRenderer();
        //设置渲染器
        this.setRenderer(renderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private static class MyRenderer implements GLSurfaceView.Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GL2JNILib.init(width, height);//调用本地方法初始化
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GL2JNILib.step();//调用本地方法刷新场景
        }
    }
}
