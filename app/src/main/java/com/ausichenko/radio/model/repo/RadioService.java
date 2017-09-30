package com.ausichenko.radio.model.repo;

import com.ausichenko.radio.model.pojo.Radio;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface RadioService {

    String HTTP_DIRBLE_URL = "http://api.dirble.com/v2/";

    @GET("stations/popular")
    Observable<List<Radio>> getPopularStations();
}
