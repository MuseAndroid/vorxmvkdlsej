package kr.co.genexon.factFinder.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import kr.co.genexon.factFinder.R;

/**
 * Created by junho on . 9. 15.2017.
 */

public class InfoContentDialog extends Dialog {

    public InfoContentDialog(Context context) {
        super (context);
    }

    ImageButton iBtn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_info_view);

        WindowManager.LayoutParams params = this.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        this.getWindow().setAttributes(params);
    }
}
