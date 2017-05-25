package com.example.nhnent.dialogtest;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

/**
 * Created by nhnent on 2017. 5. 24..
 */

public class RadioDialogBuilder extends AlertDialog.Builder {
    private RadioGroup radioGroup;
    private Button positiveButton;
    private Button negativeButton;

    private int checkedIndex;

    public interface RadioDialogListener {
        void onPositiveClickListener(int checkedId);
        void onNegativeClickListener();
    }

    public RadioDialogBuilder(@NonNull Context context) {
        super(context);
    }

    @Override
    public AlertDialog create() {
        return super.create();
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
//        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                checkedIndex = checkedId;
//            }
//        });
    }

    public void setPositiveButton(Button positiveButton, final RadioDialogListener listener) {
        this.positiveButton = positiveButton;
        this.positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveClickListener(radioGroup.getCheckedRadioButtonId());
            }
        });
    }

    public void setNegativeButton(Button negativeButton, final RadioDialogListener listener) {
        this.negativeButton = negativeButton;
        this.negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNegativeClickListener();
            }
        });
    }


}
