package com.example.nhnent.customdialog;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    Button dialogBtn;
    AlertDialog dialog;

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("main", "" + which);
            }
        };

        final DialogInterface.OnClickListener negstiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        };

        dialogBtn = (Button) findViewById(R.id.button_dialog);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DialogHelper dialogHelper = new DialogHelper(MainActivity.this);
                ArrayList<String> items = new ArrayList<>();
                items.add("A");
                items.add("B");
                items.add("C");
//                RadioDialog radioDialog = dialogHelper.createRadioDialog(items);
//                radioDialog.show(getFragmentManager(), "radio");
//                dialogHelper.createRadioDialog(items, MainActivity.this).show(getFragmentManager(), null);
//                dialogHelper.createSimpleAlertDialog("simple alert").show(getFragmentManager(), "simple");
//                dialogHelper.createCheckBoxDialog(items).show(getFragmentManager(), "checkbox");
//                dialogHelper.createRadioDialog(items, positiveListener, negstiveListener);


//                dialogHelper.createRadioDialog(items, MainActivity.this).show(getFragmentManager(), null);

//                RadioDialogBuilder radioDialogBuilder = new RadioDialogBuilder(MainActivity.this, items);
//                radioDialogBuilder.setPositiveButton("confirm", MainActivity.this)
//                        .setNegativeButton("cancel", MainActivity.this)
//                        .create().show(getFragmentManager(), null);

                RadioDialog.Builder builder = new RadioDialog.Builder();
                builder.setRadioItems(items)
                        .setTitle("목록 보기 설정")
                        .setPositiveButton("ok", new BaseDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogFragment dialog, int which) {
                                Log.d("main", "check : " + which);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("no", new BaseDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogFragment dialog, int which) {
                                Log.d("main", "cancel");
                                dialog.dismiss();
                            }
                        })
//                        .setNeutralButton("neutral", new BaseDialog.OnClickListener() {
//                            @Override
//                            public void onClick(DialogFragment dialog, int which) {
//                                Log.d("main", "neutral");
//                                dialog.dismiss();
//                            }
//                        })
                        .create().show(getFragmentManager(), "dialog");

//                dialogHelper.createSimpleAlertDialog("It is simple dialog...", MainActivity.this).show(getFragmentManager(), null);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
