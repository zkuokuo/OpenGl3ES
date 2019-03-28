package com.nibiru.opengldemo.sample5.sample5_7;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.nibiru.opengldemo.utils.Constant;
import com.nibiru.opengldemo.utils.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019-03-27
 * 描述:点和线的绘制方式
 */
public class MySurfaceView extends GLSurfaceView {
    private SceneRenderer mRenderer;//场景渲染器

    public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);//使用opengl es 3.0 需要设置该值为3
        mRenderer = new SceneRenderer();   //创建render
        this.setRenderer(mRenderer);        //设置渲染器
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY); //设置渲染模式为主动渲染
        Constant.CURR_DRAW_MODE = Constant.GL_POINTS;//初始化为绘制点模式
    }

    private class SceneRenderer implements Renderer {
        Belt belt;//条状物
        Circle circle;//圆
        @Override
        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //保护现场
            MatrixState.pushMatrix();
            //绘制条状物
            MatrixState.pushMatrix();//保护现场
            MatrixState.translate(-0.3f, 0, 0);//沿x轴负方向平移
            belt.drawSelf();
            MatrixState.popMatrix();//恢复现场
            //绘制圆
            MatrixState.pushMatrix();//保护现场
            MatrixState.translate(0.3f, 0, 0);//沿x轴正方向平移
            circle.drawSelf();
            MatrixState.popMatrix();//恢复现场
            //恢复现场
            MatrixState.popMatrix();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视口的大小及位置
            GLES30.glViewport(0, 0, width, height);
            //计算视口的宽高比
            Constant.ratio = (float) width / height;
            // 调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1, 1, 20, 100);
            // 调用此方法产生摄像机矩阵
            MatrixState.setCamera(0, 8f, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            //初始化变换矩阵
            MatrixState.setInitStack();
        }
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0.5f,0.5f,0.5f, 1.0f);
            //创建圆对象
            circle=new Circle(MySurfaceView.this);
            //创建条状物对象
            belt=new Belt(MySurfaceView.this);
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);

        }

    }

}
