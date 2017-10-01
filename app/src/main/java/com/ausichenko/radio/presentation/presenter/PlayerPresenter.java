package com.ausichenko.radio.presentation.presenter;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;

import com.ausichenko.radio.model.service.PlayerIntentService;
import com.ausichenko.radio.presentation.view.PlayerView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class PlayerPresenter extends MvpPresenter<PlayerView> {

    private boolean isPlaying = false;

    private AudioManager mAudioManager;

    private ContentObserver mVolumeObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            if (mAudioManager != null) {
                int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                getViewState().volumeChanged(volume);
            }
        }
    };

    public void initAudioManager(Context context) {
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        getViewState().setMaxVolume(maxVolume);
        getViewState().volumeChanged(volume);

        context.getContentResolver().registerContentObserver(
                android.provider.Settings.System.CONTENT_URI, true,
                mVolumeObserver);
    }

    public void setVolume(int volume) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    }

    public void playStop(Context context, String streamUrl) {
        if (!isPlaying) {
            play(context, streamUrl);
        } else {
            stop(context);
        }
    }

    private void play(Context context, String streamUrl) {
        PlayerIntentService.startActionPlay(context, streamUrl);
        isPlaying = true;
        getViewState().play();
    }

    private void stop(Context context) {
        PlayerIntentService.startActionStop(context);
        isPlaying = false;
        getViewState().stop();
    }



}
