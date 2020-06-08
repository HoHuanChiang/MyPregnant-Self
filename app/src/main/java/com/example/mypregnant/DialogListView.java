package com.example.mypregnant;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

public class DialogListView extends Dialog {
    Activity context;
    int userID;
    int pregnantWeek;
    LinearLayout listLayout;
    LayoutInflater inflater;
    int currentWeeek;
    public DialogListView(Activity context,int currentWeeek) {
        super(context);
        this.context=context;
        userID=context.getSharedPreferences("data",0).getInt("user",0);
        pregnantWeek=context.getSharedPreferences("data",0).getInt("PregnantWeek",0);
        this.currentWeeek=currentWeeek;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list);
        inflater = LayoutInflater.from(context);
        //getWindow().getAttributes().windowAnimations = R.style.dialogUpDownAnimation;


    }
}
