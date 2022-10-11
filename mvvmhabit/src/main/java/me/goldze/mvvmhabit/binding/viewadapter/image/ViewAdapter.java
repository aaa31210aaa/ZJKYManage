package me.goldze.mvvmhabit.binding.viewadapter.image;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import androidx.databinding.BindingAdapter;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes))
                    .into(imageView);
        }
    }


    @BindingAdapter(value = {"drawableRes","placeholderRes"}, requireAll = false)
    public static void setImageRes(final ImageView imageView, int drawableRes, final int placeholderRes) {
        Glide.with(imageView.getContext())
                .load(drawableRes)
                .skipMemoryCache(true)//禁止Glide内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存资源
                .error(placeholderRes)
                .placeholder(placeholderRes)
                .into(imageView);
    }

    @BindingAdapter(value = {"uri","placeholderRes"}, requireAll = false)
    public static void setImageRes(final ImageView imageView, Uri uri, final int placeholderRes) {
        Glide.with(imageView.getContext())
                .load(uri)
                .skipMemoryCache(true)//禁止Glide内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存资源
                .error(placeholderRes)
                .placeholder(placeholderRes)
                .into(imageView);
    }
}

