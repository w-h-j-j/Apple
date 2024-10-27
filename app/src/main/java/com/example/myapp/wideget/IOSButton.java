package com.example.myapp.wideget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapp.R;

public class IOSButton extends View implements View.OnClickListener {

    private int checkColor = Color.GREEN;
    private int unCheckColor = Color.GRAY;
    private int thumbColor = Color.WHITE;
    private boolean check = false;

    private Paint mOutPaint;
    private Paint mSwitchPaint;

    private RectF mDrawRoundRectF = new RectF();

    private int mRadius;

    private int mRx;

    private IosListener listener;

    public IOSButton(Context context) {
        super(context);
    }

    public IOSButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IOSButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IOSButton);
            checkColor = typedArray.getColor(R.styleable.IOSButton_check_color, Color.GREEN);
            unCheckColor = typedArray.getColor(R.styleable.IOSButton_uncheck_color, Color.GRAY);
            thumbColor = typedArray.getColor(R.styleable.IOSButton_thumb_color, Color.WHITE);
            check = typedArray.getBoolean(R.styleable.IOSButton_check, false);
            typedArray.recycle();
        }

        initPaint();
    }

    private void initPaint() {
        mOutPaint = new Paint();
        mSwitchPaint = new Paint();

        mOutPaint.setColor(unCheckColor);
        mOutPaint.setAntiAlias(true);
        mSwitchPaint.setColor(thumbColor);
        mSwitchPaint.setAntiAlias(true);

        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        check = !check;
    }

    private int dipToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        mDrawRoundRectF.set(0, 0, getWidth(), getHeight());
        mRadius = getHeight() / 2 - dipToPx(2);

        mOutPaint.setColor(check ? checkColor : unCheckColor);
        canvas.drawRoundRect(mDrawRoundRectF, (float) getHeight() / 2,
                (float) getHeight() / 2, mOutPaint);

        if (mRx == 0) {
            if (!check) {
                mRx = mRadius + dipToPx(2);
            } else {
                mRx = getWidth() - mRadius - dipToPx(2);
            }
        }
        canvas.drawCircle(mRx, mRadius + dipToPx(2), mRadius, mSwitchPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {//warp_content 给个默认的
            width = dipToPx(80);
            height = width / 2;
        } else if (heightMode == MeasureSpec.AT_MOST && widthMode == MeasureSpec.EXACTLY) {
            height = width / 2;
        }

        height = Math.min(width / 2, height);//高度不能大于 宽度的一半

        setMeasuredDimension(width, height);
    }

    public void setIosListener(IosListener listener) {
        listener = listener;
    }

    public interface IosListener{
        void onChange(boolean check);
    }
}
