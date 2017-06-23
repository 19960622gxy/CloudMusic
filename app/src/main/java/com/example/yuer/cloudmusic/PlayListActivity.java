package com.example.yuer.cloudmusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yuer.cloudmusic.activity.base.BaseBottomNavActivity;
import com.example.yuer.cloudmusic.adapter.PlayListAdapter;
import com.example.yuer.cloudmusic.bean.HomeResponse;
import com.example.yuer.cloudmusic.bean.NewPlayListResponse;
import com.example.yuer.cloudmusic.bean.NewPlayListResultsBean;
import com.example.yuer.cloudmusic.bean.PlayList;
import com.example.yuer.cloudmusic.service.MusicService;
import com.example.yuer.cloudmusic.utils.HttpUtils;
import com.example.yuer.cloudmusic.utils.JFMusicUrlJoint;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import image.SmartImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 匹配正在播放的歌单
 * 1. 需要获取到正在播放的歌单的id
 * 2. 如果播放的歌单id 和 进入界面的歌单id一致 ，需要获取正在播放的歌曲下标
 */
public class PlayListActivity extends BaseBottomNavActivity {
    public static final String OBJECTID_KEY = "objectId";
    public static final String PLAYLISTBEAN_KEY = "PlayListBean";
    public static final String AUTHOR_KEY = "AUTHOR";
    private static final String TAG = "PlayListActivity";

    ArrayList<NewPlayListResultsBean> mResultsBeens;
    private PlayListAdapter mPlayListAdapter;
    private HomeResponse.ResultsBean.PlayListBean mPlayListBean;
    private PlayBroadcastReceiver mPlayBroadcastReceiver;

    //歌单对象
    PlayList mPlayList = new PlayList();



    private RecyclerView rl;
    private RelativeLayout ll_actionbar;
    private FrameLayout f_parent;
    private ImageView iv_bg;
    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_name;
    private ImageView iv_playstatu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        rl = (RecyclerView) findViewById(R.id.rl);
        //如果当前布局没有这个View，使用这个注解可以使得没有使用这个View的时候不会奔溃，
        // 但是使用这个View会出现空指针@Nullable
        ll_actionbar = (RelativeLayout) findViewById(R.id.ll_actionbar);
        f_parent = (FrameLayout) findViewById(R.id.f_parent);

        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_playstatu = (ImageView) findViewById(R.id.iv_playstatu);
        tv_name = (TextView) findViewById(R.id.tv_name);
        iv_playstatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMusicBinder.isPlaying()) {
                    mMusicBinder.pause();
                    iv_playstatu.setImageResource(R.mipmap.a2s);
                } else {
                    mMusicBinder.play();
                    iv_playstatu.setImageResource(R.mipmap.play_rdi_btn_pause);
                }

            }
        });
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ll_actionbar.getLayoutParams();
        //getStatusBarHeight(this)状态栏高度
        layoutParams.height = layoutParams.height + getStatusBarHeight(this);
        ll_actionbar.setLayoutParams(layoutParams);

//        Log.i(TAG, "onCreate: " + height);
//        拿到首页歌单传递的信息
        mPlayListBean = getIntent().getParcelableExtra(PLAYLISTBEAN_KEY);
        String author = getIntent().getStringExtra(AUTHOR_KEY);


        mResultsBeens = new ArrayList<>();
//        Log.e(TAG, "onCreate: " + mPlayListBean.getObjectId());
        getNewPlayList(mPlayListBean.getObjectId());
        rl.setLayoutManager(new LinearLayoutManager(this));
        mPlayListAdapter = new PlayListAdapter(mPlayList);

//        View headView = LayoutInflater.from(this).inflate(R.layout.layout_playlist_head, rl, false);
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_playlist_head, f_parent, false);
        ImageView head_iv_bg = (ImageView) headView.findViewById(R.id.iv_bg);
        //模糊背景
        Glide.with(this)
                .load(mPlayListBean.getPicUrl())
                //模糊图片, this   10 模糊度   5 将图片缩放到5倍后进行模糊
                .bitmapTransform(new BlurTransformation(this,10,5) {
                })
                .into(head_iv_bg);

        ImageView iv_play = (ImageView) headView.findViewById(R.id.iv_play);
        iv_play.setColorFilter(Color.BLACK);


        //设置歌单封面图
        SmartImageView siv_pic = (SmartImageView) headView.findViewById(R.id.siv_pic);
        siv_pic.setImageUrl(mPlayListBean.getPicUrl());
