package com.example.mypregnant;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetBMI;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DialogToolMenu extends Dialog {
    Activity context;
    int userID;
    public DialogToolMenu(Activity context) {
        super(context);
        this.context=context;
        userID=context.getSharedPreferences("data",0).getInt("user",0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tool_menu);
        getWindow().getAttributes().windowAnimations = R.style.dialogUpDownAnimation;


        ImageButton questionnaireBtn=findViewById(R.id.toolMenuQuestionnaireBtn);
        ImageButton educationBtn=findViewById(R.id.toolMenuEducationBtn);
        ImageButton videoBtn=findViewById(R.id.toolMenuVideoBtn);
        ImageButton physicalBtn=findViewById(R.id.toolMenuPhysicalBtn);
        ImageButton recordBtn=findViewById(R.id.toolMenuRecordBtn);
        ImageButton scheduleBtn=findViewById(R.id.toolMenuScheduleBtn);
        ImageButton shopBtn=findViewById(R.id.toolMenuShopBtn);
        ImageButton othersBtn=findViewById(R.id.toolMenuOthersBtn);

        questionnaireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, QuestionnaireMain.class);
                context.startActivity(intent);
                context.finish();
            }
        });
        educationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, EducationMain2.class);
                intent.putExtra("IsEducational","1");
                context.startActivity(intent);
                context.finish();
            }
        });
        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, VideoTotalV2.class);
                context.startActivity(intent);
                context.finish();
            }
        });
        physicalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setClass(context, PhysicalMain.class);
                context.startActivity(intent);
                context.finish();
            }
        });
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setClass(context, RecordMain.class);
                context.startActivity(intent);
                context.finish();
            }
        });
        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setClass(context, ScheduleCalendar.class);
                context.startActivity(intent);
                context.finish();
            }
        });
        shopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent();
                intent.setClass(context, ShopTotalV2.class);
                context.startActivity(intent);
                context.finish();
            }
        });
        othersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, EducationMain2.class);
                intent.putExtra("IsEducational","0");
                context.startActivity(intent);
                context.finish();
            }
        });
    }
}
