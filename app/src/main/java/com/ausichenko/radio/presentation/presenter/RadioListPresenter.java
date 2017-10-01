package com.ausichenko.radio.presentation.presenter;

import android.util.Log;

import com.ausichenko.radio.model.pojo.Radio;
import com.ausichenko.radio.model.repo.RadioRepository;
import com.ausichenko.radio.presentation.view.RadioListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class RadioListPresenter extends MvpPresenter<RadioListView> {

    public void getRadioList() {
        getViewState().showProgress();
        RadioRepository.getInstance().getRadioList().subscribe(new Observer<List<Radio>>() {
            @Override
            public void onError(Throwable e) {
                getViewState().hideProgress();
                getViewState().showError(e);
            }

            @Override
            public void onComplete() {
                getViewState().hideProgress();
            }

            @Override
            public void onSubscribe(Disposable d) {
                Log.d("TAG", "onSubscribe: ");
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
