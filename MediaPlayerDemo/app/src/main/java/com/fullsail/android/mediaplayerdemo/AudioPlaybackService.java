package com.fullsail.android.mediaplayerdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;


public class AudioPlaybackService extends Service implements MediaPlayer.OnPreparedListener {

    private static final int STATE_IDLE = 0;
    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_PREPARING = 2;
    private static final int STATE_PREPARED = 3;
    private static final int STATE_STARTED = 4;
    private static final int STATE_PAUSED = 5;
    private static final int STATE_STOPPED = 6;
    private static final int STATE_PLAYBACK_COMPLETED = 7;
    private static final int STATE_END = 8;
    private static final int NOTIFICATION_ID = 0x0011;
    private static final String CHANNEL_ID = "AUDIO_CHANNEL";
    private static final String CHANNEL_NAME = "Audio Channel";
    private int mState;
    private MediaPlayer mPlayer;

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // Mark that we've reached STATE_PREPARED.
        mState = STATE_PREPARED;
        // Start the player to move to STATE_STARTED and play audio.
        mPlayer.start();
        mState = STATE_STARTED;
    }

    public class AudioServiceBinder extends Binder {
        public AudioPlaybackService getService() {
            return AudioPlaybackService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new AudioServiceBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mPlayer = new MediaPlayer();
        mState = STATE_IDLE;

        // Can be done in any state, but should be done before preparing.
        mPlayer.setOnPreparedListener(this);
        buildNotificationChannel();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPlayer.release();
        mState = STATE_END;
    }

    private void buildNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private Notification buildNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle("Music Playing");
        builder.setContentText("Click to Reopen App");
        builder.setOngoing(true);

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(activityPendingIntent);

        return builder.build();
    }

    public void play() {
        if (mState == STATE_PAUSED) {
            mPlayer.start();
            mState = STATE_STARTED;
        } else if (mState != STATE_STARTED && mState != STATE_PREPARING) {
            // Reset the player to make sure we're in STATE_IDLE.
            mPlayer.reset();
            mState = STATE_IDLE;

            try {
                // Specify our song location as a resource URI.
                Uri songUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.something_elated);
                // Set our data source to transition to STATE_INITIALIZED
                mPlayer.setDataSource(this, songUri);
                mState = STATE_INITIALIZED;
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Make sure we initialized properly.
            if (mState == STATE_INITIALIZED) {
                // Start preparing asynchronously to move to STATE_PREPARING
                mPlayer.prepareAsync();
                mState = STATE_PREPARING;
            }

            Notification ongoing = buildNotification();
            startForeground(NOTIFICATION_ID, ongoing);
        }
    }

    public void pause() {
        if (mState == STATE_STARTED) {
            mPlayer.pause();
            mState = STATE_PAUSED;
        }
    }

    public void stop() {
        if (mState == STATE_STARTED || mState == STATE_PAUSED || mState == STATE_PLAYBACK_COMPLETED) {
            mPlayer.stop();
            mState = STATE_STOPPED;
            
            stopForeground(true);
        }
    }
}
