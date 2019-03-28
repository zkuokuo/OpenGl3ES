package com.nibiru.opengldemo.sample3_1;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Sample3_1Activity extends AppCompatActivity {
    private MyTDView mTDView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为竖屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mTDView = new MyTDView(this);
        mTDView.requestFocus();//获取焦点
        mTDView.setFocusableInTouchMode(true);//设置为可触控
        setContentView(mTDView);//跳转到相关界面
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mTDView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTDView.onPause();
    }
}
