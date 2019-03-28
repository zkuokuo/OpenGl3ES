package com.nibiru.opengldemo.sample5.sample5_13;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.nibiru.opengldemo.R;
import com.nibiru.opengldemo.utils.MatrixState;

import static com.nibiru.opengldemo.utils.Constant.ratio;
/**
 * @des 目的是比较视角设置的合理与不合理的情况
 */
public class Sample5_13_Activity extends Activity {
    private MySurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //设置横屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_sample5_13);
        //把surfaceView添加到布局中
        mGLSurfaceView = new MySurfaceView(this);
        mGLSurfaceView.requestFocus();
        mGLSurfaceView.setFocusableInTouchMode(true);
        LinearLayout ll = (LinearLayout) findViewById(R.id.main_liner);
        ll.addView(mGLSurfaceView);
        //控制是否打开背面剪裁的ToggleButton
        ToggleButton tb = (ToggleButton) this.findViewById(R.id.ToggleButton01);
        tb.setOnCheckedChangeListener(
                new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            //视角不合适导致变形的情况
                            //调用此方法计算产生透视投影矩阵
                            MatrixState.setProjectFrustum(-ratio * 0.7f, ratio * 0.7f, -0.7f, 0.7f, 1, 10);
                            //调用此方法产生摄像机观察矩阵
                            MatrixState.setCamera(0, 0.5f, 4, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
                        } else {
                            //视角合适不变形的情况
                            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 20, 100);
                            MatrixState.setCamera(0, 8f, 45, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
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