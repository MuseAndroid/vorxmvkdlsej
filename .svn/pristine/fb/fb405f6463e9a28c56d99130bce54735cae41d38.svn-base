package kr.co.genexon.factFinder;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by topgu on 2018-04-05.
 */

public class NetworkUtil {
    public static int TYPE_WIFI = 1;

    public static int TYPE_MOBILE = 2;

    public static int TYPE_NOT_CONNECTED = 0;





    public static int getConnectivityStatus(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context

                .getSystemService(Context.CONNECTIVITY_SERVICE);



        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (null != activeNetwork) {

            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)

                return TYPE_WIFI;



            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)

                return TYPE_MOBILE;

        }

        return TYPE_NOT_CONNECTED;

    }



    public static String getConnectivityStatusString2(Context context) {

        int conn = NetworkUtil.getConnectivityStatus(context);

        String status = null;

        if (conn == NetworkUtil.TYPE_WIFI) {

            status = "WiFi를 사용합니다.";

        } else if (conn == NetworkUtil.TYPE_MOBILE) {

            status = "모바일 데이터를 사용합니다.\n데이터 사용량에 따라 금액이 청구됩니다.";

        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {

            status = "인터넷과의 연결이 끊겼습니다.";

        }

        return status;

    }



    public static String getConnectivityStatusString(Context context) {

        int conn = NetworkUtil.getConnectivityStatus(context);

        String status = null;

        if (conn == NetworkUtil.TYPE_WIFI) {

            status = "WiFi를 사용합니다.";

        } else if (conn == NetworkUtil.TYPE_MOBILE) {

            status = "모바일 데이터를 사용합니다.\n데이터 사용량에 따라 금액이 청구됩니다.";

        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {

            status = "인터넷과의 연결이 끊겼습니다.";

        }

        return status;

    }
}
