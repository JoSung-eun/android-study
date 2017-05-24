package com.example.nhnent.dialogtest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by nhnent on 2017. 5. 24..
 */

public class DialogHelper {

    private DialogListener listener;

    public interface DialogListener {
        void onDialogItemClick(int which);
        void onDialogMultipleItemClick(int which, boolean isChecked);
    }

    public static AlertDialog.Builder createSimpleListDialog(Context context, CharSequence[] items, final DialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDialogItemClick(which);
            }
        });

        return builder;
    }

    public static AlertDialog.Builder createRadioButtonDialog(Context context, CharSequence[] items, int checked, final DialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setSingleChoiceItems(items, checked, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDialogItemClick(which);
            }
        });

        return builder;
    }

    public AlertDialog.Builder createCheckBoxDialog(Context context, CharSequence[] items, boolean[] checked, final DialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMultiChoiceItems(items, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                listener.onDialogMultipleItemClick(which, isChecked);
            }
        });

        return builder;
    }

}
