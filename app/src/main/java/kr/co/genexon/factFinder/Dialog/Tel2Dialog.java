package kr.co.genexon.factFinder.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import kr.co.genexon.factFinder.R;

/**
 * Created by junho on . 9. 18.2017.
 */

public class Tel2Dialog extends Dialog {

    public Tel2Dialog (Context context) {
        super (context);
    }

    WebView telDocView2;

    TextView telDoc2Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_teldoc2);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        telDoc2Title = (TextView) findViewById(R.id.telDoc2Title);
        telDocView2 = (WebView) findViewById(R.id.telDocView2);

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);
    }

    public void setContent2body(String Body) {
//        telDocView2.loadData(Body, "text/html", "UTF-8");
        telDocView2.loadUrl(Body);
    }

    public void dialogDestroy() {
        telDocView2.clearCache(true);
        telDocView2.clearHistory();
        telDocView2.destroy();
    }
}
