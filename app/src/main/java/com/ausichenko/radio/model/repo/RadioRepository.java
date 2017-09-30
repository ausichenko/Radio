package com.ausichenko.radio.model.repo;

import com.ausichenko.radio.model.pojo.Radio;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.schedulers.AndroidSchedulers;

public class RadioRepository {

    private RadioService mRadioService;
    private static RadioRepository sRadioRepository;

    private RadioRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RadioService.HTTP_DIRBLE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
                .getPopularStations("d061dbb423f9b30bbf691ef256")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread());
    }
}
