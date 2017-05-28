package com.example.nhnent.customdialog;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sung-EunJo on 2017. 5. 29..
 */

public class DialogParams implements Serializable {
    public String title;
    public String contentsText;

    public String positiveBtnLabel;
    public String negativeBtnLabel;
    public String neutralBtnLabel;

    public ArrayList<String> items;

    public BaseDialog.OnClickListener positiveBtnClickListener;
    public BaseDialog.OnClickListener negativeBtnClickListener;
    public BaseDialog.OnClickListener neutralBtnClickListener; 
}
