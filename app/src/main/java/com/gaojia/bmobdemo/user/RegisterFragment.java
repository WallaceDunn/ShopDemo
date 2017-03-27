package com.gaojia.bmobdemo.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.gaojia.bmobdemo.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/14 0014.
 */
public class RegisterFragment extends Fragment {
    @BindView(R.id.fragment_register_user_et)
    EditText name;
    @BindView(R.id.fragment_register_pwd_et)
    EditText pwd;
    @BindView(R.id.fragment_register_email_et)
    EditText email;
    @BindView(R.id.fragment_register_bt)
    Button register;
    String uname;
    String upwd;
    String uemail;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg != null){
                switch (msg.what){
                    case App.FAILURE:
                        Toast.makeText(getContext(), "请检查网络连接设置", Toast.LENGTH_SHORT).show();
                        break;
                    case App.SUCCEED:
                        LoginInfo info = (LoginInfo) msg.obj;
                        LoginInfo.LoginData data = info.getData();
                        if(info.getData()==null){
                            Toast.makeText(getContext(), "请输入正确的用户名，密码和邮箱地址", Toast.LENGTH_SHORT).show();
                        }else {
                            switch (data.getResult()) {
                                case 0:
                                    Toast.makeText(getContext(), "注册成功", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.putExtra("token", data.getToken());
                                    intent.putExtra("name", uname);
                                    getActivity().setResult(App.RESULTCODE, intent);
                                    UserActivity activity = (UserActivity) getActivity();
                                    activity.setTargetFragment("LoginFragment");
                                    break;
                                case -1:
                                    Toast.makeText(getContext(), "服务器不允许注册(用户数量已满)", Toast.LENGTH_SHORT).show();
                                    break;
                                case -2:
                                    Toast.makeText(getContext(), "用户名重复", Toast.LENGTH_SHORT).show();
                                    break;
                                case -3:
                                    Toast.makeText(getContext(), "邮箱重复", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                        break;
                }
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    public static boolean regexUname(String str){
        Pattern p = Pattern.compile("^[a-zA-Z]\\w{6,24}$");
        Matcher m = p.matcher(str);
        boolean b = m.matches();
        return b;
    }
    public static boolean regexUpwd(String str){
        Pattern p = Pattern.compile("^[a-zA-Z0-9]\\w{6,24}$");
        Matcher m = p.matcher(str);
        boolean b = m.matches();
        return b;
    }
    public static boolean regexUemail(String str){
        Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher m = p.matcher(str);
        boolean b = m.matches();
        return b;
    }


    @OnClick(R.id.fragment_register_bt)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.fragment_register_bt:
                uname = name.getText().toString().trim();
                upwd = pwd.getText().toString().trim();
                uemail = email.getText().toString().trim();
                if(regexUname(uname)==false|| TextUtils.isEmpty(uname)){
                    Toast.makeText(getContext(),"请输入正确的用户名",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(upwd)||regexUpwd(upwd)==false){
                    Toast.makeText(getContext(),"请输入正确的密码",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(uemail)||regexUemail(uemail)==false){
                    Toast.makeText(getContext(),"请输入正确的邮箱地址",Toast.LENGTH_SHORT).show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserManager.register(uname,upwd,uemail,handler);
                        }
                    }).start();
                }
                System.out.println(uname);
                System.out.println(regexUname(uname));
                break;
        }
    }
}
