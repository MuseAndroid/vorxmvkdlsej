package kr.co.genexon.factFinder.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;

import kr.co.genexon.factFinder.R;

/**
 * Created by junho on . 9. 15.2017.
 */

public class Detail2Dialog extends Dialog {

    public Detail2Dialog(Context context) {
        super (context);
    }

    ImageButton iBtn_close;

    WebView uPersInfoView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_personal_info);

        uPersInfoView1 = (WebView) findViewById(R.id.wv_service_prom2);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);
    }

    public void setPersonalInfoBody(String Body) {
        uPersInfoView1.loadUrl(Body);
    }

    public void dialogDestroy() {
        uPersInfoView1.clearCache(true);
        uPersInfoView1.clearHistory();
        uPersInfoView1.destroy();
    }
}
