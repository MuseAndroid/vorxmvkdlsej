package kr.co.genexon.factFinder.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.JsonWriter;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.TypeAdapterFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import kr.co.genexon.factFinder.LogUtil;
import kr.co.genexon.factFinder.R;
import kr.co.genexon.factFinder.SelectServerURL;

import static android.content.Context.MODE_PRIVATE;

public class RiseCustomerAgreeDialog extends Dialog implements  View.OnClickListener{

    protected static final String TAG = "FactFinder";

    String custName = "";
    String custHp = "";
    String custHpCom = "";
    String overlapTF = "";
    String custInfo = "";
    String runTime = "";
    String consultantNm = "";
    String baseURL = "";
    String smsSeq = "";
    String signSeq = "";
    String signIdx = "";
    String mb_id = "";
    String const_id = "";
    String savedUserId, savedUserPw;
    String savedComCode, savedComUser, savedComUserPw;

    Button btnCancel;
    Button btnSMS;
    Button btnSignature;

    TextView tvSmsContent;
    TextView tvSignContent;
    TextView tvUserSearchInfo;

    ImageView ivSMS;
    ImageView ivSIGN;

    SelectServerURL ssURL = new SelectServerURL();

    Context marketingContext;

    AQuery mAQuery = new AQuery(this.getContext());

    public RiseCustomerAgreeDialog(Context context) {
        super (context);
        marketingContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_rise_customer_agree);

        btnCancel = findViewById(R.id.btnCancel);
        btnSMS = findViewById(R.id.btnSendSMS);
        btnSignature = findViewById(R.id.btnRiseSignWeb);

        tvSmsContent = findViewById(R.id.tvSmsContent);
        tvSmsContent.bringToFront();
        tvSignContent = findViewById(R.id.tvSignContent);
        tvSignContent.bringToFront();
        tvUserSearchInfo = findViewById(R.id.tvUserSearchInfo);
        tvUserSearchInfo.bringToFront();
        ivSMS = findViewById(R.id.ivSMS);
        ivSMS.bringToFront();
        ivSIGN = findViewById(R.id.ivSIGN);
        ivSIGN.bringToFront();

        SharedPreferences getComPref = getContext().getSharedPreferences("compLogin", MODE_PRIVATE);
        savedComCode = getComPref.getString("ComCode", "");
        savedComUser = getComPref.getString("cUserID", "");
        savedComUserPw = getComPref.getString("cUserPw", "");
        LogUtil.d(TAG, "저장된 회사코드 값 = " + savedComCode);
        LogUtil.d(TAG, "저장된 ID값 = " + savedComUser);
        LogUtil.d(TAG, "저장된 PW값 = " + savedComUserPw);
        if (!savedComCode.equals(null)) {
            mb_id = savedComCode;
            const_id = savedComUser;
        } else {
            mb_id = "NO_MB_ID";
            const_id = savedComUser;
        }

        baseURL = ssURL.getServerURL();

        btnSMS.setOnClickListener(this);
        btnSignature.setOnClickListener(this);

