    package com.allinone.smartocity.retrofit;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import com.allinone.smartocity.R;


    public class ProgressHUD extends Dialog {
    public ProgressHUD(Context context) {
        super(context);
    }

    public ProgressHUD(Context context, int theme) {
        super(context, theme);
    }


    public static ProgressHUD show(Context context, boolean pink, String title) {
        ProgressHUD dialog = new ProgressHUD(context, R.style.MyProgressDialog);

        dialog.setTitle("");

        /*if (pink)
            dialog.setContentView(R.layout.pink_dialog_layout);
        else*/
            dialog.setContentView(R.layout.progress_dialog_layout);
        dialog.setCancelable(false);

       /* try {
            TextView msgTV = (TextView) dialog.findViewById(R.id.msgTV);
            if (TextUtils.isEmpty(title)) {
                msgTV.setText("");
                msgTV.setVisibility(View.GONE);
            } else {
                msgTV.setVisibility(View.GONE);
                msgTV.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;


        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
    }


}