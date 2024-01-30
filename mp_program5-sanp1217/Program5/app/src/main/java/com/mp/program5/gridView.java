package com.mp.program5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class gridView extends View {
    private Paint linePaint;
    private int gridSize;

    public gridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(10);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        gridSize = getWidth() / 3;

        canvas.drawLine(0, gridSize, getWidth(), gridSize, linePaint);
        canvas.drawLine(0, 2 * gridSize, getWidth(),2 * gridSize, linePaint);

        canvas.drawLine(gridSize, 0, gridSize, getHeight(), linePaint);
        canvas.drawLine(2 * gridSize, 0, 2 * gridSize, getHeight(), linePaint);
    }
}
