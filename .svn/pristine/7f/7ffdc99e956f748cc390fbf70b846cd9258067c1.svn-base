package kr.co.genexon.factFinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FindUserDataActivity extends BaseActivity implements View.OnClickListener {

    protected static final String TAG = "FactFinder";

    TextView findUDataTitle;

    AQuery mAquery = new AQuery(this);

    SelectServerURL sSUrl = new SelectServerURL();
    String baseURL = sSUrl.getServerURL();

    RadioButton fud1RBtn1; // 휴대폰인증 라디오버튼
    TextView fud1HPTitle; // 휴대폰인증 라디오버튼 text
    RadioButton fud1RBtn2; // 이메일인증 라디오버튼
    TextView fud1emailTitle; // 이메일 인증 라디오버튼 text
    LinearLayout fud_name; // 이름 입력 layout
    EditText etFudName; // 이름 입력 edittext
    LinearLayout fud_id; // 아이디 입력 layout
    EditText etFudID; // 아이디 입력 edittext
    ConstraintLayout clHP; // 핸드폰 번호 입력 layout
    EditText etfudHp; // 핸드폰 번호 입력 edittext
    Button btnReqSecure; // 인증번호 요청 버튼
    ConstraintLayout clEmail; // 이메일 주소 입력 layout
    EditText etFindEmail; // 이메일 주소 입력 edittext
    Button btnReqSecure1; // 인증번호 요청 버튼1
    EditText etSecureNum; // 인증번호 입력 edittext
    LinearLayout warn_layout; // 이메일 경고 문구 layout
    Button btnReqUserDt; // 다음 버튼
    Intent fIntent;
    Toolbar toolbar;

    // parameter 변수
    String idpw = ""; // 아이디, 비밀번호 찾기 flag값
    String mbtype = ""; // 개인, 기업회원 flag값
    String hpmail = ""; // 핸드폰, 이메일 찾기 flag값
    String mb_id = "NO_MB_ID"; // 회사코드 hardcoding (개인회원만)
    String cont_id = ""; // 회원 id
    String cont_name = ""; // 회원 이름
    String passWd = ""; // 회원 pw
    String hp_tel = ""; // 회원 핸드폰번호
    String email_Addr = ""; // 회원 이메일주소
    String verify_No = ""; // 인증번호
    String verify_idx = ""; // 인증번호 인덱스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user_data);

        findUDataTitle = findViewById(R.id.toolbarTitle);

        fud1RBtn1 = findViewById(R.id.fud1RBtn1);
        fud1HPTitle = findViewById(R.id.fud1HPTitle);
        fud1RBtn2 = findViewById(R.id.fud1RBtn2);
        fud1emailTitle = findViewById(R.id.fud1emailTitle);
        fud_name = findViewById(R.id.fud_name);
        etFudName = findViewById(R.id.etFudName);
        fud_id = findViewById(R.id.fud_id);
        etFudID = findViewById(R.id.etFudID);
        clHP = findViewById(R.id.clHp);
        etfudHp = findViewById(R.id.etFudHp);
        btnReqSecure = findViewById(R.id.btnReqSecure);
        clEmail = findViewById(R.id.clEmail);
        etFindEmail = findViewById(R.id.etFindEmail);
        btnReqSecure1 = findViewById(R.id.btnReqSecure1);
        etSecureNum = findViewById(R.id.etSecureNum);
        warn_layout = findViewById(R.id.warn_layout);
        btnReqUserDt = findViewById(R.id.btnReqUserDt);
        toolbar = findViewById(R.id.toolbar);

        toolbar.bringToFront();
        fIntent = getIntent();
        LogUtil.d(TAG, "넘어온 값 = " + fIntent.getStringExtra("findID"));
        LogUtil.d(TAG, "넘어온 값 = " + fIntent.getStringExtra("findPassword"));
        if (fIntent.getStringExtra("findID").equals("0")) {
            LogUtil.d(TAG, "아이디찾기 화면");
            findUDataTitle.setText("아이디찾기");
            fud_name.setVisibility(View.VISIBLE);
            fud_id.setVisibility(View.GONE);
            fud1RBtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        LogUtil.d(TAG, "라디오버튼 눌렸니?1 = " + isChecked);
                        clHP.setVisibility(View.VISIBLE);
                        clEmail.setVisibility(View.GONE);
                        warn_layout.setVisibility(View.GONE);
                        idpw = "id";
                        mbtype = "pers";
                        hpmail = "hp";
                    } else {
                        LogUtil.d(TAG, "라디오버튼 눌렸니?2 = " + isChecked);
                        clHP.setVisibility(View.GONE);
                        clEmail.setVisibility(View.VISIBLE);
                        warn_layout.setVisibility(View.VISIBLE);
                        idpw = "id";
                        mbtype = "pers";
                        hpmail = "mail";
                    }
                }
            });
        } else if (fIntent.getStringExtra("findPassword").equals("1")) {
            LogUtil.d(TAG, "비밀번호 찾기 화면");
            findUDataTitle.setText("비밀번호 찾기");
            fud_name.setVisibility(View.GONE);
            fud_id.setVisibility(View.VISIBLE);
            fud1RBtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        fud_id.setVisibility(View.VISIBLE);
                        clHP.setVisibility(View.VISIBLE);
                        clEmail.setVisibility(View.GONE);
                        warn_layout.setVisibility(View.GONE);
                        idpw = "pw";
                        mbtype = "pers";
                        hpmail = "hp";
                    } else {
                        fud_id.setVisibility(View.GONE);
                        clHP.setVisibility(View.GONE);
                        clEmail.setVisibility(View.VISIBLE);
                        warn_layout.setVisibility(View.VISIBLE);
                        idpw = "pw";
                        mbtype = "pers";
                        hpmail = "mail";
                    }
                }
            });
        }

        fud1RBtn1.setChecked(true); // 휴대폰으로 인증 화면 초기화
        fud1RBtn2.setChecked(false); // 이메일로 인증 체크 안되도록


        fud1HPTitle.setOnClickListener(this);
        fud1emailTitle.setOnClickListener(this);
        btnReqSecure.setOnClickListener(this);
        btnReqSecure1.setOnClickListener(this);
        btnReqUserDt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fud1HPTitle:
                if (fIntent.getStringExtra("findID").equals("0")) {
                    fud1RBtn1.setChecked(true);
                    fud1RBtn2.setChecked(false);
                    clHP.setVisibility(View.VISIBLE);
                    clEmail.setVisibility(View.GONE);
                    warn_layout.setVisibility(View.GONE);
                    idpw = "id";
                    mbtype = "pers";
                    hpmail = "hp";
                } else if (fIntent.getStringExtra("findPassword").equals("1")) {
                    fud1RBtn1.setChecked(true);
                    fud1RBtn2.setChecked(false);
                    fud_id.setVisibility(View.VISIBLE);
                    clHP.setVisibility(View.VISIBLE);
                    clEmail.setVisibility(View.GONE);
                    warn_layout.setVisibility(View.GONE);
                    idpw = "pw";
                    mbtype = "pers";
                    hpmail = "hp";
                }
                break;
            case R.id.fud1emailTitle:
                if (fIntent.getStringExtra("findID").equals("0")) {
                    fud1RBtn1.setChecked(false);
                    fud1RBtn2.setChecked(true);
                    clHP.setVisibility(View.GONE);
                    clEmail.setVisibility(View.VISIBLE);
                    warn_layout.setVisibility(View.VISIBLE);
                    idpw = "id";
                    mbtype = "pers";
                    hpmail = "mail";
                } else if (fIntent.getStringExtra("findPassword").equals("1")) {
                    fud1RBtn1.setChecked(false);
                    fud1RBtn2.setChecked(true);
                    fud_id.setVisibility(View.GONE);
                    clHP.setVisibility(View.GONE);
                    clEmail.setVisibility(View.VISIBLE);
                    warn_layout.setVisibility(View.VISIBLE);
                    idpw = "pw";
                    mbtype = "pers";
                    hpmail = "mail";
                }
                break;
            case R.id.btnReqSecure:  // 핸드폰인증
                if (fIntent.getStringExtra("findID").equals("0")) {
                    // 아이디찾기
                    if (etFudName.getText().length() != 0) {
                        cont_name = etFudName.getText().toString();
                        if (etfudHp.getText().length() != 0) {
                            boolean hpTF = isHp(etfudHp.getText().toString());
                            if (hpTF) {
                                hp_tel = etfudHp.getText().toString();
                                if (etfudHp.getText().length() == 11) {
                                    // 인증 api실행
                                    getVerifyNo(idpw, mbtype, hpmail, mb_id, "", cont_name, "", hp_tel, "", "");
                                } else if (etfudHp.getText().length() == 10) {
                                    // 인증 api실행
                                    getVerifyNo(idpw, mbtype, hpmail, mb_id, "", cont_name, "", hp_tel, "", "");
                                }
                            } else {
                                Toast.makeText(this, "핸드폰 번호를 정확히 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "핸드폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }

                } else if (fIntent.getStringExtra("findPassword").equals("1")) {
                    // 비밀번호찾기
                    if (etFudID.getText().length() != 0) {
                        cont_id = etFudID.getText().toString();
                        if (etfudHp.getText().length() != 0) {
                            hp_tel = etfudHp.getText().toString();
                            if (etfudHp.getText().length() == 11) {
                                // 인증 api실행
                                getVerifyNo(idpw, mbtype, hpmail, mb_id, cont_id, "", "", hp_tel, "", "");
                            } else if (etfudHp.getText().length() == 10) {
                                // 인증 api실행
                                getVerifyNo(idpw, mbtype, hpmail, mb_id, cont_id, "", "", hp_tel, "", "");
                            } else {
                                Toast.makeText(this, "핸드폰 번호를 정확히 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "핸드폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnReqSecure1: // 이메일 인증
                if (fIntent.getStringExtra("findID").equals("0")) {
                    // 아이디찾기
                    if (etFudName.getText().length() != 0) {
                        cont_name = etFudName.getText().toString();
                        if (etFindEmail.getText().length() != 0) {
                            // 정규식 실행
                            boolean mailTF = isEmail(etFindEmail.getText().toString());
                            if (mailTF) {
                                // 인증 api실행
                                email_Addr = etFindEmail.getText().toString();
                                getVerifyNo(idpw, mbtype, hpmail, mb_id, "", cont_name, "", "", email_Addr, "");
                            } else {
                                Toast.makeText(this, "이메일이 정확하지 않습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }

                } else if (fIntent.getStringExtra("findPassword").equals("1")) {
                    // 비밀번호찾기
                    if (etFindEmail.getText().length() != 0) {
                        // 정규식 실행
                        boolean mailTF = isEmail(etFindEmail.getText().toString());
                        if (mailTF) {
                            // 인증 api실행
                            email_Addr = etFindEmail.getText().toString();
                            getVerifyNo(idpw, mbtype, hpmail, mb_id, "", "", "", "", email_Addr, "");
                        } else {
                            Toast.makeText(this, "이메일이 정확하지 않습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnReqUserDt:
                if (fIntent.getStringExtra("findID").equals("0")) {
                    // 아이디찾기
                    if (fud1RBtn1.isChecked()) { // 휴대폰 인증
                        String r1Name = etFudName.getText().toString();
                        String r1Hp = etfudHp.getText().toString();
                        String r1SecureTxt = etSecureNum.getText().toString();
                        if (r1Name.length() != 0) {
                            if (r1Hp.length() != 0) {
                                if (r1SecureTxt.length() != 0) {
                                    // 아이디 Search api 실행
                                    verify_No = r1SecureTxt;
                                    requestCheckVerifyNo(idpw, mbtype, hpmail, mb_id, "", r1Name, "", r1Hp, "", verify_No, verify_idx);
                                } else {
                                    Toast.makeText(this, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "핸드폰번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String r1Name1 = etFudName.getText().toString();
                        String r1Email1 = etFindEmail.getText().toString();
                        String r1SecureTxt1 = etSecureNum.getText().toString();
                        if (r1Name1.length() != 0) {
                            if (r1Email1.length() != 0) {
                                if (r1SecureTxt1.length() != 0) {
                                    // 아이디 Search api 실행
                                    verify_No = r1SecureTxt1;
                                    requestCheckVerifyNo(idpw, mbtype, hpmail, mb_id, "", r1Name1, "", "", r1Email1, verify_No, verify_idx);
                                } else {
                                    Toast.makeText(this, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (fIntent.getStringExtra("findPassword").equals("1")) {
                    // 비밀번호찾기
                    if (fud1RBtn1.isChecked()) {
                        String r1ID = etFudID.getText().toString();
                        String r1Hp = etfudHp.getText().toString();
                        String r1SecureTxt = etSecureNum.getText().toString();
                        if (r1ID.length() != 0) {
                            if (r1Hp.length() != 0) {
                                if (r1SecureTxt.length() != 0) {
                                    // 아이디 Search api 실행
                                    requestCheckVerifyNo(idpw, mbtype, hpmail, mb_id, r1ID, "", "", r1Hp, "", r1SecureTxt, verify_idx);
                                } else {
                                    Toast.makeText(this, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "핸드폰번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        String r1Email1 = etFindEmail.getText().toString();
                        String r1SecureTxt1 = etSecureNum.getText().toString();
                        if (r1Email1.length() != 0) {
                            if (r1SecureTxt1.length() != 0) {
                                // 아이디 Search api 실행
                                requestCheckVerifyNo(idpw, mbtype, hpmail, mb_id, "", "", "", "", r1Email1, r1SecureTxt1, verify_idx);
                            } else {
                                Toast.makeText(this, "인증번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    private static boolean isEmail(String email) {

        String regex = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";
        if (email == null) {
            return false;
        } else {
            boolean b = Pattern.matches(regex, email.trim());
            return b;
        }
    }

    private static boolean isHp(String hpNum) {
        String regex = "^01([0|1|6|7|8|9])([0-9]{7,8})$";
        if (hpNum == null) {
            return false;
        } else {
            boolean h = Pattern.matches(regex, hpNum);
            return h;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_foward, R.anim.slide_out_right);
        finish();
    }

    public void getVerifyNo(String idpw,
                            String mbt,
                            String hp_mail,
                            String mb_id,
                            String con_id,
                            String con_name,
                            String pw,
                            String hpNo,
                            String mailAd,
                            String v_no) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("IDPW", idpw);
            params.put("MBTYPE", mbt);
            params.put("HPMAIL", hp_mail);
            params.put("MB_ID", mb_id);
            params.put("CONSULTANT_ID", con_id);
            params.put("CONSULTANT_NAME", con_name);
            params.put("PASSWORD", pw);
            params.put("MOBILE_TEL", hpNo);
            params.put("EMAIL", mailAd);
            params.put("VERIFY_NO", v_no);
            LogUtil.d(TAG, "파라미터 값 = " + params);
            mAquery.ajax(baseURL+"/getVerifyNo.ajax", params, JSONObject.class, this, "responseVerifyNo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void responseVerifyNo(String url, JSONObject json, AjaxStatus status) {
        try {
            LogUtil.d(TAG, "리턴 결과 = " + json.toString());
            LogUtil.d(TAG, "상태 값 확인 = " + status.toString());
            String statOK = getString(R.string.statOK);
            String statFail = getString(R.string.statFail);
            String statLoginApi = getString(R.string.statLoginApi);
            String stat = json.get("result").toString();
            String statRe = stat.split("[|]")[0];
            if (statRe.equals("OK")) {
                verify_idx = json.get("verify_idx").toString();
                showDialog(statOK);
            } else {
                if (statRe.equals("FAIL")) {
                    showDialog(statFail);
                } else if (statRe.equals("LOGIN_API|")) {
                    showDialog(statLoginApi);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestCheckVerifyNo(String mIdpw,
                                     String imbtype,
                                     String mHpmail,
                                     String iMbid,
                                     String mCont_id,
                                     String mCont_nm,
                                     String mPw,
                                     String mHp_No,
                                     String mEmail,
                                     String mV_No,
                                     String mV_idx) {
        Map<String, String> cParams = new HashMap<String, String>();
        cParams.put("IDPW", mIdpw);
        cParams.put("MBTYPE", imbtype);
        cParams.put("HPMAIL", mHpmail);
        cParams.put("MB_ID", iMbid);
        cParams.put("CONSULTANT_ID", mCont_id);
        cParams.put("CONSULTANT_NAME", mCont_nm);
        cParams.put("PASSWORD", mPw);
        cParams.put("MOBILE_TEL", mHp_No);
        cParams.put("EMAIL", mEmail);
        cParams.put("VERIFY_NO", mV_No);
        cParams.put("VERIFY_IDX", mV_idx);
        LogUtil.d(TAG, "파라미터 값 = " + cParams);
        mAquery.ajax(baseURL+"/checkVerifyNo.ajax", cParams, JSONObject.class, this, "responseCheckVal");
    }

    public void responseCheckVal(String url, JSONObject json, AjaxStatus status) {
        try {
            LogUtil.d(TAG, "파라미터 내용 = " + json.toString());
            LogUtil.d(TAG, "상태 값 = " + status.toString());
            String id_ok = getString(R.string.id_ok);
            String pw_ok = getString(R.string.pw_ok);
            String fc_fail = getString(R.string.fc_fail);
            String login_api = getString(R.string.login_api);
            String idpw_fail = getString(R.string.idpw_fail);
            String stat1 = json.get("result").toString();
            String statRes = stat1.split("[|]")[0];
            LogUtil.d(TAG, "변환된 값 = " + statRes);
            switch (statRes) {
                case "ID_OK" :
                    showDialog1(id_ok, statRes);
                    break;
                case "PW_OK" :
                    showDialog1(pw_ok, statRes);
                    break;
                case "FC_FAIL" :
                    showDialog(fc_fail);
                    break;
                case "LOGIN_API" :
                    showDialog(login_api);
                    break;
                case "ID_FAIL" :
                    showDialog(idpw_fail);
                    break;
                case "PW_FAIL" :
                    showDialog(idpw_fail);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void showDialog(String msg) {
        new AlertDialog.Builder(FindUserDataActivity.this)
                .setTitle("")
                .setMessage(msg)
                .setPositiveButton(getString(R.string.submit), new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    public void showDialog1(String msg, String result) {
        switch (result) {
            case "ID_OK":
                new AlertDialog.Builder(FindUserDataActivity.this)
                        .setTitle("")
                        .setMessage(msg)
                        .setPositiveButton(getString(R.string.submit), new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Intent
                                Intent go_checkID = new Intent(FindUserDataActivity.this, RebuildUserDataActivity.class);
                                startActivity(go_checkID);
                                dialog.cancel();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
                break;
            case "PW_OK":
                new AlertDialog.Builder(FindUserDataActivity.this)
                        .setTitle("")
                        .setMessage(msg)
                        .setPositiveButton(getString(R.string.submit), new AlertDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Intent
                                Intent go_renew_pw = new Intent(FindUserDataActivity.this, RebuildUserDataActivity.class);
                                startActivity(go_renew_pw);
                                dialog.cancel();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
                break;
        }
    }
}
