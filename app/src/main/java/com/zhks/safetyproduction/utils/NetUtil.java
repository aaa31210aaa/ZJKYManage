package com.zhks.safetyproduction.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author Administrator 网络状态判断
 */
public class NetUtil {
	/**
	 * 判断网络连接是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
		} else {
			// 如果仅仅是用来判断网络连接
			// 则可以使用 cm.getActiveNetworkInfo().isAvailable();
			NetworkInfo[] info = cm.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void isNetworkOnline(Handler myHandler) {
		//Process ipProcess = runtime.exec("ping -c 1 114.114.114.114");
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String ip = "www.baidu.com";
					Process ipProcess = null;// -c 次数 -w 超时
					ipProcess = Runtime.getRuntime().exec("ping -c 1 " + ip);
					//exitValue==0网络可用，否则网络不可用
					int exitValue = ipProcess.waitFor();
					Message message = new Message();
					message.arg1 = exitValue;
					myHandler.sendMessage(message);
				} catch (Exception  e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 判断是否是wifi
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        return networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

}
