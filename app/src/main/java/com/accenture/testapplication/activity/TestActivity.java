package com.accenture.testapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.support.v7.widget.RecyclerView;
import android.arch.lifecycle.ViewModelProviders;

import com.accenture.testapplication.R;
import com.accenture.testapplication.adapter.AlbumListAdapter;
import com.accenture.testapplication.model.AlbumListViewModel;
import com.accenture.testapplication.model.AlbumListViewModel.callBackToActivity;
import com.accenture.testapplication.network.entity.Album;
import com.accenture.testapplication.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestActivity extends AppCompatActivity implements callBackToActivity {

    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private AlbumListViewModel albumListViewModel;
    private AlbumListAdapter albumAdapter;
    private List<Album> mAlbumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mProgressBar = findViewById(R.id.list_item_progress_bar);
        mRecyclerView = findViewById(R.id.list_item_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        mAlbumList = new ArrayList<>();
        albumAdapter = new AlbumListAdapter(this, mAlbumList);
        mRecyclerView.setAdapter(albumAdapter);

        AlbumListViewModel.AlbumListViewModelFactory factory =
                new AlbumListViewModel.AlbumListViewModelFactory();

        albumListViewModel = ViewModelProviders.of(this, factory).get(AlbumListViewModel.class);
        albumListViewModel.notifyResults(this);
    }

    public void callApi (View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        albumListViewModel.callAlbumApi();
    }

    @Override
    public void onSuccess(List<Album> albumList) {
        mAlbumList = albumList;
        Collections.sort(mAlbumList);

        albumAdapter = new AlbumListAdapter(this, mAlbumList);
        mRecyclerView.setAdapter(albumAdapter);

        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFailure(String error) {
        Log.d("TestActivity", "error: " + error);
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
