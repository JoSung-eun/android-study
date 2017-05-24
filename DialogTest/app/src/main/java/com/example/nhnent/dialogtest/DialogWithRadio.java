package com.example.nhnent.dialogtest;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

/**
 * Created by nhnent on 2017. 5. 24..
 */

public class DialogWithRadio extends DialogFragment {
    RadioDialogListener2 listener;

    private RadioGroup radioGroup;
    private Button positiveButton;
    private Button negativeButton;

    private int checkedIndex;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RadioDialogListener2) getActivity();
        } catch (ClassCastException e) {
            Log.d("radio", "error");
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                checkedIndex = checkedId;
            }
        });
    }

    public void setPositiveButton(Button positiveButton) {
        this.positiveButton = positiveButton;
        this.positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveClickListener(checkedIndex);
            }
        });
    }

    public void setNegativeButton(Button negativeButton, final RadioDialogListener2 listener) {
        this.negativeButton = negativeButton;
        this.negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNegativeClickListener();
            }
        });
    }

    public interface RadioDialogListener2 {
        void onPositiveClickListener(int checkedId);
        void onNegativeClickListener();
    }
}
