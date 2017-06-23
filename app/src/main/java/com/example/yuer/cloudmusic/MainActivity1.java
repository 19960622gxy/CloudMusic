package com.example.yuer.cloudmusic;

import android.support.v7.app.AppCompatActivity;

public class MainActivity1 extends AppCompatActivity {
//    private static final String TAG = "MainActivity";
//    //侧滑栏布局
//    private DrawerLayout dl;
//    //内容布局
//    private Toolbar toolbar;
//    private RecyclerView rl_home;
//
//
//    ArrayList<Result> results = new ArrayList<>();
//// 将那几个条目抽取出来写进数组好进行序列化分组
//    ArrayList<Home> homes = new ArrayList<Home>();
//
//    //    ArrayList<SmartImageView> smartImageViews = new ArrayList<>();
//    private BannerAdapter bannerAdapter;  //加载轮播图的adapter
//    //将首页轮播图作为head，调整栏目作为footer添加到recycleview中的adapter
//    private HomeHeadFooterAdapter homeAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
////        点击toolbar上面的图标，弹出侧滑栏
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        dl = (DrawerLayout) findViewById(R.id.dl);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_menu);
//        setTitle("");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "onClick: 点一下");
//                dl.openDrawer(Gravity.LEFT);
//            }
//        });
//
//
//
//        //        RecyclerView实现主界面  几个嵌套在一起
//
//        //拿到这个recycleview，动态设置他是线性布局
//        rl_home = (RecyclerView) findViewById(R.id.rl_home);
//        rl_home.setLayoutManager(new LinearLayoutManager(this));
////        将homes分组填充进适配器中，便于显示
//        homeAdapter = new HomeHeadFooterAdapter(homes);
////        将head和footer的布局添加进来
//        View headView = LayoutInflater.from(this).inflate(R.layout.layout_home_header,null);
//        View footerView = LayoutInflater.from(this).inflate(R.layout.layout_home_footer,null);
////       点击调整栏目这个按钮会实现跳转
//        Button bt = (Button) footerView.findViewById(R.id.bt);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(MainActivity1.this,AdjusColumnActivity.class);
//                intent.putParcelableArrayListExtra("homes",homes);
//                startActivityForResult(intent,111);  //老师写的是111，我试了一次1也没问题
////                       一般都是设置大于或者等于0 设定目标activity的requestCode，
//            }
//        });
//
//
////        这个homeadapter中包含着head content 和footer 可设置view显示
////        setHeadView  setFooterView是adapter中的方法
//        homeAdapter.setHeadView(headView);
//        homeAdapter.setFooterView(footerView);
//
////       将这个adapter中的head content footer嵌入recycleview中
//        rl_home.setAdapter(homeAdapter);
//
//
//
//        //加载轮播图
//        getBanner();
//        //设置轮播图
//        ViewPager vp = (ViewPager) headView.findViewById(R.id.vp);
//        bannerAdapter = new BannerAdapter(results);
//        vp.setAdapter(bannerAdapter);
//
//
//
//        getHome();
////        getOKGOHome();
//
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == 111){
//            ArrayList<Home> results = data.getParcelableArrayListExtra("homes");
//            homes.clear();
//            homes.addAll(results);
//            homeAdapter.notifyDataSetChanged();
//            rl_home.scrollToPosition(0);
//        }
//    }
//
//    private void getOKGOHome() {
//
//        OkGo.get(Constant.URL.HOME)
//                .tag(this)
//                .headers("X-LC-Id", "kCFRDdr9tqej8FRLoqopkuXl-gzGzoHsz")
//                .headers("X-LC-Key", "bmEeEjcgvKIq0FRaPl8jV2Um")
//                .headers("Content-Type", "application/json")
//                .params("include","playList,playList.author")
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        Log.d(TAG, "onSuccess: "+s);
//
//                        Gson gson = new Gson();
//                        HomeResponse homeResponse = gson.fromJson(s, HomeResponse.class);
//                        Log.e(TAG, "onResponse: "+homeResponse);
//                        //存取有序吗？
//                        HashMap<String,ArrayList<HomeResponse.ResultsBean.PlayListBean>> hashMap = new HashMap<>();
//                        //
//
//                        //最新音乐
//                        //11111111111
//                        //22222222222
//                        //推荐音乐
//                        //11111111111
//                        //22222222222
//
//                        for (int i = 0; i < homeResponse.getResults().size(); i++) {
//
//                            HomeResponse.ResultsBean resultsBean = homeResponse.getResults().get(i);
//                            HomeResponse.ResultsBean.PlayListBean playListBean = homeResponse.getResults().get(i).getPlayList();
//
//                            //获取【最新音乐】【推荐音乐】
//                            String Item = resultsBean.getItem();
//
//                            if(hashMap.containsKey(Item)){
//                                //包含
//                                ArrayList<HomeResponse.ResultsBean.PlayListBean> resultsBeens = hashMap.get(Item);
//                                resultsBeens.add(playListBean);
//                            }else{
//                                //不包含 Home.ResultsBean.PlayListBean 使用类中的内部类中的内部类
//                                ArrayList<HomeResponse.ResultsBean.PlayListBean> resultsBeens = new ArrayList<HomeResponse.ResultsBean.PlayListBean>();
//                                resultsBeens.add(playListBean);
//                                hashMap.put(Item,resultsBeens);
//                            }
//                        }
//
//                        Log.i(TAG, "onResponse: " + hashMap);
//                        Set<Map.Entry<String,ArrayList<HomeResponse.ResultsBean.PlayListBean>>> entrySet = hashMap.entrySet();
//                        for (Map.Entry<String,ArrayList<HomeResponse.ResultsBean.PlayListBean>> entry:entrySet) {
//                            String name = entry.getKey();
//                            ArrayList<HomeResponse.ResultsBean.PlayListBean> playList = entry.getValue();
//                            Home home = new Home();
//                            home.setName(name);
//                            home.setPlayListBeen(playList);
//                            homes.add(home);
//                        }
//
//                        Log.e(TAG, "onResponse: "+homes);
//
//
//                    }
//                });
//    }
//    //    获取广告数据
//    private void getBanner() {
//
//        //https://leancloud.cn:443/1.1/classes/Banner?limit=10&&&&
//        String url = "https://leancloud.cn:443/1.1/classes/Banner?limit=6";
//        OkHttpClient client = new OkHttpClient();
////        Request request = new Request.Builder()
////                .addHeader("X-LC-Id", "kCFRDdr9tqej8FRLoqopkuXl-gzGzoHsz")
////                .addHeader("X-LC-Key", "bmEeEjcgvKIq0FRaPl8jV2Um")
////                .addHeader("Content-Type", "application/json")
////                .url(url)
////                .get()
////                .build();
//        Request request = HttpUtils.requestGET(url);
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result = response.body().string();
//                parJson(result);
//                Log.i(TAG, "onResponse: " + result);
//
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    JSONArray jsonArray = jsonObject.getJSONArray("results");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        String picurl = object.getString("picurl");
//                        String desc = object.getString("desc");
//                        String createdAt = object.getString("createdAt");
//                        String updatedAt = object.getString("updatedAt");
//                        String objectId = object.getString("objectId");
//                        Result resu = new Result(picurl, desc, createdAt, updatedAt, objectId);
//
//
//                        SmartImageView smartImageView = new SmartImageView(MainActivity1.this);
//                        //注意：使用xml的布局可以直接使用 imageView.getLayoutParams()
//                        //如果是通过代码new出来的View，不能使用该方法，必须主动创建LayoutParams对象
//                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                        //设置View的参数
//                        smartImageView.setLayoutParams(layoutParams);
//                        //设置默认图片
//                        smartImageView.setImageResource(R.mipmap.ic_launcher);
////                        smartImageViews.add(smartImageView);
//
//                        resu.setSmartImageView(smartImageView);
//
//
//                        results.add(resu);
//
//                    }
//
////                    for (int i = 0; i < results.size(); i++) {
////                        SmartImageView smartImageView = new SmartImageView(MainActivity.this);
////                        //注意：使用xml的布局可以直接使用 imageView.getLayoutParams()
////                        //如果是通过代码new出来的View，不能使用该方法，必须主动创建LayoutParams对象
////                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
////                                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
////                        //设置View的参数
////                        smartImageView.setLayoutParams(layoutParams);
////                        //设置默认图片
////                        smartImageView.setImageResource(R.mipmap.ic_launcher);
////                        smartImageViews.add(smartImageView);
//
////                    }
//
//                    //更新ViewPager数据
//                    //不能在子线程更新UI   Only the original thread that created a view hierarchy can touch its views.
//                    //需要切换回主线程
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            bannerAdapter.notifyDataSetChanged();
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
//
//
//    }
//    private void parJson(String json){
//        Gson gson = new Gson();
//
//        //json --- 》 object  反序列化
//        Banner banner = gson.fromJson(json, Banner.class);
//
//        //object   ---》  json 序列化
//        //将banner 对象序列化为Json
//        String bannerJson = gson.toJson(banner);
//
//
//
//        Log.i(TAG, "parJson: " + banner.toString());
//        Log.i(TAG, "parJson: " + bannerJson);
//
//
//
//
//    }
////    获取数据
//    public void getHome(){
//
//    String url = Constant.URL.HOME + "?include=playList%2CplayList.author&";
//    OkHttpClient client = new OkHttpClient();
////        Request request = HttpUtils.getBuilder().url(url).get().build();
////        发起一个请求
//    Request request = HttpUtils.requestGET(url);
//    client.newCall(request).enqueue(new Callback() {
//        //请求失败
//        @Override
//        public void onFailure(Call call, IOException e) {
//
//        }
//
//        //请求成功
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//
//            String result = response.body().string();
////          这里的result相当于okgo中的s
//            Gson gson = new Gson();
//            HomeResponse homeResponse = gson.fromJson(result, HomeResponse.class);
//            Log.e(TAG, "onResponse: "+homeResponse);
////            hashmap无序，先使用它实现分类，再将它放入arraylist中实现有序化
//            //存取有序吗？
//            HashMap<String,ArrayList<HomeResponse.ResultsBean.PlayListBean>> hashMap = new HashMap<>();
//            //
//
//            //最新音乐
//            //11111111111
//            //22222222222
//            //推荐音乐
//            //11111111111
//            //22222222222
//
//            for (int i = 0; i < homeResponse.getResults().size(); i++) {
//
//                HomeResponse.ResultsBean resultsBean = homeResponse.getResults().get(i);
//                HomeResponse.ResultsBean.PlayListBean playListBean = homeResponse.getResults().get(i).getPlayList();
//
//                //获取【最新音乐】【推荐音乐】
//                String Item = resultsBean.getItem();
//
//                if(hashMap.containsKey(Item)){
//                    //包含
//                    ArrayList<HomeResponse.ResultsBean.PlayListBean> resultsBeens = hashMap.get(Item);
//                    resultsBeens.add(playListBean);
//                }else{
//                    //不包含 Home.ResultsBean.PlayListBean 使用类中的内部类中的内部类
//                    ArrayList<HomeResponse.ResultsBean.PlayListBean> resultsBeens = new ArrayList<HomeResponse.ResultsBean.PlayListBean>();
//                    resultsBeens.add(playListBean);
//                    hashMap.put(Item,resultsBeens);
//                }
//            }
//
//            Log.i(TAG, "onResponse: " + hashMap);
//            Set<Map.Entry<String,ArrayList<HomeResponse.ResultsBean.PlayListBean>>> entrySet = hashMap.entrySet();
//            for (Map.Entry<String,ArrayList<HomeResponse.ResultsBean.PlayListBean>> entry:entrySet) {
//                String name = entry.getKey();
//                ArrayList<HomeResponse.ResultsBean.PlayListBean> playList = entry.getValue();
//                Home home = new Home();
//                home.setName(name);
//                home.setPlayListBeen(playList);
//                homes.add(home);
//            }
//
////            runOnUiThread(new Runnable() {
////                @Override
////                public void run() {
////                    homeAdapter.notifyDataSetChanged();
////                }
////            });
//            Log.e(TAG, "onResponse: "+homes);
//
//        }
//    });
//}



}
