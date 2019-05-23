package com.example.allinone.ui.ipcamera.editplatform;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.app.ObjectBox;
import com.example.allinone.databinding.ActivityEditPlatformBinding;
import com.example.allinone.entity.PlatformEntity;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import io.objectbox.Box;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.base.IToolbarNavigator;

import static com.example.allinone.ui.ipcamera.platforms.PlatformsActivity.ON_EDIT_ITEM_ID;

/**
 * Created by Jong Lim on 18/5/19.
 */
public class EditPlatformActivity extends BaseActivity<ActivityEditPlatformBinding, EditPlatformsViewModel> implements
        IToolbarNavigator {

    private Box<PlatformEntity> platforms;
    private PlatformEntity platform;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_edit_platform;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        platforms = ObjectBox.get().boxFor(PlatformEntity.class);
        long id = getIntent().getLongExtra(ON_EDIT_ITEM_ID, -1);
        if (id == -1) {
            platform = new PlatformEntity();
        } else {
            platform = platforms.get(id);
        }

        binding.setModel(platform);
        viewModel.setNavigator(this);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uiObs.pwdSwitchEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (viewModel.uiObs.pwdSwitchEvent.getValue()) {
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        viewModel.uiObs.saveInnerEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    platform.setSaveInner(true);
                    binding.ckExtraCard.setChecked(false);
                } else {
                    platform.setSaveInner(false);
                    binding.ckSaveInner.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onLeftClick() {
        setResult(RESULT_CANCELED);
        onBackPressed();
    }

    @Override
    public void onRightClick(String right) {
        platforms.put(platform);
        Intent intent = new Intent();
        intent.putExtra(ON_EDIT_ITEM_ID, platform.getId());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onTitleClick(String title) {

    }

}
