package kr.co.genexon.factFinder;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by topgu on 2018-04-05.
 */

public class NetworkCheckReceiver extends BroadcastReceiver {

    protected static final String TAG = "FactFinder";

    @Override
    public void onReceive(Context context, Intent intent) {

        String status = NetworkUtil.getConnectivityStatusString2(context);

        Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
    }
}
