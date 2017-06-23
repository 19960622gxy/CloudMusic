package com.example.yuer.cloudmusic;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yuer.cloudmusic.bean.LrcBean;
import com.example.yuer.cloudmusic.bean.PlayList;
import com.example.yuer.cloudmusic.service.MusicService;
import com.example.yuer.cloudmusic.widget.DiscView;
import com.example.yuer.cloudmusic.widget.LrcView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

///**
// * 播放详情
// * 1. 唱针
// *      状态：
// *          1. 按下
// *          2. 抬起
// * 2. 唱盘
//         状态
//            1. 旋转
// *          2. 静止
// */
public class PlayDetailsActivity extends AppCompatActivity {
    public static final String DETAILS_KEY = "details";
    public static final String RESULTSBEEN_KEY = "mResultsBeen";
    public static final String INDEX_KEY = "position";


    private static final String TAG = "PlayDetailsActivity";

    @BindView(R.id.iv_bg)
    ImageView iv_bg;

    @BindView(R.id.iv_play)
    ImageView iv_play;


    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_artist)
    TextView tv_artist;

    @BindView(R.id.dv)
    DiscView dv;

    @BindView(R.id.lrcView)
    LrcView lrcView;

    //歌单的歌曲列表
    PlayList mPlayList;
    private PlayList.Music mMusic;
//    public static SeekBar seek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_details);
        ButterKnife.bind(this);

//        seek = (SeekBar) findViewById(R.id.seek);

        mPlayList = getIntent().getParcelableExtra(RESULTSBEEN_KEY);
        Log.e(TAG, "onCreate: " + mPlayList);

        //获取下标
        int position = getIntent().getIntExtra(INDEX_KEY, 0);
        mMusic = mPlayList.getMusics().get(position);


