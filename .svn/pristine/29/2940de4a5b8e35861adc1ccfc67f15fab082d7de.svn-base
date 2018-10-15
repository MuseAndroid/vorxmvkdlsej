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

public class Credit2Dialog extends Dialog {

    public Credit2Dialog (Context context)
    {
        super (context);
    }

    WebView creditView2;

    TextView credit2Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_credit2);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        credit2Title = (TextView) findViewById(R.id.credit2Title);
        creditView2 = (WebView) findViewById(R.id.creditView2);

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);
    }

    public void setContent2body(String Body) {
//        creditView2.loadData(Body, "text/html", "UTF-8");
        creditView2.loadUrl(Body);
    }

    public void dialogDestroy() {
        creditView2.clearCache(true);
        creditView2.clearHistory();
        creditView2.destroy();
    }
}
