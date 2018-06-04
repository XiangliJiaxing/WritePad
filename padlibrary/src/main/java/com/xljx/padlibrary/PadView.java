package com.xljx.padlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class PadView extends View {

    private static final String TAG = PadView.class.getSimpleName();

    private Paint mPaint;
    private Canvas mCanvas;

    public PadView(Context context) {
        this(context, null);
    }

    public PadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addListener();
        generatePaint();
    }

//    @RequiresApi(21)
//    public PadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    private float startX;
    private float startY;
    private void addListener(){

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        startY = event.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        float eventX = event.getX();
                        float eventY = event.getY();
                        mCanvas.drawLine(startX, startY, eventX, eventY, mPaint);
//                        mCanvas.save();
                        startX = eventX;
                        startY = eventY;
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;


    }

    private void generatePaint() {
        mPaint = new Paint();
//        mPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));
        mPaint.setAlpha(80);
        mPaint.setAntiAlias(true);
//        new PathEffect()
//        mPaint.setPathEffect()


    }
}
