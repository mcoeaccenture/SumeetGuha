package com.accenture.testapplication.network.service;

import com.accenture.testapplication.network.entity.Album;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumServiceGenerator {

    private static Retrofit retrofit;
    private static String ALBUM_API_GATEWAY_BASE = "https://jsonplaceholder.typicode.com/";

    public static Call<List<Album>> createCallForAlbumList() {
        AlbumService service = createService(AlbumService.class);
        return service.loadAlbums();
    }

    private static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = retrofit();
        return retrofit.create(serviceClass);
    }

    private static Retrofit retrofit() {
        if (retrofit == null) {
            return retrofit = new Retrofit.Builder().baseUrl(ALBUM_API_GATEWAY_BASE)
                    .addConverterFactory(GsonConverterFactory.create(getSpecialGson())).build();
        }
        return retrofit;
    }

    private static Gson getSpecialGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.setLenient().create();
    }
}
