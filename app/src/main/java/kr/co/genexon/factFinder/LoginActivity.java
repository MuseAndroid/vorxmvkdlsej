// local 63
package kr.co.genexon.factFinder;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import kr.co.genexon.factFinder.Fingerprint.FingerprintAuthenticationDialogFragment;
import kr.co.genexon.factFinder.Fingerprint.Util;
import kr.co.genexon.factFinder.Sqlite.DBLoginHelper;
import kr.co.genexon.factFinder.Sqlite.LoginInfoClass;

public class LoginActivity extends BaseActivity implements FingerprintAuthenticationDialogFragment.SecretAuthorize{

    protected static final String TAG = "FactFinder";

    private DBLoginHelper mLoginHelper;

    private Cursor mCursor;

    private LoginInfoClass mInfoClass;
    private LoginInfoClass mInfoClass1;

    // fingerprint
    private FingerprintAuthenticationDialogFragment mFragment;

    // private ArrayList<LoginInfoClass> mInfoArr;

    // private ArrayList<LoginInfoClass> gInfoArr;
    // private ArrayList<LoginInfoClass> g1InfoArr;

    Button login_btn, login_comBtn;
    ScrollView login_scroll;
    TextView join_btn, comUserTitle, singleUserTitle, find_userId, find_userPw;
    FrameLayout personalLogin, companyLogin;
    EditText et_id, et_pw, et_com_cd, et_emp_cd, et_emp_pw;
    RadioGroup loginRBtn;
    RadioButton loginRBtn1, loginRBtn2;
    String comCode, sId, sPw;
    String savedUserComCd, savedUserId, savedUserPw; // 일반회원 데이터 저장변수
    String savedComCode, savedComUser, savedComUserPw; // 기업회원 데이터 저장변수
    String device_gubun = "and";
    String device_id;

    PresistentCookieStore preCookieStore;

    public static Context mContext;

    AQuery mAQuery = new AQuery(this);

    SelectServerURL sSUrl = new SelectServerURL();

    String baseURL = sSUrl.getServerURL();

    InputMethodManager imm;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

//        requestLoginInfo();
        mContext = this;

        mFragment = new FingerprintAuthenticationDialogFragment();
        mFragment.setCallback(this);
        // 지문인식 실행메서드
//        mFragment.show(this.getFragmentManager(), "fingerprint_fragment");

        personalLogin = findViewById(R.id.personalLogin);
        companyLogin = findViewById(R.id.companyLogin);
        login_scroll = findViewById(R.id.login_scroll);

        loginRBtn = findViewById(R.id.loginRBtn);
        loginRBtn1 = findViewById(R.id.loginRBtn1); // 일반회원
        loginRBtn2 = findViewById(R.id.loginRBtn2); // 고객사회원
        comUserTitle = findViewById(R.id.comUserTitle);
        singleUserTitle = findViewById(R.id.singleUserTitle);

        et_id = findViewById(R.id.etEmail);
        et_pw = findViewById(R.id.etPassword);
        et_com_cd = findViewById(R.id.etComCode);
        et_emp_cd = findViewById(R.id.etEmpCode);
        et_emp_pw = findViewById(R.id.etEmpPassword);
        login_btn = findViewById(R.id.btnLogin);
        login_comBtn = findViewById(R.id.btnComLogin);
        join_btn = findViewById(R.id.txtEnterJoin);
        find_userId = findViewById(R.id.find_userId);
        find_userPw = findViewById(R.id.find_userPw);

