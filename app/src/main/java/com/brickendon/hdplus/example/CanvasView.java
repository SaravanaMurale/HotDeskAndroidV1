package com.brickendon.hdplus.example;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.brickendon.hdplus.R;

public class CanvasView extends View {
    private static final int SQUARE_SIZE=100;
    private Rect mRectSquare;
    private Paint mPaintSquare;
    private ShapeDrawable drawable;
    public CanvasView(Context context) {
        super(context);
        inti(null);

    }


    private void inti(AttributeSet set) {
        mRectSquare = new Rect();
        mPaintSquare = new Paint(Paint.ANTI_ALIAS_FLAG);
         if (set == null)
             return;

        TypedArray ts = getContext().obtainStyledAttributes(set,R.styleable.CanvasView);
    }

    public CanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inti(attrs);

    }

    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inti(attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRectSquare.left =50;
        mRectSquare.top =50;
        mRectSquare.right =mRectSquare.left +SQUARE_SIZE;
        mRectSquare.right =mRectSquare.top +SQUARE_SIZE;

        mPaintSquare.setColor(Color.GREEN);
        canvas.drawRect(mRectSquare,mPaintSquare);
    }
    public void swapColor(){
        mPaintSquare.setColor(mPaintSquare.getColor()== Color.GREEN ? Color.RED:Color.GREEN);
        postInvalidate();
    }
    public CanvasView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }
}
