package com.example.allinone.ipcamera.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.ArrayAdapter;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.app.AppViewModelFactory;
import com.example.allinone.data.login.Platform;
import com.example.allinone.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import me.goldze.mvvmhabit.base.BaseActivity;

public final class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    @Override
    public void initData() {
        List<Platform> platformList = new ArrayList<>();
        platformList.add(new Platform("Jong", "123", "My Room", "123"));
        platformList.add(new Platform("Lin", "123", "My House", "123"));
        platformList.add(new Platform("Guangxi", "123", "My HOME", "123"));
        ArrayAdapter<Platform> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                platformList);
        dataAdapter.setDropDownViewResource(R.layout.platform_item);
        binding.platSpinner.setAdapter(dataAdapter);
        super.initData();
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.pwdSwitchEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (viewModel.uc.pwdSwitchEvent.getValue()) {
                    binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        viewModel.uc.platform.observe(this, new Observer<Platform>() {
            @Override
            public void onChanged(Platform platform) {
                viewModel.onSelectPlatform(platform);
            }
        });
    }
}
