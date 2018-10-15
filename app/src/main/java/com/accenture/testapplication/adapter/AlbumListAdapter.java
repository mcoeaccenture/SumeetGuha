package com.accenture.testapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accenture.testapplication.R;
import com.accenture.testapplication.network.entity.Album;

import java.util.List;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListItemViewHolder> {
    private AlbumListAdapter.AlbumListItemViewHolder albumItemViewHolder = null;
    private List<Album> mAlbumList;
    private Context mContext;

    public AlbumListAdapter(Context context, List<Album> albumList) {
        mContext = context;
        mAlbumList = albumList;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        try {
            super.onDetachedFromRecyclerView(recyclerView);
            mContext = null;
            mAlbumList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public AlbumListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.album_list_item, viewGroup,
                false);
        albumItemViewHolder = new AlbumListItemViewHolder(view);
        return albumItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListItemViewHolder albumListItemViewHolder,
                                 int position) {
        bindHolderValues(albumListItemViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }

    private void bindHolderValues(AlbumListItemViewHolder albumListItemViewHolder, int position) {
        albumItemViewHolder = albumListItemViewHolder;
        albumItemViewHolder.titleView.setText(mAlbumList.get(position).getTitle());
    }

    public class AlbumListItemViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;

        AlbumListItemViewHolder(View view) {
            super(view);
            titleView = view.findViewById(R.id.titleView);
        }
    }
}
