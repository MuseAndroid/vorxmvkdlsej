package kr.co.genexon.factFinder;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import java.net.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidquery.AQuery;

import org.apache.http.util.EncodingUtils;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import okhttp3.Cookie;

import static kr.co.genexon.factFinder.R.layout.activity_mainview;

/**
 * Created by topgu on 2017-09-20.
 */

public class MainViewActivity extends BaseActivity {

    protected static final String TAG = "FactFinder";

    private final Handler handler = new Handler();
    protected WebView mWebView = null;

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final int REQUEST_SEND_SMS = 1;
    private static final int REQUEST_CALL = 2;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 3;

    Button testBtn2;
    URL Url;
    String strCookies = "";
    String result = "";
    String loadUrlStr;
    String returnURL;

    AQuery mAQuery = new AQuery(this);

    Handler webHandler;

    String deviceVersion;
    String storeVersion;

    String saveSmsPhoneNum;
    String saveCallPhoneNum;

    String nowUrl;

    SelectServerURL ssUrl = new SelectServerURL();

    ConstraintLayout loading_view;

    String comCode;
    String id;
    String pw;
    String device_gubun;
    String device_id;

    ImageView iv_loading_splash;

    String baseURL = ssUrl.getServerURL();

    Intent mIntent;

    @SuppressLint({"SetJavaScriptEnabled"})
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    @JavascriptInterface
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        removeSaveData();

//        mWebView = new WebView(this);
//        setLayout();

        mWebView = findViewById(R.id.webview);

        loading_view = findViewById(R.id.loading_view);

        loading_view.bringToFront();

        webHandler = new Handler();

        Intent loginIntent = getIntent();

        if(loginIntent!=null){
            comCode = loginIntent.getStringExtra("comp");
            LogUtil.d(TAG, "comcode = "+comCode);
            id = loginIntent.getStringExtra("id");
            LogUtil.d(TAG, "id = "+id);
            pw = loginIntent.getStringExtra("pw");
            LogUtil.d(TAG, "pw = "+pw);
            device_gubun = loginIntent.getStringExtra("device_gubun");
            LogUtil.d(TAG, "device_gubun = " + device_gubun);
            device_id = loginIntent.getStringExtra("device_id");
            LogUtil.d(TAG, "device_id = " + device_id);
        }

        // 웹뷰에서 자바스크립트실행가능
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setLayerType(mWebView.LAYER_TYPE_HARDWARE, null);
        } else {
            mWebView.setLayerType(mWebView.LAYER_TYPE_SOFTWARE, null);
        }

        mWebView.addJavascriptInterface(new AndroidBridge(mWebView), "ffAndroid");

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(MainViewActivity.this)
                        .setTitle("")
                        .setMessage(message)
                        .setPositiveButton("확인", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.confirm();
                            }
                        })
                        .setNegativeButton("취소", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
                return true;
            }
        });

        mWebView.setWebViewClient(new WebViewClientClass() {});

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (0 != (getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE))
            { WebView.setWebContentsDebuggingEnabled(true); }
        }

        loadUrlStr = baseURL + "/mobile/login.do?mb_id=" + comCode + "&consultant_id=" + id + "&password=" + pw + "&device_gubun=" + device_gubun + "&device_id=" + device_id;
        String postData = "mb_id=" + comCode + "&consultant_id=" + id + "&password=" + pw + "&device_gubun=" + device_gubun + "&device_id=" + device_id;

        mWebView.postUrl(baseURL + "/mobile/login.do", EncodingUtils.getBytes(postData, "BASE64"));
