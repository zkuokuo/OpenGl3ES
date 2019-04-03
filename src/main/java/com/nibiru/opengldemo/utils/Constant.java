package com.nibiru.opengldemo.utils;

public class Constant 
{
	//单位尺寸
	public static final float UNIT_SIZE=0.2f;
	//计算GLSurfaceView的宽高比
    public static float ratio;

	//绘制方式
	public static final int GL_POINTS = 0;
	public static final int GL_LINES = 1;
	public static final int GL_LINE_STRIP = 2;
	public static final int GL_LINE_LOOP = 3;

	public static int CURR_DRAW_MODE = 0;//当前绘制方式

	//旋转地月系线程的工作标志位
	public static boolean threadFlag=true;

	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
}
