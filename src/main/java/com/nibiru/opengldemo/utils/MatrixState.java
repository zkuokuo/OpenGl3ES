package com.nibiru.opengldemo.utils;

import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019-03-27
 * 描述: 存储系统矩阵状态的类
 */

public class MatrixState {
    //4*4 矩阵 投影用
    private static float[] mProjMatrix = new float[16];
    //摄像机位置朝向参数矩阵
    private static float[] mVMatrix = new float[16];
    //最终的总变换矩阵
    private static float[] currMatrix;//当前变换矩阵
    static float[] mMVPMatrix = new float[16];//总变换矩阵
    static float[][] mStack = new float[10][16];//用于保存变换矩阵的栈
    static int stackTop = -1;//栈顶索引
    public static FloatBuffer cameraFB;
    public static FloatBuffer lightPositionFBSun;
    public static float[] lightLocationSun = new float[]{0, 0, 0};//太阳定位光光源位置
    public static float[] lightLocation = new float[]{0, 0, 0};//定位光光源位置
    public static FloatBuffer lightPositionFB;

    /**
     * 产生无任何变换的初始化矩阵
     */
    public static void setInitStack() {
        currMatrix = new float[16];
        Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }

    /**
     * 将当前变换矩阵存入栈中
     */
    public static void pushMatrix() {
        stackTop++;//栈顶索引加1
        for (int i = 0; i < 16; i++) {
            mStack[stackTop][i] = currMatrix[i];//当前变换矩阵中的各元素入栈
        }
    }

    /**
     * 从栈顶取出变换矩阵
     */
    public static void popMatrix() {
        for (int i = 0; i < 16; i++) {
            currMatrix[i] = mStack[stackTop][i]; //栈顶矩阵元素进当前变换矩阵
        }
        stackTop--;//栈顶索引减1
    }

    /**
     * 沿X、Y、Z轴方向进行平移变换的方法
     */
    public static void translate(float x, float y, float z) {
        Matrix.translateM(currMatrix, 0, x, y, z);
    }

    /**
     * 沿x,y,z轴进行旋转变换的方法
     */
    public static void totate(float angle, float x, float y, float z) {
        Matrix.rotateM(currMatrix, 0, angle, x, y, z);
    }

    //沿X、Y、Z轴方向进行旋转变换的方法
    public static void rotate(float angle, float x, float y, float z)//设置绕xyz轴移动
    {
        Matrix.rotateM(currMatrix, 0, angle, x, y, z);
    }

    /**
     * 沿x,y,z轴进行缩变换的方法
     */
    public static void scale(float x, float y, float z) {
        Matrix.scaleM(currMatrix, 0, x, y, z);
    }

    /**
     * 设置摄像机的方法
     */
    //设置摄像机
    public static void setCamera
    (
            float cx,    //摄像机位置x
            float cy,   //摄像机位置y
            float cz,   //摄像机位置z
            float tx,   //摄像机目标点x
            float ty,   //摄像机目标点y
            float tz,   //摄像机目标点z
            float upx,  //摄像机UP向量X分量
            float upy,  //摄像机UP向量Y分量
            float upz   //摄像机UP向量Z分量
    ) {
        Matrix.setLookAtM
                (
                        mVMatrix,
                        0,
                        cx,
                        cy,
                        cz,
                        tx,
                        ty,
                        tz,
                        upx,
                        upy,
                        upz
                );

        float[] cameraLocation = new float[3];//摄像机位置
        cameraLocation[0] = cx;
        cameraLocation[1] = cy;
        cameraLocation[2] = cz;

        ByteBuffer llbb = ByteBuffer.allocateDirect(3 * 4);
        llbb.order(ByteOrder.nativeOrder());//设置字节顺序
        cameraFB = llbb.asFloatBuffer();
        cameraFB.put(cameraLocation);
        cameraFB.position(0);
    }

    /**
     * 设置正交投影的方法
     */
    public static void setProjectOrtho(
            float left,
            float right,
            float bottom,
            float top,
            float near,
            float far
    ) {
        Matrix.orthoM(
                mProjMatrix,     //存储生成矩阵元素的float[]类型数组
                0,                //填充起始偏移量
                left, right,       //near面的left、right
                bottom, top,            //near面的bottom、top
                near, far            //near面、far面与视点的距离
        );

    }

    //设置透视投影
    public static void setProjectFrustum(
            float left, // near面的left
            float right, // near面的right
            float bottom, // near面的bottom
            float top, // near面的top
            float near, // near面与视点的距离
            float far // far面与视点的距离
    ) {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    /**
     * 获取具体物体的总变换矩阵
     * 生成物体总变换矩阵的方法
     */
    public static float[] getFinalMatrix(float[] gec) {
        //摄像机矩阵乘以变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, gec, 0);
        //将投影矩阵乘以上一步的结果矩阵得到最终变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

    /**
     * 获取具体物体的总变换矩阵
     * 生成物体总变换矩阵的方法
     */
    public static float[] getFinalMatrix() {
        //摄像机矩阵乘以变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        //将投影矩阵乘以上一步的结果矩阵得到最终变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }


    //获取具体物体的变换矩阵
    public static float[] getMMatrix() {
        return currMatrix;
    }

    //设置太阳光源位置的方法
    public static void setLightLocationSun(float x, float y, float z) {
        lightLocationSun[0] = x;
        lightLocationSun[1] = y;
        lightLocationSun[2] = z;
        ByteBuffer llbb = ByteBuffer.allocateDirect(3 * 4);
        llbb.order(ByteOrder.nativeOrder());//设置字节顺序
        lightPositionFBSun = llbb.asFloatBuffer();
        lightPositionFBSun.put(lightLocationSun);
        lightPositionFBSun.position(0);
    }

    //设置灯光位置的方法
    public static void setLightLocation(float x, float y, float z) {
        lightLocation[0] = x;
        lightLocation[1] = y;
        lightLocation[2] = z;
        ByteBuffer llbb = ByteBuffer.allocateDirect(3 * 4);
        llbb.order(ByteOrder.nativeOrder());//设置字节顺序
        lightPositionFB = llbb.asFloatBuffer();
        lightPositionFB.put(lightLocation);
        lightPositionFB.position(0);
    }
}
