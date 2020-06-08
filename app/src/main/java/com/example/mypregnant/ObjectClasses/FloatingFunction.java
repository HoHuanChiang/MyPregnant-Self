package com.example.mypregnant.ObjectClasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mypregnant.DialogPhysialSummary;
import com.example.mypregnant.PhysicalEdit;
import com.example.mypregnant.R;

public class FloatingFunction {
    private Boolean isFabOpen = true;
    private ImageButton editBtn,menuBtn,summaryBtn;
    private Animation fab_open,fab_close;

    public FloatingFunction(View view, final Context context, final String category, final boolean needTime, final boolean needSession, final int sessionArray) {
        menuBtn = view.findViewById(R.id.floatingMenuBtn);
        editBtn = view.findViewById(R.id.floatingEditBtn);
        summaryBtn= view.findViewById(R.id.floatingSummaryBtn);

        fab_open = AnimationUtils.loadAnimation(context, R.anim.edit_fab_open);
        fab_close = AnimationUtils.loadAnimation(context,R.anim.edit_fab_close);


        editBtn.animate().translationX(-190).start();
        summaryBtn.animate().translationX(-380).start();
        menuBtn.animate().rotation(90);
        editBtn.startAnimation(fab_open);
        summaryBtn.startAnimation(fab_open);
        editBtn.setEnabled(true);
        summaryBtn.setEnabled(true);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setClass(context, PhysicalEdit.class);
                intent.putExtra("Category",category);
                intent.putExtra("NeedTime",needTime);
                intent.putExtra("NeedSession",needSession);
                intent.putExtra("SessionArray",sessionArray);
                context.startActivity(intent);
            }
        });
        summaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPhysialSummary dps=new DialogPhysialSummary((Activity) context,category);
                dps.show();
            }
        });


        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFabOpen){
                    editBtn.animate().translationY(0).start();
                    editBtn.animate().translationX(0).start();
                    summaryBtn.animate().translationY(0).start();
                    summaryBtn.animate().translationX(0).start();
                    menuBtn.animate().rotation(0);
                    editBtn.startAnimation(fab_close);
                    summaryBtn.startAnimation(fab_close);
                    editBtn.setEnabled(false);
                    summaryBtn.setEnabled(false);
                    isFabOpen = false;

                } else {

                    editBtn.animate().translationX(-190).start();
                    summaryBtn.animate().translationX(-380).start();
                    menuBtn.animate().rotation(90);
                    editBtn.startAnimation(fab_open);
                    summaryBtn.startAnimation(fab_open);
                    editBtn.setEnabled(true);
                    summaryBtn.setEnabled(true);
                    isFabOpen = true;

                }
            }
        });
    }

}