package com.gaojia.bmobdemo.user;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/2/3 0003.
 */

public class UserManager {
    //登录
    public static void login(String name, String pwd , final Handler handler){
        String url = App.USER_BASE + "user_login?ver=1000000&uid="+name+"&pwd="+pwd+"&device=0";
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(App.FAILURE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                Log.i("Tag",result);
                LoginInfo info = gson.fromJson(result,LoginInfo.class);
                Message msg = handler.obtainMessage();
                msg.what = App.SUCCEED;
                msg.obj = info;
                handler.sendMessage(msg);
            }
        });
    }
    //注册
    public static void register(String name, String pwd , String email,final Handler handler){
        String url = App.USER_BASE + "user_register?ver=1000000&uid="+name+"&email="+email+"&pwd="+pwd;
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(App.FAILURE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                LoginInfo info = gson.fromJson(result,LoginInfo.class);
                Message msg = handler.obtainMessage();
                msg.what = App.SUCCEED;
                msg.obj = info;
                handler.sendMessage(msg);
            }
        });
    }

}
