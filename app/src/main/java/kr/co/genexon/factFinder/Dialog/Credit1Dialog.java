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

public class Credit1Dialog extends Dialog {

    public Credit1Dialog (Context context) {
        super (context);
    }

    WebView creditView1;

    TextView credit1Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_credit1);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        credit1Title = (TextView) findViewById(R.id.credit1Title);
        creditView1 = (WebView) findViewById(R.id.creditView1);

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);
    }

    public void setContent1body(String Body) {
//        creditView1.loadData(Body, "text/html", "UTF-8");
        creditView1.loadUrl(Body);
    }

    public void dialogDestroy() {
        creditView1.clearHistory();
        creditView1.clearCache(true);
        creditView1.destroy();
    }


}
