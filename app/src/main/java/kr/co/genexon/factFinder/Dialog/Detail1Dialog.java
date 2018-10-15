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

public class Detail1Dialog extends Dialog {

    public Detail1Dialog(Context context) {
        super (context);
    }

    ImageButton iBtn_close;

    WebView uServiceView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_user_service);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        uServiceView1 = (WebView) findViewById(R.id.wv_service_prom1);

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);
    }

    public void setUserServBody(String Body) {
        uServiceView1.loadUrl(Body);
    }

    public void dialogDestroy() {
        uServiceView1.clearCache(true);
        uServiceView1.clearHistory();
        uServiceView1.destroy();
    }
}
