package com.accenture.testapplication.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.accenture.testapplication.activity.TestActivity;
import com.accenture.testapplication.network.entity.Album;
import com.accenture.testapplication.network.service.AlbumServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumListViewModel extends ViewModel {

    private TestActivity mTestActivity;
    private Call<List<Album>> albumCall;

    public void notifyResults(TestActivity activity) {
        mTestActivity = activity;
    }

    public void callAlbumApi () {
        startAlbumApi ();
    }

    private void startAlbumApi () {
        if (albumCall != null && !albumCall.isCanceled()) {
            albumCall.cancel();
        }

        AlbumCallback callback = new AlbumCallback();
        albumCall = AlbumServiceGenerator.createCallForAlbumList();
        albumCall.enqueue(callback);
    }

    private class AlbumCallback implements Callback<List<Album>> {

        AlbumCallback() { }

        @Override
        public void onResponse(@NonNull Call<List<Album>> call, @NonNull Response<List<Album>> response) {
            try {
                Log.d("AlbumListViewModel", "Inside onResponse");
                // success?
                if (!response.isSuccessful() && response.errorBody() != null) {
                    notifyError(response.errorBody().toString());
                    return;
                }

                List<Album> albumList = response.body();
                if (albumList == null &&  response.errorBody() != null) {
                    notifyError(response.errorBody().toString());
                    return;
                }

                notifySuccess(albumList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(@NonNull Call<List<Album>> call, @NonNull Throwable t) {
            try {
                // error
                Log.d("AlbumListViewModel", "Inside onFailure");

                notifyError(t.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface callBackToActivity {
        public void onSuccess(List<Album> albumList);
        public void onFailure(String error);
    }

    private void notifySuccess(List<Album> albumList) {
        try {
            mTestActivity.onSuccess(albumList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyError(String error) {
        try {
            mTestActivity.onFailure(error);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class AlbumListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

        public AlbumListViewModelFactory() {
            super();
        }

        @SuppressWarnings("unchecked")
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(AlbumListViewModel.class)) {
                return (T) new AlbumListViewModel();
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
