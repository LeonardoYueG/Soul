package com.test.framework.utils;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class AnimUtils {
    public static ObjectAnimator rotation(View view, int time){
        ObjectAnimator mAnim = ObjectAnimator.ofFloat(view,"rotation",0f,360f);
        mAnim.setDuration(time);
        mAnim.setRepeatMode(ValueAnimator.RESTART);
        mAnim.setRepeatCount(ValueAnimator.INFINITE);
        mAnim.setInterpolator(new LinearInterpolator());
        return mAnim;
    }
}
