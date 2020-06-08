package com.example.mypregnant;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.UpdateSchedule;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.ObjectClasses.ScheduleEvent;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DialogPhysicalDayDetails extends Dialog {
    Activity context;
    LinearLayout detailsLayout;
    TextView detailsName,detailsDate;
    String titleName;
    JSONArray detailsArray;
    String date;
    int userID;
    public DialogPhysicalDayDetails(Activity context, String titleName, String date,JSONArray details) {
        super(context);
        this.context=context;
        this.titleName=titleName;
        detailsArray=details;
        this.date=date;
        userID=context.getSharedPreferences("data",0).getInt("user",0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_physical_day_details);

        getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;

        detailsLayout=findViewById(R.id.physicalDayDetailsLayout);
        detailsName=findViewById(R.id.physicalDayDetailsName);
        detailsDate=findViewById(R.id.physicalDayDetailsDate);

        detailsName.setText(titleName);
        detailsDate.setText(date);
        for(int i=0;i<detailsArray.length();i++){
            try {
                JSONObject eachDetails=detailsArray.getJSONObject(i);
                View detailView= LayoutInflater.from(context).inflate(R.layout.layout_physical_details_list,detailsLayout,false);
                TextView name=detailView.findViewById(R.id.physicalListName);
                TextView value=detailView.findViewById(R.id.physicalListValue);
                name.setText(eachDetails.getString("time"));
                value.setText(eachDetails.getString("value"));
                if(eachDetails.getInt("isAbnormal")==1){
                    value.setTextColor(context.getResources().getColor(R.color.myRed));
                }
                detailsLayout.addView(detailView);
            } catch (JSONException e) {
                Log.e("DialogPPhsyicasdlsdf",e.getMessage());
            }
        }
    }
}
