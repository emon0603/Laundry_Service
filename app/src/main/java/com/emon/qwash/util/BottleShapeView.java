package com.emon.qwash.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

public class BottleShapeView extends View {

    private Paint paint;
    private Path path;

    public BottleShapeView(Context context) {
        super(context);
        init(context);
    }

    public BottleShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottleShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(context, android.R.color.background_dark)); // or any color you want
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        // Similar ratio to the vector shape you gave
        path.reset();

        // Top curve edges (simulate bezier from SVG)
        path.moveTo(width * 0.037f, height * 0.1425f);
        path.cubicTo(width * 0.042f, height * 0.027f, width * 0.107f, 0,
                width * 0.187f, 0); // Left side curve
        path.lineTo(width * 0.802f, 0);
        path.cubicTo(width * 0.882f, 0, width * 0.958f, height * 0.027f,
                width * 0.963f, height * 0.1425f); // Right side curve

        // Bottom right curve to bottom edge
        path.lineTo(width * 0.987f, height * 0.8425f);
        path.cubicTo(width, height * 0.928f, width * 0.928f, height,
                width * 0.837f, height);

        // Line to bottom left curve
        path.lineTo(width * 0.153f, height);
        path.cubicTo(width * 0.062f, height, 0, height * 0.928f,
                width * 0.013f, height * 0.8425f);

        // Close the path
        path.close();

        canvas.drawPath(path, paint);
    }

    public void setCustomColor(int color) {
        paint.setColor(color);
        invalidate();
    }
}
