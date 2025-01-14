package kr.co.genexon.factFinder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import kr.co.genexon.factFinder.Dialog.Credit1Dialog;
import kr.co.genexon.factFinder.Dialog.Credit2Dialog;
import kr.co.genexon.factFinder.Dialog.Credit3Dialog;
import kr.co.genexon.factFinder.Dialog.FirstInfoDialog;
import kr.co.genexon.factFinder.Dialog.InfoContentDialog;
import kr.co.genexon.factFinder.Dialog.PhoneComSelDialog;
import kr.co.genexon.factFinder.Dialog.RiseCustomerAgreeDialog;
import kr.co.genexon.factFinder.Dialog.Tel1Dialog;
import kr.co.genexon.factFinder.Dialog.Tel2Dialog;
import kr.co.genexon.factFinder.Dialog.Tel3Dialog;
import kr.co.genexon.factFinder.Dialog.Tel4Dialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infotech.IFTCrypto.InfoTecCoreCompelete;
import com.infotech.IFTCrypto.iftCoreEnV2;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import com.bigkoo.svprogresshud.SVProgressHUD;

import static android.view.KeyEvent.ACTION_UP;

public class MainActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    protected static final String TAG = "FactFinder";

//    private SVProgressHUD svProgressHUD;

    private static Logger mLogger = Logger.getLogger(MainActivity.class);

    TextView tvMsg;  //infotech 엔진
    TextView tvResult; //infotech 엔진 

    String deviceVersion;
    String storeVersion;

    ScrollView mScrollView1; //infotech 엔진
    ScrollView mScrollView2; //infotech 엔진
    String errMsg; //infotech 엔진
    String pgMessage; //infotech 엔진

    ProgressBar loadingProgress;
    LinearLayout rlLoading;
    ProgressBar loadingProgress1;

    Spinner selectNation;

    NetworkInfo mNetworkState;

    Button btnAll;
    Button customerAgreeBtn;
    Button viewInfo;
    Button mobileSelect;
    EditText userName;
//    EditText juminNo1;
//    EditText juminNo2;
    EditText phoneNumber;
    AppCompatCheckBox supplyInfoChk;
    TextView supplyInfoTitle;
    CheckBox check1;
    TextView check1Title;
    CheckBox check2;
    TextView check2Title;
    CheckBox check3;
    TextView check3Title;
    CheckBox telChk1;
    TextView telChk1Title;
    CheckBox telChk2;
    TextView telChk2Title;
    CheckBox telChk3;
    TextView telChk3Title;
    CheckBox telChk4;
    TextView telChk4Title;
    AppCompatCheckBox info_check1;
    int chkNum = 0;
    TextView info_checkTitle1;
    AppCompatCheckBox allChk;
    TextView allChkTitle;
    String seq;
    Button credit1Btn;
    Button credit2Btn;
    Button credit3Btn;
    Button tel1Btn;
    Button tel2Btn;
    Button tel3Btn;
    Button tel4Btn;

    ScrollView mainScroll;

    public static Context mainContext;

    PhoneComSelDialog dialogResult;

    InfoContentDialog dialogInfo;

//    InfotechEngineModule infotechEM = new InfotechEngineModule(mainContext);

    Credit1Dialog credit1Dialog;
    Credit2Dialog credit2Dialog;
    Credit3Dialog credit3Dialog;

    Tel1Dialog tel1Dialog;
    Tel2Dialog tel2Dialog;
    Tel3Dialog tel3Dialog;
    Tel4Dialog tel4Dialog;

    TextView tvStatus;

    TextView toolbarTitle;

    RiseCustomerAgreeDialog customerAgreeDialog;

    PackageManager pManager;

//    CheckTypesTask task;

    SelectServerURL ssUrl = new SelectServerURL();

    AQuery mAQuery = new AQuery(this);

    String juminNo;
    String returnContent;
    String contentTitle;

    String rOrgCD = "";
    String slideGubun = "";

    boolean btnCheck = true;

    String target_url = "";
    String customer_seq = "";
    String consultant_id = "";
    String customer_name = "";
    String mb_id = "";
    String telNum = "";
    String telComCode = "";
    String orgCD = ""; // 내보험다보여/찾아줌 구분코드
    String consultant_nm = "";
    String juminNo1 = "";
    String juminNo2 = "";
    String katalk_plus = "";
    String smsSeq = "";
    String signSeq = "";
    String signIdx = "";
    String mIdx = "";
    String mPeopleCD = "";
    boolean overlap = false;
    String msg_cust = "";
    String msgTime = "";

//    Timer startTimer = null;
//    Timer startTimer1;
//    TimerTask juminGetTimer;
//    TimerTask warningAlertTimer;

    Handler startHandler;

    LinearLayout th7;

    static int counter = 0;

    static int msgInt = 0;

    boolean marketingCheck = true;

    ProgressDialog pDialog;

//    private BackgroundThread1 mBackgroundThread1;

