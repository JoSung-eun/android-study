package com.example.nhnent.customdialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by nhnent on 2017. 5. 25..
 */

public class RadioDialog extends BaseDialog {
    private RadioGroup radioGroup;

    public static RadioDialog createInstance(DialogParams params) {
        RadioDialog radioDialog = new RadioDialog();
        Bundle args = new Bundle();
        args.putSerializable("params", params);
        radioDialog.setArguments(args);
        return radioDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_radio, container);

        Bundle args = getArguments();
        DialogParams params = (DialogParams) args.getSerializable("params");

        if (params != null) {
            radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);

            setRadioItems(params.items);

            setTitle(params.title);

            setPositiveButton(params.positiveBtnLabel, params.positiveBtnClickListener);
            setNegativeButton(params.negativeBtnLabel, params.negativeBtnClickListener);
            setNeutralButton(params.neutralBtnLabel, params.neutralBtnClickListener);
        }

        return view;
    }

    private void setRadioItems(ArrayList<String> items) {
        if (items != null) {
            for (String label : items) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(label);
                radioButton.setButtonDrawable(R.drawable.radio_selector);
                radioButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                radioGroup.addView(radioButton);
            }
        }
    }

    @Override
    protected void setPositiveButton(String label, final OnClickListener listener) {
        if (label != null && listener != null) {
            Button positiveBtn = (Button) view.findViewById(R.id.btn_positive);
            positiveBtn.setText(label);
            positiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    int checkedIndex = radioGroup.indexOfChild(radioGroup.findViewById(checkedId));
                    listener.onClick(RadioDialog.this, checkedIndex);
                }
            });
        }
    }

    public static class Builder {
        private BaseDialog.DialogParams params;

        public Builder() {
            params = new BaseDialog.DialogParams();
        }

        public Builder setTitle(String title) {
            params.title = title;
            return this;
        }

        public Builder setRadioItems(ArrayList<String> items) {
            params.items = items;
            return this;
        }

        public Builder setPositiveButton(String label, BaseDialog.OnClickListener listener) {
            params.positiveBtnLabel = label;
            params.positiveBtnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String label, BaseDialog.OnClickListener listener) {
            params.negativeBtnLabel = label;
            params.negativeBtnClickListener = listener;
            return this;
        }

        public Builder setNeutralButton(String label, BaseDialog.OnClickListener listener) {
            params.neutralBtnLabel = label;
            params.neutralBtnClickListener = listener;
            return this;
        }

        public RadioDialog create() {
            return RadioDialog.createInstance(params);
        }
    }

}
