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

public class Credit3Dialog extends Dialog {

    public Credit3Dialog (Context context) {
        super (context);
    }

    WebView creditView3;

    TextView credit3Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_credit3);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        credit3Title = (TextView) findViewById(R.id.credit3Title);
        creditView3 = (WebView) findViewById(R.id.creditView3);

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);
    }

    public void setContent3body(String Body) {
//        creditView3.loadData(Body, "text/html", "UTF-8");
        creditView3.loadUrl(Body);
    }

    public void dialogDestroy() {
        creditView3.clearHistory();
        creditView3.clearCache(true);
        creditView3.destroy();
    }
}
