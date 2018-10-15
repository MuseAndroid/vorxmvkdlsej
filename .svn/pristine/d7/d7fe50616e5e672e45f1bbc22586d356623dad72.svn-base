package kr.co.genexon.factFinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import kr.co.genexon.factFinder.Dialog.Detail1Dialog;
import kr.co.genexon.factFinder.Dialog.Detail2Dialog;

public class AgreeInfoUseActivity extends BaseActivity implements View.OnClickListener {

    protected static final String TAG = "FactFinder";

    ImageButton backBtn; // 뒤로가기 버튼

    CheckBox allAgreeChkBtn; // 모두동의 체크버튼
    TextView allAgreeChkTitle;

    CheckBox userServiceChk; // 서비스 이용약관 체크버튼
    TextView userServiceTitle;
    ImageButton detailBtn1; // 서비스 이용약관 보기 버튼

    CheckBox personalInfoChk; // 개인정보 수집 및 이용에 대한 안내 체크버튼
    TextView personalInfoTitle;
    ImageButton detailBtn2; // 개인정보 수집 및 이용

    Button btnNextGo; // 회원가입 화면으로 이동하는 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_info_use);

        backBtn = findViewById(R.id.backBtn);
        allAgreeChkBtn = findViewById(R.id.allAgreeChkBtn);
        allAgreeChkTitle = findViewById(R.id.allChkTitle);

        userServiceChk = findViewById(R.id.userServiceChk);
        userServiceTitle = findViewById(R.id.userServiceTitle);

        detailBtn1 = findViewById(R.id.detailBtn1);
        personalInfoChk = findViewById(R.id.personalInfoChk);
        personalInfoTitle = findViewById(R.id.personalInfoTitle);

        detailBtn2 = findViewById(R.id.detailBtn2);
        btnNextGo = findViewById(R.id.btnNextGo);

        backBtn.setOnClickListener(this);
        detailBtn1.setOnClickListener(this);
        detailBtn2.setOnClickListener(this);
        btnNextGo.setOnClickListener(this);

        allAgreeChkBtn.setOnClickListener(this);
        allAgreeChkTitle.setOnClickListener(this);
        userServiceChk.setOnClickListener(this);
        userServiceTitle.setOnClickListener(this);
        personalInfoChk.setOnClickListener(this);
        personalInfoTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.backBtn:
                onBackPressed();
                break;
            case R.id.allAgreeChkBtn:
                if (allAgreeChkBtn.isChecked()) {
                    userServiceChk.setChecked(true);
                    personalInfoChk.setChecked(true);
                } else {
                    userServiceChk.setChecked(false);
                    personalInfoChk.setChecked(false);
                }
                break;
            case R.id.allChkTitle:
                allChkFn();
                break;
            case R.id.userServiceChk:
                if (userServiceChk.isChecked()) {
                    if (personalInfoChk.isChecked()) {
                        allAgreeChkBtn.setChecked(true);
                    }
                } else {
                    if (allAgreeChkBtn.isChecked()) {
                        allAgreeChkBtn.setChecked(false);
                    }
                }
                break;
            case R.id.userServiceTitle:
                userServChkFn();
                break;
            case R.id.detailBtn1:
                riseDetailPopup1();
                break;
            case R.id.personalInfoChk:
                if (personalInfoChk.isChecked()) {
                    if (userServiceChk.isChecked()) {
                        allAgreeChkBtn.setChecked(true);
                    }
                } else {
                    if (allAgreeChkBtn.isChecked()) {
                        allAgreeChkBtn.setChecked(false);
                    }
                }
                break;
            case R.id.personalInfoTitle:
                personalInfoChkFn();
                break;
            case R.id.detailBtn2:
                riseDetailPopup2();
                break;
            case R.id.btnNextGo:
                if (!userServiceChk.isChecked()) {
                    Toast.makeText(this, "서비스 이용에 대해 동의해 주세요", Toast.LENGTH_SHORT).show();
                } else if (!personalInfoChk.isChecked()) {
                    Toast.makeText(this, "개인정보 수집/이용에 대해 동의해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Intent nextIntent = new Intent(this, MemberJoinActivity.class);
                    startActivity(nextIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                break;
        }
    }

    public void riseDetailPopup1() {
        final Detail1Dialog dialogWindow1 = new Detail1Dialog(this);

        dialogWindow1.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                dialogWindow1.setUserServBody("file:///android_asset/www/service_prom1.html");
            }
        });
        dialogWindow1.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogWindow1.dialogDestroy();
            }
        });
        WindowManager.LayoutParams lp = dialogWindow1.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogWindow1.show();
        dialogWindow1.getWindow().setAttributes((android.view.WindowManager.LayoutParams) lp);
    }

    public void riseDetailPopup2() {
        final Detail2Dialog dialogWindow2 = new Detail2Dialog(this);

        dialogWindow2.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                dialogWindow2.setPersonalInfoBody("file:///android_asset/www/service_prom2.html");
            }
        });
        dialogWindow2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogWindow2.dialogDestroy();
            }
        });
        WindowManager.LayoutParams lp = dialogWindow2.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialogWindow2.show();
        dialogWindow2.getWindow().setAttributes((android.view.WindowManager.LayoutParams) lp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void allChkFn() {
        if (!allAgreeChkBtn.isChecked()) {
            allAgreeChkBtn.setChecked(true);
            userServiceChk.setChecked(true);
            personalInfoChk.setChecked(true);
        } else {
            allAgreeChkBtn.setChecked(false);
            userServiceChk.setChecked(false);
            personalInfoChk.setChecked(false);
        }
    }

    public void userServChkFn() {
        if (!userServiceChk.isChecked()) {
            userServiceChk.setChecked(true);
            if (personalInfoChk.isChecked()) {
                allAgreeChkBtn.setChecked(true);
            }
        } else {
            userServiceChk.setChecked(false);
            allAgreeChkBtn.setChecked(false);
        }
    }

    public void personalInfoChkFn() {
        if (!personalInfoChk.isChecked()) {
            personalInfoChk.setChecked(true);
            if (userServiceChk.isChecked()) {
                allAgreeChkBtn.setChecked(true);
            }
        } else {
            personalInfoChk.setChecked(false);
            allAgreeChkBtn.setChecked(false);
        }
    }
}
