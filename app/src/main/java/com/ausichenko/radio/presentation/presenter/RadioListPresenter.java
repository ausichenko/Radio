package com.ausichenko.radio.presentation.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ausichenko.radio.model.pojo.Radio;
import com.ausichenko.radio.model.repo.RadioRepository;
import com.ausichenko.radio.presentation.view.RadioListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.paginate.Paginate;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@InjectViewState
public class RadioListPresenter extends MvpPresenter<RadioListView> {

    private int mPage = 1;

    public void getRadioList() {
        mPage = 1;
        getViewState().showProgress();
        RadioRepository.getInstance().getRadioList(mPage).subscribe(new Observer<List<Radio>>() {
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

    public void initPaginate(RecyclerView recyclerView) {
        Paginate.Callbacks callbacks =new Paginate.Callbacks() {
            @Override
            public void onLoadMore() {
                mPage++;
                loadItems();
            }

            @Override
            public boolean isLoading() {
                return false;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return false;
            }
        };

        Paginate.with(recyclerView, callbacks)
                .setLoadingTriggerThreshold(2)
                .addLoadingListItem(true)
                .build();
    }

    private void loadItems() {
        RadioRepository.getInstance().getRadioList(mPage).subscribe(new Observer<List<Radio>>() {
            @Override
            public void onError(Throwable e) {}

            @Override
            public void onComplete() {}

            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(List<Radio> data) {
                if (data != null && !data.isEmpty()) {
                    getViewState().addData(data);
                }
            }
        });
    }
}
