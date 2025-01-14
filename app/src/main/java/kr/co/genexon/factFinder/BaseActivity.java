package kr.co.genexon.factFinder;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by topgu on 2018-04-05.
 */

public class BaseActivity extends AppCompatActivity {

    protected static final String TAG = "FactFinder";

    private AQuery mAQuery = new AQuery(this);
    private SelectServerURL serverURL = new SelectServerURL();
    String baseURL = serverURL.getServerURL();
    String deviceToken;
    String deviceGubun = "and";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getWindow().getDecorView();
        deviceToken = FirebaseInstanceId.getInstance().getToken();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (view != null) {
                getWindow().setStatusBarColor(Color.parseColor("#3f62ba"));
            }
        } else if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#3f62ba"));
        } else {
            // 킷캣 이하버전 기본 상단바로
        /*SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#204182f7"));*/
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void viewError1(String errString, String mb_id, String const_id, String cust_nm, String mobileNo, String err_gubun, String mobileCo) {
        Map<String, String> errParam = new HashMap<String, String>();
        errParam.put("MB_ID", mb_id);
        errParam.put("CONSULTANT_ID", const_id);
        errParam.put("CUSTOMER_NM", cust_nm);
        errParam.put("MOBILE_NO", mobileNo);
        errParam.put("ERR_GUBUN", err_gubun);
        errParam.put("DEVICE_GUBUN", "android");
        errParam.put("MOBILE_CO", mobileCo);
        errParam.put("ERR_MSG", errString);

        mAQuery.ajax(baseURL + "/insertErrorLog.ajax", errParam, JSONObject.class, this, "responseErrorMsg1");
    }

    public void responseErrorMsg1(String url, JSONObject json, AjaxStatus status) {

        if (json != null) {
            try {
                LogUtil.d(TAG, "리턴 결과 = " + json.toString());
                LogUtil.d(TAG, "상태값 확인 = " + status.toString());
                String result = json.getString("result");
                LogUtil.d(TAG, "결과값 = " + result);
                if (result.equals("OK")) {
//                    LogUtil.d(TAG, "에러메시지 전송 성공");
//                    LogUtil.d(TAG, "팝업 띄우기 위한 핸들러 동작 시작");
//                    Message msg = errHandler.obtainMessage(0);
//                    errHandler.sendMessage(msg);
                } else {
//                    LogUtil.d(TAG, "에러메시지 전송 실패");
//                    LogUtil.d(TAG, "팝업 띄우기 위한 핸들러 동작 시작(서버 전송 실패시에도 보여줘야 함)");
//                    Message msg = errHandler.obtainMessage(0);
//                    errHandler.sendMessage(msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void requestLoginInfo() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("device_id", deviceToken);
        params.put("device_gubun", deviceGubun);

        mAQuery.ajax(baseURL + "/searchLoginInfo.ajax", params, JSONObject.class, this, "responseLoginInfo");
    }

    public void responseLoginInfo(String url, JSONObject json, AjaxStatus status) {
        if (json != null) {
            try {
                LogUtil.d(TAG, "리턴 결과 = " + json.toString());
                String result = json.getString("result");
                String jsonStatus = json.getString("status");
                if (result.equals("OK") && jsonStatus.equals("OK")) {
                    String sComCd = json.getString("comCd");
                    String sUserID = json.getString("userID");
                    LogUtil.d(TAG, "내려온 회사코드 = " + sComCd);
                    LogUtil.d(TAG, "내려온 아이디 = " + sUserID);
                    if (!sComCd.equals("null")) {
                        if (!sComCd.equals("NO_MB_ID")) {
//                        SharedPreferences comPref = getSharedPreferences("compLogin", MODE_PRIVATE);
//                        SharedPreferences.Editor editor1 = comPref.edit();
//                        editor1.putString("ComCode", sComCd);
//                        editor1.putString("cUserID", sUserID);
//                        editor1.commit();

                            ((LoginActivity)LoginActivity.mContext).setUserData(sComCd, sUserID);
                            LogUtil.d(TAG, "기업회원 정보 전달받음");
                        } else {
                        /*SharedPreferences normalPref = getSharedPreferences("userLogin", MODE_PRIVATE);
                        SharedPreferences.Editor editor2 = normalPref.edit();
                        editor2.putString("uComCd", sComCd);
                        editor2.putString("userID", sUserID);
                        editor2.commit();*/

                            ((LoginActivity)LoginActivity.mContext).setUserData(sComCd, sUserID);
                            LogUtil.d(TAG, "개인회원 정보 전달받음");
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "서버와의 통신이 원활하지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
