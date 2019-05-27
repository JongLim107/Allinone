package com.example.allinone.ui.ipcamera.platforms;

import android.content.Intent;
import android.os.Bundle;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.app.ObjectBox;
import com.example.allinone.databinding.ActivityPlatformsBinding;
import com.example.allinone.entity.PlatformEntity;
import com.example.allinone.ui.ipcamera.editplatform.EditPlatformActivity;

import androidx.annotation.Nullable;
import io.objectbox.Box;
import me.goldze.mvvmhabit.base.BaseActivity;

import static com.example.allinone.ui.ipcamera.login.LoginActivity.SELECTED_INDEX;

/**
 * Created by Jong Lim on 18/5/19.
 */
public class PlatformsActivity extends BaseActivity<ActivityPlatformsBinding, PlatformsViewModel> implements
        PlatformsNavigator {
    public static final int REQUEST_CODE_ADD = 1;
    public static final int REQUEST_CODE_EDIT = 2;
    public static final String ON_EDIT_ITEM_ID = "EDIT_PLATFORM_ID";

    private Box<PlatformEntity> platforms;
    private PlatformsAdapter adapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_platforms;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        platforms = ObjectBox.get().boxFor(PlatformEntity.class);
        adapter = new PlatformsAdapter(this, viewModel, platforms.getAll());
        binding.lvPlatform.setAdapter(adapter);
        viewModel.setNavigator(this);
        viewModel.setPlatforms(platforms);
        viewModel.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            long id = data.getLongExtra(ON_EDIT_ITEM_ID, -1);
            if (id != -1) {
                adapter.clear();
                adapter.addAll(platforms.getAll());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onLeftClick() {
        setResult(RESULT_CANCELED);
        onBackPressed();
    }

    @Override
    public void onRightClick(String right) {
        Intent intent = new Intent(this, EditPlatformActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD);
    }

    @Override
    public void onTitleClick(String title) {

    }

    @Override
    public void onSelectItem(Integer pos) {
        Intent intent = new Intent();
        intent.putExtra(SELECTED_INDEX, pos);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void openEditPlatformActivity(PlatformEntity item) {
        Intent intent = new Intent(this, EditPlatformActivity.class);
        intent.putExtra(ON_EDIT_ITEM_ID, item.getId());
        startActivityForResult(intent, REQUEST_CODE_EDIT);
    }
}
