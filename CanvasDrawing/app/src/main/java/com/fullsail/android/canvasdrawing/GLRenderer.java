package com.fullsail.android.canvasdrawing;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRenderer implements GLSurfaceView.Renderer {

    private boolean mFirstDraw;
    private boolean mSurfaceCreated;
    private int mWidth;
    private int mHeight;
    private long mLastTime;
    private final float DEG2RAD = 3.14159f/180;

    public GLRenderer() {
        mFirstDraw = true;
        mSurfaceCreated = false;
        mWidth = -1;
        mHeight = -1;
        mLastTime = System.currentTimeMillis();

        GLES30.glClearColor(0f, 0f, 0f, 0f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mSurfaceCreated = true;
        mWidth = -1;
        mHeight = -1;
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        GLES30.glViewport(0, 0, i, i1);

        if (!mSurfaceCreated && i == mWidth && i1 == mHeight) {
            return;
        }

        mWidth = i;
        mHeight = i1;
        mSurfaceCreated = false;
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastTime >= 1000) {
            mLastTime = currentTime;
        }
        if (mFirstDraw) {
            mFirstDraw = false;
        }
    }
}
