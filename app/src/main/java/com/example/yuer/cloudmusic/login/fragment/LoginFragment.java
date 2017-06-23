package com.example.yuer.cloudmusic.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yuer.cloudmusic.MainActivity;
import com.example.yuer.cloudmusic.RegisterActivity;
import com.example.yuer.cloudmusic.utils.AppStringUtil;
import com.example.yuer.cloudmusic.R;
import com.example.yuer.cloudmusic.bean.LoginResponse;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Yuer on 2017/6/5.
 */

public class LoginFragment extends BaseFragment {
    private static final String TAG = "LoginFragment";
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pass)
    EditText etPass;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,null);
        return view;
    }

    /**
     * View 创建完毕后会执行的方法，在改方法里面可以获取View，设置View的监听等操作
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.bt_login, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:



                String username = etName.getText().toString();
                String password = etPass.getText().toString();
                if (username.equals("")||password.equals(""))
                {
                    Toast.makeText(getActivity(), "请完善信息", Toast.LENGTH_SHORT).show();
                }
                else{

                if (!AppStringUtil.checkUserName(username)) {
                    etName.setError("用户名格式不正确");
                    return;
                }

                if (!AppStringUtil.checkPassword(password)) {
                    etPass.setError("密码格式不正确");
                    return;
                }

                    showProgressDialog("请等待...");
                    login(username, password);
                }

                break;
            case R.id.bt_register:
//                RegisterFragment registerFragment = new RegisterFragment();
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fl_content,registerFragment);
//                //将fragment 添加到返回栈
//                transaction.addToBackStack(null);
//                transaction.commit();

                Intent intent=new Intent(getActivity(), RegisterActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }

    private void login(String username, String password) {
//        Toast.makeText(getActivity(), "登陆啦", Toast.LENGTH_SHORT).show();
        OkGo.post("https://leancloud.cn:443/1.1/login")
                .tag(this)
                .headers("X-LC-Id", "kCFRDdr9tqej8FRLoqopkuXl-gzGzoHsz")
                .headers("X-LC-Key", "bmEeEjcgvKIq0FRaPl8jV2Um")
                .headers("Content-Type", "application/json")
                .params("username",username)
                .params("password",password)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        if (response.code() == 210) {
                            Toast.makeText(getActivity(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        closeProgressDialog();
//                        }


                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.d(TAG, "onSuccess: "+s);
//                        Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();

                        if (response.code() == 200)
                        {
                            Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
                            Gson gson = new Gson();
                            LoginResponse loginResponse = gson.fromJson(s,LoginResponse.class);
                            Intent intent=new Intent(getActivity(), MainActivity.class);
//                            getActivity().startActivity(intent);
                            startActivity(intent);
                            getActivity().finish();
                        }
//                        else if(response.code() == 210)
//                        {
//                            Toast.makeText(getActivity(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
//                        }
                        closeProgressDialog();
                    }
                });
    }


//
////   okhttp实现方式
//    private void login(String username, String password) {
//        Toast.makeText(getActivity(), "登陆啦", Toast.LENGTH_SHORT).show();
//        OkHttpClient client = new OkHttpClient();
//
//        String url = "https://leancloud.cn:443/1.1/login?username=" + username + "&password=" + password;
//
//        //测试账号  aaa  123456
//        //测试账号  weidong  123456
//        //测试账号  bbb  123456
//        //测试账号  ccc  123456
//        //测试账号  ddd  123456
//        Request request = new Request.Builder()
//                .addHeader("X-LC-Id", "kCFRDdr9tqej8FRLoqopkuXl-gzGzoHsz")
//                .addHeader("X-LC-Key", "bmEeEjcgvKIq0FRaPl8jV2Um")
//                .addHeader("Content-Type", "application/json")
//                .url(url)
//                .get()
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
////                Log.e(TAG, "登陆失败 ");
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                //https://leancloud.cn/docs/error_code.html#_202
//                String result = response.body().string();
////                Log.e(TAG, "onResponse: " + result);
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//
//                        if (response.code() == 200) {//获取请求结果码
//                            //解析成功的数据
//                            Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
////
//                        } else {
//
//
//                            Toast.makeText(getActivity(), "登陆失败", Toast.LENGTH_SHORT).show();
//                            //{"code":211,"error":"Could not find user"} 用户不存在
//                            //{"code":210,"error":"The username and password mismatch."} 用户名和密码不匹配
//
//                            //解析失败的数据
//                        }
//
//                    }
//                });
//
//            }
//        });
//
//
//    }


}
