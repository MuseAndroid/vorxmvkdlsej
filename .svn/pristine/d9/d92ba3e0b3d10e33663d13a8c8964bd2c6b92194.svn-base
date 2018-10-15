package kr.co.genexon.factFinder;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MemberJoinActivity extends BaseActivity implements View.OnClickListener {

    protected static final String TAG = "FactFinder";

    EditText mber_email;
    EditText mber_name;
    EditText mber_passwd;
    EditText mber_passwd_check;
    EditText mber_mobileNum;
    Button btnMber_join;
    ImageButton backBtn;

    String baseURL;

    AQuery mAQuery = new AQuery(this);

    SelectServerURL ssUrl = new SelectServerURL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_join);

        baseURL = ssUrl.getServerURL(); // SelectServerURL 클래스에서 서버 정보를 받아옴

        mber_email = findViewById(R.id.mber_email);
        mber_name = findViewById(R.id.mber_name);
        mber_passwd = findViewById(R.id.mber_passwd);
        mber_passwd_check = findViewById(R.id.mber_passwd_check);
        mber_mobileNum = findViewById(R.id.mber_mobileNum);
        btnMber_join = findViewById(R.id.btnMber_join);
        backBtn = findViewById(R.id.backBtn);

        btnMber_join.setOnClickListener(this);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMber_join:
                String email = mber_email.getText().toString();
                String mainPasswd = mber_passwd.getText().toString();
                String checkPasswd = mber_passwd_check.getText().toString();
                String phoneNum = mber_mobileNum.getText().toString();
                if (!email.equals(null) || !email.equals("")) {
                    boolean emailTF = isEmail(email);
                    if (!emailTF) {
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_InputMethod));
                        alertDialogBuilder.setTitle("이메일 형식");
                        alertDialogBuilder.setMessage("이메일 형식에 맞지 않습니다. 다시 입력하세요.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                } else {
                    Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                if (!mainPasswd.equals(null) || !mainPasswd.equals("") && !checkPasswd.equals(null) || !checkPasswd.equals("")) {
                    if (!mainPasswd.equals(checkPasswd) ) {
                        final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_InputMethod));
                        alertDialogBuilder1.setTitle("비밀번호 불일치");
                        alertDialogBuilder1.setMessage("비밀번호가 일치하지 않습니다. 다시 입력해주시기 바랍니다.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alertDialog1 = alertDialogBuilder1.create();
                        alertDialog1.show();
                    }
                } else {
                    Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                if (!phoneNum.equals(null) || !phoneNum.equals("")) {
                    boolean phoneYN = isCellPhone(phoneNum);
                    if (!phoneYN) {
                        final AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_InputMethod));
                        alertDialogBuilder2.setTitle("핸드폰번호 확인");
                        alertDialogBuilder2.setMessage("핸드폰번호 형식에 맞지 않습니다. 다시 입력해주세요.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alertDialog2 = alertDialogBuilder2.create();
                        alertDialog2.show();
                    }
                } else {
                    Toast.makeText(this, "핸드폰번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                if (!email.equals(null) || !email.equals("") && mainPasswd.equals(checkPasswd) && !phoneNum.equals(null) || !phoneNum.equals("")) {
                    requestNewMberData();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void requestNewMberData() {
        String email = mber_email.getText().toString();
        String name = mber_name.getText().toString();
        String passwd = mber_passwd.getText().toString();
        String mobileNum = mber_mobileNum.getText().toString();

        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("CONSULTANT_ID", email);
            params.put("CONSULTANT_NAME", name);
            params.put("PASSWORD", passwd);
            params.put("MOBILE_TEL", mobileNum);
            mAQuery.ajax(baseURL + "/join/joinSave.ajax", params, JSONObject.class, this, "requestJoinCallBack"); // request 목적지가 되는 url 추가해야 됨.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestJoinCallBack (String url, JSONObject json, AjaxStatus status) {
        LogUtil.d("testApp", "리턴 결과 = " + json.toString());
        LogUtil.d("testApp", "상태값 확인 = " + status.toString());

        if (json != null) {
            try {
                LogUtil.d(TAG, "리턴 결과 = " + json.toString());
                String resultStatus = json.getString("successYN");
                String returnMsg = json.getString("msg");
                if (resultStatus.equals("OK")) {
                    final AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_InputMethod));
                    alertDialogBuilder2.setTitle("가입확인");
                    alertDialogBuilder2.setMessage(returnMsg)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent LoginIntent = new Intent(MemberJoinActivity.this, LoginActivity.class);
                                    startActivity(LoginIntent);
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                }
                            });
                    AlertDialog alertDialog2 = alertDialogBuilder2.create();
                    alertDialog2.show();
                } else {

                    final AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_DeviceDefault_InputMethod));
                    alertDialogBuilder2.setTitle("");
                    alertDialogBuilder2.setMessage(returnMsg)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alertDialog2 = alertDialogBuilder2.create();
                    alertDialog2.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                viewError1("개인고객 회원가입 에러, 에러내용 : " + e.toString(), "NO_MB_ID", mber_email.getText().toString(), mber_name.getText().toString(), mber_mobileNum.getText().toString(), "", "");
            }
        }
    }

    public static boolean isEmail(String email) {
        if (email==null) return false;
        boolean b = Pattern.matches(
                "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+",
                email.trim());
        return b;
    }

    public static boolean isCellPhone(String str) {
        String regex = "^010([0-9]{7,8})$";
        String regex1 = "^01([1|6|7|8|9])([0-9]{7,8})$";
        if (str.length() == 11 || str.substring(0,3) == "010") {
            boolean h = Pattern.matches(regex, str);
            return h;
        } else if (str.length() >= 10 || str.substring(0,3) != "010") {
            boolean h = Pattern.matches(regex1, str);
            return h;
        } else {
            return false;
        }
    }
}
