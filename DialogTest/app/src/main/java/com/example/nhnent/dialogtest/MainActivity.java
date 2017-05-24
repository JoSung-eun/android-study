package com.example.nhnent.dialogtest;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends FragmentActivity implements DialogWithRadio.RadioDialogListener2 {

    Button dialogBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogBtn = (Button) findViewById(R.id.button_dialog);
        dialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CharSequence[] items = {"A", "B", "C", "D"};

                AlertDialog.Builder builder = DialogHelper.createRadioButtonDialog(MainActivity.this, items, -1, MainActivity.this);
                builder.setTitle("Character List");
//                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
//
//                builder.setView(inflater.inflate(R.layout.dialog_signin, null));
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();*/

                RadioDialogBuilder radioDialogBuilder = new RadioDialogBuilder(MainActivity.this);
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_signin, null);

                /*radioDialogBuilder.setView(view);
                radioDialogBuilder.setRadioGroup((RadioGroup) view.findViewById(R.id.radio_group));
                radioDialogBuilder.setPositiveButton((Button) view.findViewById(R.id.btn_ok), MainActivity.this);
                radioDialogBuilder.create().show();*/

                DialogWithRadio dialogWithRadio = new DialogWithRadio();
                new AlertDialog.Builder(MainActivity.this).setView(view);
                dialogWithRadio.setRadioGroup((RadioGroup) view.findViewById(R.id.radio_group));
                dialogWithRadio.setPositiveButton((Button) view.findViewById(R.id.btn_ok));
                dialogWithRadio.show(getFragmentManager(), "radio");

            }
        });

    }


    @Override
    public void onPositiveClickListener(int checkedId) {
        Log.d("Main", "check : " + checkedId);
    }

    @Override
    public void onNegativeClickListener() {

    }
}