        final ConstraintLayout constraintWrapper = findViewById(R.id.clLoginView);
        constraintWrapper.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int mRootViewHeight = constraintWrapper.getRootView().getHeight();
                int mConstraintWrapperHeight = constraintWrapper.getHeight();
                int mDiff = mRootViewHeight - mConstraintWrapperHeight;
                if (mDiff > dpToPx(200)) {
                    LogUtil.d(TAG, "키보드 활성화");
                    join_btn.setVisibility(View.GONE);
                } else {
                    LogUtil.d(TAG, "키보드 비활성화");
                    if (personalLogin.getVisibility() == View.VISIBLE) {
                        join_btn.setVisibility(View.VISIBLE);
                    } else {
                        join_btn.setVisibility(View.GONE);
                    }
                }
            }
        });

        et_com_cd.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        et_emp_cd.setImeOptions(EditorInfo.IME_ACTION_NEXT);

        // mLoginHelper = new DBLoginHelper(this);
        // try {
        //     mLoginHelper.open();
        //     LogUtil.d(TAG, "SQLite 오픈");
        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }

        // mInfoArr = new ArrayList<LoginInfoClass>();
        // gInfoArr = new ArrayList<LoginInfoClass>();
        // g1InfoArr = new ArrayList<LoginInfoClass>();

        // doLastCursorToArray();
        // doSecondaryCursorToArray();

        // if (!gInfoArr.isEmpty()) {
        //     for (LoginInfoClass i : gInfoArr) {
        //         Log.i(TAG, "ID = " + i._id);
        //         Log.i(TAG, "MB_ID = " + i.mb_id);
        //         Log.i(TAG, "CUST_ID = " + i.cust_id);
        //         Log.i(TAG, "PASSWORD = " + i.password);

        //         tmpComCode = i.mb_id;
        //         tmpEmpCode = i.cust_id;
        //         tmpPasswd = i.password;
        //     }
        // }

        // if (!g1InfoArr.isEmpty()) {
        //     for (LoginInfoClass i : g1InfoArr) {
        //         Log.i(TAG, "ID = " + i._id);
        //         Log.i(TAG, "MB_ID = " + i.mb_id);
        //         Log.i(TAG, "CUST_ID = " + i.cust_id);
        //         Log.i(TAG, "PASSWORD = " + i.password);

        //         tmpComCode1 = i.mb_id;
        //         tmpEmpCode1 = i.cust_id;
        //         tmpPasswd1 = i.password;
        //     }
        // }

        et_emp_pw.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getId() == R.id.etEmpPassword && actionId == EditorInfo.IME_ACTION_DONE) {
                    bt_Login(login_comBtn);
                }
                return false;
            }
        });

        et_pw.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getId() == R.id.etPassword && actionId == EditorInfo.IME_ACTION_DONE) {
                    bt_Login(login_btn);
                }
                return false;
            }
        });

        SharedPreferences loginStatus = getSharedPreferences("loginStatus", MODE_PRIVATE);
        String screenStatus = loginStatus.getString("loginScreen", "");
        LogUtil.d(TAG, "로그인했던 창 = " + screenStatus);
        // 화면 상태 저장 끝
        if (screenStatus.equals("기업고객")) {
            loginRBtn.check(loginRBtn2.getId());
            callComLogin();
        } else if (screenStatus.equals("개인고객")) {
            loginRBtn.check(loginRBtn1.getId());
            callSingleLogin();
        } else {
            loginRBtn.check(loginRBtn2.getId());
            callComLogin();
        }

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        device_id = FirebaseInstanceId.getInstance().getToken();
        LogUtil.d(TAG, "디바이스 토큰 = " + device_id);

        // EditText의 Touch 이벤트 및 focus 이동이 있을 때 스크롤이 유연하게 움직이도록 스크롤 애니메이션 추가
        login_scroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });
