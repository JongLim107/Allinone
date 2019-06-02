package com.example.allinone.ui.ipcamera.login;

import android.content.Intent;
import android.os.AsyncTask;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import io.objectbox.Box;
import me.goldze.mvvmhabit.base.BaseActivity;

public final class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {
    public static final int REQUEST_CODE = 1;
    public static final String SELECTED_INDEX = "INDEX_OF_PLATFORM";

    private final List<PlatformEntity> platforms = new ArrayList<>();
    private QueryPlatformsTask queryTask;

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
        queryTask = new QueryPlatformsTask(this, binding, viewModel, platforms);
        queryTask.execute();
    }

    @Override
    public void initViewObservable() {
        binding.platSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.onSelectPlatform(platforms.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewModel.setNavigator(this);
        viewModel.uiObs.pwdSwitchEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (viewModel.uiObs.pwdSwitchEvent.getValue()) {
                    binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Box<PlatformEntity> platformBox = ObjectBox.get().boxFor(PlatformEntity.class);
        platforms.clear();
        platforms.addAll(platformBox.getAll());

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            int pos = data.getIntExtra(SELECTED_INDEX, -1);
            if (pos != -1) {
                PlatformSpinnerAdapter spinnerAdapter = new PlatformSpinnerAdapter(this, platforms, viewModel);
                binding.platSpinner.setAdapter(spinnerAdapter);
                binding.platSpinner.setSelection(pos);
                viewModel.onSelectPlatform(platforms.get(pos));
            }
        }
    }

    @Override
    protected void onDestroy() {
        queryTask.cancel(true);
        super.onDestroy();
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

    private static class QueryPlatformsTask extends AsyncTask<Void, Void, Void> {
        WeakReference<LoginActivity> weakContext;
        ActivityLoginBinding binding;
        LoginViewModel viewModel;
        List<PlatformEntity> entities;

        QueryPlatformsTask(LoginActivity loginActivity, ActivityLoginBinding binding, LoginViewModel viewModel,
                List<PlatformEntity> entities) {
            this.weakContext = new WeakReference<>(loginActivity);
            this.binding = binding;
            this.viewModel = viewModel;
            this.entities = entities;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Box<PlatformEntity> platformBox = ObjectBox.get().boxFor(PlatformEntity.class);
            if (platformBox.getAll().size() < 5) {
                viewModel.showDialog("Loading Data...");
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    platformBox.put(new PlatformEntity("衢州联通" + i, "qzlt", "qzlt1234", "221.12.141.170", "9910"));
                }
            }

            entities.addAll(platformBox.getAll());
            return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            super.onPostExecute(o);
            viewModel.dismissDialog();
            PlatformSpinnerAdapter spinnerAdapter = new PlatformSpinnerAdapter(this.weakContext.get(), entities,
                    viewModel);
            binding.platSpinner.setAdapter(spinnerAdapter);
        }

    }
}
