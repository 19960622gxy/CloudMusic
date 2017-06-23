package com.example.yuer.cloudmusic;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;


/**
 * Created by Yuer on 2017/4/27.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context=getApplicationContext();
//        OkGo.getInstance().init(this);   okgo+升级之后
        OkGo.init(this);//初始化 OkGo框架


    }

    public static Context getContext()
    {
        return context;
    }
}