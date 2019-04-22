package com.example.allinone.ip_camera.login;

import android.os.Bundle;

import com.example.allinone.R;
import com.example.allinone.databinding.ActivityLoginBinding;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.app.app;

public final class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public  LoginViewModel initViewModel(){
        AppViewModelFactory factory = null;
    }
}
