package com.example.allinone.ipcamera.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.app.AppViewModelFactory;
import com.example.allinone.entity.PlatformEntity;
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
        return ViewModelProviders.of(this, factory)
                .get(LoginViewModel.class);
    }

    @Override
    public void initData() {
        super.initData();
        List<PlatformEntity> platformList = new ArrayList<>();
        platformList.add(new PlatformEntity("Jong Lim", "2010LinZhong", "新加坡祖屋2号机", "123" ));
        platformList.add(new PlatformEntity("Lin Zhong", "2010LinZhong", "Nanning Longgang", "123"));
        platformList.add(new PlatformEntity("Shenzhen University", "2010LinZhong", "Ex Company 2nd Level", "123"));
        PlatformAdapter dataAdapter = new PlatformAdapter(this, viewModel, platformList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        binding.platSpinner.setAdapter(dataAdapter);
        binding.platSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PlatformEntity platform = (PlatformEntity) parent.getItemAtPosition(position);
                viewModel.onSelectPlatform(platform);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.platSpinner.showContextMenu();
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
    }
}
