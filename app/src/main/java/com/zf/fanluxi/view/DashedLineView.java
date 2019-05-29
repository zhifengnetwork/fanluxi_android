package com.zf.fanluxi.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;

public class DashedLineView extends View {
    public DashedLineView(Context context) {
        super(context);
    }

    public DashedLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        Path path = new Path();
        path.moveTo(0, 10);
        path.lineTo(10,480);
        PathEffect effects = new DashPathEffect(new float[]{10,10,10,10},2);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }
}
