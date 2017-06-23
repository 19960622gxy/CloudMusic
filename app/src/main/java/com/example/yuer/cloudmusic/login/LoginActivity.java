package com.example.yuer.cloudmusic.login;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.yuer.cloudmusic.R;
import com.example.yuer.cloudmusic.login.fragment.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.fl_content)
    FrameLayout flContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去掉屏幕上方遮挡
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        android.app.FragmentManager manager=getFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        LoginFragment loginFragment=new LoginFragment();
        transaction.add(R.id.fl_content,loginFragment);
        transaction.commit();



    }
}
