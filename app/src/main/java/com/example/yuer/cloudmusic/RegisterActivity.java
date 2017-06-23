package com.example.yuer.cloudmusic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yuer.cloudmusic.bean.LoginResponse;
import com.example.yuer.cloudmusic.bean.RegisterResponse;
import com.example.yuer.cloudmusic.bean.User;
import com.example.yuer.cloudmusic.login.LoginActivity;
import com.example.yuer.cloudmusic.utils.AppStringUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;

import butterknife.internal.DebouncingOnClickListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    ProgressDialog progressDialog;

//    private ActionBar actionBar;
    private EditText etNameReg;
    private EditText etPassReg;
    private Button btnRegisetReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去掉屏幕上方遮挡
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Toolbar toolbar=(Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);//将他设置为系统能够得知的actionbar的位置上
//        getSupportActionBar().setTitle("注册");
        getSupportActionBar().setTitle("");

        final ImageView regBack=(ImageView) findViewById(R.id.reg_back);
        regBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regBack.setImageResource(R.mipmap.iv_back);
                finish();
            }
        });


//        //如果存在这样一个标题栏
//        actionBar = getSupportActionBar();
//        if (actionBar !=null)
//        {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.mipmap.pic_back);//暂时先设置默认图片
//        }


        etNameReg = (EditText) findViewById(R.id.et_name_reg);
        etPassReg = (EditText) findViewById(R.id.et_pass_reg);
        btnRegisetReg = (Button) findViewById(R.id.bt_register_reg);
        btnRegisetReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etNameReg.getText().toString();
                String password = etPassReg.getText().toString();
                if (username.equals("")||password.equals(""))
                {
                    Toast.makeText(RegisterActivity.this, "请完善信息", Toast.LENGTH_SHORT).show();
                }
                else{

                if (!AppStringUtil.checkUserName(username)) {
                    etNameReg.setError("用户名在3-10个字符之间");
                    return;
                }

                if (!AppStringUtil.checkPassword(password)) {
                    etPassReg.setError("密码在3-10个字符之间");
                    return;
                }

                    showProgressDialog("请等待...");
                    register(username,password);
                }
            }
        });



    }
//显示提示框  mes 提示的文本
    private void showProgressDialog(String mes) {
        if (progressDialog==null)
                {
                    progressDialog=new ProgressDialog(RegisterActivity.this);
                }
                progressDialog.setMessage(mes);
                progressDialog.show();

    }
//    关闭提示框
    private void closeProgressDialog() {
        if (progressDialog!=null&&progressDialog.isShowing())
        {
            progressDialog.dismiss();
        }
    }

//    //okhttp格式
//    private void register(String userName, final String password) {
//        OkHttpClient client = new OkHttpClient();
//        //将两个参数封装为Json对象
//        //{"password":"123456","username":"yyyyyy"}
//        User user = new User(userName, password);
//        //Gson
//        //将User 转为 Json 格式的数据
//        Gson gson = new Gson();
//        String json = gson.toJson(user);//将user 转为 Json 格式的数据
//        Log.i(TAG, "register: " + json);
//
//        //告诉服务器，我们上传的格式是json格式
//        MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSONTYPE, json);
//
//        String url = "https://leancloud.cn:443/1.1/users";
//        final Request request = new Request.Builder()
//                .addHeader("X-LC-Id", "kCFRDdr9tqej8FRLoqopkuXl-gzGzoHsz")
//                .addHeader("X-LC-Key", "bmEeEjcgvKIq0FRaPl8jV2Um")
//                .addHeader("Content-Type", "application/json")
//                .url(url)
//                .post(body)
//                .build();
//
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                final String result = response.body().string();
//                Log.i(TAG, "run: " + result);
//
//                RegisterActivity.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (response.code() == 201) {
//                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                            finish();
//                        } else {
//                            Toast.makeText(RegisterActivity.this, "该用户已注册", Toast.LENGTH_SHORT).show();
//                        }
//
////                        if (progressDialog!=null)
////                        {
////                            progressDialog.dismiss();
////                        }
//                        closeProgressDialog();
//                    }
//                });
//
//            }
//        });
//    }

//    okgo
    private void register(String username, String password) {
        User user = new User(username, password);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        OkGo.post("https://leancloud.cn:443/1.1/users")
                .tag(this)
                .headers("X-LC-Id", "kCFRDdr9tqej8FRLoqopkuXl-gzGzoHsz")
                .headers("X-LC-Key", "bmEeEjcgvKIq0FRaPl8jV2Um")
                .headers("Content-Type", "application/json")
                .upJson(json)
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        if (response.code() == 202) {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                        closeProgressDialog();
//                        }
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        Log.d(TAG, "onSuccess: "+s);

                        if (response.code() == 201)
                        {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Gson gson = new Gson();
                            RegisterResponse registerResponse = gson.fromJson(s,RegisterResponse.class);
//                            Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
//                            startActivity(intent);
                            closeProgressDialog();
                            finish();
                        }
//                        else
//                        {
//                            Toast.makeText(RegisterActivity.this, "该用户名可能已注册", Toast.LENGTH_SHORT).show();
//
//                        }


                    }
                });

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //资源文件设置给对象
//        getMenuInflater().inflate(R.menu.registertoolbar,menu);
//        return true;
//
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//
//            case android.R.id.home:
//                actionBar.setHomeAsUpIndicator(R.mipmap.iv_back);//暂时先设置默认图片
//                finish();//退出当前页面
//                break;
//            default:
//        }
//        return true;
//    }

}