//    Boolean devYN = true;
////    Boolean devYN = false;

    private long startTime  = 0;
    private long endTime  = 0;

    private Activity activity = this;

    private String baseURL = "";

    private void scrollToBottom() //infotech 엔진
    {
        mScrollView1.post(new Runnable() {
            public void run() {
                mScrollView1.smoothScrollTo(0, tvResult.getBottom());
            }
        });
        mScrollView2.post(new Runnable() {
            public void run() {
                mScrollView2.smoothScrollTo(0, tvMsg.getBottom());
            }
        });
    }

    //infotech 엔진
    InfoTecCoreCompelete mListener = new InfoTecCoreCompelete() {

        @Override
        public void onRequestComplete(boolean status, String jsonData) {

        }
        @Override
        public void onRequestProgress(int status, String progressMsg) {

            LogUtil.d(TAG, "상태 값1 : " + status);

            pgMessage = progressMsg;

            pDialog.setMessage(progressMsg);
//            svProgressHUD.showWithStatus(progressMsg);


//            Toast.makeText(MainActivity.this, progressMsg, Toast.LENGTH_SHORT).show();



            LogUtil.d(TAG, "상태 값2 : " + status);
        }
    };

    /*/**
     * Convert a JSON string to pretty print version
     * @param jsonString
     * @return
     */
    //infotech 엔진
    public static String toPrettyFormat(String jsonString)
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();
        LogUtil.d(TAG, "보여줘 = " + json);
        mLogger.debug("신정원 데이터 = " + json);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    private void hideKeyboard(){
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(mainScroll.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissDialogs();
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("FactFinder");

        SharedPreferences getPernoPref = getSharedPreferences("savePerno", MODE_PRIVATE);
        SharedPreferences.Editor editor1 = getPernoPref.edit();
        editor1.putString("perNo1", "");
        editor1.putString("perNo2", "");
        editor1.commit();

//        svProgressHUD = new SVProgressHUD(this);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("지넥슨 크롤링");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        loadingProgress = findViewById(R.id.loadingProgress);
        loadingProgress.bringToFront();
        loadingProgress.setVisibility(View.GONE);
        rlLoading = findViewById(R.id.rlLoading);
        rlLoading.bringToFront();
        rlLoading.setVisibility(View.GONE);
        loadingProgress1 = findViewById(R.id.loadingProgress1);
        loadingProgress1.bringToFront();

//        mainLayout = findViewById(R.id.mainLayout);

        selectNation = findViewById(R.id.select_national);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.nation_array, android.R.layout.simple_spinner_dropdown_item);
        selectNation.setAdapter(adapter);

        selectNation.setOnItemSelectedListener(this);

//        th7 = findViewById(R.id.th7);

        tvResult = findViewById(R.id.tvResult);
        tvMsg = findViewById(R.id.tvMsg);
//        mScrollView1 = findViewById(R.id.SCROLLER_ID1);
//        mScrollView2 = findViewById(R.id.SCROLLER_ID2);
        viewInfo = findViewById(R.id.viewInfo);
        supplyInfoChk = findViewById(R.id.supplyInfoChk);
        supplyInfoTitle = findViewById(R.id.supplyInfoTitle);
//        check1 = (CheckBox) findViewById(R.id.check1);
//        check1Title = (TextView) findViewById(R.id.check1Title);
//        check2 = (CheckBox) findViewById(R.id.check2);
//        check2Title = (TextView) findViewById(R.id.check2Title);
//        check3 = (CheckBox) findViewById(R.id.check3);
//        check3Title = (TextView) findViewById(R.id.check3Title);
//        telChk1 = (CheckBox) findViewById(R.id.telCheck1);
//        telChk1Title = (TextView) findViewById(R.id.telCheck1Title);
//        telChk2 = (CheckBox) findViewById(R.id.telCheck2);
//        telChk2Title = (TextView) findViewById(R.id.telCheck2Title);
//        telChk3 = (CheckBox) findViewById(R.id.telCheck3);
//        telChk3Title = (TextView) findViewById(R.id.telCheck3Title);
//        telChk4 = (CheckBox) findViewById(R.id.telCheck4);
//        telChk4Title = (TextView) findViewById(R.id.telCheck4Title);
        info_check1 = findViewById(R.id.info_check1);
        info_checkTitle1 = findViewById(R.id.info_check1Title);
//        credit1Btn = (Button) findViewById(R.id.creditBtn1);
//        credit2Btn = (Button) findViewById(R.id.creditBtn2);
//        credit3Btn = (Button) findViewById(R.id.creditBtn3);
//        tel1Btn = (Button) findViewById(R.id.tel_docBtn1);
//        tel2Btn = (Button) findViewById(R.id.tel_docBtn2);
//        tel3Btn = (Button) findViewById(R.id.tel_docBtn3);
//        tel4Btn = (Button) findViewById(R.id.tel_docBtn4);
        allChk = findViewById(R.id.allChk);
        allChkTitle = findViewById(R.id.allChkTitle);
        mainScroll = findViewById(R.id.main_scroll);
        userName = findViewById(R.id.userName);
        phoneNumber = findViewById(R.id.phoneNumber);
        customerAgreeBtn = findViewById(R.id.customerAgreeBtn);
        mobileSelect = findViewById(R.id.mobileSelect);
        btnAll = findViewById(R.id.btnAll);

        tvStatus = findViewById(R.id.tvStatus);

        dialogResult = new PhoneComSelDialog(this);
        dialogInfo = new InfoContentDialog(this);

//        credit1Dialog = new Credit1Dialog(this);
//        credit2Dialog = new Credit2Dialog(this);
//        credit3Dialog = new Credit3Dialog(this);
//
//        tel1Dialog = new Tel1Dialog(this);
//        tel2Dialog = new Tel2Dialog(this);
//        tel3Dialog = new Tel3Dialog(this);
//        tel4Dialog = new Tel4Dialog(this);

        mainScroll.setTop(0);

        mainContext = this;

        Intent intent = getIntent();

        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
//            Uri uri = intent.getData();
//            mb_id = uri.getQueryParameter("mb_id");
//            consultant_id = uri.getQueryParameter("consultant_id");
//            katalk_plus = uri.getQueryParameter("plus_friend");
//            if (!uri.getQueryParameter("customer_name").equals("")) {
//                customer_name = uri.getQueryParameter("customer_name");
//                userName.setText(customer_name);
//                userName.setFocusable(false);
//                userName.setClickable(false);
//            } else {
//                //customer_name = "";
//            }
//            if (!uri.getQueryParameter("mobile").equals("")) {
//                telNum = uri.getQueryParameter("mobile");
//                phoneNumber.setText(telNum);
//            } else {
//                //telNum = "";
//            }
//
//            Log.d("testApp", "mb_id = " + mb_id);
//            Log.d("testApp", "consultant_id = " + consultant_id);
//            Log.d("testApp", "kakao_plus = " + katalk_plus);
        } else {

            consultant_id = intent.getStringExtra("consultant_id");
            LogUtil.d(TAG, "설계사 ID = " + consultant_id);
            if (!intent.getStringExtra("customer_name").equals("")) {
                customer_name = intent.getStringExtra("customer_name");
                userName.setText(customer_name);
                userName.setFocusable(false);
                userName.setClickable(false);
                mobileSelect.setEnabled(false);
                phoneNumber.setFocusable(false);
                phoneNumber.setClickable(false);
                customer_seq = intent.getStringExtra("cust_seq");
            } else {
                customer_name = "";
            }
            mb_id = intent.getStringExtra("mb_id");
            LogUtil.d(TAG, "회사 ID = " + mb_id);
            if (!intent.getStringExtra("mobile").equals("")) {
                telNum = intent.getStringExtra("mobile");
                phoneNumber.setText(telNum);
            } else {
                telNum = "";
            }
            if (!intent.getStringExtra("userSsn1").equals("")) {
                juminNo1 = intent.getStringExtra("userSsn1");
            } else {
                juminNo1 = "";
            }

            if (!intent.getStringExtra("userSsn2").equals("")) {
                juminNo2 = intent.getStringExtra("userSsn2");
            } else {
                juminNo2 = "";
            }

            if (!intent.getStringExtra("constNm").equals("")) {
                consultant_nm = intent.getStringExtra("constNm");
            } else {
                consultant_nm = "";
            }

            if (!intent.getStringExtra("mobileco").equals("")) {
                telComCode = intent.getStringExtra("mobileco");
            } else {
                telComCode = "null"; // 통신사 SKT로 초기화
            }

            orgCD = intent.getStringExtra("orgCD");
            LogUtil.d(TAG, "구분코드 = " + orgCD);
            if (orgCD.equals("credit4u")) {
                riseInfoPopup();
            } else {
                customer_seq = intent.getStringExtra("cust_seq");
                LogUtil.d(TAG, "고객seq = " + customer_seq);
                tvStatus.setText(userName.getText() + "님이 동의하셨습니다.");
                customerAgreeBtn.setVisibility(View.GONE);
                selectNation.setEnabled(false);
                allChk.setChecked(true);
                supplyInfoChk.setChecked(true);
                info_check1.setChecked(true);
                btnAll.setText("내보험 찾기");
            }

            LogUtil.d(TAG, "consultant_id = " + consultant_id);
            mLogger.debug("consultant_id = " + consultant_id);
            LogUtil.d(TAG, "customer_name = " + customer_name);
            mLogger.debug("customer_name = " + customer_name);
            LogUtil.d(TAG, "juminNo1 = " + juminNo1);
            mLogger.debug("juminNo1 = " + juminNo1);
            LogUtil.d(TAG, "juminNo2 = " + juminNo2);
            mLogger.debug("juminNo2 = " + juminNo2);
            LogUtil.d(TAG, "mb_id = " + mb_id);
            mLogger.debug("mb_id = " + mb_id);
            LogUtil.d(TAG, "mobile = " + telNum);
            mLogger.debug("mobile = " + telNum);
            LogUtil.d(TAG, "mobileco = " + telComCode);
            mLogger.debug("mobileco = " + telComCode);
            LogUtil.d(TAG, "orgCD = " + orgCD);
            mLogger.debug("orgCD = " + orgCD);
        }

        baseURL = ssUrl.getServerURL(); // F.F의 경우 SelectServerURL 클래스에서 상용/개발을 구분하여 getServerURL을 통해 서버를 내려주는 방식으로 변경

        if (!telComCode.equals("null")) {
            if (telComCode.equals("KTF")) {
                mobileSelect.setText("KT");
            } else if (telComCode.equals("LGT")) {
                mobileSelect.setText("LGU+");
            } else if (telComCode.equals("SKM")) {
                mobileSelect.setText("SK알뜰폰");
            } else if (telComCode.equals("KTM")) {
                mobileSelect.setText("KT알뜰폰");
            } else if (telComCode.equals("LGM")) {
                mobileSelect.setText("LG알뜰폰");
            } else {
                mobileSelect.setText("SKT");
            }

        }

        mobileSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                risePopup();
            }
        });
        viewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                risePopup1();
            }
        });
        customerAgreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString() != "" && userName.getText().toString().length() > 1) {
                    if (phoneNumber.getText().toString() != "" && phoneNumber.getText().toString().length() > 1) {
                        if (isHp(phoneNumber.getText().toString())) {
                            if (!telComCode.equals("null")) {
                                if (marketingCheck) {
                                    requestOverlapAnalysis();
                                } else {
                                    Toast.makeText(MainActivity.this, "마케팅 동의 진행 중입니다. 완료된 후 다시 진행해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "통신사를 선택해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity, "전화번호를 정확히 입력해주세요", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "전화번호를 입력해 주세요.",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "사용자 이름을 입력해 주세요.",Toast.LENGTH_LONG).show();
                }
            }
        });

//        credit1Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                riseCredit1Popup();
//            }
//        });

//        juminNo1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (juminNo1.getText().length() == 6) {
//                    juminNo2.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//
//        juminNo2.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (juminNo2.getText().length() == 7) {
//                    phoneNumber.requestFocus();
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (phoneNumber.getText().length() == 11) {
                    hideKeyboard();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



//        allChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.d("factfinder", "체크결과 : " + isChecked);
//                if (buttonView.isChecked() != false) {
//                    supplyInfoChk.setChecked(true);
////                    check1.setChecked(true);
////                    check2.setChecked(true);
////                    check3.setChecked(true);
////                    telChk1.setChecked(true);
////                    telChk2.setChecked(true);
////                    telChk3.setChecked(true);
////                    telChk4.setChecked(true);
//                    info_check1.setChecked(true);
//                } else {
//                    supplyInfoChk.setChecked(false);
////                    check1.setChecked(false);
////                    check2.setChecked(false);
////                    check3.setChecked(false);
////                    telChk1.setChecked(false);
////                    telChk2.setChecked(false);
////                    telChk3.setChecked(false);
////                    telChk4.setChecked(false);
//                    info_check1.setChecked(false);
//                }
//            }
//        });

        allChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, "체크결과 : " + allChk.isChecked());
                if (allChk.isChecked() != false) {
                    supplyInfoChk.setChecked(true);
//                    check1.setChecked(true);
//                    check2.setChecked(true);
//                    check3.setChecked(true);
//                    telChk1.setChecked(true);
//                    telChk2.setChecked(true);
//                    telChk3.setChecked(true);
//                    telChk4.setChecked(true);
                    info_check1.setChecked(true);
                } else {
                    supplyInfoChk.setChecked(false);
//                    check1.setChecked(false);
//                    check2.setChecked(false);
//                    check3.setChecked(false);
//                    telChk1.setChecked(false);
//                    telChk2.setChecked(false);
//                    telChk3.setChecked(false);
//                    telChk4.setChecked(false);
                    info_check1.setChecked(false);
                }
            }
        });

        allChkTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allChk.isChecked() == false) {
                    allChk.setChecked(true);
                    supplyInfoChk.setChecked(true);
//                    check1.setChecked(true);
//                    check2.setChecked(true);
//                    check3.setChecked(true);
//                    telChk1.setChecked(true);
//                    telChk2.setChecked(true);
//                    telChk3.setChecked(true);
//                    telChk4.setChecked(true);
                    info_check1.setChecked(true);
                } else {
                    allChk.setChecked(false);
                    supplyInfoChk.setChecked(false);
//                    check1.setChecked(false);
//                    check2.setChecked(false);
//                    check3.setChecked(false);
//                    telChk1.setChecked(false);
//                    telChk2.setChecked(false);
//                    telChk3.setChecked(false);
//                    telChk4.setChecked(false);
                    info_check1.setChecked(false);
                }
            }
        });

        supplyInfoChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (supplyInfoChk.isChecked() != false) {
                    if (info_check1.isChecked() == true) {
                        allChk.setChecked(true);
                    }
                } else {
                    supplyInfoChk.setChecked(false);
                    allChk.setChecked(false);
                }
            }
        });

        supplyInfoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (supplyInfoChk.isChecked() == false) {
                    supplyInfoChk.setChecked(true);
                    if (info_check1.isChecked() == true) {
                        allChk.setChecked(true);
                    }
                } else {
                    supplyInfoChk.setChecked(false);
                    allChk.setChecked(false);
                }
            }
        });

