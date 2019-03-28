package com.nibiru.opengldemo.sample5.sample5_13;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.nibiru.opengldemo.utils.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.nibiru.opengldemo.utils.Constant.ratio;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019-03-27
 * 描述:
 */

public class MySurfaceView extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;//角度缩放比例
    private SceneRenderer mRenderer;//场景渲染器
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标
    float yAngle = 0;//总场景绕y轴旋转的角度

    public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);//使用opengl es 3.0 需要设置该值为3
        mRenderer = new SceneRenderer();   //创建render
        this.setRenderer(mRenderer);        //设置渲染器
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY); //设置渲染模式为主动渲染
    }

    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();//获取此次触控的x坐标
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE://若为移动动作
                float dx = x - mPreviousX;//计算触控位置的X位移
                yAngle += dx * TOUCH_SCALE_FACTOR;
        }
        mPreviousX = x;//记录触控笔x坐标
        return true;
    }

    private class SceneRenderer implements Renderer {
        Cube cube;//立方体对象引用
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//设置屏幕背景色RGBA
            GLES30.glClearColor(0.5f,0.5f,0.5f, 1.0f);
            //创建立方体对象
            cube=new Cube(MySurfaceView.this);
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视口的大小及位置
            GLES30.glViewport(0, 0, width, height);
            //计算视口的宽高比
            ratio = (float) width / height;

            //调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-ratio*0.7f, ratio*0.7f, -0.7f, 0.7f, 1, 10);
            //调用此方法产生摄像机矩阵
            MatrixState.setCamera(0,0.5f,4,0f,0f,0f,0f,1.0f,0.0f);

            //初始化变换矩阵
            MatrixState.setInitStack();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //保护现场
            MatrixState.pushMatrix();
            //绕Y轴旋转（实现总场景旋转）
            MatrixState.rotate(yAngle, 0, 1, 0);

            //绘制左侧立方体
            MatrixState.pushMatrix();
            MatrixState.translate(-2, 0, 0);
            MatrixState.rotate(60, 0, 1, 0);
            cube.drawSelf();
            MatrixState.popMatrix();

            //绘制右侧立方体
            MatrixState.pushMatrix();
            MatrixState.translate(2, 0, 0);
            MatrixState.rotate(-60, 0, 1, 0);
            cube.drawSelf();
            MatrixState.popMatrix();

            //恢复现场
            MatrixState.popMatrix();
        }
    }

}
