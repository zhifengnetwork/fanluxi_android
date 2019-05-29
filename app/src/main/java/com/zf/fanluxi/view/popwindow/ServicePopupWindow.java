package com.zf.fanluxi.view.popwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.zf.fanluxi.R;

public abstract class ServicePopupWindow {

    protected Activity context;
    private View contentView;
    private PopupWindow mInstance;
    private boolean isShowing = false;

    public ServicePopupWindow(Activity c, int layoutRes, int w, int h) {
        context = c;
        contentView = LayoutInflater.from(c).inflate(layoutRes, null, false);
        initView();
        mInstance = new PopupWindow(contentView, w, h, true);
        initWindow();
    }

    public View getContentView() {
        return contentView;
    }

    public PopupWindow getPopupWindow() {
        return mInstance;
    }

    protected abstract void initView();

    private void initWindow() {
        mInstance.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mInstance.setOutsideTouchable(true);
        mInstance.setTouchable(true);
        /** 设置出入动画 */
        mInstance.setAnimationStyle(R.style.pop_translate);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        mInstance.showAtLocation(parent, gravity, x, y);
        isShowing = true;
        mInstance.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //隐藏后显示背景为透明
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1.0f;
                context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                context.getWindow().setAttributes(lp);
            }
        });

        //显示时候设置背景为灰色
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.7f;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public void onDismiss() {
        mInstance.dismiss();
        isShowing = false;

    }

}