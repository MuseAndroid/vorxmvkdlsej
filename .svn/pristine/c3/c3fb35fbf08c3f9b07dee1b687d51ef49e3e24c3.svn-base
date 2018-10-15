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

public class Tel1Dialog extends Dialog {

    public Tel1Dialog (Context context) {
        super (context);
    }

    WebView telDocView1;

    TextView telDoc1Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_teldoc1);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        telDoc1Title = (TextView) findViewById(R.id.telDoc1Title);
        telDocView1 = (WebView) findViewById(R.id.telDocView1);

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);
    }

    public void setContent1body(String Body) {
//        telDocView1.loadData(Body, "text/html", "UTF-8");
        telDocView1.loadUrl(Body);
    }

    public void dialogDestroy() {
        telDocView1.clearCache(true);
        telDocView1.clearHistory();
        telDocView1.destroy();
    }

}
