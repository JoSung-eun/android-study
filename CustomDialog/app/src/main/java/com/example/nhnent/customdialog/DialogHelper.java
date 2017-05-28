package com.example.nhnent.customdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhnent on 2017. 5. 24..
 */

public class DialogHelper {
    public static RadioDialog createRadioDialog(ArrayList<String> items, String[] buttonLabels, BaseDialog.OnClickListener[] listeners) {
        RadioDialog.Builder builder = new RadioDialog.Builder();
        builder.setRadioItems(items);

        int buttonsSize = buttonLabels.length;
        if (buttonsSize > 0) {
            builder.setPositiveButton(buttonLabels[0], listeners[0]);
        }
        if (buttonsSize > 1) {
            builder.setNegativeButton(buttonLabels[1], listeners[1]);
        }
        if (buttonsSize > 2) {
            builder.setNeutralButton(buttonLabels[2], listeners[2]);
        }

        return builder.create();
    }

    public static SimpleAlertDialog createSimpleDialog(String contentsText, String[] buttonLabels, BaseDialog.OnClickListener[] listeners) {
        SimpleAlertDialog.Builder builder = new SimpleAlertDialog.Builder();
        builder.setContentsText(contentsText);

        int buttonsSize = buttonLabels.length;
        if (buttonsSize > 0) {
            builder.setPositiveButton(buttonLabels[0], listeners[0]);
        }
        if (buttonsSize > 1) {
            builder.setNegativeButton(buttonLabels[1], listeners[1]);
        }
        if (buttonsSize > 2) {
            builder.setNeutralButton(buttonLabels[2], listeners[2]);
        }

        return builder.create();
    }
}
