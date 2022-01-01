package com.fullsail.android.canvasdrawing;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class DrawingSurface extends GLSurfaceView implements SurfaceHolder.Callback {

    private final GLRenderer mRenderer;
    private Paint mTextPaint;
    private Paint mBlackHolePaint;
    private ArrayList<Point> mPoints;

    public DrawingSurface(Context context) {
        super(context);

        setEGLContextClientVersion(3);

        mRenderer = new GLRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setWillNotDraw(false);
        getHolder().addCallback(this);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(60.0f);

        mBlackHolePaint = new Paint();
        mBlackHolePaint.setColor(Color.BLACK);

        mPoints = new ArrayList<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPoints.add(new Point((int) event.getX(), (int) event.getY()));
            postInvalidate();
        }

        return super.onTouchEvent(event);
    }
}
