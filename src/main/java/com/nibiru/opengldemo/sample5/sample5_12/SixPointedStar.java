package com.nibiru.opengldemo.sample5.sample5_12;

import android.opengl.GLES30;
import android.opengl.Matrix;

import com.nibiru.opengldemo.utils.MatrixState;
import com.nibiru.opengldemo.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.nibiru.opengldemo.utils.ShaderUtil.createProgram;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019-03-27
 * 描述:六角形
 */

public class SixPointedStar {
    int mProgram;//自定义渲染管线着色器程序id
    int muMVPMatrixHandle;//总变换矩阵引用
    int maPositionHandle; //顶点位置属性引用
    int maColorHandle; //顶点颜色属性引用
    String mVertexShader;    //顶点着色器代码脚本
    String mFragmentShader;    //片元着色器代码脚本
    static float[] mMMatrix = new float[16];    //具体物体的3D变换矩阵，包括旋转、平移、缩放
    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲
    int vCount = 0;
    float yAngle = 0;//绕y轴旋转的角度
    float xAngle = 0;//绕x轴旋转的角度
    final float UNIT_SIZE = 1;
    final float UNIT_COLOR = 1;
    float color[] = new float[3];//五角星的颜色

    public SixPointedStar(MySurfaceView mv, float r, float R, float z, float[] color) {
        this.color = color;//五角星的颜色
        //调用初始化顶点数据的initVertexData方法
        initVertexData(R, r, z);
        //调用初始化着色器的intShader方法
        initShader(mv);
    }

    /**
     * 初始化顶点数据的initVertexData方法
     *
     * @param R 表示中心点到凹顶点的距离
     * @param r 表示中心点到6个凸顶点的距离
     * @param z 表示z轴距离
     */
    private void initVertexData(float R, float r, float z) {
        List<Float> flist = new ArrayList<>();
        float tempAngle = 360 / 6;
        //循环生成构成六角形各三角形的顶点坐标
        for (float angle = 0; angle < 360; angle += tempAngle) {
            //第一个三角形
            //第一个点的x、y、z坐标
            flist.add(0f);
            flist.add(0f);
            flist.add(z);
            //第二个点的x、y、z坐标
            flist.add((float) (R * UNIT_SIZE * Math.cos(Math.toRadians(angle))));
            flist.add((float) (R * UNIT_SIZE * Math.sin(Math.toRadians(angle))));
            flist.add(z);
            //第三个点的x、y、z坐标
            flist.add((float) (r * UNIT_SIZE * Math.cos(Math.toRadians(angle + tempAngle / 2))));
            flist.add((float) (r * UNIT_SIZE * Math.sin(Math.toRadians(angle + tempAngle / 2))));
            flist.add(z);

            //第二个三角形
            //第一个中心点的x、y、z坐标
            flist.add(0f);
            flist.add(0f);
            flist.add(z);
            //第二个点的x、y、z坐标
            flist.add((float) (r * UNIT_SIZE * Math.cos(Math.toRadians(angle + tempAngle / 2))));
            flist.add((float) (r * UNIT_SIZE * Math.sin(Math.toRadians(angle + tempAngle / 2))));
            flist.add(z);
            //第三个点的x、y、z坐标
            flist.add((float) (R * UNIT_SIZE * Math.cos(Math.toRadians(angle + tempAngle))));
            flist.add((float) (R * UNIT_SIZE * Math.sin(Math.toRadians(angle + tempAngle))));
            flist.add(z);
        }
        vCount = flist.size() / 3;//顶点个数
        float[] vertexArray = new float[flist.size()];//顶点坐标数组
        for (int i = 0; i < vCount; i++) {
            vertexArray[i * 3] = flist.get(i * 3);
            vertexArray[i * 3 + 1] = flist.get(i * 3 + 1);
            vertexArray[i * 3 + 2] = flist.get(i * 3 + 2);
        }
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexArray.length * 4);
        vbb.order(ByteOrder.nativeOrder());    //设置字节顺序为本地操作系统顺序
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertexArray);//将顶点坐标数据放进缓冲
        mVertexBuffer.position(0);
    }

    private void initShader(MySurfaceView mv) {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("sample3_1/vertex.sh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("sample3_1/frag.sh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用id
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    //绘制图形的方法
    public void drawSelf() {
        //指定使用某套着色器程序
        GLES30.glUseProgram(mProgram);
        //初始化变换矩阵
        Matrix.setRotateM(mMMatrix, 0, 0, 0, 1, 0);
        //设置沿Z轴正向位移1
        Matrix.translateM(mMMatrix, 0, 0, 0, 1);
        //设置绕y轴旋转yAngle度
        Matrix.rotateM(mMMatrix, 0, yAngle, 0, 1, 0);
        //设置绕x轴旋转xAngle度
        Matrix.rotateM(mMMatrix, 0, xAngle, 1, 0, 0);
        //将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(mMMatrix), 0);
        //将顶点位置数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3 * 4,
                        mVertexBuffer
                );

        //将顶点颜色数据送入渲染管线
        GLES30.glVertexAttrib4f(maColorHandle, color[0], color[1], color[2], 1.0f);
        //启用顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        //绘制六角星
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }
}
