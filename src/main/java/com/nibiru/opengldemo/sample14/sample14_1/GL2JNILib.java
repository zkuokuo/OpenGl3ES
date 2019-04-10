package com.nibiru.opengldemo.sample14.sample14_1;

import android.content.res.AssetManager;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019-04-08
 * 描述:
 */

public class GL2JNILib {
     static {
         //加载so动态库
         System.loadLibrary("gl2jni");
     }
     //本地初始化方法
     public static native void init(int width,int height);
    //本地刷新场景方法
     public static native void step();
    //将AssetManger传入c++的方法
     public static native void nativeSetAssetManager(AssetManager am);
}