//        NewPlayListResultsBean bean = getIntent().getParcelableExtra(DETAILS_KEY);
        String url = "http://ac-kCFRDdr9.clouddn.com/e3e80803c73a099d96a5.jpg";
        if (mMusic.getAlbumPicUrl() != null) {
            url = mMusic.getAlbumPicUrl();
        }
        tv_name.setText(mMusic.getTitle());
        tv_artist.setText(mMusic.getArtist());


        Glide.with(this)
                .load(url)
                //模糊图片, this   10 模糊度   5 将图片缩放到5倍后进行模糊
                .bitmapTransform(new BlurTransformation(this, 10, 3) {
                })
                .into(iv_bg);

        //这里想要拿到更新标题的通知
        dv.setDiscChangListener(discChangListener);
        dv.setMusicData(mPlayList, position);
        bindMusicService();

        lrcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lrcView.setVisibility(View.GONE);
                dv.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 下载歌词
     *
     * @param lrcUrl 下载地址
     */
    private void downloadLrc(String lrcUrl) {
        //生成一个唯一的文件名，这个文件名和歌词的下载连接绑定
        //将lrcUrl 的连接生成一个 md5 字符串

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(lrcUrl).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //每一次都需要去请求歌词，可以将歌词存储到文件里面，
                //下一次请求，判断是否存在该文件，如果存在，则直接使用
                String result = response.body().string();
                Log.e(TAG, "onResponse: " + result.toString());
                parseLrc(result);
            }
        });


    }

    ArrayList<LrcBean> mLrcBeens = new ArrayList<>();
    /**
     * 解析歌词
     *
     * @param lrcStr
     */
    public void parseLrc(String lrcStr) {
        String[] split = lrcStr.split("\n");
        Log.e(TAG, "parseLrc: " + Arrays.toString(split));
        for (int i = 0; i < split.length; i++) {
            //获取每一行
            String line = split[i];
            //[00:00.00] 作曲 : Turtles


//            String str = line
//                    //[00:00.00     0
//                    //作曲 : Turtles    1
//                    .split("\\]")[0]
//                    //[00           0
//                    //00.00         1
//                    .split(":")[0]
//                    // ""           0
//                    // 00           1
//                    .split("\\[")[1];
            String[] arr = line.split("\\]");


            //arr[0]   [00:00.00         [00
            //分
            String min = arr[0].split(":")[0].replace("[", "");
            //秒
            String sec = arr[0].split(":")[1].split("\\.")[0];
            //毫秒
            String mills = arr[0].split(":")[1].split("\\.")[1];
            String content;
            if(arr.length > 1){
                content = arr[1];
            }else{
                content = "music";
            }

            //将获取到的数据封装为对象

            long startTime = Long.valueOf(min) * 60 * 1000 + Long.valueOf(sec) * 1000 + Long.valueOf(mills);

            LrcBean lrcBean = new LrcBean(content, startTime, 0);

            Log.e(TAG, "parseLrc: " + min + "  " + sec + "  " + mills);

            mLrcBeens.add(lrcBean);

            if(mLrcBeens.size() > 1){
                mLrcBeens.get(mLrcBeens.size()-2).setEndTime(startTime);
            }

            if(i == split.length - 1){
                mLrcBeens.get(mLrcBeens.size() -1).setEndTime(startTime + 100000000);
            }


        }

        Log.e(TAG, "parseLrc: " + mLrcBeens );

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lrcView.setLrcData(mLrcBeens);
            }
        });

    }

    public void bindMusicService() {
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    MusicService.MusicBinder mMusicBinder;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicBinder = (MusicService.MusicBinder) service;
            Log.e(TAG, "onServiceConnected: " + "MainActivity 服务连接啦");

            //服务链接成功，判断播放按钮状态
            setPlayBtnStatus();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
//
//    public static Handler handler=new Handler()   //设置进度条
//    {
//        //处理service发来的消息
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what)
//            {
//                case 1:
//                    //修改进度条
//
//                    int duration=msg.arg1;
//                    int progress=msg.arg2;
//                    //设置进度条
//                    seek.setMax(duration);  //最大值是当前歌曲的时长
//                    seek.setProgress(progress);//进度是当前进行的进度
//                    break;
//
//            }
//        }
//    };

    /**
     * 改变播放按钮状态
     */
    public void setPlayBtnStatus() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mMusicBinder.isPlaying()) {
                    iv_play.setImageResource(R.mipmap.play_rdi_btn_pause);
                    dv.setMusicPlayStatus(DiscView.MusicPlayStatus.PLAY);
                    dv.playAnim();
                } else {
                    iv_play.setImageResource(R.mipmap.play_rdi_btn_play);
                    dv.setMusicPlayStatus(DiscView.MusicPlayStatus.PAUSE);
                    dv.pauseAnim();
                }
            }
        });
    }


    DiscView.DiscChangListener discChangListener = new DiscView.DiscChangListener() {
        @Override
        public void onActionbarChanged(PlayList.Music bean) {
            tv_name.setText(bean.getTitle());
            tv_artist.setText(bean.getArtist());
        }

        @Override
        public void onNext(final int position) {
            //Toast.makeText(PlayDetailsActivity.this, "下一首", Toast.LENGTH_SHORT).show();
            if (mMusicBinder != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mMusicBinder.play(position);
                        setPlayBtnStatus();
                    }
                }).start();
            }


        }

        @Override
        public void onLast(final int position) {
            //Toast.makeText(PlayDetailsActivity.this, "上一首", Toast.LENGTH_SHORT).show();
            if (mMusicBinder != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mMusicBinder.play(position);
                        setPlayBtnStatus();
                    }
                }).start();
            }
        }

        @Override
        public void onItemClick() {
            Toast.makeText(PlayDetailsActivity.this, "我被点击啦", Toast.LENGTH_SHORT).show();
            lrcView.setVisibility(View.VISIBLE);
            dv.setVisibility(View.GONE);
            if(mLrcBeens.isEmpty()) {
                downloadLrc(mMusic.getLrcUrl());
            }else{
                lrcView.setLrcData(mLrcBeens);
            }

        }
    };


    @OnClick(R.id.iv_back)
    void back() {
        finish();
    }


    @OnClick({R.id.iv_last, R.id.iv_play, R.id.iv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_last:
                dv.playLast();
                break;
            case R.id.iv_play:
                //点击按钮，如果这种播放状态，那么需要暂停音乐
                if (mMusicBinder.isPlaying()) {
                    mMusicBinder.pause();
                    iv_play.setImageResource(R.mipmap.play_rdi_btn_play);
                    dv.pauseAnim();
                } else {
                    mMusicBinder.play();
                    dv.playAnim();
                    iv_play.setImageResource(R.mipmap.play_rdi_btn_pause);
                }
                //dv.pause();
                break;
            case R.id.iv_next:
                dv.playNext();
                break;
        }
    }
}
