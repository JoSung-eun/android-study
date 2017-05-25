package com.example.nhnent.customdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

/**
 * Created by nhnent on 2017. 5. 24..
 */

public class DialogWithRadio extends DialogFragment {
    RadioDialogListener2 listener;

    private View view;

    private RadioGroup radioGroup;
    private Button positiveButton;
    private Button negativeButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        radioGroup = (RadioGroup) view.findViewWithTag("radio");
        positiveButton = (Button) view.findViewWithTag("pos_btn");
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveClickListener(radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId())));
                dismiss();
            }
        });
        negativeButton = (Button) view.findViewWithTag("neg_btn");
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            Log.d("radio", "attach");
            listener = (RadioDialogListener2) getActivity();
        } catch (ClassCastException e) {
            Log.d("radio", "error");
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
    }

    public void setView(View view) {
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        view = inflater.inflate(layoutId, null);
        this.view = view;
    }

    public void setPositiveButton(Button positiveButton) {
        Log.d("radio", "set button");
        this.positiveButton = positiveButton;
        this.positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("radio", "click");
                listener.onPositiveClickListener(radioGroup.getCheckedRadioButtonId());
                DialogWithRadio.this.dismiss();
            }
        });
    }



    public void setNegativeButton(Button negativeButton, final RadioDialogListener2 listener) {
        this.negativeButton = negativeButton;
        this.negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNegativeClickListener(DialogWithRadio.this);
            }
        });
    }

    public interface RadioDialogListener2 {
        void onPositiveClickListener(int checkedId);
        void onNegativeClickListener(DialogFragment dialogFragment);
    }
}
