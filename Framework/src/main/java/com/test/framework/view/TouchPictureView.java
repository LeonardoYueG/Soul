package com.test.framework.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.test.framework.R;

/**
 * FileName: TouchPictureV
 * Founder: LiuGuiLin
 * Profile: 图片验证View
 */
public class TouchPictureView extends View {

    //背景
    private Bitmap mbgBitmap;
    //背景画笔
    private Paint mBgPaint;

    //空白块
    private Bitmap mNullBitmap;
    //空白块画笔
    private Paint mNullPaint;

    //移动方块
    private Bitmap mMoveBitmap;
    //移动方块画笔
    private Paint mMovePaint;

    //View的宽 高
    private int mWidth;
    private int mHeight;

    //方块大小
    private int CARD_SIZE = 200;
    //方块坐标
    private int LINE_W, LINE_H = 0;

    //移动方块横坐标
    private int moveX = 200;
    //误差值
    private int errorValues = 10;

    //设置滑动监听器
    private OnViewResultListener viewResultListener;

    public void setViewResultListener(OnViewResultListener viewResultListener) {
        this.viewResultListener = viewResultListener;
    }


    public TouchPictureView(Context context) {
        super(context);
        init();
    }

    public TouchPictureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TouchPictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TouchPictureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        mBgPaint = new Paint();
        mNullPaint = new Paint();
        mMovePaint = new Paint();
    }


    /**
     * get the width and height of view
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBg(canvas);
        drawNullCard(canvas);
        drawMoveCard(canvas);
    }

    /**
     * 绘制移动滑块
     *
     * @param canvas
     */
    private void drawMoveCard(Canvas canvas) {
        //1.截取空白块位置坐标的Bitmap图像
        mMoveBitmap = Bitmap.createBitmap(mbgBitmap, LINE_W, LINE_H, CARD_SIZE, CARD_SIZE);
        //2.绘制在View上 如果使用LINE_W, LINE_H，那会和空白块重叠
        canvas.drawBitmap(mMoveBitmap, moveX, LINE_H, mMovePaint);

    }

    /**
     * 绘制空白快
     *
     * @param canvas
     */
    private void drawNullCard(Canvas canvas) {
        //1.获取空白块图片
        mNullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_null_card);

        //2.获取空白块大小
        CARD_SIZE = mNullBitmap.getWidth();

        //3.生成空白块位置
        //99 / 3 = 33 * 2 = 66
        LINE_W = mWidth / 3 * 2;
        //除以2并不是中心
        LINE_H = mHeight / 2 - (CARD_SIZE / 2);

        //4.绘制
        canvas.drawBitmap(mNullBitmap, LINE_W, LINE_H, mNullPaint);

    }

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        //1.获取图片
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_bg);
        //2.创建一个空的Bitmap Bitmap w h = View w h
        mbgBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        //3.将图片绘制到空的Bitmap
        Canvas bgCanvas = new Canvas(mbgBitmap);
        bgCanvas.drawBitmap(mBitmap, null, new Rect(0, 0, mWidth, mHeight), mBgPaint);
        //4.将bgBitmap绘制到view上
        canvas.drawBitmap(mbgBitmap, null, new Rect(0, 0, mWidth, mHeight), mBgPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //判断点击的坐标是否是方块的内部，如果是就可以拖动
                break;
            case MotionEvent.ACTION_MOVE:
                //防止越界
                if (event.getX() > 0 && event.getX() < (mWidth - CARD_SIZE)) {
                    moveX = (int) event.getX();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                //验证结果
                if (moveX > (LINE_W - errorValues) && moveX < (LINE_W + errorValues)) {
                    if(viewResultListener != null){
                        viewResultListener.onResult();
                        //重置
                        moveX = 200;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }


    public interface OnViewResultListener {
        void onResult();
    }

}
