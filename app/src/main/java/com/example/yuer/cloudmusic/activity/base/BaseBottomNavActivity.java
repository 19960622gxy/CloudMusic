package com.example.yuer.cloudmusic.activity.base;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.LayoutRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yuer.cloudmusic.Constant;
import com.example.yuer.cloudmusic.R;
import com.example.yuer.cloudmusic.bean.PlayList;
import com.example.yuer.cloudmusic.service.MusicService;


/**
 * 在这个类里面添加底部播放条，应该限制子Activity的布局大小
 * 该类里面处理：
 * 底部播放条所有的逻辑
 * 1. 播放按钮的状态
 *
 *
 * Created by weidong on 2017/6/16.
 */

public abstract class BaseBottomNavActivity extends AppCompatActivity {
    public static String TAG = "BaseBottomNavActivity";


    //方式一
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base_bottom_nav);
//        FrameLayout root = (FrameLayout) findViewById(R.id.root);
//
//        //获取子类的布局文件
//        int resid = layoutResId();
//
//        //加载子类布局
//        View childView = LayoutInflater.from(this).inflate(resid,root,false);
//
//        //将子类对应的View添加到root view中
//        root.addView(childView);
//
//    }

//    /**
//     * 返回想要显示的布局
//     * @return 布局id
//     */
//    public abstract int layoutResId();


    //方式二

    protected MusicService.MusicBinder mMusicBinder;

    //播放按钮
    protected ImageView iv_playstatu;

    //监听播放状态
    private MusicChangeBroadcastReceiver mPlayBroadcastReceiver;
    public BottomSheetBehavior mBehavior;


    @Override
    public final void setContentView(@LayoutRes int layoutResID) {
        //加载父类的布局
        View view = LayoutInflater.from(this).inflate(R.layout.activity_base_bottom_nav,null);
        ImageButton ib = (ImageButton) view.findViewById(R.id.ib);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideBehavior();
            }
        });
        initBottomView(view);

        FrameLayout root = (FrameLayout) view.findViewById(R.id.root);
        //加载子类布局
        View childView = LayoutInflater.from(this).inflate(layoutResID,root,false);
        root.addView(childView);

        setContentView(view);
        //获取类型的名字
        TAG = getClass().getName();

        bindMusicService();

        registerBroadcast();
    }

    /**
     * 方式一：通过Activity生命周期去控制
     * 在 Activity 可见时去判断当前音乐播放状态
     */
    @Override
    protected void onResume() {
        super.onResume();
//        if(mMusicBinder!=null){
//            if (mMusicBinder.isPlaying()) {
//                iv_playstatu.setImageResource(R.mipmap.play_rdi_btn_pause);
//            } else {
//                iv_playstatu.setImageResource(R.mipmap.a2s);
//            }
//        }
    }

    public void bindMusicService() {
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMusicBinder = (MusicService.MusicBinder) service;
            Log.e(TAG, "onServiceConnected: " + "MainActivity 服务连接啦");

            //进入到界面，应该判断当前音乐的播放状态
            if (mMusicBinder.isPlaying()) {
                iv_playstatu.setImageResource(R.mipmap.play_rdi_btn_pause);
            } else {
                iv_playstatu.setImageResource(R.mipmap.a2s);
            }


//            PlayList playList = mMusicBinder.getCurrPlayList();
//            int currIndex = mMusicBinder.getCurrPlayIndex();
//
//            updatePlayListStatus(playList,currIndex);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    /**
     * 更新播放列表的状态
     */
    public void updatePlayListStatus(PlayList playList, int currIndex){};



    /**
     * 初始化底部按钮的点击事件
     * @param bottomView
     */
    private void initBottomView(View bottomView){
        iv_playstatu = (ImageView) bottomView.findViewById(R.id.iv_playstatu);
        iv_playstatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMusicBinder!=null) {
                    if (mMusicBinder.isPlaying()) {
                        mMusicBinder.pause();
                        iv_playstatu.setImageResource(R.mipmap.a2s);
                    } else {
                        mMusicBinder.play();
                        iv_playstatu.setImageResource(R.mipmap.play_rdi_btn_pause);
                    }
                }
            }
        });

        LinearLayout ll_playlist = (LinearLayout) bottomView.findViewById(R.id.ll_playlist);
        ImageView iv_showlist = (ImageView) bottomView.findViewById(R.id.iv_showlist);
        RecyclerView rl = (RecyclerView) bottomView.findViewById(R.id.rl);


        mBehavior = BottomSheetBehavior.from(ll_playlist);

