package com.example.allinone.ui.ipcamera.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.AdapterView;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.app.AppViewModelFactory;
import com.example.allinone.app.ObjectBox;
import com.example.allinone.databinding.ActivityLoginBinding;
import com.example.allinone.entity.PlatformEntity;
import com.example.allinone.ui.ipcamera.devices.DevicesActivity;
import com.example.allinone.ui.ipcamera.platforms.PlatformsActivity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import io.objectbox.Box;
import me.goldze.mvvmhabit.base.BaseActivity;

public final class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {
    public static final int REQUEST_CODE = 1;

    Box<PlatformEntity> platformBox;

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
        super.initData();

        platformBox = ObjectBox.get().boxFor(PlatformEntity.class);
        if (platformBox.isEmpty()) {
            platformBox.put(new PlatformEntity("衢州联通", "qzlt", "qzlt1234", "221.12.141.170", 9910));
            platformBox.put(new PlatformEntity("Nanning Longgang", "Lin Zhong", "2010LinZhong", "221.12.141.170"));
            platformBox.put(new PlatformEntity("Ex Company 2nd Level", "Shenzhen University", "2010LinZhong",
                    "221.12.141.170"));
        }

        PlatformSpinnerAdapter dataAdapter = new PlatformSpinnerAdapter(this, viewModel, platformBox.getAll());
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
    }

    @Override
    public void initViewObservable() {
        viewModel.setNavigator(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            long id = data.getLongExtra("SELECTED_ITEM_ID", -1);
            int pos = data.getIntExtra("SELECTED_ITEM_POS", 0);
            if (id != -1) {
                binding.platSpinner.setSelection(pos);
                viewModel.onSelectPlatform(platformBox.get(id));
            }
        }
    }

    @Override
    public void openDevicesActivity() {
        startActivity(new Intent(this, DevicesActivity.class));
        finish();
    }

    @Override
    public void openPlatformsActivity() {
        Intent i = new Intent(this, PlatformsActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }
}
