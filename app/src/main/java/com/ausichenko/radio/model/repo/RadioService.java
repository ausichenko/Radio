package com.ausichenko.radio.model.repo;

import com.ausichenko.radio.model.pojo.Radio;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RadioService {

    String HTTP_DIRBLE_URL = "http://api.dirble.com/v2/";

    @GET("stations/popular")
    Observable<List<Radio>> getPopularStations(@Query("token") String token);
}
