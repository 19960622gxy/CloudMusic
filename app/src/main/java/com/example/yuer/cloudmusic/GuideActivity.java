package com.example.yuer.cloudmusic;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.yuer.cloudmusic.login.LoginActivity;
import com.example.yuer.cloudmusic.utils.AppConfigUtils;
import com.rd.PageIndicatorView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity {


    private float ratio;
    int index = 0;//记录当前界面的位置
    private TextSwitcher ts_title;
    private TextSwitcher ts_desc;


    String[] titles = new String[]{
            "个 性 推 荐",
            "精 彩 评 论",
            "海 量 资 讯"
    };
    String[] descs = new String[]{
            "每 天 为 你 量 身 推 荐 最 合 口 味 的 好 音 乐",
            "4 亿 多 条 有 趣 的 故 事，听 歌 再 不 孤 单",
            "明 星 动 态、音 乐 热 点 尽 收 眼 底"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去掉屏幕上方遮挡
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    ArrayList<View> views = new ArrayList<>();

    private void initView() {

        //获取图片资源和屏幕的比例
        ratio = 1F * (getResources().getDisplayMetrics().heightPixels / 1920.0F);

        ts_title = (TextSwitcher) findViewById(R.id.ts_title);
        ts_title.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(GuideActivity.this);
                tv.setTextSize(24);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(getResources().getColor(android.R.color.white));
                tv.setTypeface(Typeface.DEFAULT_BOLD);
                return tv;
            }
        });

        ts_desc = (TextSwitcher) findViewById(R.id.ts_desc);
        ts_desc.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(GuideActivity.this);
                tv.setTextSize(13);
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(getResources().getColor(R.color.color5));
                tv.setTypeface(Typeface.DEFAULT_BOLD);
                return tv;
            }
        });


        ts_title.setText(titles[0]);
        ts_desc.setText(descs[0]);


        View view1 = LayoutInflater.from(this).inflate(R.layout.layout_guide1, null);
        setViewSize(view1, 0);


        View view2 = LayoutInflater.from(this).inflate(R.layout.layout_guide2, null);
        setViewSize(view2, 1);

        View view3 = LayoutInflater.from(this).inflate(R.layout.layout_guide3, null);
        setViewSize(view3, 2);

        views.add(view1);
        views.add(view2);
        views.add(view3);

        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new MyPagerAdapter());

        //viewpager页面切换时的一些变化
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//当前切换的是第几个页面
                View view = views.get(position); //取得每一个viewpager的position 0 1 2
                if (index < position) { //往回滑不出现动画效果
                    if (position == 1) {
                        //第二页时aes执行动画效果，偏移量(517F * ratio)
                        ivAesAnimator(view.findViewById(R.id.iv_aes), (517F * ratio));
                        //aek整个文字和aet头像都是从无到淡到显示成功 一个动态效果
                        ivAekAnimator(view.findViewById(R.id.iv_aek));
                        ivAekAnimator(view.findViewById(R.id.iv_aet));
                    }
                    if (position == 2) {
                        //第三页文字之类的从无到淡到显示成功的动态效果
                        ivAekAnimator(view.findViewById(R.id.iv_ael));
//                    ivAesAnimator(view.findViewById(R.id.iv_aet), (333F * ratio));//老师给的数据
                        //第二页的头像实现一个动态向上的效果 偏移量 (348F * ratio)
                        ivAesAnimator(view.findViewById(R.id.iv_aet), (348F * ratio));
                    }

                    ts_title.setInAnimation(GuideActivity.this, R.anim.right_in);
                    ts_title.setOutAnimation(GuideActivity.this, R.anim.right_out);

                    ts_desc.setInAnimation(GuideActivity.this, R.anim.right_in);
                    ts_desc.setOutAnimation(GuideActivity.this, R.anim.right_out);

                } else {
                    ts_title.setInAnimation(GuideActivity.this, R.anim.left_in);
                    ts_title.setOutAnimation(GuideActivity.this, R.anim.left_out);

                    ts_desc.setInAnimation(GuideActivity.this, R.anim.left_in);
                    ts_desc.setOutAnimation(GuideActivity.this, R.anim.left_out);
                }

                ts_title.setText(titles[position]);
                ts_desc.setText(descs[position]);
                index = position;

            }

            //渐入。执行动画
            private void ivAesAnimator(View view, float size) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", size, 0);
                animator.setDuration(800);//从开始位置到目的地的时间
                animator.start();
            }

            //显示，慢慢显示
            private void ivAekAnimator(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", new float[]{0F, 1F});
                animator.setDuration(600);//从开始位置到目的地的时间
                animator.start();
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //viewpager控制器，选择哪一页，小红点相应跳转  使用的是PagheIndicatorView框架
        PageIndicatorView pageIndicatorView = (PageIndicatorView) findViewById(R.id.pageIndicatorView);
        pageIndicatorView.setViewPager(vp);
        pageIndicatorView.setRadius(11F * ratio);//15
        pageIndicatorView.setPadding((int) (5 * ratio), 0, (int) (5 * ratio), 0);

        //使viewpager滑动时的样式看不出左右滑动（感觉背景不动）
        vp.setPageTransformer(true, new ViewPager.PageTransformer() {
            /**
             *
             * @param page  当前滑动的View
             * @param position View所占屏幕的空间
             *                 值为-1 page 滑出屏幕了
             *                 值为 0 在屏幕中间
             */
            @Override
            public void transformPage(View page, float position) {
//                //1  LinearLayout   2 RelativeLayout
//                Log.i(TAG, "transformPage: " + page.getClass() + "   " + position);
//                //

                int pageWidth = page.getWidth();
                if (position < -1) {
                    page.setAlpha(0);
                } else if (position <= 1) {
                    if (position < 0) {
                        page.setTranslationX(-pageWidth * position);
                    } else {
                        page.setTranslationX(pageWidth);

                        //系统默认的是两个view贴在一起滑动的样式，这个是改变 移动view的距离
                        //抵消掉移动的距离 让他直接隐藏 然后下一个view直接在上一个view的位置显示
                        //从而实现这样一个样式，和系统原来的样式不一样
                        page.setTranslationX(-pageWidth * position);
                        //TranslationX的意义
                        //*
                        // .setTranstionX(i++)  往x正方向移动   平移效果
                        // .setTranstionY(i++)  往Y正方向移动   垂直移动
                        // */
                    }
                    page.setAlpha(Math.max(0, 1 - Math.abs(position)));//Alpha透明度
                } else {
                    page.setAlpha(0);
                }

            }
        });


        View bg_Color = findViewById(R.id.bg_color);
        bg_Color.getLayoutParams().height = (int) (1180F * ratio);


        //登陆注册    立即体验按钮
        Button login=(Button) findViewById(R.id.guide_login_regist);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GuideActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                AppConfigUtils.getInstance().setGuide(GuideActivity.this,false);
            }
        });
        Button tiyan=(Button) findViewById(R.id.guide_tiyan);
        tiyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                //存储第一次的数据，修改为flase
                AppConfigUtils.getInstance().setGuide(GuideActivity.this,false);

