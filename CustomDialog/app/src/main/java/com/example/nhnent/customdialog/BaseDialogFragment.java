package com.example.nhnent.customdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhnent on 2017. 5. 25..
 */

public abstract class BaseDialogFragment extends DialogFragment {
    public static final int BUTTON_POSITIVE = -1;
    public static final int BUTTON_NEGATIVE = -2;
    public static final int BUTTON_NEUTRAL = -3;

    protected View view;

    protected void setTitle(String text) {
        TextView titleText = (TextView) view.findViewById(R.id.text_title);
        titleText.setText(text);
    }

    protected void setPositiveButton(String label, final OnClickListener listener) {
        Button positiveBtn = (Button) view.findViewById(R.id.btn_positive);
        positiveBtn.setText(label);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(BaseDialogFragment.this, BUTTON_POSITIVE);
            }
        });
    }

    protected void setNegativeButton(String label, final OnClickListener listener) {
        Button negativeBtn = (Button) view.findViewById(R.id.btn_negative);
        negativeBtn.setText(label);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(BaseDialogFragment.this, BaseDialogFragment.BUTTON_NEGATIVE);
            }
        });
    }

    protected void setNeutralButton(String label, final OnClickListener listener) {
        Button neutralBtn = (Button) view.findViewById(R.id.btn_neutral);
        neutralBtn.setText(label);
        neutralBtn.setVisibility(View.VISIBLE);
        neutralBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(BaseDialogFragment.this, BaseDialogFragment.BUTTON_NEUTRAL);
            }
        });
    }

    interface OnClickListener {
        void onClick(DialogFragment dialog, int which);
    }

    public static class DialogParams implements Serializable {
        public String title;
        public String contentsText;

        public String positiveBtnLabel;
        public String negativeBtnLabel;
        public String neutralBtnLabel;

        public ArrayList<String> items;

        public OnClickListener positiveBtnClickListener;
        public OnClickListener negativeBtnClickListener;
        public OnClickListener neutralBtnClickListener;
    }
}
