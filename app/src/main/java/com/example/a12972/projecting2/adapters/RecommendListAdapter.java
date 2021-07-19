package com.example.a12972.projecting2.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a12972.projecting2.R;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class RecommendListAdapter extends RecyclerView.Adapter<RecommendListAdapter.InnerHolder> {

    List<Album> albumList = new ArrayList<>();

    @NonNull
    @Override
    //载view
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recommend_item, viewGroup, false);
        return new InnerHolder(itemView);
    }

    @Override
    //设置数据
    public void onBindViewHolder(@NonNull InnerHolder innerHolder, int position) {
        //将TAG设置为position便于后续打印点击位置
        innerHolder.itemView.setTag(position);
        innerHolder.setDate(albumList.get(position));
    }

    @Override
    public int getItemCount() {
        if (albumList != null) {
            return albumList.size();
        }
        return 0;
    }

    public void setData(List<Album> albumList) {
        if (this.albumList != null) {
            this.albumList.clear();
            this.albumList.addAll(albumList);
        }
        //更新UI
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }

        // TODO: 2021/7/20 设置控件对应数据
        public void setDate(Album album) {
            //图标
            ImageView albumIcon = itemView.findViewById(R.id.album_icon);
            Glide.with(itemView.getContext()).load(album.getCoverUrlSmall()).into(albumIcon);
            //标题
            TextView albumTitle = itemView.findViewById(R.id.album_tilte_tv);
            albumTitle.setText(album.getAlbumTitle());
            //描述
            TextView albumDescription = itemView.findViewById(R.id.album_description_tv);
            albumDescription.setText(album.getAlbumIntro());
            //播放量
            TextView albumPlayCount = itemView.findViewById(R.id.album_play_count);
            albumPlayCount.setText(album.getPlayCount()+"");
            //集数
            TextView albumContentSize = itemView.findViewById(R.id.album_content_size);
            albumContentSize.setText(album.getIncludeTrackCount()+"");

        }
    }
}
