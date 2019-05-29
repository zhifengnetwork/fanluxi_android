package com.zf.fanluxi.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.zf.fanluxi.utils.LogUtils;

/**
 * 注意使用的时候需要放大的view,一般是第一个RelativeLayout或者LinearLayout。要加上 android:layout_gravity="center_horizontal"
 * <p>
 */
public class HeadZoomScrollView extends ScrollView {


    public HeadZoomScrollView(Context context) {
        super(context);
    }


    public HeadZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public HeadZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //用于记录下拉位置
    private float y = 0f;
    //zoomView原本的宽高
    private int zoomViewWidth = 0;
    private int zoomViewHeight = 0;
    //是否正在放大
    private boolean mScaling = false;
    //放大的view,默认为第一个子view
    private View zoomView;

    public void setZoomView(View zoomView) {
        this.zoomView = zoomView;
    }

    //滑动放大系数,系数越大,滑动时放大程度越大
    private float mScaleRatio = 1.0f;

    public void setmScaleRatio(float mScaleRatio) {
        this.mScaleRatio = mScaleRatio;
    }

    //最大的放大倍数
    private float mScaleTimes = 2f;

    public void setmScaleTimes(int mScaleTimes) {
        this.mScaleTimes = mScaleTimes;
    }

    //回弹时间系数,系数越小,回弹越快
    private float mReplyRatio = 0.5f;

    public void setmReplyRatio(float mReplyRatio) {
        this.mReplyRatio = mReplyRatio;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//      不可过度滚动,否则上移后下拉会出现部分空白的情况
        setOverScrollMode(OVER_SCROLL_NEVER);
//       获得默认第一个view
        if (getChildAt(0) != null && getChildAt(0) instanceof ViewGroup && zoomView == null) {
            ViewGroup vg = (ViewGroup) getChildAt(0);
            if (vg.getChildCount() > 0) {
                zoomView = vg.getChildAt(0);
            }
        }
    }

    /**
     *
     *
     */

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (zoomViewWidth <= 0 || zoomViewHeight <= 0) {
            zoomViewWidth = zoomView.getMeasuredWidth();
            zoomViewHeight = zoomView.getMeasuredHeight();
        }
        if (zoomView == null || zoomViewWidth <= 0 || zoomViewHeight <= 0) {
            return super.onTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:

                if (!mScaling) {
                    touchY = (int) ev.getY();
                    mScaling = true;
                }
                dis = (int) ((ev.getY() - touchY) * mScaleRatio);

                LogUtils.INSTANCE.e(">>>>dis：" + dis + "    ev.getY:" + ev.getY() + "   touchY:" + touchY);
                setZoom(finalY * mScaleRatio + dis);

                LogUtils.INSTANCE.e(">>>>finalY" + finalY);

                // 累计增加的高度
                leiJi = leiJi + (int) ev.getY() - touchY;

                LogUtils.INSTANCE.e(">>>>leiJi" + leiJi);
                //判断如果超出一定就break
                if (leiJi >= 600) {
                    return true;
                }
                return true;
            case MotionEvent.ACTION_UP:

                finalY = finalY + (int) (ev.getY() - touchY);

                mScaling = false;
//                replyView();
                break;
        }
        return super.onTouchEvent(ev);
    }

    int leiJi;
    private int finalY = 0; //累计增加的高度
    private int touchY;  //第一次按的焦点
    private int dis; //距离

    /**
     * 放大view
     */
    private void setZoom(float s) {
        float scaleTimes = (float) ((zoomViewWidth + s) / (zoomViewWidth * 1.0));
//        如超过最大放大倍数,直接返回
        if (scaleTimes > mScaleTimes) return;

        ViewGroup.LayoutParams layoutParams = zoomView.getLayoutParams();
//        layoutParams.width = (int) (zoomViewWidth + s);
        layoutParams.width = (int) (zoomViewWidth);
        layoutParams.height = (int) (zoomViewHeight * ((zoomViewHeight + s) / zoomViewHeight));


//        设置控件水平居中
        ((MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - zoomViewWidth) / 2, 0, 0, 0);
        zoomView.setLayoutParams(layoutParams);
    }


    /**
     * 回弹
     */
    private void replyView() {
        final float distance = zoomView.getMeasuredWidth() - zoomViewWidth;
        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F).setDuration((long) (distance * mReplyRatio));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }
        });
        anim.start();
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) onScrollListener.onScroll(l, t, oldl, oldt);
    }


    private OnScrollListener onScrollListener;

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }


    /**
     * 滑动监听
     */
    public interface OnScrollListener {
        void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
