package com.example.yuer.cloudmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.yuer.cloudmusic.R;
import com.example.yuer.cloudmusic.activity.base.BaseBottomNavActivity;

/**
 * 附近的人
 */
public class NearFriendActivity extends BaseBottomNavActivity {
    private static final String TAG = "NearFriendActivity";
    //方式一
//    @Override
//    public int layoutResId() {
//        return R.layout.activity_near_friend;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        setContentView(R.layout.activity_near_friend);
    }




    /**
     * 跳转测试界面
     */
    public void startTest(View view){
        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);
    }

}
