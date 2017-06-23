package com.example.yuer.cloudmusic.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.yuer.cloudmusic.R;


/**
 * Created by weidong on 2017/6/23.
 */

public class JFMusicWidgetProvider extends AppWidgetProvider {
    private static final String TAG = "JFMusicWidgetProvider";

    public static final String WIDGET_LAST = "com.jf.action.WIDGET_LAST";
    public static final String WIDGET_PLAY = "com.jf.action.WIDGET_PLAY";
    public static final String WIDGET_NEXT = "com.jf.action.WIDGET_NEXT";

    public static final String WIDGET_LAST_ACTION = "com.jf.action.WIDGET_LAST_ACTION";
    public static final String WIDGET_PLAY_ACTION = "com.jf.action.WIDGET_PLAY_ACTION";
    public static final String WIDGET_NEXT_ACTION = "com.jf.action.WIDGET_NEXT_ACTION";

    //每接收一次广播消息就调用一次，使用频繁
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e(TAG, "onReceive: ");
        String action = null;
        switch (intent.getAction()){
            case WIDGET_LAST:
                action = WIDGET_LAST_ACTION;
                break;
            case WIDGET_PLAY:
                action = WIDGET_PLAY_ACTION;
                break;
            case WIDGET_NEXT:
                action = WIDGET_NEXT_ACTION;
                break;
        }
        Intent i = new Intent();
        i.setAction(action);
        context.sendBroadcast(i);
    }

    //每次更新都调用一次该方法，使用频繁
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        //super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e(TAG, "onUpdate: ");

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        //参数一  控件id
        //参数二  意图
        Intent intentLast = new Intent(WIDGET_LAST);
        PendingIntent pendingIntentLast = PendingIntent.getBroadcast(context,0,intentLast,PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_last,pendingIntentLast);


        Intent intentPlay = new Intent(WIDGET_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context,0,intentPlay,PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_play,pendingIntentPlay);


        Intent intentNext = new Intent(WIDGET_NEXT);
        PendingIntent pendingIntentNext = PendingIntent.getBroadcast(context,0,intentNext,PendingIntent.FLAG_CANCEL_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_next,pendingIntentNext);




        //更新小控件信息
        appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);
    }


    //每删除一个就调用一次
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.e(TAG, "onDeleted: ");

    }

    //当该Widget第一次添加到桌面是调用该方法，可添加多次但只第一次调用
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.e(TAG, "onEnabled: ");
    }

    //当最后一个该Widget删除是调用该方法，注意是最后一个
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.e(TAG, "onDisabled: ");

    }
}
