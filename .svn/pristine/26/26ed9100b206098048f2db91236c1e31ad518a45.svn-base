package kr.co.genexon.factFinder.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import kr.co.genexon.factFinder.LogUtil;
import kr.co.genexon.factFinder.R;

/**
 * Created by junho on . 9. 8.2017.
 */

public class PhoneComSelDialog extends Dialog{

    protected static final String TAG = "FactFinder";

    public PhoneComSelDialog (Context context) {
        super (context);
    }

    ImageButton iBtn_skt;
    TextView iBtn_skt_title;
    ImageButton iBtn_kt;
    TextView iBtn_kt_title;
    ImageButton iBtn_lgt;
    TextView iBtn_lgt_title;
    ImageButton iBtn_skm;
    TextView iBtn_skm_title;
    ImageButton iBtn_ktm;
    TextView iBtn_ktm_title;
    ImageButton iBtn_lgm;
    TextView iBtn_lgm_title;

    private String tel_com_name = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_tel);

        iBtn_skt = findViewById(R.id.iBtn_skt); // 통신사 : SKT
        iBtn_skt_title = findViewById(R.id.iBtn_skt_title);
        iBtn_kt = findViewById(R.id.iBtn_kt); // 통신사 : KT
        iBtn_kt_title = findViewById(R.id.iBtn_kt_title);
        iBtn_lgt = findViewById(R.id.iBtn_lgt); // 통신사 : LGU+
        iBtn_lgt_title = findViewById(R.id.iBtn_lgt_title);
        iBtn_skm = findViewById(R.id.iBtn_skm); // 통신사 : SKT(알뜰폰)
        iBtn_skm_title = findViewById(R.id.iBtn_skm_title);
        iBtn_ktm = findViewById(R.id.iBtn_ktm); // 통신사 : KT(알뜰폰)
        iBtn_ktm_title = findViewById(R.id.iBtn_ktm_title);
        iBtn_lgm = findViewById(R.id.iBtn_lgm); // 통신사 : LGU+(알뜰폰)
        iBtn_lgm_title = findViewById(R.id.iBtn_lgm_title);


        iBtn_skt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel_com_name = "SKT";
                LogUtil.d(TAG, "선택된 통신사 = " + tel_com_name);
                dismiss();
            }
        });
        iBtn_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel_com_name = "KTF";
                LogUtil.d(TAG, "선택된 통신사 = " + tel_com_name);
                dismiss();
            }
        });
        iBtn_lgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel_com_name = "LGT";
                LogUtil.d(TAG, "선택된 통신사 = " + tel_com_name);
                dismiss();
            }
        });
        iBtn_skm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel_com_name = "SKM";
                LogUtil.d(TAG, "선택된 통신사 = " + tel_com_name);
                dismiss();
            }
        });
        iBtn_ktm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel_com_name = "KTM";
                LogUtil.d(TAG, "선택된 통신사 = " + tel_com_name);
                dismiss();
            }
        });
        iBtn_lgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tel_com_name = "LGM";
                LogUtil.d(TAG, "선택된 통신사 = " + tel_com_name);
                dismiss();
            }
        });
    }

    public String getComText()
    {
        return tel_com_name;
    }
}
