package com.test.soul.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.test.framework.base.baseUIActivity;
import com.test.soul.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导页面
 * 1.ViewPager : 适配器|帧动画播放
 * 2.小圆点的逻辑
 * 3.歌曲的播放
 * 4.属性动画旋转
 * 5.跳转
 */

public class GuideActivity extends baseUIActivity {

    private ImageView iv_music_switch;
    private TextView tv_guide_skip;
    private ImageView iv_guide_point_1;
    private ImageView iv_guide_point_2;
    private ImageView iv_guide_point_3;
    private ViewPager mViewPager;
    
    private View view1;
    private View view2;
    private View view3;
    
    private List<View> mPageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    private void initView(){
        iv_music_switch = (ImageView) findViewById(R.id.iv_music_switch);
        tv_guide_skip = (TextView) findViewById(R.id.tv_guide_skip);
        iv_guide_point_1 = (ImageView) findViewById(R.id.iv_guide_point_1);
        iv_guide_point_2 = (ImageView) findViewById(R.id.iv_guide_point_2);
        iv_guide_point_3 = (ImageView) findViewById(R.id.iv_guide_point_3);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        view1 = View.inflate(this, R.layout.layout_pager_guide_1, null);
        view2 = View.inflate(this, R.layout.layout_pager_guide_2, null);
        view3 = View.inflate(this, R.layout.layout_pager_guide_3, null);
    }


}