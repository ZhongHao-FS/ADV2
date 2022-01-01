package com.fullsail.android.canvasdrawing;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class MainActivity extends Activity {

    private GLSurfaceView gLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gLView = new DrawingSurface(this);
        gLView.setBackgroundResource(R.drawable.cosmic);
        setContentView(gLView);
    }
}
