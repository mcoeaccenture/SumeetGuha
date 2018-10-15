package com.accenture.testapplication.network.service;

import com.accenture.testapplication.network.entity.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumService {

    @GET("albums")
    Call<List<Album>> loadAlbums();
}
