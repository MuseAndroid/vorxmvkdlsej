package kr.co.genexon.factFinder.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import kr.co.genexon.factFinder.R;

public class FirstInfoDialog extends Dialog {

    Button btnOK;

    WebView firstWebView;

    public FirstInfoDialog(Context context) {
        super (context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_first_info);

        btnOK = findViewById(R.id.btnOK);
        firstWebView = findViewById(R.id.firstInfoView);

        String firstURL = "file:///android_asset/www/firstInfo.html";
        firstWebView.loadUrl(firstURL);

        final WindowManager.LayoutParams params = this.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirstInfoDialog.this.dismiss();
            }
        });
    }
}
