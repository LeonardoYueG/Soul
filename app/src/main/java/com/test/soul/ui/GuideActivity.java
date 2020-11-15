package com.test.soul.ui;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.test.framework.base.basePageAdapter;
import com.test.framework.base.baseUIActivity;
import com.test.framework.manager.MediaPlayerManager;
import com.test.framework.utils.AnimUtils;
import com.test.soul.R;

import java.io.File;
import java.io.InputStream;
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

public class GuideActivity extends baseUIActivity implements View.OnClickListener {

    private ImageView iv_music_switch;
    private TextView tv_guide_skip;
    private ImageView iv_guide_point_1;
    private ImageView iv_guide_point_2;
    private ImageView iv_guide_point_3;
    /**
     * 继承自ViewGroup,同样是view的容器，提供左划右划的效果
     * 需要使用PagerAdapter适配器类提供数据
     */
    private ViewPager mViewPager;

    private View view1;
    private View view2;
    private View view3;

    private List<View> mPageList = new ArrayList<>();
    private basePageAdapter mPageAdapter;

    //帧动画
    private ImageView iv_guide_star;
    private ImageView iv_guide_night;
    private ImageView iv_guide_smile;

    //音乐播放器
    private MediaPlayerManager mMediaPlayerManager;

    //音乐播放器旋转动画
    private ObjectAnimator mMusicAnim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    private void initView() {
        iv_music_switch = (ImageView) findViewById(R.id.iv_music_switch);
        tv_guide_skip = (TextView) findViewById(R.id.tv_guide_skip);
        iv_guide_point_1 = (ImageView) findViewById(R.id.iv_guide_point_1);
        iv_guide_point_2 = (ImageView) findViewById(R.id.iv_guide_point_2);
        iv_guide_point_3 = (ImageView) findViewById(R.id.iv_guide_point_3);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        iv_music_switch.setOnClickListener(this);
        tv_guide_skip.setOnClickListener(this);

        //静态的方式进行填充
        view1 = View.inflate(this, R.layout.layout_pager_guide_1, null);
        view2 = View.inflate(this, R.layout.layout_pager_guide_2, null);
        view3 = View.inflate(this, R.layout.layout_pager_guide_3, null);

        //找到帧动画对象
        iv_guide_star = view1.findViewById(R.id.iv_guide_star);
        iv_guide_night = view2.findViewById(R.id.iv_guide_night);
        iv_guide_smile = view3.findViewById(R.id.iv_guide_smile);

        mPageList.add(view1);
        mPageList.add(view2);
        mPageList.add(view3);

        //预加载，default = 1, 一次性加载所有页面
        mViewPager.setOffscreenPageLimit(mPageList.size());

        mPageAdapter = new basePageAdapter(mPageList);
        mViewPager.setAdapter(mPageAdapter);

        //播放帧动画
        AnimationDrawable animStar = (AnimationDrawable) iv_guide_star.getBackground();
        animStar.start();

        AnimationDrawable animNight = (AnimationDrawable) iv_guide_night.getBackground();
        animNight.start();

        AnimationDrawable animSmile = (AnimationDrawable) iv_guide_smile.getBackground();
        animSmile.start();

        //小圆点逻辑：监听滑动逻辑
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * 播放歌曲
         */
        startMusic();

    }

    /**
     * 播放歌曲
     */
    private void startMusic() {
        mMediaPlayerManager = new MediaPlayerManager();
        mMediaPlayerManager.setLooping(true);
        AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.guide);
        mMediaPlayerManager.startPlay(file);
        mMediaPlayerManager.setOnComplteionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMediaPlayerManager.startPlay(file);
            }
        });

        mMusicAnim = AnimUtils.rotation(iv_music_switch, 2 * 1000);
        mMusicAnim.start();


    }

    /**
     * 动态选择小圆点
     *
     * @param position
     */
    private void selectPoint(int position) {
        switch (position) {
            case 0:
                iv_guide_point_1.setImageResource(R.drawable.img_guide_point_p);
                iv_guide_point_2.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_3.setImageResource(R.drawable.img_guide_point);
                break;
            case 1:
                iv_guide_point_1.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_2.setImageResource(R.drawable.img_guide_point_p);
                iv_guide_point_3.setImageResource(R.drawable.img_guide_point);
                break;
            case 2:
                iv_guide_point_1.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_2.setImageResource(R.drawable.img_guide_point);
                iv_guide_point_3.setImageResource(R.drawable.img_guide_point_p);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_guide_skip:
                if (mMediaPlayerManager.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PAUSE){
                    mMusicAnim.start();
                    mMediaPlayerManager.MEDIA_STATUS = MediaPlayerManager.MEDIA_STATUS_PLAY;
                    mMediaPlayerManager.continuePlay();
                    iv_music_switch.setImageResource(R.drawable.img_guide_music);
                }else if(mMediaPlayerManager.MEDIA_STATUS == MediaPlayerManager.MEDIA_STATUS_PLAY){
                    mMusicAnim.pause();
                    mMediaPlayerManager.puasePlay();
                    mMediaPlayerManager.MEDIA_STATUS = MediaPlayerManager.MEDIA_STATUS_PAUSE;
                    iv_music_switch.setImageResource(R.drawable.img_guide_music_off);
                }
                break;
            case R.id.iv_music_switch:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayerManager.stopPlay();
    }
}