package com.ausichenko.radio.presentation.view;

import com.arellomobile.mvp.MvpView;

public interface PlayerView extends MvpView {

    void play();
    void stop();
    void setMaxVolume(int maxVolume);
    void volumeChanged(int volume);
}
