package com.nibiru.opengldemo.sample5.sample5_2;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.nibiru.opengldemo.sample5.sample5_1.MySurfaceView;

public class Sample5_2_Activity extends Activity {

    private MySurfaceView mMySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置为横屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化GlSurfaceView
        mMySurfaceView = new MySurfaceView(this);
        //切换到主界面
        setContentView(mMySurfaceView);
        mMySurfaceView.requestFocus();//获取焦点
        mMySurfaceView.setFocusableInTouchMode(true);//设置为可触控

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMySurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMySurfaceView.onPause();
    }
}
