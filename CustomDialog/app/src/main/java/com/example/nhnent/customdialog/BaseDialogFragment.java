package com.example.nhnent.customdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

/**
 * Created by nhnent on 2017. 5. 25..
 */

public abstract class BaseDialogFragment extends DialogFragment {
    protected View view;
    protected Button positiveBtn;
    protected Button negativeBtn;

    protected DialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        bindContents();
        bindPositiveButton();
        bindNegativeButton();

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement NoticeDialogListener");
        }
    }

    public void setView(View view) {
        this.view = view;
    }

    abstract void bindContents();
    abstract void bindPositiveButton();
    abstract void bindNegativeButton();

    interface DialogListener {
        void onPositiveButtonClick(int checkedId);
        void onPositiveButtonClick(List<Integer> checkedIds);
        void onNegativeButtonClick();
    }
}
