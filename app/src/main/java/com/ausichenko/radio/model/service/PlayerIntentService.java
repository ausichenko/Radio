package com.ausichenko.radio.model.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class PlayerIntentService extends Service
        implements AudioManager.OnAudioFocusChangeListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener {

    private static final String TAG = PlayerIntentService.class.getSimpleName();

    private static final String ACTION_PLAY = "action_play";
    private static final String ACTION_STOP = "action_stop";

    private static final String EXTRA_STREAM_URL = "stream_url";
    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;
    private String mStreamUrl;

    public static void startActionPlay(Context context, String streamUrl) {
        Intent intent = new Intent(context, PlayerIntentService.class);
        intent.setAction(ACTION_PLAY);
        intent.putExtra(EXTRA_STREAM_URL, streamUrl);
        context.startService(intent);
    }

    public static void startActionStop(Context context) {
        Intent intent = new Intent(context, PlayerIntentService.class);
        intent.setAction(ACTION_STOP);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PLAY.equals(action)) {
                final String streamUrl = intent.getStringExtra(EXTRA_STREAM_URL);
                handleActionPlay(streamUrl);
            } else if (ACTION_STOP.equals(action)) {
                handleActionStop();
            }
        }
        return START_STICKY;
    }

    private void handleActionPlay(String streamUrl) {
        Log.d(TAG, "User explicitly started play back");
        mStreamUrl = streamUrl;
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            handleActionStop();
        }
        if (requestFocus()) {
            Log.i(TAG, "Audio focus was granted.");
            if (mMediaPlayer == null) {
                initMediaPlayer();
            }
        } else {
            Log.e(TAG, "Failed to request audio focus.");
            // Schedule Alarm event. Maybe exponential backoff
        }
    }

    private void handleActionStop() {
        Log.i(TAG, "User explicitly stopped play back");
        if (abandonFocus()) {
            Log.i(TAG, "Audio focus abandoned.");
            releasePlayer();
        } else {
            Log.e(TAG, "OMG! We can`t abandon audio focus. Something terrible happened!");
        }
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                Log.d(TAG, "Resuming playback");
                if (mMediaPlayer == null) {
                    initMediaPlayer();
                } else if (!mMediaPlayer.isPlaying()) {
                    mMediaPlayer.start();
                }
                mMediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                Log.d(TAG, "Focus lost. Releasing MediaPlayer");
                releasePlayer();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                Log.d(TAG, "Focus lost for short time. Pausing play back.");
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                Log.d(TAG, "Focus lost for short time, but lets lower play back.");
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.setVolume(0.1f, 0.1f);
                }
                break;
        }
    }

    private void releasePlayer() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void initMediaPlayer() {
        Log.d(TAG, "Initializing player");
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);

        try {
            Log.e(TAG, "Setting data source: " + mStreamUrl);
            mMediaPlayer.setDataSource(mStreamUrl);

            Log.d(TAG, "Preparing player");
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer player) {
        Log.i(TAG, "Starting playback");
        player.start();
    }

    @Override
    public boolean onError(MediaPlayer player, int what, int extra) {
        Log.e(TAG, "Something bad happened with MediaPlayer \n What: " + what + " \n Extra: " + extra);
        Log.i(TAG, "Resetting media player");
        player.reset();
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Service is about to die.");
        releasePlayer();
    }

    private boolean requestFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                mAudioManager.requestAudioFocus(this,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN);
    }

    private boolean abandonFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                mAudioManager.abandonAudioFocus(this);
    }
}