//        check1Title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (check1.isChecked() == false) {
//                    check1.setChecked(true);
//                    if (supplyInfoChk.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true){
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    check1.setChecked(false);
//                    allChk.setChecked(false);
//                }
//            }
//        });
//
//        check2Title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (check2.isChecked() == false) {
//                    check2.setChecked(true);
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true){
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    check2.setChecked(false);
//                    allChk.setChecked(false);
//                }
//            }
//        });
//
//        check3Title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (check3.isChecked() == false) {
//                    check3.setChecked(true);
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true){
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    check3.setChecked(false);
//                    allChk.setChecked(false);
//                }
//            }
//        });
//
//        telChk1Title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (telChk1.isChecked() == false) {
//                    telChk1.setChecked(true);
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true){
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    telChk1.setChecked(false);
//                    allChk.setChecked(false);
//                }
//            }
//        });
//
//        telChk2Title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (telChk2.isChecked() == false) {
//                    telChk2.setChecked(true);
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true){
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    telChk2.setChecked(false);
//                    allChk.setChecked(false);
//                }
//            }
//        });
//
//        telChk3Title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (telChk3.isChecked() == false) {
//                    telChk3.setChecked(true);
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true){
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    telChk3.setChecked(false);
//                    allChk.setChecked(false);
//                }
//            }
//        });
//
//        telChk4Title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (telChk4.isChecked() == false) {
//                    telChk4.setChecked(true);
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            info_check1.isChecked() == true){
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    telChk4.setChecked(false);
//                    allChk.setChecked(false);
//                }
//            }
//        });

        info_check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info_check1.isChecked() != false) {
                    if (supplyInfoChk.isChecked() == true){
                        allChk.setChecked(true);
                    }
                } else {
                    info_check1.setChecked(false);
                    allChk.setChecked(false);
                }
            }
        });

        info_checkTitle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info_check1.isChecked() == false) {
                    info_check1.setChecked(true);
                    if (supplyInfoChk.isChecked() == true){
                        allChk.setChecked(true);
                    }
                } else {
                    info_check1.setChecked(false);
                    allChk.setChecked(false);
                }
            }
        });

        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tvMsg.setText("");
//                tvResult.setText("");
                LogUtil.d(TAG, "내/외국인 선택 여부 = " + mPeopleCD);
                mLogger.debug("내/외국인 선택 여부 = " + mPeopleCD);
                if (orgCD.equals("credit4u")) {
                    SharedPreferences getPernoPref = getSharedPreferences("savePerno", MODE_PRIVATE);

                    juminNo1 = getPernoPref.getString("perNo1", "");
                    juminNo2 = getPernoPref.getString("perNo2", "");
                }

                if (userName.getText().toString() == "" || userName.getText().toString().length() <= 1) {
                    Toast.makeText(getBaseContext(), "사용자 이름을 입력해 주세요.",Toast.LENGTH_LONG).show();
                    return;
                }

                else if (juminNo1.length() != 6) {
                    Toast.makeText(getBaseContext(), "고객정보활용동의를 진행해주세요.",Toast.LENGTH_LONG).show();
                    return;
                }

                else if (mobileSelect.toString().length() == 0 || telComCode.equals("null")) {
                    Toast.makeText(getBaseContext(), "통신사를 선택 해주세요.",Toast.LENGTH_LONG).show();
                    return;
                }

                else if (phoneNumber.getText().toString() == "") {
                    Toast.makeText(getBaseContext(), "전화번호를 입력해 주세요.",Toast.LENGTH_LONG).show();
                    if (!isHp(phoneNumber.getText().toString())) {
                        Toast.makeText(activity, "전화번호를 정확히 입력해주세요", Toast.LENGTH_LONG).show();
                    }
                    return;
                }

                if (supplyInfoChk.isChecked() == false) {
                    Toast.makeText(getBaseContext(), "정보활용 참조사항을 확인해주세요.",Toast.LENGTH_LONG).show();
                    return;
                }
