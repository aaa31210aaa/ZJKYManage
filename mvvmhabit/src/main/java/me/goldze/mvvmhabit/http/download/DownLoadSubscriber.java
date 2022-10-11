package me.goldze.mvvmhabit.http.download;

import android.util.Log;


import java.io.File;
import java.io.FileInputStream;

import io.reactivex.observers.DisposableObserver;
import me.goldze.mvvmhabit.constants.Constants;

/**
 * Created by goldze on 2017/5/11.
 */

public class DownLoadSubscriber<T> extends DisposableObserver<T> {
    private ProgressCallBack fileCallBack;
    private DownLoadStateBean downLoadStateBean;

    public DownLoadSubscriber(ProgressCallBack fileCallBack, DownLoadStateBean downLoadStateBean) {
        this.fileCallBack = fileCallBack;
        this.downLoadStateBean = downLoadStateBean;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (fileCallBack != null)
            fileCallBack.onStart();
    }

    @Override
    public void onComplete() {
        if (fileCallBack != null)
            fileCallBack.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        if (fileCallBack != null)
            fileCallBack.onError(e);
    }

    @Override
    public void onNext(T t) {
        if (fileCallBack != null) {
            try {
                long bytesLoaded = getFileSize(new File(Constants.APK_SAVE_PATH + "/" +Constants.APK_NAME));
                if (bytesLoaded == downLoadStateBean.getTotal()) {
                    fileCallBack.onSuccess(t);
                } else {
                    onError(new Throwable());
                }
                Log.e("DownLoadSubscriber","下载的文件大小："+ bytesLoaded +"====="+"文件总大小:"+downLoadStateBean.getTotal());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取指定文件大小
     * @return
     * @throws Exception 　　
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }
}