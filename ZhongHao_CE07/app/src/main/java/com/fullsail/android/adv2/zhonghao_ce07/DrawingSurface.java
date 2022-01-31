package com.fullsail.android.adv2.zhonghao_ce07;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.InputStream;
import java.util.ArrayList;

public class DrawingSurface extends SurfaceView implements SurfaceHolder.Callback {
    public static int width;
    public static int height;
    private int mPrize;
    private Rect mDimensions;
    private Paint mBlankPaint;
    private Paint mTextPaint;
    private Paint mItemPaint;
    private Bitmap mBackground;
    private Bitmap mHole;
    private ArrayList<Rect> mDigs;
    private ArrayList<Point> mPoints;
    private ArrayList<Item> mItemList = new ArrayList<>();

    public DrawingSurface(Context context) {
        super(context);
    }

    public DrawingSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawingSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        storeDimensions(surfaceHolder);

        InputStream inputStream = getResources().openRawResource(R.raw.items);
        CsvUtil csvFile = new CsvUtil(inputStream);
        mItemList = csvFile.read();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        storeDimensions(surfaceHolder);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        setWillNotDraw(false);
        getHolder().addCallback(this);

        Resources res = getResources();
        mBackground = BitmapFactory.decodeResource(res, R.drawable.field);
        mHole = BitmapFactory.decodeResource(res, R.drawable.hole);

        mPrize = 0;
        mBlankPaint = new Paint();

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTextSize(60.0f);

        mDigs = new ArrayList<>();

        mItemPaint = new Paint();
        mItemPaint.setColor(Color.YELLOW);
        mPoints = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mBackground, null, mDimensions, mBlankPaint);

        int itemsLeft = mItemList.size() - mPoints.size();
        canvas.drawText("Items left: " + itemsLeft + " Total Prize: " + mPrize,
                10.0f, 80.0f, mTextPaint);

        for (Rect r: mDigs) {
            canvas.drawBitmap(mHole, null, r, mBlankPaint);
        }
        for (Point p: mPoints) {
            canvas.drawCircle(p.x, p.y, 10.0f, mItemPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Rect coordinate = new Rect((int) event.getX() - 50, (int) event.getY() - 50,
                    (int) event.getX() + 50, (int) event.getY() + 50);
            mDigs.add(coordinate);

            for (Item treasure : mItemList) {
                if (coordinate.contains(treasure.getX(), treasure.getY())) {
                    mPoints.add(new Point(treasure.getX(), treasure.getY()));
                    mPrize += treasure.getValue();
                }
            }
            postInvalidate();
        }

        return super.onTouchEvent(event);
    }

    public void storeDimensions(SurfaceHolder _holder) {
        // Lock the canvas to get an instance of it back.
        Canvas canvas = _holder.lockCanvas();
        // Retrieve the dimensions and hold onto them for later.
        mDimensions = new Rect(0, 0, canvas.getWidth(), canvas.getHeight());
        width = canvas.getWidth();
        height = canvas.getHeight();
        // Release the canvas and post a draw.
        _holder.unlockCanvasAndPost(canvas);
    }
}
