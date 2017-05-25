package com.example.nhnent.customdialog;

import android.view.View;
import android.widget.Button;

/**
 * Created by nhnent on 2017. 5. 25..
 */

public class SimpleAlertDialog extends BaseDialogFragment {
    @Override

    void bindContents() {

    }

    @Override
    void bindPositiveButton() {
        positiveBtn = (Button) view.findViewById(R.id.btn_positive);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveButtonClick(-1);
                dismiss();
            }
        });
    }

    @Override
    void bindNegativeButton() {

    }
}
