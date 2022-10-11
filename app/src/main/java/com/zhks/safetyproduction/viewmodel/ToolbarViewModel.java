package com.zhks.safetyproduction.viewmodel;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;


import com.zhks.safetyproduction.R;

import me.goldze.mvvmhabit.base.BaseModel;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 对应include标题的ToolbarViewModel
 *
 * @param <M>
 */
public class ToolbarViewModel<M extends BaseModel> extends BaseViewModel<M> {
    public ObservableField<Drawable> backImage = new ObservableField<>(getApplication().getResources().getDrawable(R.drawable.back_white));
    //标题文字
    public ObservableField<String> titleText = new ObservableField<>("");
    //标题文字颜色
    public ObservableInt titleTextColor = new ObservableInt(getApplication().getResources().getColor(R.color.white));
    //右边文字
    public ObservableField<String> rightText = new ObservableField<>("更多");
    //右边文字的观察者
    public ObservableInt rightTextVisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt rightTextColor = new ObservableInt(getApplication().getResources().getColor(R.color.white));
    //右边图标的观察者
    public ObservableInt rightIconVisibleObservable = new ObservableInt(View.GONE);
    public ObservableInt toolbarBgColor = new ObservableInt(getApplication().getResources().getColor(R.color.main_color));
    public ObservableInt rightIcon = new ObservableInt();
    public SingleLiveEvent<String> addImageEvent = new SingleLiveEvent<>();
    //兼容databinding，去泛型化
    public ToolbarViewModel toolbarViewModel;

    public ToolbarViewModel(@NonNull Application application) {
        this(application, null);
    }

    public ToolbarViewModel(@NonNull Application application, M model) {
        super(application, model);
        toolbarViewModel = this;
    }

    /**
     * 返回按键
     */
    public void setBackImage(Drawable drawable) {
        backImage.set(drawable);
    }

    /**
     * 右上方图片
     */
    public void setRightIcon(int drawable) {
        rightIcon.set(drawable);
    }

    /**
     * 设置标题
     *
     * @param text 标题文字
     */
    public void setTitleText(String text) {
        titleText.set(text);
    }

    /**
     * 设置标题文字颜色
     */
    public void setTitleTextColor(int color) {
        titleTextColor.set(color);
    }


    /**
     * 设置右边文字
     *
     * @param text 右边文字
     */
    public void setRightText(String text) {
        rightText.set(text);
    }

    /**
     * 设置右边文字颜色
     */
    public void setRightTextColor(int color) {
        rightTextColor.set(color);
    }

    /**
     * 设置右边文字的显示和隐藏
     *
     * @param visibility
     */
    public void setRightTextVisible(int visibility) {
        rightTextVisibleObservable.set(visibility);
    }

    /**
     * 设置右边图标的显示和隐藏
     *
     * @param visibility
     */
    public void setRightIconVisible(int visibility) {
        rightIconVisibleObservable.set(visibility);
    }

    public void setToolbarBgColor(int color) {
        toolbarBgColor.set(color);
    }


    /**
     * 返回按钮的点击事件
     */
    public final BindingCommand backOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            backOnClick();
        }
    });

    public BindingCommand rightTextOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            rightTextOnClick();
        }
    });
    public BindingCommand rightIconOnClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            rightIconOnClick();
        }
    });

    /**
     * 右边文字的点击事件，子类可重写
     */
    protected void rightTextOnClick() {
    }

    /**
     * 右边图标的点击事件，子类可重写
     */
    protected void rightIconOnClick() {
    }

    protected void backOnClick() {
        finish();
    }

}
