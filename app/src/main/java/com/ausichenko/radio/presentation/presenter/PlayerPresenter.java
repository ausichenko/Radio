package com.ausichenko.radio.presentation.presenter;

import android.content.Context;

import com.ausichenko.radio.model.PlayerIntentService;
import com.ausichenko.radio.presentation.view.PlayerView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

@InjectViewState
public class PlayerPresenter extends MvpPresenter<PlayerView> {

    private boolean isPlaying = false;

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
