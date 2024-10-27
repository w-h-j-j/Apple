package com.example.myapp.wideget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


//https://blog.csdn.net/spring_he/article/details/10387301
public class FillterView extends View {

    String[] res  = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private Paint paint;
    private int setSelection = -1;
    private int singleHeight;

    public FillterView(Context context) {
        super(context);
    }

    public FillterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(30);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        //单个字母的高度
        singleHeight = height / (res.length);
        for (int index = 0; index < res.length; index++) {
            if (setSelection == index){
                paint.setColor(Color.RED);
                paint.setFakeBoldText(true);
            }else {
                paint.setColor(Color.BLACK);
                paint.setFakeBoldText(false);
            }
            int x = width / 2;
            int y = singleHeight*index+singleHeight;
            canvas.drawText(res[index], x, y, paint);
            //paint.reset();
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                index = (int) y / (height / res.length);
                Log.d("", "点击的字母   index: "+index);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("----->:ACTION_UP ");
                invalidate();
                break;
        }
        return true;
    }

    private int width;
    private int height;
    private int index = -1;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
