package com.nibiru.opengldemo.utils;

import android.content.res.Resources;
import android.opengl.GLES30;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 作者:zkk
 * 公司:nibiru
 * 描述:加载顶点着色器和片元着色器的类
 */

public class ShaderUtil {

    /**
     * @des 加载指定着色器的方法
     */
    public static int loadShader(int shaderType, String source) {
        //创建一个shader,并记录其id
        int shader = GLES30.glCreateShader(shaderType);
        if (shader != 0) {
            //加载着色器的源代码
            GLES30.glShaderSource(shader, source);
            //对源码进行编译
            GLES30.glCompileShader(shader);
            int[] compiled = new int[1];
            //获取shader的编译情况
            GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {//如果编译失败则显示错误日志并删除shader
                Log.e("ES30_ERROR", "Could not compile shader " + shaderType + ":");
                Log.e(" ES30_ERROR", GLES30.glGetShaderInfoLog(shader));
                GLES30.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    /**
     * @des 创建着色器程序的方法
     */
    public static int createProgram(String vertexSource, String fragmentSource) {
        //加载顶点着色器
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            return 0;
        }
        //加载片元着色器
        int pixelShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) {
            return 0;
        }
        //创建程序
        int program = GLES30.glCreateProgram();
        if (program != 0) {//若程序创建成功则向程序中加入顶点着色器与片元着色器
            //向程序中加入顶点着色器
            GLES30.glAttachShader(program, vertexShader);
            checkGLError("glAttachShader");
            //向程序中加入片元着色器
            GLES30.glAttachShader(program, pixelShader);
            checkGLError("glAttachShader");
            //链接程序
            GLES30.glLinkProgram(program);
            //存放链接成功program状态值得数组
            int[] linkStatus = new int[1];
            GLES30.glGetProgramiv(program, GLES30.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES30.GL_TRUE) {//如果链接失败则报错并删除程序
                Log.e("ES30_ERROR", "Could not link program: ");
                Log.e("ES30_ERROR", GLES30.glGetProgramInfoLog(program));
                //删除程序
                GLES30.glDeleteProgram(program);
                program = 0;

            }
        }
        return program;
    }

    /**
     * @des 检查每一步操作是否有错误的方法
     */
    public static void checkGLError(String op) {
        int error;
        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
            //打印后台错误
            Log.e("ES30_ERROR", op + ": glError " + error);
            //抛出异常
            throw new RuntimeException(op + ": glError " + error);
        }
    }

    /**
     * @des 从sh脚本中加载着色器内容的方法
     */
    public static String loadFromAssetsFile(String fname, Resources r) {
        String result = null;
        try {
            InputStream in = r.getAssets().open(fname);
            int ch = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = in.read()) != -1) {
                baos.write(ch);
            }
            byte[] buff = baos.toByteArray();
            baos.close();
            in.close();
            result = new String(buff, "UTF-8");
            result = result.replaceAll("\\r\\n", "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