//
        et_id.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        moveScrollY(150, 250);
                }
                return false;
            }
        });

        et_pw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        moveScrollY(150, 150);
                }
                return false;
            }
        });

        et_com_cd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        moveScrollY(150, 250);
                }
                return false;
            }
        });

        et_emp_cd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        moveScrollY(150, 150);
                }
                return false;
            }
        });

        et_emp_pw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        moveScrollY(150, 50);
                }
                return false;
            }
        });

        et_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                moveScrollY(150, 250);
                et_id.setHint("");
                if (!hasFocus && et_id.getText().toString().equals("")) {
                    et_id.setHint("이메일");
                }
            }
        });

        et_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                moveScrollY(150, 150);
                et_pw.setHint("");
                if (!hasFocus && et_pw.getText().toString().equals("")) {
                    et_pw.setHint("비밀번호");
                }
            }
        });

        et_com_cd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                moveScrollY(150, 250);
                et_com_cd.setHint("");
                if (!hasFocus && et_com_cd.getText().toString().equals("")) {
                    et_com_cd.setHint("회사코드");
                }
            }
        });

        et_emp_cd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                moveScrollY(150, 150);
                et_emp_cd.setHint("");
                if (!hasFocus && et_emp_cd.getText().toString().equals("")) {
                    et_emp_cd.setHint("사원코드");
                }
            }
        });

        et_emp_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                moveScrollY(150, 50);
                et_emp_pw.setHint("");
                if (!hasFocus && et_emp_pw.getText().toString().equals("")) {
                    et_emp_pw.setHint("비밀번호");
                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_Login(v);
            }
        });
        login_comBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_Login(v);
            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_Join(v);
            }
        });
        comUserTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callComLogin();
                loginRBtn2.setChecked(true);
                loginRBtn1.setChecked(false);
            }
        });

        singleUserTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callSingleLogin();
                loginRBtn2.setChecked(false);
                loginRBtn1.setChecked(true);
//                Toast.makeText(LoginActivity.this, "개인고객 로그인은 준비중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        find_userId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "서비스 준비 중입니다.", Toast.LENGTH_SHORT).show();
//                Intent findID = new Intent(LoginActivity.this, FindUserDataActivity.class);
//                findID.putExtra("findID", "0");
//                findID.putExtra("findPassword", "0");
//                startActivity(findID);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
            }
        });

        find_userPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "서비스 준비 중입니다.", Toast.LENGTH_SHORT).show();
