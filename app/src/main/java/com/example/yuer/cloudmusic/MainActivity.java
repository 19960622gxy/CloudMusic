package com.example.yuer.cloudmusic;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.example.yuer.cloudmusic.fragment.FoundFragment;
import com.example.yuer.cloudmusic.fragment.FriendFragment;
import com.example.yuer.cloudmusic.fragment.MyFragment;
import com.example.yuer.cloudmusic.service.MusicService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;

    @BindView(R.id.dl)
    DrawerLayout dl;

    @BindView(R.id.vp_main)
    ViewPager vp_main;


    ArrayList<Fragment> fragments;
    ArrayList<ImageView> mImageViews;
    private MusicChangeBroadcastReceiver mPlayBroadcastReceiver;

    private ImageView my;
    private ImageView found;
    private ImageView friend;


    //侧边栏布局
    //内容布局
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        my = (ImageView) findViewById(R.id.my);
        found = (ImageView) findViewById(R.id.found);
        friend = (ImageView) findViewById(R.id.friend);
        mImageViews = new ArrayList<>();
        mImageViews.add(my);
        mImageViews.add(found);
        mImageViews.add(friend);

        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        MyFragment f1 = new MyFragment();
        FoundFragment f2 = new FoundFragment();
        FriendFragment f3 = new FriendFragment();
        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);

        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager());
        vp_main.setAdapter(mainAdapter);
        vp_main.setCurrentItem(1);
        found.setSelected(true);
        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main.setCurrentItem(0);

            }
        });

        found.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main.setCurrentItem(1);
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vp_main.setCurrentItem(2);

            }
        });
        vp_main.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switchTabs(position);
//                if(position==0){
//                    found.setSelected(false);
//                    friend.setSelected(false);
//                    my.setSelected(true);
//                }
//                if(position == 1){
//                    found.setSelected(true);
//                    friend.setSelected(false);
//                    my.setSelected(false);
//                }
//
//                if(position == 2){
//                    found.setSelected(false);
//                    friend.setSelected(true);
//                    my.setSelected(false);
//                }

            }
        });


  //颜色过滤器  setColorFilter
        ImageView i1=(ImageView) findViewById(R.id.i1);
        ImageView i2=(ImageView) findViewById(R.id.i2);
        ImageView i3=(ImageView) findViewById(R.id.i3);
        ImageView i4=(ImageView) findViewById(R.id.i4);
        ImageView i5=(ImageView) findViewById(R.id.i5);
        ImageView i6=(ImageView) findViewById(R.id.i6);
        ImageView i7=(ImageView) findViewById(R.id.i7);
        ImageView i8=(ImageView) findViewById(R.id.i8);
        ImageView i9=(ImageView) findViewById(R.id.i9);
        ImageView i10=(ImageView) findViewById(R.id.i10);
        ImageView i11=(ImageView) findViewById(R.id.i11);
        ImageView i12=(ImageView) findViewById(R.id.i12);
        i1.setColorFilter(Color.GRAY);
        i2.setColorFilter(Color.GRAY);
        i3.setColorFilter(Color.GRAY);
        i4.setColorFilter(Color.GRAY);
        i5.setColorFilter(Color.GRAY);
        i6.setColorFilter(Color.GRAY);
        i7.setColorFilter(Color.GRAY);
        i8.setColorFilter(Color.GRAY);
        i9.setColorFilter(Color.GRAY);
        i10.setColorFilter(Color.GRAY);
        i11.setColorFilter(Color.GRAY);
        i12.setColorFilter(Color.GRAY);



        registerBroadcast();
        bindMusicService();
    }

    private void registerBroadcast() {
        mPlayBroadcastReceiver = new MusicChangeBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.Action.PLAY);
        intentFilter.addAction(Constant.Action.PAUSE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mPlayBroadcastReceiver,intentFilter);
    }
    class MusicChangeBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果监听两个广播，那么需要判断接收到的广播类型
            switch (intent.getAction()){
                case Constant.Action.PLAY:
                    iv_playstatu.setImageResource(R.mipmap.play_rdi_btn_pause);
                    break;
                case Constant.Action.PAUSE:
                    iv_playstatu.setImageResource(R.mipmap.a2s);
                    break;
            }
        }
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
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @BindView(R.id.iv_playstatu)
    ImageView iv_playstatu;

    @OnClick(R.id.iv_playstatu)
    void palyStatus(View view) {
        if (mMusicBinder.isPlaying()) {
            mMusicBinder.pause();
            iv_playstatu.setImageResource(R.mipmap.a2s);
        } else {
            mMusicBinder.play();
            iv_playstatu.setImageResource(R.mipmap.play_rdi_btn_pause);
        }
    }
    private void switchTabs(int position){
        for (int i = 0; i <mImageViews.size() ; i++) {
            if(i == position){
                mImageViews.get(i).setSelected(true);
            }else{
                mImageViews.get(i).setSelected(false);
            }
        }
    }


    class MainAdapter extends FragmentPagerAdapter {
        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }




    @OnClick(R.id.iv_menu)
    void showMenu(View view){
        dl.openDrawer(Gravity.LEFT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mPlayBroadcastReceiver);
        unbindService(connection);
    }

}
