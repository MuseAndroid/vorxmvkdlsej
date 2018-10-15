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

public class Tel4Dialog extends Dialog {

    public Tel4Dialog (Context context) {
        super (context);
    }

    WebView telDocView4;

    TextView telDoc4Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_teldoc4);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        telDoc4Title = (TextView) findViewById(R.id.telDoc4Title);
        telDocView4 = (WebView) findViewById(R.id.telDocView4);

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);
    }

    public void setContent4body(String Body) {
//        telDocView4.loadData(Body, "text/html", "UTF-8");
        telDocView4.loadUrl(Body);
    }

    public void dialogDestroy() {
        telDocView4.clearCache(true);
        telDocView4.clearHistory();
        telDocView4.destroy();
    }
}