//        Palette

        //设置歌单和作者名字
        TextView tv_playListName = (TextView) headView.findViewById(R.id.tv_playListName);
        TextView tv_author = (TextView) headView.findViewById(R.id.tv_author);
        tv_playListName.setText(mPlayListBean.getPlayListName());
        tv_author.setText(author);

        RelativeLayout ll = (RelativeLayout) headView.findViewById(R.id.ll);
        RelativeLayout.LayoutParams llParams = (RelativeLayout.LayoutParams) ll.getLayoutParams();
        llParams.topMargin = layoutParams.height+ getStatusBarHeight(this);
        ll.setLayoutParams(llParams);


        mPlayListAdapter.setHeadView(headView);

        rl.setAdapter(mPlayListAdapter);


        //监听RecyclerView的滑动，实现标题View的透明度变化
        rl.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e(TAG, "onScrollStateChanged: " + newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(TAG, "onScrolled:  dx" + dx + "   dy" + dy);
                View headView = null;

                //获取第一个Item
                //获取布局管理器
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int findFirstVisibleItemPosition = manager.findFirstVisibleItemPosition();//获取屏幕显示的第一个条目的下标
                if (findFirstVisibleItemPosition == 0) {
                    headView = recyclerView.getChildAt(findFirstVisibleItemPosition);
                }

                // alpha 透明度
                float alpha = 0;
                //如果headView 不是空的，那么标题View不透明
                if (headView == null) {
                    alpha = 1;
                } else {
                    alpha = Math.abs(headView.getTop()) * 1.0f / headView.getHeight();
                }

                if (alpha > 0.5) {
                    tv_title.setText(mPlayListBean.getPlayListName());
                } else {
                    tv_title.setText("歌单");
                }
                iv_bg.setAlpha(alpha);
            }
        });
        registerBroadcast();
    }
    @Override
    public void musicStatusChange() {
        Log.e(TAG, "musicStatusChange: 我被调用啦");
        PlayList currPlayList = MusicService.getCurrPlayList();
        if(mPlayList.getObjectId().equals(currPlayList.getObjectId())){
            mPlayList.setMusics(currPlayList.getMusics());
            mPlayListAdapter.notifyDataSetChanged();
        }
    }
    public void registerBroadcast() {
        mPlayBroadcastReceiver = new PlayBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.Action.ACTION_PLAY);
        LocalBroadcastManager.getInstance(this).registerReceiver(mPlayBroadcastReceiver, intentFilter);
    }


    /**
     * Activity 关闭
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mPlayBroadcastReceiver);

    }

    class PlayBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: 我接收到播放的广播啦");

            PlayList bean = intent.getParcelableExtra(PlayListAdapter.PLAYDATA_KEY);
            mMusicBinder.play(bean);
            iv_playstatu.setImageResource(R.mipmap.play_rdi_btn_pause);

        }
    }

    public void getNewPlayList(String objectId) {
        OkHttpClient client = new OkHttpClient();
        String url = Constant.URL.NEWPLAYLIST + JFMusicUrlJoint.getNewPlayListUrl(objectId);
        Request request = HttpUtils.requestGET(url);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();//F11 添加书签   Shift + F11 查看书签
                Log.e(TAG, "onResponse: " + result);
                NewPlayListResponse newPlayListResponse = new Gson().fromJson(result, NewPlayListResponse.class);
                Log.e(TAG, "onResponse: " + newPlayListResponse.toString());
                //将所有数据添加到 adapter 持有的集合里面
                mResultsBeens.addAll(newPlayListResponse.getResults());
                mPlayList.setObjectId(mPlayListBean.getObjectId());
                ArrayList<PlayList.Music> musics = new ArrayList<PlayList.Music>();
                //歌曲列表
                for (int i = 0; i < newPlayListResponse.getResults().size(); i++) {
                    NewPlayListResultsBean bean = newPlayListResponse.getResults().get(i);
                    String alubmPic = bean.getAlbumPic() == null ? "" : bean.getAlbumPic().getUrl();
                    //歌词下载链接
                    String lrcUrl = bean.getLrc() == null ? "" : bean.getLrc().getUrl();
                    PlayList.Music music = new PlayList.Music(
                            bean.getObjectId(),
                            bean.getTitle(),
                            bean.getArtist(),
                            bean.getFileUrl().getUrl(),
                            alubmPic,
                            bean.getAlbum(),
                            lrcUrl
                    );

                    //直接使用服务的静态方法
                    int currIndex = MusicService.getCurrPlayIndex();
                    PlayList playList = MusicService.getCurrPlayList();
                    if (currIndex != -1 && playList != null && playList.getObjectId().equals(mPlayListBean.getObjectId())) {
                        if (i == currIndex) {
                            music.setPlayStatus(true);
                        }
                    }


                    musics.add(music);
                }
                mPlayList.setMusics(musics);
                //currPlayList = new PlayList();
                Log.e(TAG, "onResponse: " + musics.toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPlayListAdapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }


    //获取状态栏高度
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


}
