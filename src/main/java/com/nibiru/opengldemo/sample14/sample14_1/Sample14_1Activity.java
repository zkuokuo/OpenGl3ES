package com.nibiru.opengldemo.sample14.sample14_1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 作者:dick
 * 公司:nibiru
 * 时间:2019-04-08
 * 描述:
 */

public class Sample14_1Activity extends Activity{
    GL2JNIView mView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将assetmanager传入c++
        GL2JNILib.nativeSetAssetManager(this.getAssets());
        mView=new GL2JNIView(getApplication());
        mView.requestFocus();
        mView.setFocusableInTouchMode(true);
        setContentView(mView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mView.onResume();
    }
}