//        mWebView.loadUrl(loadUrlStr);
    }

    public class AndroidBridge {

        private WebView webView;

        public AndroidBridge(WebView mWebView) {
            this.webView = mWebView;
        }

        @JavascriptInterface
        public void sendUserData(final String name, final String juminFront, final String juminBack, final String mobile, final String mobileCo, final String orgCD, final String consultantNm, final String cust_seq) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    moveCrawling(name, juminFront, juminBack, mobile, mobileCo, orgCD, consultantNm, cust_seq, comCode, id);
                }
            });
        }

        @JavascriptInterface
        public void goLoginPage() {

            AlertDialog.Builder alertDialogBuilder =  new AlertDialog.Builder(new android.view.ContextThemeWrapper(MainViewActivity.this, android.R.style.Theme_DeviceDefault_InputMethod));
            alertDialogBuilder.setTitle("로그아웃");
            alertDialogBuilder.setIcon(R.mipmap.ff_icon);
            alertDialogBuilder.setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent LoginPage = new Intent(MainViewActivity.this, LoginActivity.class);
                            finish();
                            startActivity(LoginPage);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
        }

        @JavascriptInterface
        public void riseNewPage(String webUrl) {
            LogUtil.d(TAG, "웹 주소 = " + webUrl);
            if (webUrl != null) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseURL + webUrl));
                startActivity(webIntent);
            }
        }

        @JavascriptInterface
        public void riseWindowOpen(String webUrl) {
            LogUtil.d(TAG, "웹 주소 = " + webUrl);
            if (webUrl != null) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                startActivity(webIntent);
            }
        }

        @JavascriptInterface
        public void copyBankNum(String bankNum) {
            if (bankNum.length() != 0) {
                ClipData clip = ClipData.newPlainText("banktext", bankNum);

                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(clip);

                Toast.makeText(MainViewActivity.this, "계좌번호를 저장하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }

        @JavascriptInterface
        public void exitApp() {
            exitFunction();
        }

        @JavascriptInterface
        public void callEvent(String phoneNum) {
            checkCallPermission(phoneNum);
        }

        @JavascriptInterface
        public void smsEvent(String phoneNum) {
            checkSmsPermission(phoneNum);
        }

        @JavascriptInterface
        public void webAlert(String alertBody) {
            // Web의 Validation 작동 시 표현됨
            LogUtil.d(TAG, "alertBody 값 = " + alertBody);
            Toast.makeText(MainViewActivity.this, alertBody, Toast.LENGTH_SHORT).show();
        }
    }
    // name, juminFront, juminBack, mobile, mobileCo, orgCD, consultantId
    public void moveCrawling(String custNm, String juminFront, String juminBack, String mobile, String mobileco, String orgCD, String consultantNm, String cust_seq, String mb_id, String consultant_id) {

        LogUtil.d(TAG, "고객 이름 = " + custNm);
        LogUtil.d(TAG, "고객 주민번호 앞 = " + juminFront);
        LogUtil.d(TAG, "고객 주민번호 뒤 = " + juminBack);
        LogUtil.d(TAG, "고객 핸드폰번호 = " + mobile);
        LogUtil.d(TAG, "고객 핸드폰 통신사 = " + mobileco);
        LogUtil.d(TAG, "엔진 구분코드 = " + orgCD);
        LogUtil.d(TAG, "PM명 = " + consultantNm);
        LogUtil.d(TAG, "고객seq = " + cust_seq);
        LogUtil.d(TAG, "회사코드 = " + mb_id);
        LogUtil.d(TAG, "앱 고객 ID = " + consultant_id);

        removeSaveData();

        LogUtil.d(TAG, "저장된 회사코드 값 = " + mb_id);
        LogUtil.d(TAG, "저장된 ID값 = " + consultant_id);

        Intent crawlingIntent = new Intent(this, MainActivity.class);

        if (!custNm.equals(null) && !mobile.equals(null) && !mobileco.equals(null)) {
            if (!custNm.equals("undefined")) {
                crawlingIntent.putExtra("mobile", mobile);
            } else {
                crawlingIntent.putExtra("mobile", "");
            }

            if (!mobile.equals("undefined")) {
                crawlingIntent.putExtra("mobileco", mobileco);
            } else {
                crawlingIntent.putExtra("mobileco", "");
            }

            if (!custNm.equals("undefined")) {
                crawlingIntent.putExtra("customer_name", custNm);
            } else {
                crawlingIntent.putExtra("customer_name", "");
            }

            if (!juminFront.equals("undefined")) {
                crawlingIntent.putExtra("userSsn1", juminFront);
            } else {
                crawlingIntent.putExtra("userSsn1", "");
            }

            if (!juminBack.equals("undefined")) {
                crawlingIntent.putExtra("userSsn2", juminBack);
            } else {
                crawlingIntent.putExtra("userSsn2", "");
            }

            if (orgCD.equals("credit4u")) {
                crawlingIntent.putExtra("orgCD", orgCD);
            } else {
                crawlingIntent.putExtra("orgCD", orgCD);
            }

            if (!consultantNm.equals("undefined")) {
                crawlingIntent.putExtra("constNm", consultantNm);
            } else {
                crawlingIntent.putExtra("constNm", "");
            }
            crawlingIntent.putExtra("cust_seq", cust_seq);
            crawlingIntent.putExtra("consultant_id", consultant_id);
            crawlingIntent.putExtra("mb_id", mb_id);
        }
        startActivityForResult(crawlingIntent, 1);
//        onPause();
    }

    private void checkSmsPermission(String phoneNum) {
        saveSmsPhoneNum = phoneNum;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS);
            }
        } else {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("sms:" + phoneNum);
            smsIntent.setData(uri);
            smsIntent.putExtra("sms_body", "");
            startActivity(smsIntent);
        }
    }

    private void checkCallPermission(String phoneNum) {
        saveCallPhoneNum = phoneNum;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            }
        } else {
            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
            startActivity(callIntent);
        }
    }

    private void checkWriteStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            LogConfigurator logConfigurator = new LogConfigurator();
            logConfigurator.setFileName(Environment.getExternalStorageDirectory() + "/FactFinder/Logs/ffLog_" + getTime() + ".txt");
            logConfigurator.configure();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mWebView.saveState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_SEND_SMS:
                for (int i=0; i <permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.SEND_SMS)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            LogUtil.d(TAG, "권한을 얻었습니다.");
                            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                            Uri uri = Uri.parse("sms:" + saveSmsPhoneNum);
                            smsIntent.setData(uri);
                            smsIntent.putExtra("sms_body", "");
                            startActivity(smsIntent);
                        } else {
                            Toast.makeText(this, "문자를 보내기 위한 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case REQUEST_CALL:
                for (int i=0; i <permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(Manifest.permission.CALL_PHONE)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            LogUtil.d(TAG, "권한을 얻었습니다.");
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + saveCallPhoneNum));
                            startActivity(callIntent);
                        } else {
                            Toast.makeText(this, "전화를 걸기 위한 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                for (int i=0; i <permissions.length; i++) {
                    String permission = permissions[i];
                    int grantResult = grantResults[i];
                    if (permission.equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (grantResult == PackageManager.PERMISSION_GRANTED) {
                            LogUtil.d(TAG, "권한을 얻었습니다.");
                            LogConfigurator logConfigurator = new LogConfigurator();
                            logConfigurator.setFileName(Environment.getExternalStorageDirectory() + "/FactFinder/Logs/ffLog_" + getTime() + ".txt");
                            logConfigurator.configure();
                        } else {
                            Toast.makeText(this, "앱 동작을 위한 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onResume() {
        super.onResume();

        new getMarketVersion().execute();

        SharedPreferences crawlingStatus = getSharedPreferences("crawlingStatus", MODE_PRIVATE);
        String go_url = crawlingStatus.getString("go_url", "");

        LogUtil.d(TAG, "이동 URL 정보 = " + go_url);

        if (go_url.length() != 0) {
            result = crawlingStatus.getString("go_url", "");
            String custSeq = crawlingStatus.getString("cust_seq", "");
            String mbId = crawlingStatus.getString("mb_id", "");
            String consultant_id = crawlingStatus.getString("con_id", "");
            String slideGubun = crawlingStatus.getString("slide_gubun", "");

            LogUtil.d(TAG, "이동할 화면 url = " + result);
            LogUtil.d(TAG, "대상 고객Seq = " + custSeq);
            LogUtil.d(TAG, "MB_ID 값 = " + mbId);
            LogUtil.d(TAG, "Consultant_ID 값 = " + consultant_id);
            LogUtil.d(TAG, "slideGubun 값 = " + slideGubun);

            mWebView.loadUrl(result + "?CUSTOMER_SEQ=" + custSeq + "&MB_ID=" + mbId + "&CONSULTANT_ID=" + consultant_id + "&MENU=ANALYSIS");
            LogUtil.d(TAG, "이동할 URL = " + result + "?MB_ID=" + mbId + "&CONSULTANT_ID=" + consultant_id + "&CUSTOMER_SEQ=" + custSeq + "&MENU=ANALYSIS&SLIDENUM=" + slideGubun);
//            returnURL = "MB_ID=" + mbId + "&CONSULTANT_ID=" + consultant_id + "&CUSTOMER_SEQ=" + custSeq + "&MENU=ANALYSIS&SLIDENUM=" + slideGubun;
//            mWebView.loadUrl(resultURL + "?MB_ID=" + mbId + "&CONSULTANT_ID=" + consultant_id + "&CUSTOMER_SEQ=" + custSeq + "&MENU=ANALYSIS&SLIDENUM=" + slideGubun);
            removeSaveData();
        } else {
//            String largeComCode = comCode.toUpperCase();
//            Log.d("FactFinder", "현재 URL = " + mWebView.getUrl());
//            if(!mWebView.getUrl().equals(baseURL + "/mobile/jquery_list.go?MB_ID=" + largeComCode + "&CONSULTANT_ID=" + id)) {
//                if (!mWebView.getUrl().equals(baseURL + "/mobile/login.do?mb_id=" + comCode + "&consultant_id=" + id + "&password=" + pw + "&device_gubun=" + device_gubun + "&device_id=" + device_id)) {
//                    mWebView.loadUrl(baseURL + "/mobile/jquery_body.go?MB_ID=" + largeComCode + "&CONSULTANT_ID=" + id);
//                }
//            }
        }

        LogUtil.d(TAG, "strCookie 길이 = " + strCookies.length());
        if (result.length() != 0) {
//            mWebView.postUrl(result, EncodingUtils.getBytes(returnURL, "BASE64"));
        } else {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    nowUrl = mWebView.getUrl();
                }

                @Override
                protected Void doInBackground(Void... voids) {
                    try {

                        Url = new URL(nowUrl);
//                    HttpsURLConnection con = (HttpsURLConnection) Url.openConnection();
                        HttpURLConnection con = (HttpURLConnection) Url.openConnection();
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        con.setDoInput(true);
                        con.setUseCaches(false);
                        con.setDefaultUseCaches(false);

                        strCookies = con.getHeaderField("Set-Cookie");
                        LogUtil.d(TAG, "쿠키값 = " + strCookies);

//                            InputStream is = con.getInputStream();
//                            StringBuilder builder = new StringBuilder();
//                            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//                            String line;
//
//                            while ((line = reader.readLine()) != null) {
//                                builder.append(line + "\n");
//                            }
//                            result = builder.toString();

                    } catch (MalformedURLException | ProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException io) {
                        io.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    LogUtil.d(TAG, "서버 쿠키 값 = " + strCookies);
                }
            }.execute();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class getMarketVersion extends AsyncTask<Void, Void, String> {

        String verSion;
        String marketVersion;
        AlertDialog.Builder mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Document doc = Jsoup
                        .connect(
                                "https://play.google.com/store/apps/details?id=" + getPackageName() )
                        .get();
                Elements Version = doc.select(".htlgb ");

                for (int i = 0; i < 15 ; i++) {
                    marketVersion = Version.get(i).text();
                    if (Pattern.matches("^[0-9]{1}.[0-9]{1}.[0-9]{1}$", marketVersion)) {
                        break;

                    }
                }
                return marketVersion;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            PackageInfo pi = null;
            try {
                pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            try {
                verSion = pi.versionName;
                marketVersion = result;
                LogUtil.d(TAG, "기기 버전 = " + verSion);
                LogUtil.d(TAG, "마켓 버전 = " + marketVersion);

                String versionData[] = verSion.split("[.]");
                String mVersionData[] = marketVersion.split("[.]");
                String strDVersion = "";
                String strMVersion = "";
                int dVersion;
                int mVersion;

                if (!"null".equals(marketVersion)) {
                    for (int i = 0; i < versionData.length; i++) {
                        strDVersion += versionData[i];
                        strMVersion += mVersionData[i];
                    }
                    dVersion = Integer.parseInt(strDVersion);
                    mVersion = Integer.parseInt(strMVersion);
                    LogUtil.d(TAG, "앱 버전 값 = " + dVersion);
                    LogUtil.d(TAG, "마켓 버전 값 = " + mVersion);
                    if (dVersion >= mVersion) {
                        LogUtil.d(TAG, "최신버전입니다.");
//                            Toast.makeText(MainActivity.this, "현재 최신버전입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        new android.app.AlertDialog.Builder(MainViewActivity.this)
                                .setTitle("업데이트")
                                .setMessage("마켓에 새로운 버전이 있습니다. \n 보다 나은 사용을 위해 업데이트 해 주세요")
                                .setPositiveButton("업데이트 하기", new android.app.AlertDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                                        startActivity(mIntent);
                                    }
                                })
                                .setCancelable(false)
                                .create()
                                .show();
                    }
                } else {
                    LogUtil.d(TAG, "마켓 서버 에러...");
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            super.onPostExecute(result);
        }
    }

    public void exitFunction() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AppTheme_PopupOverlay);
        alertDialogBuilder.setTitle("종료");
        alertDialogBuilder.setIcon(R.mipmap.ff_icon);
        alertDialogBuilder.setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    int cnt = 1;

    private class WebViewClientClass extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            if (mWebView.getVisibility() == View.GONE) {
                webHandler.postDelayed(webActivity, 500);
            }
        }
    }

    Runnable webActivity = new Runnable() {
        @Override
        public void run() {
            Animation animation1 = new AlphaAnimation(1, 1);
            animation1.setDuration(1000);
            loading_view.setAnimation(animation1);
            loading_view.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
        }
    };

    /*
     * Layout
     */
    private void setLayout(){
        mWebView = findViewById(R.id.webview);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            nowUrl = mWebView.getUrl();
            LogUtil.d(TAG,"현재 URL = " + nowUrl);
            String largeComCode = comCode.toUpperCase();
            //http://192.168.0.49:8036/mobile/jquery_list.go?MB_ID=GNX&CONSULTANT_ID=guest
            if (nowUrl.equals(baseURL + "/mobile/jquery_list.go?MB_ID=" + largeComCode + "&CONSULTANT_ID=" + id)) {
//                onBackPressed();
            } else {
                mWebView.goBack();
                return true;
            }
//            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
//            super.onBackPressed();
            finishAffinity();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }

    public void removeSaveData() {
        LogUtil.d(TAG, "저장한 데이터 지우기 시작!!!");
        SharedPreferences crawlingStatus = getSharedPreferences("crawlingStatus", MODE_PRIVATE);
        SharedPreferences.Editor editData = crawlingStatus.edit();
        editData.remove("mb_id");
        editData.remove("con_id");
        editData.remove("cust_seq");
        editData.remove("slide_gubun");
        editData.remove("go_url");
        editData.commit();
        LogUtil.d(TAG, "저장한 데이터 지우기 끝!!!");
    }

    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}
