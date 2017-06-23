package com.example.yuer.cloudmusic.login.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;

import com.example.yuer.cloudmusic.RegisterActivity;

/**
 * Created by Yuer on 2017/6/7.
 * 所有fragment的基类
 */

public class BaseFragment extends Fragment{
    ProgressDialog mprogressDialog;
    //显示提示框  mes 提示的文本
    public void showProgressDialog(String mes) {
        if (mprogressDialog==null)
        {
            mprogressDialog=new ProgressDialog(getActivity());
        }
        mprogressDialog.setMessage(mes);
        mprogressDialog.show();

    }
    //    关闭提示框
    public void closeProgressDialog() {
        if (mprogressDialog!=null&&mprogressDialog.isShowing())
        {
            mprogressDialog.dismiss();
        }
    }

}
