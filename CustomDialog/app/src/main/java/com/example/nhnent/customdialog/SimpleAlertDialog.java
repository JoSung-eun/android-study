package com.example.nhnent.customdialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by nhnent on 2017. 5. 25..
 */

public class SimpleAlertDialog extends BaseDialog {
    public static SimpleAlertDialog createInstance(DialogParams params) {
        SimpleAlertDialog simpleAlertDialog = new SimpleAlertDialog();
        Bundle args = new Bundle();
        args.putSerializable("params", params);
        simpleAlertDialog.setArguments(args);
        return simpleAlertDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_simple_alert, container);

        DialogParams params = (DialogParams) getArguments().getSerializable("params");
        if (params != null) {
            setTitle(params.title);

            setContentsText(params.contentsText);

            setPositiveButton(params.positiveBtnLabel, params.positiveBtnClickListener);
            setNegativeButton(params.negativeBtnLabel, params.negativeBtnClickListener);
            setNeutralButton(params.neutralBtnLabel, params.neutralBtnClickListener);
        }

        return view;
    }

    private void setContentsText(String text) {
        if (text != null) {
            TextView contentsText = (TextView) view.findViewById(R.id.text_contents);
            contentsText.setText(text);
        }
    }

    public static class Builder {
        private BaseDialog.DialogParams params;

        public Builder() {
            params = new BaseDialog.DialogParams();
        }

        public Builder setTitle(String title) {
            params.title = title;
            return this;
        }

        public Builder setContentsText(String text) {
            params.contentsText = text;
            return this;
        }

        public Builder setPositiveButton(String label, BaseDialog.OnClickListener listener) {
            params.positiveBtnLabel = label;
            params.positiveBtnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String label, BaseDialog.OnClickListener listener) {
            params.negativeBtnLabel = label;
            params.negativeBtnClickListener = listener;
            return this;
        }

        public Builder setNeutralButton(String label, BaseDialog.OnClickListener listener) {
            params.neutralBtnLabel = label;
            params.neutralBtnClickListener = listener;
            return this;
        }

        public SimpleAlertDialog create() {
            return SimpleAlertDialog.createInstance(params);
        }
    }
}
