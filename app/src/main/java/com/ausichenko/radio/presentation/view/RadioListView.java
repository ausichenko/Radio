package com.ausichenko.radio.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.ausichenko.radio.model.pojo.Radio;

import java.util.List;

public interface RadioListView extends MvpView {

    void showProgress();
    void hideProgress();
    void showData(List<Radio> data);
    void addData(List<Radio> data);
    void showError(Throwable error);
    void showEmptyList();
}
