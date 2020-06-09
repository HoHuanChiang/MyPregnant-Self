package com.example.mypregnant;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.InsertPhysical;
import com.example.mypregnant.Function.RepositoryFucntion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DialogPhysialAdd extends Dialog {
    private EditText valueText,dateText,timeText,doubleValue1Text,doubleValue2Text;
    private Spinner sessionSpinner;
    private LinearLayout timeLayout,sessionLayout;
    private boolean needTime,needSession;
    private int sessionArray;
    private ImageButton cancelBtn,saveBtn;
    private String category;
    private LinearLayout singleLayout,doubleLayout;
    Activity context;
    int userID;
    String initialDate;
    public DialogPhysialAdd(Activity context,String category,boolean needTime,boolean needSession,int sessionArray,String initialDate) {
        super(context);
        this.context=context;
        this.needSession=needSession;
        this.needTime=needTime;
        this.sessionArray=sessionArray;
        this.category=category;
        this.initialDate=initialDate;
        userID=context.getSharedPreferences("data",0).getInt("user",0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_physical_add);

        getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;

        valueText=findViewById(R.id.dialogPhysicalAddValue);
        doubleValue1Text=findViewById(R.id.dialogPhysicalAddDoubleValue1);
        doubleValue2Text=findViewById(R.id.dialogPhysicalAddDoubleValue2);
        dateText=findViewById(R.id.dialogPhysicalAddDate);
        timeText=findViewById(R.id.dialogPhysicalAddTime);
        sessionSpinner=findViewById(R.id.dialogPhysicalAddSpinner);
        timeLayout=findViewById(R.id.dialogPhysicalAddTimeLayout);
        sessionLayout=findViewById(R.id.dialogPhysicalAddSpinnerLayout);
        cancelBtn=findViewById(R.id.dialogPhysicalAddCancelBtn);
        saveBtn=findViewById(R.id.dialogPhysicalAddSaveBtn);
        singleLayout=findViewById(R.id.dialogPhysicalAddSingleLayout);
        doubleLayout=findViewById(R.id.dialogPhysicalAddDoubleLayout);

        if(needSession){
            sessionLayout.setVisibility(View.VISIBLE);
            sessionSpinner.setAdapter(ArrayAdapter.createFromResource(context,sessionArray,android.R.layout.simple_dropdown_item_1line));
        }
        if(needTime){
            timeLayout.setVisibility(View.VISIBLE);
        }
        if(category.equals("pressure")){
            singleLayout.setVisibility(View.GONE);
            doubleLayout.setVisibility(View.VISIBLE);

        }
        RepositoryFucntion.setDateText(dateText, context);
        RepositoryFucntion.setTimeText(timeText, context);
        dateText.setText(initialDate);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "新增確認", Toast.LENGTH_SHORT).show();
                //database
                /*
                String date=dateText.getText().toString();
                String session="";
                String value=valueText.getText().toString();
                if(needTime){
                    date=dateText.getText().toString()+" "+timeText.getText().toString()+":00.000";
                }
                if(needSession){
                    session=sessionSpinner.getSelectedItem().toString();
                }
                if(category.equals("pressure")){
                    value=doubleValue1Text.getText().toString()+";"+doubleValue2Text.getText().toString();
                }
                Log.e("session",session);
                Log.e("category",category);
                Log.e("value",value);
                InsertPhysical insertPhysical=new InsertPhysical(String.valueOf(userID), date,
                        session,value, category, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response",response);
                        try {
                            JSONArray responseArray=new JSONArray(response);
                            for(int i=0;i<responseArray.length();i++){
                                JSONObject eachResponse=responseArray.getJSONObject(i);
                                RepositoryFucntion.notifyApp(context,eachResponse.getString("title"),eachResponse.getString("content"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        dismiss();
                    }
                });
                RequestQueue q= Volley.newRequestQueue(context);
                q.add(insertPhysical);*/
                dismiss();
            }
        });
    }
}
