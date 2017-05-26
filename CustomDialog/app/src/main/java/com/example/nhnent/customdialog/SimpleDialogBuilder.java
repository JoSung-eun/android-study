package com.example.nhnent.customdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by nhnent on 2017. 5. 26..
 */

public class SimpleDialogBuilder {
    private Context context;

    private View alertView;
    private SimpleAlertDialog simpleDialog;

    public SimpleDialogBuilder(Context context) {
        this.context = context;
        simpleDialog = new SimpleAlertDialog();
        initView();
    }

    private void initView() {
        alertView = LayoutInflater.from(context).inflate(R.layout.dialog_simple_alert, null, false);
    }

    public SimpleDialogBuilder setContentsText(String text) {
        TextView contentsText = (TextView) alertView.findViewById(R.id.text_contents);
        contentsText.setText(text);
        return this;
    }

//    public SimpleDialogBuilder setPositiveButton(String label, final BaseDialogFragment.DialogListener listener) {
//        Button positiveBtn = (Button) alertView.findViewById(R.id.btn_positive);
//        positiveBtn.setText(label);
//        positiveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                simpleDialog.dismiss();
//            }
//        });
//
//        return this;
//    }
//
//    public SimpleDialogBuilder setNegativeButton(String label, final BaseDialogFragment.DialogListener listener) {
//        Button negativeBtn = (Button) alertView.findViewById(R.id.btn_negative);
//        negativeBtn.setText(label);
//        negativeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onNegativeButtonClick();
//                simpleDialog.dismiss();
//            }
//        });
//
//        return this;
//    }
//
//    public SimpleDialogBuilder setNeutralButton(String label, final BaseDialogFragment.DialogListener listener) {
//        Button neutralBtn = (Button) alertView.findViewById(R.id.btn_neutral);
//        neutralBtn.setText(label);
//        neutralBtn.setVisibility(View.VISIBLE);
//        neutralBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                simpleDialog.dismiss();
//            }
//        });
//        return this;
//    }
//
//    public SimpleAlertDialog create() {
//        simpleDialog.setView(alertView);
//        return simpleDialog;
//    }
}