// else if (check1.isChecked() == false) {
//                    Toast.makeText(getBaseContext(), "(필수) 개인정보활용에 동의해주세요.",Toast.LENGTH_LONG).show();
//                    return;
//                } else if (check2.isChecked() == false) {
//                    Toast.makeText(getBaseContext(), "(필수) 고유식별정보처리에 동의해주세요.",Toast.LENGTH_LONG).show();
//                    return;
//                } else if (check3.isChecked() == false) {
//                    Toast.makeText(getBaseContext(), "(필수) 내보험다보여 이용약관에 동의해주세요.",Toast.LENGTH_LONG).show();
//                    return;
//                } else if (telChk1.isChecked() == false) {
//                    Toast.makeText(getBaseContext(), "통신사 개인정보이용에 동의해주세요.",Toast.LENGTH_LONG).show();
//                    return;
//                } else if (telChk2.isChecked() == false) {
//                    Toast.makeText(getBaseContext(), "통신사 서비스 이용약관에 동의해주세요.",Toast.LENGTH_LONG).show();
//                    return;
//                } else if (telChk3.isChecked() == false) {
//                    Toast.makeText(getBaseContext(), "통신사 고유식별정보처리에 동의해주세요.",Toast.LENGTH_LONG).show();
//                    return;
//                } else if (telChk4.isChecked() == false) {
//                    Toast.makeText(getBaseContext(), "통신사 이용약관에 동의해주세요.",Toast.LENGTH_LONG).show();
//                    return;
//                }
                else if (info_check1.isChecked() == false) {
                    Toast.makeText(getBaseContext(), "개인정보 활용에 동의해주세요.", Toast.LENGTH_LONG).show();
                } else {
                    //infotech 엔진
                    if (btnCheck == true) {
                        timeThread("");
                        mainScroll.setBackgroundColor(Color.argb(50, 0, 0, 0));
//                        loadingProgress.setVisibility(View.VISIBLE);
//                        loadingProgress.bringToFront();
                        rlLoading.setVisibility(View.VISIBLE);
                        btnCheck = false;
                        String uName = userName.getText().toString();
//                        String uBirth = juminNo1.getText().toString();
//                        String uIdent = juminNo2.getText().toString();
                        String mobileCom = mobileSelect.getText().toString();
                        String mobileNo = phoneNumber.getText().toString();
                        //infotechEM.executeEngine(uName, uBirth, uIdent, mobileCom, mobileNo);
//                        try {
//                            infotechEM.executeEngine(uName, uBirth, uIdent, mobileCom, mobileNo);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Toast.makeText(getBaseContext(), "정보를 조회중입니다.", Toast.LENGTH_SHORT).show();
//                    }

                        String encodedString = "";
                        try {
                            String searchedString = userName.getText().toString();
                            encodedString = URLEncoder.encode(searchedString, "euc-kr");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        juminNo = juminNo1 + juminNo2;
                        String sex = juminNo.substring(6, 7);
                        String gender = "1";

                        if (sex.equals("1") || sex.equals("3")) {
                            gender = "1";
                        } else if (sex.equals("2") || sex.equals("4")) {
                            gender = "0";
                        } else {
                            gender = "1";
                        }

                        final JSONObject obj1 = new JSONObject();
                        try {
                            obj1.put("orgCd", orgCD);
                            obj1.put("appCd", "factfinder");
                            obj1.put("svcCd", "B0001,B1001,B1011,B2001,B2011,B3001");
                            obj1.put("userName", uName);
                            obj1.put("userNameEUCKR", encodedString);
                            obj1.put("ssnFront", juminNo1);
                            obj1.put("ssnBack", juminNo2);
                            obj1.put("mobileCo", telComCode);
                            obj1.put("gender", gender);
                            obj1.put("nationalInfo", mPeopleCD);
                            obj1.put("mobileNo", mobileNo);
                        } catch (JSONException e) {
                            // e.printStackTrace();
                        }

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final long startTime = System.currentTimeMillis();

                                iftCoreEnV2 ss1 = new iftCoreEnV2(MainActivity.this, mListener);
                                hideKeyboard();
                                String response = "";
                                //                        ss1.init(MainActivity.this, mListener);
                                response = ss1.startEngine(obj1.toString());
                                final long endTime = System.currentTimeMillis();
                                final String finalResponse = response;
                                requestBohumContent(finalResponse);
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        String r = tvResult.getText().toString() + "\r\n";
                                        r += toPrettyFormat(finalResponse);
                                        LogUtil.d(TAG, "진행상태 = " + r);
                                        r += "\r\n보험다보여 조회 완료=================================";
                                        mLogger.debug("진행상태 = " + r);
                                        tvResult.setText(r);
                                    }
                                });
                            }
                        }).start();
                    } else {
                        Toast.makeText(getBaseContext(), "정보를 조회중입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        juminGetTimer = new TimerTask() {
//            @Override
//            public void run() {
//                if (counter < 36) {
//                    Log.d("factFinder", "인터벌 시작..." + String.valueOf(counter));
//                    getJuminRequest();
//                    counter++;
//                } else {
//                    riseTimeWarningAlert();
//                }
//            }
//        };

        startHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                counter++;

                LogUtil.d(TAG, "핸들러 msg값 = " + msgInt);
                mLogger.debug("고객동의 핸들러 msg값 = " + msgInt);
                LogUtil.d(TAG, "카운터 값 = " + counter);
                mLogger.debug("고객동의 카운터 값 = " + counter);

                getJuminRequest();

                if (counter > 36) {
                    riseTimeWarningAlert();
                    startHandler.removeMessages(msgInt);
                } else {
                    startHandler.sendEmptyMessageDelayed(msgInt, 5000);
                }
            }
        };
    }

    public void timeThread(String msg) {
        pDialog = new ProgressDialog(this);
        pDialog.setTitle("");
        pDialog.setMessage(msg);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
//        svProgressHUD.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(1500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LogUtil.d(TAG, "쓰레드 종료!!!");
                    mLogger.debug("크롤링 과정 프로그레스 쓰레드 종료!!!");
                }
                pDialog.dismiss();
