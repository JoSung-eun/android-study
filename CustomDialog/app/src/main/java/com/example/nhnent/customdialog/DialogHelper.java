package com.example.nhnent.customdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhnent on 2017. 5. 24..
 */

public class DialogHelper {
    private Context context;

    public DialogHelper(Context context) {
        this.context = context;
    }

    public RadioDialogFragment createRadioDialog(List<String> items) {
        View radioView = LayoutInflater.from(context).inflate(R.layout.dialog_radio, null, false);
        RadioGroup radioGroup = (RadioGroup) radioView.findViewById(R.id.radio_group);
        for (String item : items) {
            RadioButton radioButton = new RadioButton(radioView.getContext());
            radioButton.setText(item);
            radioGroup.addView(radioButton);
        }

        RadioDialogFragment radioDialogFragment = new RadioDialogFragment();
        radioDialogFragment.setView(radioView);

        return radioDialogFragment;
    }

    public RadioDialogFragment createRadioDialog(ArrayList<String> items, RadioDialogFragment.RadioDialogListener listener) {
        RadioDialogBuilder radioDialogBuilder = new RadioDialogBuilder(context, items);
        return radioDialogBuilder.setPositiveButton("ok", listener)
                .setNegativeButton("nene", listener)
                .create();

//        return RadioDialogFragment.getInstance(items);
    }

    public SimpleAlertDialog createSimpleAlertDialog(String text) {
        View alertView = LayoutInflater.from(context).inflate(R.layout.dialog_simple_alert, null, false);
        TextView textView = (TextView) alertView.findViewById(R.id.text_contents);
        textView.setText(text);

        SimpleAlertDialog simpleAlertDialog = new SimpleAlertDialog();
        simpleAlertDialog.setView(alertView);

        return simpleAlertDialog;
    }

}
