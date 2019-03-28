package com.nibiru.opengldemo.sample5.sample5_3;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

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

    public MySurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);//使用opengl es 3.0 需要设置该值为3
        mRenderer = new SceneRenderer();   //创建render
        this.setRenderer(mRenderer);        //设置渲染器
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY); //设置渲染模式为主动渲染
    }


    private class SceneRenderer implements Renderer {
         Cube mCube;   //立方体对象引用
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            //创建立方体对象
            mCube=new Cube(MySurfaceView.this);
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
            //设置正交投影
            MatrixState.setProjectFrustum(
                    -ratio * 0.8f, ratio * 1.2f,
                    -1, 1,
                    20, 100
            );

            //设置摄像机
            MatrixState.setCamera(
                    -16, 8, 45,
                    0, 0, 0f,
                    0f, 1f, 0f
            );
            //初始化变换矩阵
            MatrixState.setInitStack();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //绘制原立方体
            MatrixState.pushMatrix();//保护现场
            mCube.drawSelf();//绘制立方体
            MatrixState.popMatrix();//恢复现场

            //绘制变换后的立方体
            MatrixState.pushMatrix();//保护现场
            MatrixState.translate(1f, 0, 0);//沿x方向平移1
            MatrixState.totate(60,0,0,1); //沿着z轴进行旋转60度
            MatrixState.scale(0.4f,2f,0.6f);//x,y,z 3个方向按各自的缩放因子进行缩放
            mCube.drawSelf();//绘制立方体
            MatrixState.popMatrix();//恢复现场
        }
    }

}
