package com.mp.program5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TicTacToeCellView extends View {
    private Paint rectPaint, circlePaint;
    boolean isX;
    boolean isO;
    public TicTacToeCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        isO = false;
        isX = false;

        rectPaint = new Paint();
        rectPaint.setColor(Color.RED);

        circlePaint = new Paint();
        circlePaint.setColor(Color.BLUE);

    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY);

        if(isX){
            canvas.drawRect(centerX - radius, centerY - radius, centerX + radius, centerY + radius, rectPaint);
        }else if(isO){
            canvas.drawCircle(centerX, centerY, radius, circlePaint);
        }
    }

    public void setToX(){
        isX = true;
        invalidate();
    }

    public void setToO(){
        isO = true;
        invalidate();
    }

    public void reset(){
        isO = false;
        isX = false;
        invalidate();
    }
}