//                SharedPreferences sp=getSharedPreferences("config",MODE_PRIVATE);
//                SharedPreferences.Editor et=sp.edit();
//                et.putBoolean("guide",false);
//                et.commit();

            }
        });


    }


    private void setViewSize(View view, int index) {
        //设置中间View的大小
        view.findViewById(R.id.cv_content).getLayoutParams().width = (int) (803F * ratio);
        view.findViewById(R.id.cv_content).getLayoutParams().height = (int) (1218F * ratio);

        int ivAesSzie = (int) (237F * ratio); //获取图片的宽高 aes这张图片237*237  此为比例值
        int marginLeft = (int) (40F * ratio); //获取图片的宽高

        if (index == 0) {
            //拿到这张图片aes 动态设置他的位置   为了屏幕适配
            ImageView iv_aes = (ImageView) view.findViewById(R.id.iv_aes);
            FrameLayout.LayoutParams ivAesParams = (FrameLayout.LayoutParams) iv_aes.getLayoutParams();
            ivAesParams.height = ivAesSzie;  //设置图片高度为比例高度值 因为是正方形 所以宽高一样
            ivAesParams.width = ivAesSzie;
            //获取图片距离顶部的距离
            int marginTop = (int) (552F * ratio); //获取图片的宽高
//                int marginLeft = (int) (40F * ratio); //获取图片的宽高
            ivAesParams.topMargin = marginTop;
            ivAesParams.leftMargin = marginLeft;
        }

        if (index == 1) {
//                int marginLeft = (int) (40F * ratio);
            FrameLayout.LayoutParams view2_iv_aes_Params = (FrameLayout.LayoutParams) view.findViewById(R.id.iv_aes).getLayoutParams();
            view2_iv_aes_Params.leftMargin = marginLeft;
            view2_iv_aes_Params.topMargin = marginLeft;
            view2_iv_aes_Params.height = ivAesSzie;
            view2_iv_aes_Params.width = ivAesSzie;

            //头像照片的比例距离
            ImageView iv_aet = (ImageView) view.findViewById(R.id.iv_aet);
            FrameLayout.LayoutParams iv_aetParams = (FrameLayout.LayoutParams) iv_aet.getLayoutParams();
//                iv_aetParams.topMargin = (int) (333F * ratio);//老师给的数据
            iv_aetParams.topMargin = (int) (348F * ratio);
            iv_aetParams.leftMargin = marginLeft;
        }
        if (index == 2) {
            ImageView iv_aet = (ImageView) view.findViewById(R.id.iv_aet);
            FrameLayout.LayoutParams ivAetParams = (FrameLayout.LayoutParams) iv_aet.getLayoutParams();
            ivAetParams.topMargin = marginLeft;
            ivAetParams.leftMargin = marginLeft;
        }


    }



    class MyPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }

}
