package com.example.nhnent.customdialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements BaseDialogFragment.DialogListener, RadioDialogFragment.RadioDialogListener {

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
                DialogHelper dialogHelper = new DialogHelper(MainActivity.this);
                ArrayList<String> items = new ArrayList<>();
                items.add("A");
                items.add("B");
                items.add("C");
//                RadioDialogFragment radioDialog = dialogHelper.createRadioDialog(items);
//                radioDialog.show(getFragmentManager(), "radio");
//                dialogHelper.createRadioDialog(items, MainActivity.this).show(getFragmentManager(), null);
//                dialogHelper.createSimpleAlertDialog("simple alert").show(getFragmentManager(), "simple");
//                dialogHelper.createCheckBoxDialog(items).show(getFragmentManager(), "checkbox");
//                dialogHelper.createRadioDialog(items, positiveListener, negstiveListener);
                dialogHelper.createRadioDialog(items, MainActivity.this).show(getFragmentManager(), null);
           }
        });




    }


    @Override
    public void onPositiveButtonClick(int checkedId) {
        Log.d("Main", "check : " + checkedId);
    }

    @Override
    public void onPositiveButtonClick(List<Integer> checkedItems) {
        Log.d("Main", "check : " + checkedItems.size());

    }

    @Override
    public void onNegativeButtonClick() {

    }
}