//                svProgressHUD.dismiss();
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        removeSaveData();

        if (!btnCheck) {
            btnCheck = true;
        }

        new getMarketVersion().execute();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case ACTION_UP:
                LogUtil.d(TAG, "터치된다!!!");
                hideKeyboard();
                break;
        }
        return super.dispatchTouchEvent(ev);
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
                viewError1("앱 업데이트 확인부 에러, 에러내용 : " + e.toString(), mb_id, consultant_id, customer_name, telNum, "", telComCode);
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
                    Log.d(TAG, "앱 버전 값 = " + dVersion);
                    Log.d(TAG, "마켓 버전 값 = " + mVersion);
                    if (dVersion >= mVersion) {
                        Log.d(TAG, "최신버전입니다.");
//                            Toast.makeText(MainActivity.this, "현재 최신버전입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        new android.app.AlertDialog.Builder(MainActivity.this)
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
                viewError1(e.toString(), mb_id, consultant_id, customer_name, telNum, "", telComCode);
            }

            super.onPostExecute(result);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!btnCheck) {
            btnCheck = true;
        }
        removeSaveData();
        startHandler.removeMessages(msgInt);
        Toast.makeText(mainContext, "보장분석 진행을 취소합니다.\n이전 화면으로 이동합니다", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void dismissDialogs() {
        LogUtil.d(TAG, "다이얼로그 없애기 시작");
        if (dialogResult != null && dialogResult.isShowing()) {
            dialogResult.dismiss();
        }

        if (dialogInfo != null && dialogInfo.isShowing()) {
            dialogInfo.dismiss();
        }

        if (customerAgreeDialog != null && customerAgreeDialog.isShowing()) {
            customerAgreeDialog.dismiss();
        }

        LogUtil.d(TAG, "다이얼로그 없애기 끝");
    }

    private static boolean isHp(String hpNum) {
        String regex = "^010([0-9]{7,8})$";
        String regex1 = "^01([1|6|7|8|9])([0-9]{7,8})$";
        if (hpNum.length() == 11 || hpNum.substring(0,3) == "010") {
            boolean h = Pattern.matches(regex, hpNum);
            return h;
        } else if (hpNum.length() >= 10 || hpNum.substring(0,3) != "010") {
            boolean h = Pattern.matches(regex1, hpNum);
            return h;
        } else {
            return false;
        }
    }


    public void onChangeChkBtn(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.supplyInfoChk:
                if (checked) {
                    if (info_check1.isChecked() == true){
                        allChk.setChecked(true);
                    }
                } else {
                    allChk.setChecked(false);
                }
                break;
            case R.id.supplyInfoTitle:
                if (checked) {
                    if (info_check1.isChecked() == true){
                        allChk.setChecked(true);
                    }
                } else {
                    allChk.setChecked(false);
                }
                break;
//            case R.id.check1:
//                if (checked) {
//                    if (supplyInfoChk.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true)
//                    {
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    allChk.setChecked(false);
//                }
//                break;
//            case R.id.check2:
//                if (checked) {
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true)
//                    {
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    allChk.setChecked(false);
//                }
//                break;
//            case R.id.check3:
//                if (checked) {
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true)
//                    {
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    allChk.setChecked(false);
//                }
//                break;
//            case R.id.telCheck1:
//                if (checked) {
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true)
//                    {
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    allChk.setChecked(false);
//                }
//                break;
//            case R.id.telCheck2:
//                if (checked) {
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true)
//                    {
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    allChk.setChecked(false);
//                }
//                break;
//            case R.id.telCheck3:
//                if (checked) {
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk4.isChecked() == true &&
//                            info_check1.isChecked() == true)
//                    {
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    allChk.setChecked(false);
//                }
//                break;
//            case R.id.telCheck4:
//                if (checked) {
//                    if (supplyInfoChk.isChecked() == true &&
//                            check1.isChecked() == true &&
//                            check2.isChecked() == true &&
//                            check3.isChecked() == true &&
//                            telChk1.isChecked() == true &&
//                            telChk2.isChecked() == true &&
//                            telChk3.isChecked() == true &&
//                            info_check1.isChecked() == true)
//                    {
//                        allChk.setChecked(true);
//                    }
//                } else {
//                    allChk.setChecked(false);
//                }
//                break;
            case R.id.info_check1:
                if (checked) {
                    if (supplyInfoChk.isChecked() == true) {
                        allChk.setChecked(true);
                    }
                } else {
                    allChk.setChecked(false);
                }
                break;
            case R.id.info_check1Title:
                if (checked) {
                    if (supplyInfoChk.isChecked() == true) {
                        allChk.setChecked(true);
                    }
                } else {
                    allChk.setChecked(false);
                }
                break;
            case R.id.allChk:
                if (checked) {
                    supplyInfoChk.setChecked(true);
//                    check1.setChecked(true);
//                    check2.setChecked(true);
//                    check3.setChecked(true);
//                    telChk1.setChecked(true);
//                    telChk2.setChecked(true);
//                    telChk3.setChecked(true);
//                    telChk4.setChecked(true);
                    info_check1.setChecked(true);
                } else {
                    supplyInfoChk.setChecked(false);
//                    check1.setChecked(false);
//                    check2.setChecked(false);
//                    check3.setChecked(false);
//                    telChk1.setChecked(false);
//                    telChk2.setChecked(false);
//                    telChk3.setChecked(false);
//                    telChk4.setChecked(false);
                    info_check1.setChecked(false);
                }
                break;
            case R.id.allChkTitle:
                if (checked) {
                    supplyInfoChk.setChecked(true);
//                    check1.setChecked(true);
//                    check2.setChecked(true);
//                    check3.setChecked(true);
//                    telChk1.setChecked(true);
//                    telChk2.setChecked(true);
//                    telChk3.setChecked(true);
//                    telChk4.setChecked(true);
                    info_check1.setChecked(true);
                } else {
                    supplyInfoChk.setChecked(false);
//                    check1.setChecked(false);
//                    check2.setChecked(false);
//                    check3.setChecked(false);
//                    telChk1.setChecked(false);
//                    telChk2.setChecked(false);
//                    telChk3.setChecked(false);
//                    telChk4.setChecked(false);
                    info_check1.setChecked(false);
                }
                break;
        }
    }

    public void creditPopup(View view) {
        switch (view.getId()) {
//            case R.id.creditBtn1:
//                riseCredit1Popup();
//                break;
//            case R.id.creditBtn2:
//                riseCredit2Popup();
//                break;
//            case R.id.creditBtn3:
//                riseCredit3Popup();
//                break;
//            case R.id.tel_docBtn1:
//                riseTelDocPopup1();
//                break;
//            case R.id.tel_docBtn2:
//                riseTelDocPopup2();
//                break;
//            case R.id.tel_docBtn3:
//                riseTelDocPopup3();
//                break;
//            case R.id.tel_docBtn4:
//                riseTelDocPopup4();
//                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                LogUtil.d(TAG, "내국인 선택");
                mPeopleCD = "0";
                mLogger.debug("내국인선택" + mPeopleCD);
                break;
            case 1:
                LogUtil.d(TAG, "외국인 선택");
                mPeopleCD = "1";
                mLogger.debug("외국인선택" + mPeopleCD);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectNation.setSelection(0);
    }

    public void risePopup() {
        final PhoneComSelDialog dialogWindow = new PhoneComSelDialog(this);

        dialogWindow.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

            }
        });
        dialogWindow.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
