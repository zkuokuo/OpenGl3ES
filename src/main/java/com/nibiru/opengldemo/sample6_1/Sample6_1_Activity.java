package com.nibiru.opengldemo.sample6_1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019-03-29
 * 描述:
 */

public class Sample6_1_Activity extends Activity {
    private MySurfaceView mGLSurfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 初始化GLSurfaceView
        mGLSurfaceView = new MySurfaceView(this);
        // 切换到主界面
        setContentView(mGLSurfaceView);
        mGLSurfaceView.requestFocus();// 获取焦点
        mGLSurfaceView.setFocusableInTouchMode(true);// 设置为可触控

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }
}
