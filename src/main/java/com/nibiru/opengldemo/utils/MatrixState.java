package com.nibiru.opengldemo.utils;

import android.opengl.Matrix;

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
    public static void rotate(float angle,float x,float y,float z)//设置绕xyz轴移动
    {
        Matrix.rotateM(currMatrix,0,angle,x,y,z);
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
    public static void setCamera(
            float cx,
            float cy,
            float cz,
            float tx,
            float ty,
            float tz,
            float upx,
            float upy,
            float upz
    ) {
        Matrix.setLookAtM(
                mVMatrix,  //存储生成矩阵元素的float[]类型数组
                0,             //填充起始偏移量
                cx, cy, cz,      //摄像机位置的X、Y、Z坐标
                tx, ty, tz,      //观察目标点X、Y、Z坐标
                upx, upy, upz    //up向量在X、Y、Z轴上的分量
        );

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
}
