package com.gaojia.bmobdemo.user;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gaojia.bmobdemo.R;
import com.gaojia.bmobdemo.base.BaseActivity;
import com.gaojia.bmobdemo.base.ShowWhatFragmentListener;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class UserActivity extends BaseActivity implements ShowWhatFragmentListener {
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        showLoginFragment();
    }

    @Override
    public void setTargetFragment(String targetFragment) {
        switch (targetFragment) {
            case "LoginFragment":
                showLoginFragment();
                break;
            case "RegisterFragment":
                showRegisterFragment();
                break;
        }
    }
    private void showLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.loginfragment, loginFragment);
        ft.commit();
    }

    private void showRegisterFragment(){
        RegisterFragment registerFragment = new RegisterFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.loginfragment,registerFragment);
        ft.commit();
    }
}
