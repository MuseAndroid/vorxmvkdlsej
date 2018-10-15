package kr.co.genexon.factFinder;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by topgu on 2017-09-24.
 */

public class MarketVersionChecker {

    protected static final String TAG = "FactFinder";
    public static String getMarketVersion(String packageName) {

        LogUtil.d(TAG, "마켓 전달 값" + packageName);
        String marketVer = null;

        try {
            Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + packageName).get();
            Elements Version = document.select(".content");


            for (Element element : Version) {
                if (element.attr("itemprop").equals("softwareVersion")) {
                    marketVer = element.text().trim();
                    return element.text().trim();
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return marketVer;
    }

    public static String getMarketVersionFast(String packageName) {

        String mData = "", mVer = null;

        try {
            URL mUrl = new URL("https://play.google.com/store/apps/details?id=" + packageName);
            HttpURLConnection mConnection = (HttpURLConnection) mUrl.openConnection();
            if (mConnection == null) {
                return null;
            }

            mConnection.setConnectTimeout(5000);
            mConnection.setUseCaches(false);
            mConnection.setDoOutput(true);

            if (mConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(mConnection.getInputStream()));
                while(true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    mData += line;
                }
                bufferedReader.close();
            }
            mConnection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String startToken = "softwareVersion\">";
        String endToken = "<";
        int index = mData.indexOf(startToken);

        if (index == -1) {
            mVer = null;
        } else {
            mVer = mData.substring(index + startToken.length(), index + startToken.length() + 100);
            mVer = mVer.substring(0, mVer.indexOf(endToken)).trim();
        }
        return mVer;
    }
}
