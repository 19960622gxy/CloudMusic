package com.example.yuer.cloudmusic.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yuer.cloudmusic.Constant;
import com.example.yuer.cloudmusic.PlayDetailsActivity;
import com.example.yuer.cloudmusic.R;
import com.example.yuer.cloudmusic.bean.PlayList;
import com.example.yuer.cloudmusic.widget.JFMusicWidgetProvider;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static com.example.yuer.cloudmusic.widget.JFMusicWidgetProvider.WIDGET_LAST_ACTION;
import static com.example.yuer.cloudmusic.widget.JFMusicWidgetProvider.WIDGET_NEXT_ACTION;
import static com.example.yuer.cloudmusic.widget.JFMusicWidgetProvider.WIDGET_PLAY_ACTION;

/**
 * 将
 * 1. 歌单id
 * 2. 歌曲列表
 * Created by weidong on 2017/6/12.
 */

public class MusicService extends Service {
    private static final String TAG = "MusicService";
    Timer timer;
    private static MediaPlayer mMediaPlayer = new MediaPlayer();


    public static PlayList mPlayList;

    //当前播放的下标
    public static int mCurrIndex = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        initMusicServiceReceiver();
        initNotification();
    }

    private void initMusicServiceReceiver() {
        MusicServiceReceiver receiver = new MusicServiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(JFMusicWidgetProvider.WIDGET_LAST_ACTION);
        intentFilter.addAction(JFMusicWidgetProvider.WIDGET_PLAY_ACTION);
        intentFilter.addAction(JFMusicWidgetProvider.WIDGET_NEXT_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    private void initNotification() {


        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);

        //上一首
        Intent intentLast = new Intent(WIDGET_LAST_ACTION);
        PendingIntent pendingIntentLast = PendingIntent.getBroadcast(this, 0, intentLast, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_last, pendingIntentLast);


        //播放
        Intent intentPlay = new Intent(WIDGET_PLAY_ACTION);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(this, 0, intentPlay, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_play, pendingIntentPlay);

        //下一首
        Intent intentNext = new Intent(WIDGET_NEXT_ACTION);
        PendingIntent pendingIntentNext = PendingIntent.getBroadcast(this, 0, intentNext, PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_next, pendingIntentNext);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Notification notification = builder.setContentTitle("我是通知栏标题")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContent(remoteViews)
                .build();
        startForeground(111, notification);

    }


    MusicBinder mMusicBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mMusicBinder = new MusicBinder();
        return mMusicBinder;
    }

    public class MusicBinder extends Binder {

        public void play() {
            mMediaPlayer.start();

            //mMediaPlayer.getDuration()   1000
            //mMediaPlayer.getCurrentPosition()   500
            Intent intent = new Intent(Constant.Action.PLAY);
            LocalBroadcastManager.getInstance(MusicService.this).sendBroadcast(intent);


            handler.sendMessageAtTime(Message.obtain(), 500);

            PlayList.Music music = mPlayList.getMusics().get(mCurrIndex);
            updateNotification(music);
            updateWidget(music);
        }

        /**
         * 播放
         */
        public void play(PlayList playList) {
            Log.e(TAG, "play: 开始播放啦");
            mPlayList = playList;
            String url = "";

            //获取当前播放的url
            for (int i = 0; i < playList.getMusics().size(); i++) {
                PlayList.Music music = playList.getMusics().get(i);
                if (music.isPlayStatus()) {
                    url = music.getMusicUrl();
                    mCurrIndex = i;
                }
            }
            playUrl(url);
            handler.sendMessageAtTime(Message.obtain(), 500);

            PlayList.Music music = mPlayList.getMusics().get(mCurrIndex);
            updateNotification(music);

        }

        /**
         * 播放
         *
         * @param position 播放的下标
         */
        public void play(int position) {
            mCurrIndex = position;

            //重置所有的播放状态
            for (int i = 0; i < mPlayList.getMusics().size(); i++) {
                mPlayList.getMusics().get(i).setPlayStatus(false);
            }

            PlayList.Music music = mPlayList.getMusics().get(mCurrIndex);
            music.setPlayStatus(true);
            String url = music.getMusicUrl();
            playUrl(url);
        }

        /**
         * 播放指定的url
         *
         * @param url
         */
        private void playUrl(String url) {
            mMediaPlayer.reset();
            try {
                mMediaPlayer.setDataSource(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.start();
            Intent intent = new Intent(Constant.Action.PLAY);
            LocalBroadcastManager.getInstance(MusicService.this).sendBroadcast(intent);

            handler.sendMessageAtTime(Message.obtain(), 500);

            PlayList.Music music = mPlayList.getMusics().get(mCurrIndex);
            updateWidget(music);
            updateNotification(music);

        }

        /**
         * 暂停
         */
        public void pause() {
            mMediaPlayer.pause();
            Intent intent = new Intent(Constant.Action.PAUSE);
            LocalBroadcastManager.getInstance(MusicService.this).sendBroadcast(intent);

            PlayList.Music music = mPlayList.getMusics().get(mCurrIndex);
            updateWidget(music);
            updateNotification(music);

        }

        /**
         * 获取播放状态
         *
         * @return
         */
        public boolean isPlaying() {
            if (mMediaPlayer != null) {
                return mMediaPlayer.isPlaying();
            } else {
                return false;
            }
        }

//        public void seekWait()
//        {
//            //停止计时器
//            //拿计时器对象  也可将他设置为成员变量
//            timer=new Timer();
//            timer.cancel();//取消计时器就是停止
//        }
//
//        public void seekStart()
//        {
//            //开启计时器
//            //再调一遍moveseekfangfa
//            timer=new Timer();
//            moveSeek();
//
//        }
    }


    /**
     * 获取当前播放的歌单
     *
     * @return
     */
    public static PlayList getCurrPlayList() {
        return mPlayList;
    }

    /**
     * 获取当前播放歌曲的下标
     *
     * @return
     */
    public static int getCurrPlayIndex() {
        return mCurrIndex;
    }


    /**
     * 获取当前播放的进度
     *
     * @return
     */
    public static int getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public static int getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (mMusicBinder.isPlaying()) {
                updateWidget(mPlayList.getMusics().get(mCurrIndex));
                updateNotification(mPlayList.getMusics().get(mCurrIndex));

            }
            handler.sendMessageDelayed(Message.obtain(), 500);
        }
    };


    private void updateWidget(PlayList.Music music) {
        if (music != null) {
            RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);
            remoteViews.setTextViewText(R.id.widget_content, music.getTitle());
//            remoteViews.setImageViewUri(R.id.widget_image, Uri.parse(music.getAlbumPicUrl()));
            if (mMusicBinder.isPlaying()) {
                remoteViews.setImageViewResource(R.id.widget_play, R.mipmap.tr);
            } else {
                remoteViews.setImageViewResource(R.id.widget_play, R.mipmap.b13);
            }

            remoteViews.setProgressBar(R.id.widget_progress, getDuration(), getCurrentPosition(), false);


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(MusicService.this);
            appWidgetManager.updateAppWidget(new ComponentName(MusicService.this, JFMusicWidgetProvider.class), remoteViews);
        }
    }

    private void updateNotification(final PlayList.Music music) {
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        if (mMusicBinder.isPlaying()) {
            remoteViews.setImageViewResource(R.id.widget_play, R.mipmap.tr);
        } else {
            remoteViews.setImageViewResource(R.id.widget_play, R.mipmap.b13);
        }
        remoteViews.setTextViewText(R.id.widget_content, music.getTitle());
        remoteViews.setProgressBar(R.id.widget_progress, getDuration(), getCurrentPosition(), false);


        //CTRL + Alt + V  生成返回值
        final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        final Notification notification = builder
                .setContent(remoteViews)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();


        //如果id 相同，那么直接更新通知栏的属性
        manager.notify(111, notification);

        //加载图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //同步
                    Bitmap bitmap = Glide
                            .with(MusicService.this)
                            .load(music.getAlbumPicUrl())
                            .asBitmap()
                            .centerCrop()
                            .into(150, 150)
                            .get();
                    //RxJava
                    //RxAndroid
                    remoteViews.setImageViewBitmap(R.id.widget_image,bitmap);
                    manager.notify(111,notification);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    class MusicServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case JFMusicWidgetProvider.WIDGET_LAST_ACTION:
                    if (mCurrIndex == 0) {
                        Toast.makeText(context, "第一首啦", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mCurrIndex = mCurrIndex - 1;
                    mMusicBinder.play(mCurrIndex);

                    updateWidget(mPlayList.getMusics().get(mCurrIndex));
                    handler.sendMessageAtTime(Message.obtain(), 500);

                    break;
                case JFMusicWidgetProvider.WIDGET_PLAY_ACTION:
                    Toast.makeText(context, "播放", Toast.LENGTH_SHORT).show();
                    if (mMusicBinder.isPlaying()) {
                        mMusicBinder.pause();
                    } else {
                        mMusicBinder.play();
                    }
                    updateWidget(mPlayList.getMusics().get(mCurrIndex));
                    handler.sendMessageAtTime(Message.obtain(), 500);

                    break;
                case JFMusicWidgetProvider.WIDGET_NEXT_ACTION:
                    if (mCurrIndex == mPlayList.getMusics().size() - 1) {
                        Toast.makeText(context, "最后一首啦", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mCurrIndex = mCurrIndex + 1;
                    mMusicBinder.play(mCurrIndex);

                    updateWidget(mPlayList.getMusics().get(mCurrIndex));
                    handler.sendMessageAtTime(Message.obtain(), 500);

                    break;
            }
        }
    }



//
//    public void moveSeek()
//    {
//
//        int duration=mMediaPlayer.getDuration();//时长
//        if (duration>0)
//        {
//            //如果时长不等于0  或者不等于1  ！=1
////            Log.d(TAG, "moveSeek: ");
//            //通过计时器
//            Timer timer=new Timer();
//            TimerTask task=new TimerTask() {
//                @Override
//                public void run() {
//                    //间隔执行任务  让进度条动起来
//                    //Service中的mp读当前进度 传给MainActivity的SeekBar
//                    int duration=mMediaPlayer.getDuration();//时长
//                    int progress=mMediaPlayer.getCurrentPosition();//进度
//                    //Hander 异步消息处理机制
//                    Message msg=new Message();
//                    msg.what=1;
//                    msg.arg1=duration;
//                    msg.arg2=progress;
//                    PlayDetailsActivity.handler.sendMessage(msg);
//                }
//            };
//            timer.schedule(task,5,1000);//开启一个计时任务
//            //              延迟五毫秒  间隔
//        }
//        else
//        {
//            Toast.makeText(this,"当前没有音乐正在播放",Toast.LENGTH_SHORT).show();
//        }
//
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
