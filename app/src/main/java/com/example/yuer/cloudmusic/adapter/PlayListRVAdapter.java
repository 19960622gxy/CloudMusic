package com.example.yuer.cloudmusic.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuer.cloudmusic.PlayListActivity;
import com.example.yuer.cloudmusic.R;
import com.example.yuer.cloudmusic.bean.HomeResponse;
import com.example.yuer.cloudmusic.bean.PlayListResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import image.SmartImageView;


/**
 * Created by Yuer on 2017/5/2.
 */

public class PlayListRVAdapter extends RecyclerView.Adapter<PlayListRVAdapter.ViewHolder>  {

    Context context;
    List<PlayListResponse.ResultsBean> playlist;
    public PlayListRVAdapter(Context context, List<PlayListResponse.ResultsBean> playlist) {
        this.context = context;
        this.playlist = playlist;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建viewholder
        View view = LayoutInflater.from(context).inflate(R.layout.item_platlistgedan_child,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(PlayListRVAdapter.ViewHolder holder, int position) {

        //绑定数据

        PlayListResponse.ResultsBean playlists = playlist.get(position);


        String picUrl = playlists.getPicUrl().getUrl();
        holder.tag.setImageUrl(picUrl);
        holder.name.setText(playlists.getName());

    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//
        SmartImageView tag;
        TextView name;
        LinearLayout item;

        public ViewHolder(View itemView) {
            super(itemView);
            tag = (SmartImageView) itemView.findViewById(R.id.playlist_pic);
            name = (TextView) itemView.findViewById(R.id.playlist_name);
            item = (LinearLayout) itemView;
        }
    }
}
