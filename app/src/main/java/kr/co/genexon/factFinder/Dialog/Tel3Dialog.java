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

public class Tel3Dialog extends Dialog {

    public Tel3Dialog (Context context) {
        super (context);
    }

    WebView telDocView3;

    TextView telDoc3Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_teldoc3);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        telDoc3Title = (TextView) findViewById(R.id.telDoc3Title);
        telDocView3 = (WebView) findViewById(R.id.telDocView3);

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);
    }

    public void setContent3body(String Body) {
//        telDocView3.loadData(Body, "text/html", "UTF-8");
        telDocView3.loadUrl(Body);
    }

    public void dialogDestroy() {
        telDocView3.clearCache(true);
        telDocView3.clearHistory();
        telDocView3.destroy();
    }
}
