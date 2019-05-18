package com.example.allinone.ui.ipcamera.platforms;

import android.content.Intent;
import android.os.Bundle;

import com.example.allinone.BR;
import com.example.allinone.R;
import com.example.allinone.app.ObjectBox;
import com.example.allinone.databinding.ActivityPlatformsBinding;
import com.example.allinone.entity.PlatformEntity;

import io.objectbox.Box;
import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * Created by Jong Lim on 18/5/19.
 */
public class PlatformsActivity extends BaseActivity<ActivityPlatformsBinding, PlatformsViewModel> implements
        PlatformsNavigator {
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
        Box<PlatformEntity> platforms = ObjectBox.get().boxFor(PlatformEntity.class);
        adapter = new PlatformsAdapter(this, viewModel, platforms.getAll());
        binding.lvPlatform.setAdapter(adapter);
        viewModel.setNavigator(this);
    }

    @Override
    public void onLeftClick() {
        setResult(RESULT_CANCELED);
        onBackPressed();
    }

    @Override
    public void onRightClick(String right) {

    }

    @Override
    public void onTitleClick(String title) {

    }


    @Override
    public void onSelectItem(Integer pos) {
        Intent intent = new Intent();
        intent.putExtra("SELECTED_ITEM_ID", adapter.getItem(pos).id);
        intent.putExtra("SELECTED_ITEM_POS", pos);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void openEditPlatformActivity(int position) {
        //        startActivity();
        ToastUtils.showLong("You click on item " + position);
    }
}