//                mobileSelect.setText(dialogWindow.getComText());
                telComCode = dialogWindow.getComText();

                LogUtil.d(TAG, "통신사 선택 값 = " + telComCode);
                mLogger.debug("통신사 선택 값 = " + telComCode);

                if (telComCode.equals("null")) {
                    mobileSelect.setText("선택");
                } else {
                    if (telComCode.equals("SKT")) {
                        mobileSelect.setText("SKT");
                    } else if (telComCode.equals("KTF")) {
                        mobileSelect.setText("KT");
                    } else if (telComCode.equals("LGT")) {
                        mobileSelect.setText("LGU+");
                    } else if (telComCode.equals("SKM")) {
                        mobileSelect.setText("SK알뜰폰");
                    } else if (telComCode.equals("KTM")) {
                        mobileSelect.setText("KT알뜰폰");
                    } else if (telComCode.equals("LGM")) {
                        mobileSelect.setText("LG알뜰폰");
                    } else {
                        mobileSelect.setText("선택");
                    }
                }
            }
        });

        dialogWindow.show();
    }

    public void riseInfoPopup() {
        final FirstInfoDialog firstDialog = new FirstInfoDialog(this);

        firstDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

            }
        });

        firstDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                firstDialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = firstDialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        firstDialog.show();
        firstDialog.getWindow().setAttributes(lp);
    }

    public void riseCustomerAgreePopup() {

        final RiseCustomerAgreeDialog agreeDialog = new RiseCustomerAgreeDialog(this);
        String cName = userName.getText().toString();
        String cHpNum = phoneNumber.getText().toString();
        String cHpCom = telComCode;
        String constNm = consultant_nm;

        final Map<String, String> datas = new HashMap<String, String>();
        datas.put("cName", cName);
        datas.put("cHpNum", cHpNum);
        datas.put("cHpCom", cHpCom);
        datas.put("constNm", constNm);

        final Map<String, String> overlapData = new HashMap<String, String>();
        overlapData.put("overLap", String.valueOf(overlap));
        overlapData.put("custInfo", msg_cust);
        overlapData.put("runTime", msgTime);

        agreeDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
//                loadingProgress.setVisibility(View.VISIBLE);
//                loadingProgress.bringToFront();
                rlLoading.setVisibility(View.VISIBLE);
                agreeDialog.setBodyText(datas);
                agreeDialog.setUserSearchData(overlapData);
            }
        });

        agreeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onDismiss(DialogInterface dialog) {
                smsSeq = agreeDialog.returnSMS();
                signSeq = agreeDialog.returnSignSeq();
                signIdx = agreeDialog.returnSignIdx();

                if (smsSeq.length() != 0) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("")
                            .setMessage("문자를 전송했습니다.")
                            .setPositiveButton("확인", new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (smsSeq.length() != 0 || signSeq.length() != 0 && signIdx.length() != 0) {

                                        if (smsSeq.length() != 0) {
                                            seq = smsSeq;
                                        } else {
                                            seq = signSeq;
                                        }

                                        if (signIdx.length() != 0) {
                                            mIdx = signIdx;
                                        }
//                                            requestJuminData();
                                        SharedPreferences getPernoPref = getSharedPreferences("savePerno", MODE_PRIVATE);
                                        SharedPreferences.Editor editor1 = getPernoPref.edit();
                                        editor1.putString("perNo1", "");
                                        editor1.putString("perNo2", "");
                                        editor1.commit();
                                        tvStatus.setText("고객동의 대기중입니다");
                                        msgInt++;
                                        startHandler.sendEmptyMessage(msgInt);
                                        marketingCheck = false;

                                    }
                                    dialog.cancel();
                                    riseAgreeProcAlert();
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }
//                else if (smsSeq.length() == 0 && signSeq.length() == 0) {
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
//                    alertDialogBuilder.setTitle("");
//                    alertDialogBuilder.setMessage("서버에 문제가 발생했습니다.\n잠시 후 다시 시도해주세요")
//                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.setCanceledOnTouchOutside(false);
//                    alertDialog.setCancelable(false);
//                    alertDialog.show();
//                }

                if (signSeq.length() != 0) {
                    Intent signIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseURL + "/personalInfoAgree.go?SEQ=" + signSeq + "&IDX=" + signIdx + "&PAGE_GUBUN=2"));
                    startActivity(signIntent);
                    if (smsSeq.length() != 0 || signSeq.length() != 0 && signIdx.length() != 0) {
                        if (smsSeq.length() != 0) {
                            seq = smsSeq;
                        } else {
                            seq = signSeq;
                        }

                        if (signIdx.length() != 0) {
                            mIdx = signIdx;
                        }
                        msgInt++;
                        startHandler.sendEmptyMessage(msgInt);
//                        riseAgreeProcAlert();\
                        marketingCheck = false;
                    }
                } else if (smsSeq.length() == 0 && signSeq.length() == 0) {
                    counter = 0;
//                    loadingProgress.setVisibility(View.GONE);
                    rlLoading.setVisibility(View.GONE);
                }
                agreeDialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = agreeDialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        agreeDialog.show();
        agreeDialog.getWindow().setAttributes(lp);
    }

    public void requestOverlapAnalysis()
    {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("MOBILE_NO", phoneNumber.getText().toString());
            params.put("CUSTOMER_NAME", userName.getText().toString());
            params.put("MB_ID", mb_id);
            params.put("CONSULTANT_ID", consultant_id);
            LogUtil.d(TAG, "파라미터 값 = " + params);
            mAQuery.ajax(baseURL + "/getOverlapAnalysis.ajax", params, JSONObject.class, this, "overlapCallback");

        } catch (Exception e) {
            e.printStackTrace();
            viewError1(e.toString(), mb_id, consultant_id, customer_name, telNum, "", telComCode);
        }
    }

    public void overlapCallback(String url, JSONObject json, AjaxStatus status) {
        LogUtil.d(TAG, "리턴 결과 = " + json.toString());
        LogUtil.d(TAG, "상태값 확인 = " + status.toString());
        try {
            overlap = json.getBoolean("OVERLAP");
            LogUtil.d(TAG, "결과값 = " + overlap);
            mLogger.debug("결과값 = " + overlap);
            msg_cust = json.getString("MSG_CUST");
            LogUtil.d(TAG, "결과값 = " + msg_cust);
            mLogger.debug("결과값 = " + msg_cust);
            msgTime = json.getString("MSG_TIME");
            LogUtil.d(TAG, "결과값 = " + msgTime);
            mLogger.debug("결과값 = " + msgTime);
            riseCustomerAgreePopup();
        } catch (JSONException e) {
            e.printStackTrace();
            viewError1("고객 보장분석 이력 확인부 에러, 에러내용 : " + e.toString(), mb_id, consultant_id, customer_name, telNum, "", telComCode);
        }
    }

    public void risePopup1() {
        final InfoContentDialog dialogWindow1 = new InfoContentDialog(this);

        dialogWindow1.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

            }
        });
        dialogWindow1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
        WindowManager.LayoutParams lp = dialogWindow1.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogWindow1.show();
        dialogWindow1.getWindow().setAttributes(lp);

