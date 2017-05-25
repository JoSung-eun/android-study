package com.example.nhnent.customdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import java.util.ArrayList;

/**
 * Created by nhnent on 2017. 5. 25..
 */

public class RadioDialogFragment extends BaseDialogFragment {
    private RadioGroup radioGroup;

    private RadioDialogListener listener;

    public static RadioDialogFragment getInstance(ArrayList<String> radioBtnLabels) {
        RadioDialogFragment radioDialogFragment = new RadioDialogFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("items", radioBtnLabels);
        radioDialogFragment.setArguments(args);
        return radioDialogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RadioDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement RadioDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayList<String> radioBtnLabels = getArguments().getStringArrayList("items");
        return new RadioDialog.Builder(getActivity(), radioBtnLabels)
                .setPositiveButton("ok", listener)
                .create();
    }

    @Override
    void bindContents() {
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
    }

    @Override
    void bindPositiveButton() {
        positiveBtn = (Button) view.findViewById(R.id.btn_positive);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()));

                listener.onPositiveButtonClick(checked);
                dismiss();
            }
        });
    }

    @Override
    void bindNegativeButton() {

    }

    public interface RadioDialogListener {
        void onPositiveButtonClick(int checkId);
        void onNegativeButtonClick();
    }

}
