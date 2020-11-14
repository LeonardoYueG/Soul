package com.test.soul.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.test.framework.entry.Contants;
import com.test.framework.utils.SpUtils;
import com.test.soul.BuildConfig;
import com.test.soul.MainActivity;
import com.test.soul.R;

/**
 * 启动页面
 */
public class IndexActivity extends AppCompatActivity {
    /**
     * 1.把启动页全屏
     * 2.延迟进入主页
     * 3.根据具体逻辑是进入主页还是引导页还是登录页
     * 4.适配刘海屏  AndroidManifest.xml
     */


    /**
     * 延时
     * SKIP_MAIN 延时消息号
     * Handler 处理延时
     */

    private static final int SKIP_MAIN = 1000;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SKIP_MAIN:
                    startMain();
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        mHandler.sendEmptyMessageDelayed(SKIP_MAIN, 1000 * 2);
    }

    private void startMain() {
        //1.判断是否是第一次启动，install -> first run
        boolean isFirstRun = SpUtils.getInstance().getBoolean(Contants.SP_IS_FIRST_APP, true);
        Intent intent = new Intent();
        if (isFirstRun) {
            //2.如果第一次启动跳转到引导页
            intent.setClass(this, GuideActivity.class);
            //设置为非第一次登录
            SpUtils.getInstance().putBoolean(Contants.SP_IS_FIRST_APP, false);

        } else {
            //3.判断是否登录，跳转到登录页或者主页
            String Token = SpUtils.getInstance().getString(Contants.SP_TOKEN, "");
            if (TextUtils.isEmpty(Token)) {
                //Token为空，表示未登录或者登录已过期
                intent.setClass(this, LoginActivity.class);
            } else {
                intent.setClass(this, MainActivity.class);
            }
        }
        startActivity(intent);
        finish();
    }
}