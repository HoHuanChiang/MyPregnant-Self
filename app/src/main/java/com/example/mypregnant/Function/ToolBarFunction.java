package com.example.mypregnant.Function;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mypregnant.DialogToolMenu;
import com.example.mypregnant.R;

public class ToolBarFunction {
    public static void setToolBarInit(Activity activity,String title){
        setInit(activity,title);
    }
    public static void setToolBarInit(Activity activity,String title,String subTitle){
        TextView subTitleText=activity.findViewById(R.id.toolSubTitle);
        subTitleText.setText(subTitle);
        setInit(activity,title);
    }
    private static void setInit(final Activity activity, String title){

        ImageButton backBtn=activity.findViewById(R.id.toolBackBtn);
        TextView titleText=activity.findViewById(R.id.toolTitle);
        ImageButton menuBtn=activity.findViewById(R.id.toolMenuBtn);
        titleText.setText(title);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogToolMenu dialogToolMenu=new DialogToolMenu(activity);
                WindowManager.LayoutParams wmlp =dialogToolMenu.getWindow().getAttributes();
                wmlp.gravity = Gravity.TOP;
                dialogToolMenu.show();

            }
        });
    }
}
