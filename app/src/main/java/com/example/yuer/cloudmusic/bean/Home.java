package com.example.yuer.cloudmusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * 处理首页的数据
 * Created by weidong on 2017/6/9.
 */

public class Home implements Parcelable{

    //处理首页的条目名称并关联其中的各条信息
    private int type;   //条目下的信息：eg：
    private String name;//条目名称eg：新专辑上架  推荐歌单  最新音乐

    static final  String PLAYLISTBEEN_KEY = "PLAYLISTBEEN_KEY";

    ArrayList<HomeResponse.ResultsBean.PlayListBean> playListBeen = new ArrayList<>();

    public Home() {

    }

    protected Home(Parcel in) {
        type = in.readInt();
        name = in.readString();
        playListBeen = in.createTypedArrayList(HomeResponse.ResultsBean.PlayListBean.CREATOR);
    }

    //读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。因为实现类在这里还是不可知的，所以需要用到模板的方式，继承类名通过模板参数传入
    //为了能够实现模板参数的传入，这里定义Creator嵌入接口,内含两个接口函数分别返回单个和多个继承类实例
    public static final Creator<Home> CREATOR = new Creator<Home>() {
        @Override
        public Home createFromParcel(Parcel in) {
            return new Home(in);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<HomeResponse.ResultsBean.PlayListBean> getPlayListBeen() {
        return playListBeen;
    }

    public void setPlayListBeen(ArrayList<HomeResponse.ResultsBean.PlayListBean> playListBeen) {
        this.playListBeen = playListBeen;
    }

    //内容描述接口，基本不用管
    @Override
    public int describeContents() {
        return 0;
    }

    //写入接口函数，打包
    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(type);
        dest.writeString(name);
        dest.writeTypedList(playListBeen);
    }


}