//                Intent findPW = new Intent(LoginActivity.this, FindUserDataActivity.class);
//                findPW.putExtra("findID", "1");
//                findPW.putExtra("findPassword", "1");
//                startActivity(findPW);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.fade_back);
            }
        });

        loginRBtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    callSingleLogin();
                }
            }
        });

        loginRBtn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    callComLogin();
                }
            }
        });

        new getMarketVersion().execute();

        et_com_cd.setSelection(et_com_cd.getText().length());
        et_emp_cd.setSelection(et_emp_cd.getText().length());
        et_emp_pw.setSelection(et_emp_pw.getText().length());
        et_id.setSelection(et_id.getText().length());
        et_pw.setSelection(et_pw.getText().length());
    }

    /* onClick에서 정의한 이름과 똑같은 이름으로 생성 */
    public void bt_Login(View view) {
        /* 버튼을 눌렀을 때 동작하는 소스 */
        switch (view.getId()) {
            case R.id.btnLogin:
                sId = et_id.getText().toString();
                sPw = et_pw.getText().toString();
                if (sId.equals(null) || sId.equals("") || sId.length() == 0){
                    Toast.makeText(this, "이메일(아이디)을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (sPw.equals(null) || sPw.equals("") || sPw.length() == 0) {
                    Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mb_id", "NO_MB_ID");
                    params.put("consultant_id", et_id.getText().toString());
                    params.put("password", et_pw.getText().toString());

                    requestNormalLogin(params);
                }
                break;
            case R.id.btnComLogin:
                comCode = et_com_cd.getText().toString();
                sId= et_emp_cd.getText().toString();
                sPw = et_emp_pw.getText().toString();
                if (comCode.equals(null) || comCode.equals("") || comCode.length() == 0) {
                    Toast.makeText(this, "회사코드를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (sId.equals(null) || sId.equals("") || sId.length() == 0) {
                    Toast.makeText(this, "사원코드를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (sPw.equals(null) || sPw.equals("") || sPw.length() == 0) {
                    Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mb_id", et_com_cd.getText().toString());
                    params.put("consultant_id", et_emp_cd.getText().toString());
                    params.put("password", et_emp_pw.getText().toString());

                    requestComLogin(params);
                }
                break;
        }
    }

    public void bt_Join(View view) {
        // 회원가입 창으로 이동하는 intent 작성
//        Toast.makeText(this, "준비 중입니다.", Toast.LENGTH_SHORT).show();
        Intent agreeIntent = new Intent(this, AgreeInfoUseActivity.class);
        startActivity(agreeIntent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void requestComLogin(Map<String, String> params) {
        try {
            LogUtil.d(TAG, "파라미터 내용 = " + params);
            mAQuery.ajax(baseURL + "/loginCheck.ajax", params, JSONObject.class, this, "responseComLoginCallBack");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void responseComLoginCallBack(String url, JSONObject json, AjaxStatus status) {
        LogUtil.d(TAG, "리턴 결과 = " + json.toString());
        LogUtil.d(TAG, "상태값 확인 = " + status.toString());
        if (json != null) {
            try {
                LogUtil.d(TAG, "리턴 결과 = " + json.toString());
                String result = json.get("result").toString();
                LogUtil.d(TAG, "결과값 = " + result);
//                preCookieStore = new PresistentCookieStore(this);
//                CookieManager manager = new CookieManager(preCookieStore, CookiePolicy.ACCEPT_ALL);
//                CookieHandler.setDefault(manager);
//                LogUtil.d(TAG, "뽑아낸 쿠키 값 = " + preCookieStore.getCookies());
                if (result.equals("OK") || result.equals("API")) {
                    // 화면 상태 저장 시작
                    SharedPreferences loginStatus = getSharedPreferences("loginStatus", MODE_PRIVATE);
                    SharedPreferences.Editor editScreen = loginStatus.edit();
                    editScreen.putString("loginScreen", "기업고객");
                    editScreen.commit();
                    // 화면 상태 저장 끝

                    Intent joinIntent = new Intent(this, MainViewActivity.class);
                    joinIntent.putExtra("comp", comCode);
                    joinIntent.putExtra("id", sId);
                    joinIntent.putExtra("pw", sPw);
                    joinIntent.putExtra("device_gubun", device_gubun);
                    joinIntent.putExtra("device_id", device_id);
                    startActivity(joinIntent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    Toast.makeText(this, "등록되지 않은 사용자이거나 비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                viewError1("기업회원 로그인 에러, 에러내용 : " + e.toString(), comCode, sId, "", "", "", "");
                Toast.makeText(this, "서버와의 통신이 원활하지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void requestNormalLogin(Map<String, String> params) {
        try {
            LogUtil.d(TAG, "파라미터 내용 = " + params);
            mAQuery.ajax(baseURL + "/loginCheck.ajax", params, JSONObject.class, this, "responseNormalLoginCallBack");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void responseNormalLoginCallBack(String url, JSONObject json, AjaxStatus status) {
        LogUtil.d(TAG, "리턴 결과 = " + json.toString());
        LogUtil.d(TAG, "상태값 확인 = " + status.toString());
        if (json != null) {
            try {
                LogUtil.d(TAG, "리턴 결과 = " + json.toString());
                String result = json.get("result").toString();
                LogUtil.d(TAG, "결과값 = " + result);
                if (result.equals("OK")) {
                    Intent joinIntent = new Intent(this, MainViewActivity.class);
                    // 화면 상태 저장 시작
                    SharedPreferences loginStatus = getSharedPreferences("loginStatus", MODE_PRIVATE);
                    SharedPreferences.Editor editScreen = loginStatus.edit();
                    editScreen.putString("loginScreen", "개인고객");
                    editScreen.commit();
                    // 화면 상태 저장 끝

                    joinIntent.putExtra("comp", "NO_MB_ID");
                    joinIntent.putExtra("id", sId);
                    joinIntent.putExtra("pw", sPw);
                    joinIntent.putExtra("device_gubun", device_gubun);
                    joinIntent.putExtra("device_id", device_id);
                    startActivity(joinIntent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    Toast.makeText(this, "등록되지 않은 사용자이거나 비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                viewError1("개인회원 로그인 에러, 에러내용 : " + e.toString(), "NO_MB_ID", sId, "", "", "", "");
                Toast.makeText(this, "서버와의 통신이 원활하지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void callComLogin() {
        personalLogin.setVisibility(View.GONE);
        join_btn.setVisibility(View.GONE);
        companyLogin.setVisibility(View.VISIBLE);
//        SharedPreferences getComPref = getSharedPreferences("compLogin", MODE_PRIVATE);

        SharedPreferences getComPref = getSharedPreferences("compLogin", MODE_PRIVATE);
        savedComCode = getComPref.getString("ComCode", "");
        savedComUser = getComPref.getString("cUserID", "");
        LogUtil.d(TAG, "저장된 회사코드 값 = " + savedComCode);
        LogUtil.d(TAG, "저장된 ID값 = " + savedComUser);
        if (!savedComCode.equals(null)) {
            et_com_cd.setText(savedComCode);
            et_emp_cd.setText(savedComUser);
        }
    }

    public void callSingleLogin() {
        companyLogin.setVisibility(View.GONE);
        join_btn.setVisibility(View.VISIBLE);
        personalLogin.setVisibility(View.VISIBLE);

        SharedPreferences getUserPref = getSharedPreferences("userLogin", MODE_PRIVATE);
        savedUserComCd = getUserPref.getString("uComCd", "");
        savedUserId = getUserPref.getString("userID", "");
        LogUtil.d(TAG, "저장된 회사코드 값 = " + savedUserComCd);
        LogUtil.d(TAG, "저장된 ID값 = " + savedUserId);
        if (!savedUserId.equals(null)) {
            et_id.setText(savedUserId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestLoginInfo();
        if (et_com_cd.length() != 0) {
            et_emp_cd.requestFocus();
            if (et_emp_cd.length() != 0) {
                moveScrollY(500, 50);
                et_emp_pw.requestFocus();
            }
        } else if (et_id.length() != 0) {
            moveScrollY(500, 150);
            et_pw.requestFocus();
        }
    }

// -- SQLite 적용부분 --
//     private void doWhileCursorToArray() {
//         mCursor = null;

//         mCursor = mLoginHelper.getAllColumns();

//         Log.i(TAG, "컬럼 갯수 = " + mCursor.getCount());

//         while (mCursor.moveToNext()) {
//             mInfoClass = new LoginInfoClass(
//                     mCursor.getInt(mCursor.getColumnIndex("_id")),
//                     mCursor.getString(mCursor.getColumnIndex("mb_id")),
//                     mCursor.getString(mCursor.getColumnIndex("cust_id")),
//                     mCursor.getString(mCursor.getColumnIndex("password"))
//             );

//             mInfoArr.add(mInfoClass);

//         }

//         mCursor.close();
//     }

//     private void doLastCursorToArray() {
//         try {
//             mCursor = null;
//             mCursor = mLoginHelper.getLastColumn();


//             Log.i(TAG, "컬럼 갯수 = " + mCursor.getCount());

//             while (mCursor.moveToNext()) {
//                 mInfoClass = new LoginInfoClass(
//                         mCursor.getInt(mCursor.getColumnIndex("_id")),
//                         mCursor.getString(mCursor.getColumnIndex("mb_id")),
//                         mCursor.getString(mCursor.getColumnIndex("cust_id")),
//                         mCursor.getString(mCursor.getColumnIndex("password"))
//                 );

//                 gInfoArr.add(mInfoClass);
//                 LogUtil.d(TAG, "array 정보 = " + gInfoArr.toString());
//                 // 지문인식 기능 활성화 코드 (현재 비활성화), 추후 로그인화면에 지문으로 로그인 버튼 추가하여 기능 모듈화 예정
//                 if (Util.isfingerprintAuthAvailable(getApplication().getApplicationContext())) {
// //                    mFragment.show(this.getFragmentManager(), "fingerprint_fragment");
//                 }
//             }
//         } catch (NullPointerException e) {
//             e.printStackTrace();
//         }
//     }

//     private void doSecondaryCursorToArray() {
//         try {
//             mCursor = null;
//             mCursor = mLoginHelper.getSecondaryColumn();

//             Log.i(TAG, "두번 째 컬럼 갯수 = " + mCursor.getCount());

//             while (mCursor.moveToNext()) {
//                 mInfoClass1 = new LoginInfoClass(
//                         mCursor.getInt(mCursor.getColumnIndex("_id")),
//                         mCursor.getString(mCursor.getColumnIndex("mb_id")),
//                         mCursor.getString(mCursor.getColumnIndex("cust_id")),
//                         mCursor.getString(mCursor.getColumnIndex("password"))
//                 );

//                 g1InfoArr.add(mInfoClass1);
//             }
//         } catch (NullPointerException e) {
//             e.printStackTrace();
//         }
//     }

    public void setUserData(final String comCD, final String userID) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!comCD.equals("null") || !comCD.equals(null)) {
                            if (!comCD.equals("NO_MB_ID")) {
                                et_com_cd.setText(comCD);
                                et_emp_cd.setText(userID);
                            } else {
                                et_id.setText(userID);
                            }
                        } else {
                            et_com_cd.setText("");
                            et_emp_cd.setText("");
                            et_id.setText("");
                        }
                    }
                });
            }
        }).start();
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
                    Log.d(TAG, "앱 버전 값 = " + dVersion);
                    Log.d(TAG, "마켓 버전 값 = " + mVersion);
                    if (dVersion >= mVersion) {
                        Log.d(TAG, "최신버전입니다.");
//                            Toast.makeText(MainActivity.this, "현재 최신버전입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        new android.app.AlertDialog.Builder(LoginActivity.this)
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

    public float dpToPx(float valueInDp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

    @Override
    public void success() {
        Toast.makeText(this, "인증 성공", Toast.LENGTH_SHORT).show();
        SharedPreferences loginStatus = getSharedPreferences("loginStatus", MODE_PRIVATE);
        String screenStatus = loginStatus.getString("loginScreen", "");
        LogUtil.d(TAG, "로그인했던 창 = " + screenStatus);
        if (screenStatus.equals("기업고객")) {
            LogUtil.d(TAG, "저장된 회사코드 값 = " + savedComCode);
            LogUtil.d(TAG, "저장된 ID값 = " + savedComUser);
            LogUtil.d(TAG, "저장된 PW값 = " + savedComUserPw);
            comCode = savedComCode;
            sId = savedComUser;
            sPw = savedComUserPw;
            try {
                Map<String, String> comParams = new HashMap<String, String>();
                if (!savedComCode.equals(null) && !savedComCode.equals("NO_MB_ID")) {
                    comParams.put("mb_id", savedComCode);
                    comParams.put("consultant_id", savedComUser);
                    comParams.put("password", savedComUserPw);
                }
                requestComLogin(comParams);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } else if (screenStatus.equals("개인고객")) {
            LogUtil.d(TAG, "저장된 회사코드 값 = " + savedUserComCd);
            LogUtil.d(TAG, "저장된 ID값 = " + savedUserId);
            LogUtil.d(TAG, "저장된 PW값 = " + savedUserPw);
            comCode = savedUserComCd;
            sId = savedUserId;
            sPw = savedUserPw;
            try {
                Map<String, String> norParams = new HashMap<String, String>();
                if (!savedUserComCd.equals(null) && savedUserComCd.equals("NO_MB_ID")) {
                    norParams.put("mb_id", savedUserComCd);
                    norParams.put("consultant_id", savedUserId);
                    norParams.put("password", savedUserPw);
                }
                requestNormalLogin(norParams);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void fail() {
        Toast.makeText(this, "지문 인증에 실패했습니다.\n비밀번호를 입력하여 로그인하시기 바랍니다.", Toast.LENGTH_SHORT).show();
    }

    private void moveScrollY(final int delayTime, final int moveY) {
        login_scroll.postDelayed(new Runnable() {
            @Override
            public void run() {
                int y = (getWindow().getDecorView().getHeight() / 4) - moveY;
                Log.d(TAG, "지정 위치 이동 시작 | 딜레이 : " + delayTime + "ms, 위치 : " + y);
//                login_scroll.smoothScrollTo(0, y);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    ValueAnimator realSmoothScrollAnimation = ValueAnimator.ofInt(login_scroll.getScrollY(), y);
                    realSmoothScrollAnimation.setDuration(500);
                    realSmoothScrollAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int scrollTo = (Integer) animation.getAnimatedValue();
                            login_scroll.scrollTo(0, scrollTo);
                        }
                    });
                    realSmoothScrollAnimation.start();
                } else {
                    login_scroll.smoothScrollTo(0, y);
                }
            }
        }, delayTime);
    }
}