package kr.co.genexon.factFinder;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.androidquery.AQuery;

import test.jinesh.captchaimageviewlib.CaptchaImageView;

public class RebuildUserDataActivity extends BaseActivity implements View.OnClickListener {

    ConstraintLayout cl_check_id;
    ScrollView sv_renew_password;
    TextView tvTitieBar_str;
    TextView resultUserId;
    Button btnMoveLogin;
    Button btn_find_password;
    TextView resultUserId1;
    EditText etWrite_pw;
    EditText etWrite_pw_re;
    CaptchaImageView captchaImageView;
    Button btn_captchaRf;
    EditText etWrite_captcha;
    Button btnMoveLogin1;
    Button btn_save_password;

    AQuery mAquery = new AQuery(this);

    SelectServerURL ssURL = new SelectServerURL();
    String baseURL = ssURL.getServerURL();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebuild_user_data);

        cl_check_id = findViewById(R.id.cl_check_id);
        sv_renew_password = findViewById(R.id.sv_renew_password);
        tvTitieBar_str = findViewById(R.id.toolbarTitle);
        resultUserId = findViewById(R.id.result_user_id);
        btnMoveLogin = findViewById(R.id.btnMoveLogin);
        btn_find_password = findViewById(R.id.btn_find_password);
        resultUserId1 = findViewById(R.id.result_user_id1);
        etWrite_pw = findViewById(R.id.etWrite_pw);
        etWrite_pw_re = findViewById(R.id.etWrite_pw_re);
        captchaImageView = findViewById(R.id.captchaView);
        btn_captchaRf = findViewById(R.id.btn_captchaRf);
        etWrite_captcha = findViewById(R.id.etWrite_captcha);
        btnMoveLogin1 = findViewById(R.id.btnMoveLogin1);
        btn_save_password = findViewById(R.id.btn_save_password);

        gettingUserID();

        tvTitieBar_str.setText("계정정보 확인/변경");

        captchaImageView.setCaptchaType(CaptchaImageView.CaptchaGenerator.BOTH);

        btnMoveLogin.setOnClickListener(this);
        btn_find_password.setOnClickListener(this);
        btnMoveLogin1.setOnClickListener(this);
        btn_captchaRf.setOnClickListener(this);
        btn_save_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMoveLogin:
                break;
            case R.id.btn_find_password:
                break;
            case R.id.btnMoveLogin1:
                break;
            case R.id.btn_captchaRf:
                captchaImageView.regenerate();
                break;
            case R.id.btn_save_password:
                break;
        }

    }

    private void gettingUserID()
    {

    }
}
