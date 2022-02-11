package com.xiwu.print;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


/**
 * Created by by.huang on 2016/12/30.
 */

public class NetWorkChangeReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("by","网络发生变化");
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if(activeInfo!=null && activeInfo.isAvailable() && activeInfo.isConnected())
        {
            Intent netIntent = new Intent(WifiUtils.Action_NetWork_Success);
            context.sendBroadcast(netIntent);
        }
    }

}
