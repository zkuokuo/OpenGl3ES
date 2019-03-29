package com.nibiru.opengldemo.sample7.sample7_3;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.nibiru.opengldemo.R;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019-03-29
 * 描述:
 */
public class MyActivity extends Activity {
    private MySurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置为竖屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //切换到主界面
        setContentView(R.layout.activity_sample7_3);
        //初始化GLSurfaceView
        mGLSurfaceView = new MySurfaceView(this);
        mGLSurfaceView.requestFocus();//获取焦点
        mGLSurfaceView.setFocusableInTouchMode(true);//设置为可触控
        //将自定义的GLSurfaceView添加到外层LinearLayout中
        LinearLayout ll = (LinearLayout) findViewById(R.id.main_liner);
        ll.addView(mGLSurfaceView);
        //为RadioButton添加监听器及SxT选择代码
        RadioButton rab = (RadioButton) findViewById(R.id.edge);
        rab.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //GL_CLAMP_TO_EDGE模式下
                        if (isChecked) {
                            mGLSurfaceView.currTextureId = mGLSurfaceView.textureCTId;
                        }
                    }
                }
        );
        rab = (RadioButton) findViewById(R.id.repeat);
        rab.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //GL_REPEAT模式下
                        if (isChecked) {
                            mGLSurfaceView.currTextureId = mGLSurfaceView.textureREId;
                        }
                    }
                }
        );

        rab = (RadioButton) findViewById(R.id.mirror);
        rab.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //GL_REPEAT模式下
                        if (isChecked) {
                            mGLSurfaceView.currTextureId = mGLSurfaceView.textureMIId;
                        }
                    }
                }
        );

        //为RadioButton添加监听器及SxT选择代码
        RadioButton rb = (RadioButton) findViewById(R.id.x11);
        rb.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {//设置为纹理坐标SxT=1x1
                            mGLSurfaceView.trIndex = 0;
                        }
                    }
                }
        );
        rb = (RadioButton) findViewById(R.id.x42);
        rb.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {//设置为纹理坐标SxT=4x2
                            mGLSurfaceView.trIndex = 1;
                        }
                    }
                }
        );
        rb = (RadioButton) findViewById(R.id.x44);
        rb.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {//设置为纹理坐标SxT=4x4
                            mGLSurfaceView.trIndex = 2;
                        }
                    }
                }
        );
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



