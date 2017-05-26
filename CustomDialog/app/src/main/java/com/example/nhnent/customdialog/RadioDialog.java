package com.example.nhnent.customdialog;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nhnent on 2017. 5. 25..
 */

public class RadioDialog extends BaseDialogFragment {
    private RadioGroup radioGroup;

    public static RadioDialog createInstance(BaseDialogFragment.DialogParams params) {
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
            String title = params.title;

            ArrayList<String> items = params.items;

            String positiveBtnLabel = params.positiveBtnLabel;
            String negativeBtnLabel = params.negativeBtnLabel;
            String neutralBtnLabel = params.neutralBtnLabel;

            final OnClickListener positiveBtnListener = params.positiveBtnClickListener;
            final OnClickListener negativeBtnListener = params.negativeBtnClickListener;
            final OnClickListener neutralBtnListener = params.neutralBtnClickListener;

            radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
            if (items != null) { // TODO: 2017. 5. 26. setContents
                for (String label : items) {
                    RadioButton radioButton = new RadioButton(getContext());
                    radioButton.setText(label);
                    radioGroup.addView(radioButton);
                }
            }

            if (title != null) {
                setTitle(title);
            }

            if (positiveBtnLabel != null && positiveBtnListener != null) {
                setPositiveButton(positiveBtnLabel, positiveBtnListener);
            }

            if (negativeBtnLabel != null && negativeBtnListener != null) {
                setNegativeButton(negativeBtnLabel, negativeBtnListener);
            }

            if (neutralBtnLabel != null && neutralBtnListener != null) {
                setNeutralButton(neutralBtnLabel, neutralBtnListener);
            }
        }

        return view;
    }

    @Override
    protected void setPositiveButton(String label, final OnClickListener listener) {
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

    public static class Builder {
        private BaseDialogFragment.DialogParams params;

        public Builder() {
            params = new BaseDialogFragment.DialogParams();
        }

        public Builder setTitle(String title) {
            params.title = title;
            return this;
        }

        public Builder setRadioItems(ArrayList<String> items) {
            params.items = items;
            return this;
        }

        public Builder setPositiveButton(String label, BaseDialogFragment.OnClickListener listener) {
            params.positiveBtnLabel = label;
            params.positiveBtnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String label, BaseDialogFragment.OnClickListener listener) {
            params.negativeBtnLabel = label;
            params.negativeBtnClickListener = listener;
            return this;
        }

        public Builder setNeutralButton(String label, BaseDialogFragment.OnClickListener listener) {
            params.neutralBtnLabel = label;
            params.neutralBtnClickListener = listener;
            return this;
        }

        public RadioDialog create() {
            return RadioDialog.createInstance(params);
        }
    }

}
