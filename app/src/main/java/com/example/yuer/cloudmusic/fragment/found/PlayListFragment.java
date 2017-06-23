package com.example.yuer.cloudmusic.fragment.found;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.yuer.cloudmusic.Constant;
import com.example.yuer.cloudmusic.R;
import com.example.yuer.cloudmusic.adapter.PlayListRVAdapter;
import com.example.yuer.cloudmusic.adapter.PlayListRVAdapter1;
import com.example.yuer.cloudmusic.bean.PlayListResponse;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 推荐
 * Created by weidong on 2017/6/12.
 */

public class PlayListFragment extends Fragment {
    private static final String TAG = "PlayListFragment";

    List<PlayListResponse.ResultsBean> playlist;
//    PlayListRVAdapter adapter;
    PlayListRVAdapter1 adapter;

    RecyclerView rl;
    LinearLayout parent_view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //构建技术问答列表
//        playlist = new ArrayList<>();
//        RecyclerView rl = (RecyclerView) view.findViewById(R.id.rl_playlist);
//        adapter  = new PlayListRVAdapter1(getContext(),playlist);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        rl.setLayoutManager(layoutManager);
//        rl.setLayoutManager(new GridLayoutManager(getContext(),2));
//        rl.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//        rl.setAdapter(adapter);

        rl= (RecyclerView) view.findViewById(R.id.rl_playlist);
        parent_view= (LinearLayout) view.findViewById(R.id.parent_view);

        playlist=new ArrayList<>();
        adapter = new PlayListRVAdapter1(playlist);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_playlist_btn,parent_view,false);
//        让他对照父布局大小，但是flase不添加入父布局
        adapter.setHeadView(headView);
        GridLayoutManager manager= new GridLayoutManager(getActivity(),2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position==0?2:1; // position等于0 占满两列   否则只占一列
            }
        });
        rl.setLayoutManager(manager);
        rl.setAdapter(adapter);


        getData();
    }

    private void getData() {
        OkGo.get(Constant.URL.PLAYLIST)
                .tag(this)
                .headers("X-LC-Id", "kCFRDdr9tqej8FRLoqopkuXl-gzGzoHsz")
                .headers("X-LC-Key", "bmEeEjcgvKIq0FRaPl8jV2Um")
                .headers("Content-Type", "application/json")
                .params("limit",35)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d(TAG, "onSuccess: "+ s);
                        Gson gson = new Gson() ;
                        PlayListResponse playlistResponse = gson.fromJson(s,PlayListResponse.class);
                        List<PlayListResponse.ResultsBean> data =playlistResponse.getResults();

                        playlist.addAll(data);
                        adapter.notifyDataSetChanged();


                    }
                });

    }
}
