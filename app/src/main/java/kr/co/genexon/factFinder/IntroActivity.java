package kr.co.genexon.factFinder;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kr.co.genexon.factFinder.Firebase.MyFirebaseInstanceIDService;
import kr.co.genexon.factFinder.Sqlite.DBLoginHelper;

// Build KeyStore - factfinder_key
// master password - 123123
// KeyStore Password - factfinder2017
// KeyStore alias Password - factfinder2018

public class IntroActivity extends BaseActivity {

    protected static final String TAG = "FactFinder";

    Handler h;
    SharedPreferences getUserPref;
    SharedPreferences getComPref;
    String comCode;
    String userId, comId;
    String userPw, comPw;
    String deviceToken;
    String deviceGubun = "and";

    MyFirebaseInstanceIDService firebaseIIService = new MyFirebaseInstanceIDService();

    SelectServerURL ssUrl = new SelectServerURL();

    String baseURL = ssUrl.getServerURL();

    AQuery mAQuery = new AQuery(this);

    String returnValue;
    Intent joinIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);

        getComPref = getSharedPreferences("compLogin", MODE_PRIVATE);
        getUserPref = getSharedPreferences("userLogin", MODE_PRIVATE);

        deviceToken = FirebaseInstanceId.getInstance().getToken();

        joinIntent = new Intent(IntroActivity.this, MainViewActivity.class);

//        final DBLoginHelper dbLoginHelper = new DBLoginHelper(getApplicationContext(), "LOGIN_HISTORY.db", null, 1);
//        JSONObject joinMember = dbLoginHelper.getResult();
//        LogUtil.d("FactFinder", "SQLite에 저장된 회원 정보 = " + joinMember);
        comCode = getComPref.getString("ComCode", "");
        if (!comCode.equals(null)) {
            comId = getComPref.getString("cUserID", "");
            comPw = getComPref.getString("cUserPw", "");
        } else {
            comCode = "NO_MB_ID";
            userId = getUserPref.getString("userID", "");
            userPw = getUserPref.getString("userPw", "");
        }
        h = new Handler();

        LogUtil.d(TAG, "device token = " + deviceToken);

        // 로그인 정보 알아오는 api 선행되어야 함.
        // searchLoginInfo(deviceGubun, deviceToken);
        requestAutoLoginYN(deviceGubun, deviceToken);


    }

    Runnable nActivity = new Runnable() {
        @Override
        public void run() {
            loginAction(returnValue);
        }
    };

    public void requestAutoLoginYN(String deviceGubun, String deviceToken) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("device_gubun", deviceGubun);
            params.put("device_id", deviceToken);
            LogUtil.d(TAG, "파라미터 내용 = " + params);
            mAQuery.ajax(baseURL + "/checkMobileLogin.ajax", params, JSONObject.class, this, "responseLoginCallBack");
            // 로그인 정보 조회 api response 후에 자동로그인 api(이 api) 실행되어야 함
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void responseLoginCallBack(String url, JSONObject json, AjaxStatus status) {
//        LogUtil.d("testApp", "리턴 결과 = " + json.toString());
//        LogUtil.d("testApp", "상태값 확인 = " + status.toString());
        if (json != null) {
            try {
                LogUtil.d(TAG, "리턴 결과 = " + json.toString());
                String result = json.get("auto_login").toString();
                LogUtil.d(TAG, "결과값 = " + result);
                returnValue = result;
                h.postDelayed(nActivity, 2000);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "서버와의 통신이 원활하지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void loginAction(String returnValue) {
        if (!comCode.equals("NO_MB_ID") && returnValue.equals("true")) {
            joinIntent.putExtra("comp", comCode);
            joinIntent.putExtra("id", comId);
            joinIntent.putExtra("pw", comPw);
            joinIntent.putExtra("device_gubun", deviceGubun);
            joinIntent.putExtra("device_id", deviceToken);
            startActivity(joinIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        } else if (comCode.equals("NO_MB_ID") && returnValue.equals("true")) {
            joinIntent.putExtra("comp", comCode);
            joinIntent.putExtra("id", userId);
            joinIntent.putExtra("pw", userPw);
            joinIntent.putExtra("device_gubun", deviceGubun);
            joinIntent.putExtra("device_id", deviceToken);
            startActivity(joinIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        } else if (returnValue.equals("false")) {
            Intent i = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

}
