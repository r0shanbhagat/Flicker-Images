package com.dev.flicker.network;

import com.dev.flicker.network.beans.FlickerImageResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("services/feeds/photos_public.gne?")
    Observable<FlickerImageResponse> getImageList(@QueryMap Map<String, String> mParams);
}