//        mBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if(newState == BottomSheetBehavior.STATE_EXPANDED){
//                    findViewById(R.id.ll_contnt).setAlpha(0.5F);
//                }else{
//                    findViewById(R.id.ll_contnt).setAlpha(1);
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });

        iv_showlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //获取正在播放的歌单

                //使用临时变量记录获取到的数据
                PlayList tempCurrPlayList = MusicService.getCurrPlayList();

                if(tempCurrPlayList == null){
                    Toast.makeText(BaseBottomNavActivity.this, "播放列表为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                Log.e(TAG, "onClick: "+ tempCurrPlayList.toString() );

                //RecyclerView.Adapter 使用的是观察者模式
                //理解：观察者模式会一直去观察一个对象，如果对象的数据发生改变，那么就会自动/被动去更新（做一些操作）

                //不改变对象引用地址的情况下更新数据
                currPlayList.setMusics(tempCurrPlayList.getMusics());
                currPlayList.setObjectId(tempCurrPlayList.getObjectId());


                //这里有没有改变currPlayList对象的内存地址？
                mPlayListAdapter.notifyDataSetChanged();


            }
        });

        initPlaylist(rl);
    }

    protected void hideBehavior(){
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }


    PlayList currPlayList = new PlayList();
    PlayListAdapter mPlayListAdapter;
    private void initPlaylist(RecyclerView rl){
        rl.setLayoutManager(new LinearLayoutManager(this));
        mPlayListAdapter = new PlayListAdapter(currPlayList);
        rl.setAdapter(mPlayListAdapter);
    }

    class PlayListAdapter extends RecyclerView.Adapter{
        PlayList playList;
        public PlayListAdapter(PlayList playList) {
            this.playList = playList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bottomnav_rl,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
        class ViewHolder extends RecyclerView.ViewHolder{
            ImageView iv_palystatus;
            TextView tv_title;
            public ViewHolder(View itemView) {
                super(itemView);
                iv_palystatus = (ImageView) itemView.findViewById(R.id.iv_palystatus);
                tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            }
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            final PlayList.Music music = playList.getMusics().get(position);

            if(music.isPlayStatus()){
                viewHolder.iv_palystatus.setVisibility(View.VISIBLE);
            }else{
                viewHolder.iv_palystatus.setVisibility(View.GONE);
            }


            viewHolder.tv_title.setText(playList.getMusics().get(position).getTitle() +" ");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for (int i = 0; i < playList.getMusics().size(); i++) {
                        playList.getMusics().get(i).setPlayStatus(false);
                    }

                    //获取点击的歌曲
                    music.setPlayStatus(true);
                    notifyDataSetChanged();
                    long start = System.currentTimeMillis();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            mMusicBinder.play(playList);
                        }
                    }).start();

                    long end = System.currentTimeMillis();
                    Log.e(TAG, "onClick: 执行时间" + (end - start));

                }
            });
        }

        @Override
        public int getItemCount() {
            return playList.getMusics().size();
        }
    }



    private void registerBroadcast() {
        mPlayBroadcastReceiver = new MusicChangeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.Action.PLAY);
        intentFilter.addAction(Constant.Action.PAUSE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mPlayBroadcastReceiver,intentFilter);
    }

    /**
     * 音乐播放的广播
     */
    class MusicChangeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果监听两个广播，那么需要判断接收到的广播类型
            switch (intent.getAction()){
                case Constant.Action.PLAY:
                    iv_playstatu.setImageResource(R.mipmap.play_rdi_btn_pause);


                    musicStatusChange();
                    break;
                case Constant.Action.PAUSE:
                    iv_playstatu.setImageResource(R.mipmap.a2s);
                    break;
            }

        }
    }

    /**
     * 音乐的状态发送改变（播放新的音乐）
     */
    public void musicStatusChange(){}


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //界面销毁，需要解绑服务和广播
        //unregisterReceiver(mPlayBroadcastReceiver);//本地广播不能使用这种方式解绑
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mPlayBroadcastReceiver);
        unbindService(mConnection);
    }
}
