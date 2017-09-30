package com.ausichenko.radio.presentation.presenter;


import com.ausichenko.radio.model.pojo.Radio;
import com.ausichenko.radio.model.repo.RadioRepository;
import com.ausichenko.radio.presentation.view.RadioListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import rx.Observer;

@InjectViewState
public class RadioListPresenter extends MvpPresenter<RadioListView> {

    public void getRadioList() {
        getViewState().showProgress();
        RadioRepository.getInstance().getRadioList().subscribe(new Observer<List<Radio>>() {
            @Override
            public void onCompleted() {
                getViewState().hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                getViewState().showError(e);
            }

            @Override
            public void onNext(List<Radio> data) {
                if (data != null && !data.isEmpty()) {
                    getViewState().showData(data);
                } else {
                    getViewState().showEmptyList();
                }
            }
        });
    }
}