        final WindowManager.LayoutParams params = this.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiseCustomerAgreeDialog.this.dismiss();
            }
        });
    }

    public void setBodyText(Map<String, String> bodyText) {
        custName = bodyText.get("cName");
        custHp = bodyText.get("cHpNum");
        custHpCom = bodyText.get("cHpCom");
        consultantNm = bodyText.get("constNm");
        String smsContent = custName + "(" + custHp + ")" + "님의 핸드폰으로\n고객정보활용동의\n안내 문자를 발송 합니다." ;
        tvSmsContent.setText("");
        final SpannableStringBuilder sp = new SpannableStringBuilder(smsContent);
        if (custName.length() == 3) {
            sp.setSpan(new ForegroundColorSpan(Color.BLACK), 0, custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.setSpan(new StyleSpan(Typeface.BOLD), 0, custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (custHp.length() == 11) {
                sp.setSpan(new ForegroundColorSpan(Color.BLUE), 37, 44, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new StyleSpan(Typeface.BOLD), 37, 44, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (custHp.length() == 10) {
                sp.setSpan(new ForegroundColorSpan(Color.BLUE), 36, 43, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new StyleSpan(Typeface.BOLD), 36, 43, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else if (custName.length() == 4) {
            sp.setSpan(new ForegroundColorSpan(Color.BLACK), 0, custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.setSpan(new StyleSpan(Typeface.BOLD), 0, custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (custHp.length() == 11) {
                sp.setSpan(new ForegroundColorSpan(Color.BLUE), 38, 45, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new StyleSpan(Typeface.BOLD), 38, 45, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (custHp.length() == 10) {
                sp.setSpan(new ForegroundColorSpan(Color.BLUE), 37, 44, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new StyleSpan(Typeface.BOLD), 37, 44, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else if (custName.length() == 2) {
            sp.setSpan(new ForegroundColorSpan(Color.BLACK), 0, custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.setSpan(new StyleSpan(Typeface.BOLD), 0, custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (custHp.length() == 11) {
                sp.setSpan(new ForegroundColorSpan(Color.BLUE), 36, 43, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new StyleSpan(Typeface.BOLD), 36, 43, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (custHp.length() == 10) {
                sp.setSpan(new ForegroundColorSpan(Color.BLUE), 35, 42, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new StyleSpan(Typeface.BOLD), 35, 42, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }


        String signContent = "핸드폰 화면에 출력된 약관을 확인하시고\n" + custName + "님이 직접 서명 하여\n동의 절차를 진행합니다.";
        tvSignContent.setText("");
        final SpannableStringBuilder sp1 = new SpannableStringBuilder(signContent);
        if (custName.length() == 3) {
            sp1.setSpan(new ForegroundColorSpan(Color.BLACK), 21, 22+custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp1.setSpan(new StyleSpan(Typeface.BOLD), 21, 22+custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp1.setSpan(new ForegroundColorSpan(Color.BLUE), 28, 34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp1.setSpan(new StyleSpan(Typeface.BOLD), 28, 34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (custName.length() == 4) {
            sp1.setSpan(new ForegroundColorSpan(Color.BLACK), 21, 22+custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp1.setSpan(new StyleSpan(Typeface.BOLD), 21, 22+custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp1.setSpan(new ForegroundColorSpan(Color.BLUE), 29, 35, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp1.setSpan(new StyleSpan(Typeface.BOLD), 29, 35, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (custName.length() == 2) {
            sp1.setSpan(new ForegroundColorSpan(Color.BLACK), 21, 22+custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp1.setSpan(new StyleSpan(Typeface.BOLD), 21, 22+custName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp1.setSpan(new ForegroundColorSpan(Color.BLUE), 27, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp1.setSpan(new StyleSpan(Typeface.BOLD), 27, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        LogUtil.d(TAG, "고객 이름 = " + custName);
        LogUtil.d(TAG, "고객 핸드폰번호 = " + custHp);
        LogUtil.d(TAG, "고객 핸드폰 통신사 = " + custHpCom);
        LogUtil.d(TAG, "설계사 이름 = " + consultantNm);
        LogUtil.d(TAG, "만들어진 내용1 = " + sp);
        LogUtil.d(TAG, "만들어진 내용1 = " + sp1);

        tvSmsContent.append(sp);
        tvSignContent.append(sp1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSendSMS:
                requestSmsSeq();
                break;
            case R.id.btnRiseSignWeb:
                riseSignWeb();
                break;
        }
    }

    public void requestSmsSeq() {
        try {
            Map<String, String> params1 = new HashMap<String, String>();
            params1.put("CUSTOMER_NAME", custName);
            params1.put("CUST_HP", custHp);
            params1.put("CONSULTANT_NAME", consultantNm);
            params1.put("MOBILE_CO", custHpCom);
            params1.put("MB_ID", mb_id);
            params1.put("CONSULTANT_ID", const_id);
            params1.put("PAGE_GUBUN", "1");
            LogUtil.d(TAG, "파라미터 내용 = " + params1);
            mAQuery.ajax(baseURL + "/sendCustInfoAgreeSMS01.ajax", params1, JSONObject.class, this, "responseToSMS");
        } catch (Exception e) {
            e.printStackTrace();
            viewError1("고객정보동의 문자 시퀀스 요청 에러, 에러내용 : " + e.toString(), mb_id, const_id, custName, custHp, "", custHpCom);
        }
    }

    public void riseSignWeb() {
        try {
            Map<String, String> params2 = new HashMap<String, String>();
            params2.put("CUSTOMER_NAME", custName);
            params2.put("CUST_HP", custHp);
            params2.put("CONSULTANT_NAME", consultantNm);
            params2.put("MOBILE_CO", custHpCom);
            params2.put("MB_ID", mb_id);
            params2.put("CONSULTANT_ID", const_id);
            params2.put("PAGE_GUBUN", "2");
            LogUtil.d(TAG, "파라미터 내용 = " + params2);
            mAQuery.ajax(baseURL + "/sendCustInfoAgreeSMS01.ajax", params2, JSONObject.class, this, "responseToSign");
        } catch (Exception e) {
            e.printStackTrace();
            viewError1("고객정보동의 사인 인덱스 요청 에러, 에러내용 : " + e.toString(), mb_id, const_id, custName, custHp, "", custHpCom);
        }
    }

    public void responseToSMS(String url, JSONObject json, AjaxStatus status) {
        LogUtil.d(TAG, "리턴 결과 = " + json.toString());
//        Log.d("testApp", "상태값 확인 = " + status.toString());
        try {
            String resultJsonString = json.getString("result");
            JSONObject smsResult = null;
            smsResult = new JSONObject(resultJsonString);

            String smsStat = smsResult.getString("status");
            smsSeq = smsResult.getString("seq");
            LogUtil.d(TAG, "상태값 = " + smsStat);
            if (smsStat.equals("OK")) {
                dismiss();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            viewError1("고객정보동의 문자 시퀀스 요청 에러, 에러내용 : " + e.toString(), mb_id, const_id, custName, custHp, "", custHpCom);
        }
    }

    public void responseToSign(String url, JSONObject json, AjaxStatus status) {
        LogUtil.d(TAG, "리턴 결과 = " + json.toString());
//        Log.d("testApp", "상태값 확인 = " + status.toString());
        try {
            String resultJsonString1 = json.getString("result");
            JSONObject signResult = null;
            signResult = new JSONObject(resultJsonString1);
            String signStat = signResult.getString("status");
            signSeq = signResult.getString("seq");
            signIdx = signResult.getString("idx");
            LogUtil.d(TAG, "상태값 = " + signStat);
            if (signStat.equals("OK")) {

                dismiss();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            viewError1("고객정보동의 사인 인덱스 요청 에러, 에러내용 : " + e.toString(), mb_id, const_id, custName, custHp, "", custHpCom);
        }
    }

    public void setUserSearchData(Map<String, String> searchData) {
//        tvUserSearchInfo
        overlapTF = searchData.get("overLap");
        custInfo = searchData.get("custInfo");
        runTime = searchData.get("runTime");

        LogUtil.d(TAG, "넘어온 정보 = " + overlapTF + ", " + custInfo + ", " + runTime);
        if (overlapTF.equals("true")) {
            String runHiscon = "";
            final SpannableStringBuilder sp;
            if (runTime.length() != 0) {
                runHiscon = custInfo + " 고객님은 " + runTime + " 전에\n보장분석을 진행하였습니다.";
                tvUserSearchInfo.setText("");
                sp = new SpannableStringBuilder(runHiscon);
                sp.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, custInfo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new StyleSpan(Typeface.BOLD), 0, custInfo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                int runTimeStart = custInfo.length() + 6;
                int runTimeEnd = runTimeStart + runTime.length();
                sp.setSpan(new ForegroundColorSpan(Color.RED), runTimeStart, runTimeEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new StyleSpan(Typeface.BOLD), runTimeStart, runTimeEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                runHiscon = custInfo + " 고객님은 방금 전에\n보장분석을 진행하였습니다.";
                sp = new SpannableStringBuilder(runHiscon);
                sp.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, custInfo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sp.setSpan(new StyleSpan(Typeface.BOLD), 0, custInfo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            LogUtil.d(TAG, "고객 이름 = " + custInfo);
            LogUtil.d(TAG, "분석 후 경과시간 = " + runTime);
            LogUtil.d(TAG, "만들어진 내용1 = " + sp);

            tvUserSearchInfo.append(sp);

        } else {
            tvUserSearchInfo.setVisibility(View.GONE);
        }
    }

    public String returnSMS() {
        return smsSeq;
    }

    public String returnSignSeq() {
        return signSeq;
    }

    public String returnSignIdx() {
        return signIdx;
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
}
