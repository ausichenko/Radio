package com.ausichenko.radio.model.repo;

import com.ausichenko.radio.model.pojo.Radio;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RadioRepository {

    private RadioService mRadioService;
    private static RadioRepository sRadioRepository;

    private RadioRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RadioService.HTTP_DIRBLE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRadioService = retrofit.create(RadioService.class);
    }

    public synchronized static RadioRepository getInstance() {
        if (sRadioRepository == null) {
            sRadioRepository = new RadioRepository();
        }

        return sRadioRepository;
    }

    public Observable<List<Radio>> getRadioList() {
        return mRadioService
                .getPopularStations()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
