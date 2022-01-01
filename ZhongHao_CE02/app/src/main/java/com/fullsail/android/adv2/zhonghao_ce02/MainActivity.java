package com.fullsail.android.adv2.zhonghao_ce02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public class MainActivity extends AppCompatActivity implements ServiceConnection, ControlFragment.PlaybackCommandListener {

    private AudioPlaybackService mService;
    private boolean mBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            ControlFragment frag = ControlFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag, ControlFragment.TAG)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mBound = false;
        Intent serviceIntent = new Intent(this, AudioPlaybackService.class);
        bindService(serviceIntent, this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unbindService(this);
        mBound = false;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        AudioPlaybackService.AudioServiceBinder binder = (AudioPlaybackService.AudioServiceBinder) iBinder;
        mService = binder.getService();
        mBound = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mBound = false;
    }

    @Override
    public void play() {
        if (mBound) {
            mService.play();
        }

        Intent serviceIntent = new Intent(this, AudioPlaybackService.class);
        startService(serviceIntent);
    }

    @Override
    public void pause() {
        if (mBound) {
            mService.pause();
        }
    }

    @Override
    public void next() {
        if (mBound) {
            mService.next();
        }
    }

    @Override
    public void stop() {
        if (mBound) {
            mService.stop();
        }

        Intent serviceIntent = new Intent(this, AudioPlaybackService.class);
        stopService(serviceIntent);
    }
}