package com.fullsail.android.adv2.zhonghao_ce02;

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

public class AudioPlaybackService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

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
    private final int[] songArray = new int[] {R.raw.bensound_ukulele, R.raw.bensound_creativeminds,
            R.raw.bensound_anewbeginning};
    private final String[] songNameArray = new String[] {"ukulele", "creative minds",
            "a new beginning"};
    private int mCurrentSong;
    private boolean mLoop = false;
    private boolean mShuffle = false;

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
        mCurrentSong = 0;

        // Can be done in any state, but should be done before preparing.
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        buildNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals("Next")) {
                next();
            } else if (intent.getAction().equals("Previous")) {
                previous();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPlayer.release();
        mState = STATE_END;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // Mark that we've reached STATE_PREPARED.
        mState = STATE_PREPARED;
        // Start the player to move to STATE_STARTED and play audio.
        mPlayer.start();
        mState = STATE_STARTED;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mState = STATE_PLAYBACK_COMPLETED;
        next();

        if (!mLoop && mCurrentSong == 0) {
            stop();
        }
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
        Intent nextIntent = new Intent(this, AudioPlaybackService.class);
        nextIntent.setAction("Next");
        PendingIntent nextPendingIntent = PendingIntent.getService(this, 0, nextIntent, PendingIntent.FLAG_IMMUTABLE);
        Intent previousIntent = new Intent(this, AudioPlaybackService.class);
        previousIntent.setAction("Previous");
        PendingIntent previousPendingIntent = PendingIntent.getService(this, 0, previousIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.addAction(0, "Next", nextPendingIntent);
        builder.addAction(0, "Previous", previousPendingIntent);
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle(songNameArray[mCurrentSong]);
        builder.setContentText("Click to Reopen App");
        builder.setOngoing(true);

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                activityIntent, PendingIntent.FLAG_UPDATE_CURRENT + PendingIntent.FLAG_IMMUTABLE);
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
            freshPlay();

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

    public void next() {
        if (!mLoop) {
            if (mShuffle) {
                mCurrentSong = (int) (Math.random() * 3);
            } else if (mCurrentSong < 2) {
                mCurrentSong += 1;
            } else if (mCurrentSong == 2) {
                mCurrentSong = 0;
            }
        }

        mPlayer.reset();
        mState = STATE_IDLE;
        freshPlay();

        Notification ongoing = buildNotification();
        startForeground(NOTIFICATION_ID, ongoing);
    }

    public void previous() {
        if (!mLoop && !mShuffle) {
            if (mCurrentSong > 0) {
                mCurrentSong -= 1;
            } else if (mCurrentSong == 0) {
                mCurrentSong = 2;
            }
        }

        mPlayer.reset();
        mState = STATE_IDLE;
        freshPlay();

        Notification ongoing = buildNotification();
        startForeground(NOTIFICATION_ID, ongoing);
    }

    public void stop() {
        if (mState == STATE_STARTED || mState == STATE_PAUSED || mState == STATE_PLAYBACK_COMPLETED) {
            mPlayer.stop();
            mState = STATE_STOPPED;

            stopForeground(true);
        }
    }

    private void freshPlay() {
        try {
            // Specify our song location as a resource URI.
            Uri songUri = Uri.parse("android.resource://" + getPackageName() + "/" + songArray[mCurrentSong]);
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
    }
    
    public void loopSwitched(boolean _switch) {
        mLoop = _switch;
    }

    public void shuffleSwitched(boolean _switch) {
        mShuffle = _switch;
    }
}
