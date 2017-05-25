package com.example.nhnent.customdialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by nhnent on 2017. 5. 25..
 */

public class RadioDialogBuilder {
    private Context context;

    private ArrayList<String> radioBtnLabels;
    private View radioView;
    private RadioGroup radioGroup;
    private Button positiveBtn;
    private Button negativeBtn;

    private RadioDialogFragment radioDialogFragment;

    public RadioDialogBuilder(Context context, ArrayList<String> radioBtnLabels) {
        this.context = context;
        this.radioBtnLabels = radioBtnLabels;
        radioDialogFragment = RadioDialogFragment.getInstance(radioBtnLabels);
        initView();
    }

    private void initView() {
        radioView = LayoutInflater.from(context).inflate(R.layout.dialog_radio, null, false);
        radioGroup = (RadioGroup) radioView.findViewById(R.id.radio_group);
        for (String label : radioBtnLabels) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(label);
            radioGroup.addView(radioButton);
        }
    }

    public RadioDialogBuilder setPositiveButton(String label, final RadioDialogFragment.RadioDialogListener listener) {
        positiveBtn = (Button) radioView.findViewById(R.id.btn_positive);
        positiveBtn.setText(label);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveButtonClick(radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId())));
                radioDialogFragment.dismiss();
            }
        });

        return this;
    }

    public RadioDialogBuilder setNegativeButton(String label, final RadioDialogFragment.RadioDialogListener listener) {
        negativeBtn = (Button) radioView.findViewById(R.id.btn_negative);
        negativeBtn.setText(label);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNegativeButtonClick();
            }
        });

        return this;
    }

    public RadioDialogFragment create() {
        radioDialogFragment.setView(radioView);
        return radioDialogFragment;
    }

}
