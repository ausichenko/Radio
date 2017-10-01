package com.ausichenko.radio.presentation.presenter;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.text.TextUtils;

import com.ausichenko.radio.model.pojo.Stream;
import com.ausichenko.radio.model.service.PlayerIntentService;
import com.ausichenko.radio.presentation.view.PlayerView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

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

    public void playStop(Context context, List<Stream> streamList) {
        if (!isPlaying) {
            play(context, streamList);
        } else {
            stop(context);
        }
    }

    private void play(Context context, List<Stream> streamList) {
        String streamUrl = "";
        for (Stream stream : streamList) {
            if (stream.getStatus() == 1) {
                streamUrl = stream.getStream();
            }
        }
        if (!TextUtils.isEmpty(streamUrl)) {
            PlayerIntentService.startActionPlay(context, streamUrl);
            isPlaying = true;
            getViewState().play();
        }
    }

    public void stop(Context context) {
        PlayerIntentService.startActionStop(context);
        isPlaying = false;
        getViewState().stop();
    }

}
