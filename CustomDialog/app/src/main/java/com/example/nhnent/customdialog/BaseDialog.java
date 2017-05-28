package com.example.nhnent.customdialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nhnent on 2017. 5. 25..
 */

public class BaseDialog extends DialogFragment {
    public static final int BUTTON_POSITIVE = -1;
    public static final int BUTTON_NEGATIVE = -2;
    public static final int BUTTON_NEUTRAL = -3;

    protected View view;

    public static BaseDialog createInstance(DialogParams params) {
        BaseDialog baseDialog = new BaseDialog();
        Bundle args = new Bundle();
        args.putSerializable("params", params);
        baseDialog.setArguments(args);
        return baseDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);
    }

    protected void setTitle(String text) {
        if (text != null) {
            TextView titleText = (TextView) view.findViewById(R.id.text_title);
            titleText.setText(text);
        }
    }

    protected void setPositiveButton(String label, final OnClickListener listener) {
        if (label != null && listener != null) {
            Button positiveBtn = (Button) view.findViewById(R.id.btn_positive);
            positiveBtn.setText(label);
            positiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(BaseDialog.this, BUTTON_POSITIVE);
                }
            });
        }
    }

    protected void setNegativeButton(String label, final OnClickListener listener) {
        if (label != null && listener != null) {
            Button negativeBtn = (Button) view.findViewById(R.id.btn_negative);
            negativeBtn.setText(label);
            negativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(BaseDialog.this, BaseDialog.BUTTON_NEGATIVE);
                }
            });
        }
    }

    protected void setNeutralButton(String label, final OnClickListener listener) {
        if (label != null && listener != null) {
            Button neutralBtn = (Button) view.findViewById(R.id.btn_neutral);
            neutralBtn.setText(label);
            neutralBtn.setVisibility(View.VISIBLE);
            neutralBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(BaseDialog.this, BaseDialog.BUTTON_NEUTRAL);
                }
            });
        }
    }

    interface OnClickListener {
        void onClick(DialogFragment dialog, int which);
    }

    public static class DialogParams implements Serializable, Parcelable {

        public String title;
        public String contentsText;

        public String positiveBtnLabel;
        public String negativeBtnLabel;
        public String neutralBtnLabel;

        public ArrayList<String> items;

        public OnClickListener positiveBtnClickListener;
        public OnClickListener negativeBtnClickListener;
        public OnClickListener neutralBtnClickListener;

        public static final Parcelable.Creator CREATOR = new Creator() {
            @Override
            public Object createFromParcel(Parcel source) {
                return null;
            }

            @Override
            public Object[] newArray(int size) {
                return new Object[0];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }
}