//        Toast.makeText(this, "바깥을 터치하면 팝업이 종료됩니다.", Toast.LENGTH_LONG).show();
    }

    public void riseCredit1Popup() {

        final Credit1Dialog credit1View = new Credit1Dialog(this);

//        requestDialogCon(baseURL, "CREDIT1");
        returnContent = htmlDialogCon("CREDIT1");

        credit1View.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                credit1View.setContent1body(returnContent);
            }
        });

        credit1View.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                credit1View.dialogDestroy();
            }
        });

        WindowManager.LayoutParams lp = credit1View.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        credit1View.show();
        credit1View.getWindow().setAttributes(lp);
    }

    public void riseCredit2Popup() {

        final Credit2Dialog credit2View = new Credit2Dialog(this);

//        requestDialogCon(baseURL, "CREDIT2");
        returnContent = htmlDialogCon("CREDIT2");

        credit2View.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                credit2View.setContent2body(returnContent);
            }
        });

        credit2View.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                credit2View.dialogDestroy();
            }
        });

        WindowManager.LayoutParams lp = credit2View.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        credit2View.show();
        credit2View.getWindow().setAttributes(lp);
    }

    public void riseCredit3Popup() {

        final Credit3Dialog credit3View = new Credit3Dialog(this);

//        requestDialogCon(baseURL, "CREDIT3");
        returnContent = htmlDialogCon("CREDIT3");

        credit3View.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                credit3View.setContent3body(returnContent);
            }
        });

        credit3View.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                credit3View.dialogDestroy();
            }
        });

        WindowManager.LayoutParams lp = credit3View.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        credit3View.show();
        credit3View.getWindow().setAttributes(lp);
    }

    public void riseTelDocPopup1() {
        final Tel1Dialog tel1Dialog = new Tel1Dialog(this);

//        requestDialogCon(baseURL, telComCode + "1");
        returnContent = htmlDialogCon(telComCode + "1");

        tel1Dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                tel1Dialog.setContent1body(returnContent);
            }
        });

        tel1Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                tel1Dialog.dialogDestroy();
            }
        });

        WindowManager.LayoutParams lp = tel1Dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        tel1Dialog.show();
        tel1Dialog.getWindow().setAttributes(lp);
    }

    public void riseTelDocPopup2() {
        final Tel2Dialog tel2Dialog = new Tel2Dialog(this);

//        requestDialogCon(baseURL, telComCode + "2");
//        returnContent = htmlDialogCon(telComCode + "2");

        tel2Dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                tel2Dialog.setContent2body("file:///android_asset/www/tel_serviceTxt.html");
            }
        });

        tel2Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                tel2Dialog.dialogDestroy();
            }
        });

        WindowManager.LayoutParams lp = tel2Dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        tel2Dialog.show();
        tel2Dialog.getWindow().setAttributes(lp);
    }

    public void riseTelDocPopup3() {
        final Tel3Dialog tel3Dialog = new Tel3Dialog(this);

//        requestDialogCon(baseURL, telComCode + "3");
        returnContent =  htmlDialogCon(telComCode + "3");

        tel3Dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                tel3Dialog.setContent3body(returnContent);
            }
        });

        tel3Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                tel3Dialog.dialogDestroy();
            }
        });

        WindowManager.LayoutParams lp = tel3Dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        tel3Dialog.show();
        tel3Dialog.getWindow().setAttributes(lp);
    }

    public void riseTelDocPopup4() {
        final Tel4Dialog tel4Dialog = new Tel4Dialog(this);

//        requestDialogCon(baseURL, telComCode + "4");
        returnContent =  htmlDialogCon(telComCode + "4");

        tel4Dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                tel4Dialog.setContent4body(returnContent);
            }
        });

        tel4Dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                tel4Dialog.dialogDestroy();
            }
        });

        WindowManager.LayoutParams lp = tel4Dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        tel4Dialog.show();
        tel4Dialog.getWindow().setAttributes(lp);
    }

    public void requestBohumContent(String json) {

        if (orgCD.equals("credit4u")) {
            rOrgCD = orgCD;
        } else {
            rOrgCD = "insure";
        }

        try {
            LogUtil.d(TAG, "시작");
            Map<String, String> params = new HashMap<String, String>();
            params.put("consultant_id", consultant_id);
            params.put("customer_name", userName.getText().toString());
            params.put("mb_id", mb_id);
            params.put("customer_phone", phoneNumber.getText().toString());
            params.put("customer_birth", juminNo1);
            params.put("customer_identNum", juminNo2);
            params.put("search_gubun", rOrgCD);
            params.put("customer_seq", customer_seq);
            params.put("json_string", json); // 필요한 파라미터 추가 (파라미터 정의 확정 시)
            LogUtil.d(TAG, "파라미터 내용 = " + params);
            LogUtil.d(TAG, "json 내용 = " + params.get("json_string"));
            mLogger.debug("신정원 결과 서버 전송 파라미터 내용 = " + params);
            mLogger.debug("신정원 결과 json 내용 = " + params.get("json_string"));
            JSONObject json1 = new JSONObject(json);
            LogUtil.d(TAG, "에러 내용 = " + json1.get("errYn"));
            mLogger.debug("신정원 에러 내용 = " + json1.get("errYn"));
            LogUtil.d(TAG, "에러 메시지 내용 = " + json1.get("errMsg"));
            mLogger.debug("신정원 에러 메시지 내용 = " + json1.get("errMsg"));
            if (json1.get("errYn").equals("Y")) {
                errMsg = json1.get("errMsg").toString();
                viewError();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pDialog.dismiss();
                            }
                        });
                    }
                }).start();
                mAQuery.ajax(baseURL + "/policymng/resultJsonSave_for_android.ajax", params, JSONObject.class, requestWebCallBack.timeout(300000));
            }
        } catch (Exception e) {
            e.printStackTrace();
            viewError1(e.toString(), mb_id, consultant_id, customer_name, telNum, "", telComCode);
        }
    }

    private AjaxCallback<JSONObject> requestWebCallBack = new AjaxCallback<JSONObject>() {
        public void callback(String url, JSONObject json, AjaxStatus status) {
            if (json != null) {
                try {
//                LogUtil.d(TAG, "리턴 결과 = " + json.toString());
//                LogUtil.d(TAG, "상태값 확인 = " + status.toString());
//                mLogger.debug("크롤링 데이터 리턴 결과1 = " + json.toString());
                    LogUtil.d(TAG, "리턴 결과 = " + json.toString());
                    mLogger.debug("크롤링 데이터 리턴 결과2 = " + json.toString());
                    JSONObject result = json.getJSONObject("result");
                    LogUtil.d(TAG, "결과값 = " + result);
                    mLogger.debug("크롤링 데이터 리턴 결과3 = " + result);
                    String resultStatus = result.getString("status");
                    String resultData = result.getString("customer_seq");
                    String resultData1 = result.getString("mb_id");
                    String resultData2 = result.getString("consultant_id");
                    mLogger.debug("크롤링 데이터 리턴 상태값 = " + resultStatus);
                    if (resultStatus.equals("OK")) {
                        btnCheck = true;

                        Toast.makeText(MainActivity.this, "분석을 종료합니다.", Toast.LENGTH_SHORT).show();

//                        svProgressHUD.dismiss();
//                    if (katalk_plus.equals("true")) {
//                        this.finish();
//                        finishAffinity(); // 다시 카카오톡으로 돌아갈 수 있도록 앱 자체를 종료
//                    } else if (katalk_plus.equals("false")) {
//                        String reUrl = baseURL + "/analysis.go?CUSTOMER_SEQ=" + resultData + "&MB_ID=" + resultData1 + "&CONSULTANT_ID=" + resultData2;
//
//                        Intent webReIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseURL + reUrl));
//                        startActivity(webReIntent);
//                        finishAffinity();
//                    } else {
                        if (rOrgCD.equals("credit4u")) {
                            slideGubun = "0";
                        } else {
                            slideGubun = "2";
                        }
                        SharedPreferences crawlingStatus = getSharedPreferences("crawlingStatus", MODE_PRIVATE);
                        SharedPreferences.Editor editData = crawlingStatus.edit();
                        editData.putString("mb_id", resultData1);
                        editData.putString("con_id", resultData2);
                        editData.putString("cust_seq", resultData);
                        editData.putString("slide_gubun", slideGubun);
                        editData.putString("go_url", baseURL + "/mobile/jquery_body.go");
                        editData.commit();
//                        startActivity(returnIntent);

//                    }
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    } else {
                        LogUtil.d(TAG, "실패");
                        mainScroll.setBackgroundColor(Color.argb(0, 255, 255, 255));
                        btnCheck = true;
//                        loadingProgress.setVisibility(View.GONE);
                        rlLoading.setVisibility(View.GONE);
                        pDialog.dismiss();
//                        svProgressHUD.dismiss();
                        Toast.makeText(MainActivity.this, "분석 데이터 저장 중 문제가 발생했습니다\n다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    LogUtil.d(TAG, "실패");
                    mainScroll.setBackgroundColor(Color.argb(0, 255, 255, 255));
//                    loadingProgress.setVisibility(View.GONE);
                    rlLoading.setVisibility(View.GONE);
                    btnCheck = true;
                    pDialog.dismiss();
//                    svProgressHUD.dismiss();
                    Toast.makeText(MainActivity.this, "서버와의 통신에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
                    viewError1("분석데이터 서버전송 에러, 에러내용 : " + e.toString(), mb_id, consultant_id, customer_name, telNum, "", telComCode);
                    mLogger.debug("크롤링 데이터 jsonError = " + e);
                    e.printStackTrace();
                }
            } else {
                pDialog.dismiss();
//                svProgressHUD.dismiss();
//                loadingProgress.setVisibility(View.GONE);
                rlLoading.setVisibility(View.GONE);
                mainScroll.setBackgroundColor(Color.argb(0, 255, 255, 255));
                btnCheck = true;
                LogUtil.d(TAG, "실패");
                viewError1("분석데이터 서버전송 에러, 에러내용 : return json value is null", mb_id, consultant_id, customer_name, telNum, "", telComCode);
                Toast.makeText(MainActivity.this, "서버와의 통신에 문제가 발생했습니다", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void viewError() {
        Map<String, String> errParam = new HashMap<String, String>();
        errParam.put("CONSULTANT_ID", consultant_id);
        errParam.put("CUSTOMER_NM", userName.getText().toString());
        errParam.put("MB_ID", mb_id);
        errParam.put("MOBILE_NO", phoneNumber.getText().toString());
        errParam.put("ERR_GUBUN", rOrgCD);
        errParam.put("DEVICE_GUBUN", "android");
        errParam.put("MOBILE_CO", telComCode);
        errParam.put("ERR_MSG", errMsg);

        mAQuery.ajax(baseURL + "/insertErrorLog.ajax", errParam, JSONObject.class, this, "responseErrorMsg");
    }

    public void responseErrorMsg(String url, JSONObject json, AjaxStatus status) {

        if (json != null) {
            try {
                LogUtil.d(TAG, "리턴 결과 = " + json.toString());
                LogUtil.d(TAG, "상태값 확인 = " + status.toString());
                mLogger.debug("리턴 결과 = " + json.toString());
                String result = json.getString("result");
                LogUtil.d(TAG, "결과값 = " + result);
                mLogger.debug("결과값 = " + result);
                if (result.equals("OK")) {
                    LogUtil.d(TAG, "에러메시지 전송 성공");
                    LogUtil.d(TAG, "팝업 띄우기 위한 핸들러 동작 시작");
                    Message msg = errHandler.obtainMessage(0);
                    errHandler.sendMessage(msg);
                } else {
                    LogUtil.d(TAG, "에러메시지 전송 실패");
                    LogUtil.d(TAG, "팝업 띄우기 위한 핸들러 동작 시작(서버 전송 실패시에도 보여줘야 함)");
                    Message msg = errHandler.obtainMessage(0);
                    errHandler.sendMessage(msg);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                viewError1("인포텍 에러내용 전송에러, 에러내용 : " + e.toString(), mb_id, consultant_id, customer_name, telNum, "", telComCode);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    public Handler errHandler = new Handler() {
        public void handleMessage(Message msg) {
            if(msg.what == 0) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("")
                        .setMessage(errMsg)
                        .setPositiveButton("확인", new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pDialog.dismiss();
//                                svProgressHUD.dismiss();
                                mainScroll.setBackgroundColor(Color.argb(0, 255, 255, 255));
//                                loadingProgress.setVisibility(View.GONE);
                                rlLoading.setVisibility(View.GONE);
                                btnCheck = true;
                                dialog.cancel();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }
        }
    };

//    public void requestDialogCon(String baseURL, String param) {
//        try {
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("CODE", param);
//            Log.d("testApp", "파라미터 내용 = " + params);
//            mAQuery.ajax(baseURL + "/policymng/getAccessTerm.ajax", params, JSONObject.class, this, "responseContentCallBack");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void responseContentCallBack(String url, JSONObject json, AjaxStatus status) {
//        Log.d("testApp", "결과 값 = " + json);
//        Log.d("testApp", "결과 상태 = " + status);
//
//        try {
//            JSONObject result = json.getJSONObject("result");
//            Log.d("testApp", "result 값 = " + result);
//            Log.d("testApp", "title 정보 = " + result.getString("CODE_DESC"));
//            contentTitle = result.getString("CODE_DESC");
//            returnContent = result.getString("CONTENT");
//            Log.d("testApp", "팝업 내용 = " + returnContent);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public static String htmlDialogCon(String param) {

        String returnValue = "";

        switch (param) {
            case "CREDIT1" :
                returnValue = "file:///android_asset/www/agree1.html";
                break;
            case "CREDIT2" :
                returnValue = "file:///android_asset/www/agree2.html";
                break;
            case "CREDIT3" :
                returnValue = "file:///android_asset/www/agree3.html";
                break;
            case "SKT1" :
                returnValue = "file:///android_asset/www/tel_skt1.html";
                break;
            case "SKT3" :
                returnValue = "file:///android_asset/www/tel_skt2.html";
                break;
            case "SKT4" :
                returnValue = "file:///android_asset/www/tel_skt3.html";
                break;
            case "KTF1" :
                returnValue = "file:///android_asset/www/tel_kt1.html";
                break;
            case "KTF3" :
                returnValue = "file:///android_asset/www/tel_kt2.html";
                break;
            case "KTF4" :
                returnValue = "file:///android_asset/www/tel_kt3.html";
                break;
            case "LGT1" :
                returnValue = "file:///android_asset/www/tel_lg1.html";
                break;
            case "LGT3" :
                returnValue = "file:///android_asset/www/tel_lg2.html";
                break;
            case "LGT4" :
                returnValue = "file:///android_asset/www/tel_lg3.html";
                break;
        }
        return returnValue;
    }

    public void getJuminRequest() {
        Map<String, String> param = new HashMap<>();
        param.put("SEQ", seq);
        SharedPreferences getPernoPref1 = getSharedPreferences("savePerno", MODE_PRIVATE);
        String perNo1 = getPernoPref1.getString("perNo1", "");
        LogUtil.d(TAG, "카운터 값 = " + counter);
        if (perNo1.length() == 6) {
            startHandler.removeMessages(msgInt);
            smsSeq = "";
            signIdx = "";
            signSeq = "";
        } else {
            mAQuery.ajax(baseURL + "/customer/selectCustomerAgree.ajax", param, JSONObject.class, this, "getJuminResponse");
        }
    }



    public void getJuminResponse(String url, JSONObject json, AjaxStatus status) {
        LogUtil.d(TAG, "결과 값 = " + json);

        try {
            JSONObject resultData = json.getJSONObject("result");
            String userBirth = resultData.getString("PERNO1");
            String userIdent = resultData.getString("PERNO2");
            if (userBirth.length() == 6) {
//                loadingProgress.setVisibility(View.GONE);
                rlLoading.setVisibility(View.GONE);
                SharedPreferences getPernoPref = getSharedPreferences("savePerno", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = getPernoPref.edit();
                editor1.putString("perNo1", userBirth);
                editor1.putString("perNo2", userIdent);
                editor1.commit(); // 받아온 주민번호 저장 완료

                riseAgreeAlert();
                marketingCheck = true;
                tvStatus.setText(userName.getText() + "님이 동의하셨습니다.");
            } else {

            }
        } catch (Exception e) {
            viewError1("주민등록번호 요청 에러, 에러내용 : " + e.toString(), mb_id, consultant_id, customer_name, telNum, "", telComCode);
        }
    }

    public void riseAgreeProcAlert() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("")
                .setMessage(userName.getText() + "님이 동의절차를 진행중입니다.\n입력 시간에 따라 최대 3분이 소요될 수 있습니다.")
                .setPositiveButton("확인", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    public void riseAgreeAlert() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("")
                .setMessage(userName.getText() + "님이 동의하였습니다.")
                .setPositiveButton("확인", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        loadingProgress.setVisibility(View.GONE);
                        rlLoading.setVisibility(View.GONE);
                        marketingCheck = true;
                        counter = 0;
                        startHandler.removeMessages(msgInt);
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    public void riseTimeWarningAlert() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("")
                .setMessage("대기시간이 초과되었습니다\n다시 시도해주세요")
                .setPositiveButton("확인", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        loadingProgress.setVisibility(View.GONE);
                        rlLoading.setVisibility(View.GONE);
                        marketingCheck = true;
                        counter = 0;
                        startHandler.removeMessages(msgInt);
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
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


}