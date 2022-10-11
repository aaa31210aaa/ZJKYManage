package com.zhks.safetyproduction.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.zhks.safetyproduction.BR;
import com.zhks.safetyproduction.R;
import com.zhks.safetyproduction.config.HomeViewModelFactory;
import com.zhks.safetyproduction.databinding.ActivityChangePwdBinding;
import com.zhks.safetyproduction.utils.ScreenUtils;
import com.zhks.safetyproduction.viewmodel.ChangePwdViewModel;
import com.zhks.safetyproduction.viewmodel.LicenceCheckViewModel;
import com.zhks.safetyproduction.wight.TextChangedListener;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.ToastUtils;

public class ChangePwdActivity extends BaseActivity<ActivityChangePwdBinding, ChangePwdViewModel> {


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_change_pwd;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initParam() {
        ScreenUtils.setStatusBarColor(this,R.color.main_color);
    }

    @Override
    public void initData() {
        TextChangedListener.StringWatcher(binding.currentPwdEdit);
        TextChangedListener.StringWatcher(binding.newPwdEdit);
        TextChangedListener.StringWatcher(binding.commitNewPwdEdit);
    }

    @Override
    public ChangePwdViewModel initViewModel() {
        HomeViewModelFactory factory = HomeViewModelFactory.getInstance(getApplication(), this);
        return ViewModelProviders.of(this, factory).get(ChangePwdViewModel.class);
    }

    @Override
    public void initViewObservable() {
        //监听ViewModel中pSwitchObservable的变化, 当ViewModel中执行【uc.pSwitchObservable.set(!uc.pSwitchObservable.get());】时会回调该方法
        viewModel.pSwitchEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //pSwitchObservable是boolean类型的观察者,所以可以直接使用它的值改变密码开关的图标
                if (viewModel.pSwitchEvent.getValue()) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    binding.changePwdShowHide.setImageResource(R.drawable.pwd_visible);
                    binding.currentPwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.newPwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    binding.commitNewPwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    binding.changePwdShowHide.setImageResource(R.drawable.pwd_unvisible);
                    binding.currentPwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.newPwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    binding.commitNewPwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                binding.currentPwdEdit.setSelection(binding.currentPwdEdit.length());
                binding.newPwdEdit.setSelection(binding.newPwdEdit.length());
                binding.commitNewPwdEdit.setSelection(binding.commitNewPwdEdit.length());
            }
        });

        viewModel.modifyPwdEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                if (TextUtils.isEmpty(binding.currentPwdEdit.getText().toString())) {
                    ToastUtils.showShort("请填写现有密码");
                    return;
                }

                if (TextUtils.isEmpty(binding.newPwdEdit.getText().toString())) {
                    ToastUtils.showShort("请填写新密码");
                    return;
                }

                if (TextUtils.isEmpty(binding.commitNewPwdEdit.getText().toString())) {
                    ToastUtils.showShort("请确认新密码");
                    return;
                }

                if (!TextUtils.equals(binding.newPwdEdit.getText().toString(), binding.commitNewPwdEdit.getText().toString())) {
                    ToastUtils.showShort("两次设置的新密码不一致，请检查后输入");
                    return;
                }

                viewModel.modifyPwd(binding.newPwdEdit.getText().toString());
            }
        });
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        viewModel.setRightIconVisible(View.GONE);
        viewModel.setTitleText("修改登录密码");
    }
}