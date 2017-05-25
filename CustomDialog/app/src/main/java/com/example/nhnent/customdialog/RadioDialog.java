package com.example.nhnent.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by nhnent on 2017. 5. 25..
 */

public class RadioDialog extends Dialog {
    public RadioDialog(@NonNull Context context) {
        super(context);
    }

    public RadioDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public RadioDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class Builder {
        private Context context;

        private ArrayList<String> radioBtnLabels;
        private View radioView;
        private RadioGroup radioGroup;
        private Button positiveBtn;

        private RadioDialog radioDialog;

        public Builder(Context context, ArrayList<String> radioBtnLabels) {
            this.context = context;
            this.radioBtnLabels = radioBtnLabels;
            radioDialog = new RadioDialog(context);
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

//    @Override
//    public AlertDialog.Builder setView(View view) {
//        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
//        for (String label : radioBtnLabels) {
//            RadioButton radioButton = new RadioButton(getContext());
//            radioButton.setText(label);
//            radioGroup.addView(radioButton);
//        }
//        return super.setView(view);
//    }

        public Builder setPositiveButton(String label, final RadioDialogFragment.RadioDialogListener listener) {
            positiveBtn = (Button) radioView.findViewById(R.id.btn_positive);
            positiveBtn.setText(label);
            positiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPositiveButtonClick(radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId())));
                    radioDialog.dismiss();
                }
            });

            return this;
        }

        public RadioDialog create() {

            return radioDialog;
        }

    }
}
