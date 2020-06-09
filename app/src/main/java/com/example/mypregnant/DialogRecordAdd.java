package com.example.mypregnant;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.InsertPhysical;
import com.example.mypregnant.DatabaseClasses.InsertSchedule;
import com.example.mypregnant.Function.RepositoryFucntion;

public class DialogRecordAdd extends Dialog {
    private EditText dateText,timeText,numberText,noteText;
    private Button cancelBtn,saveBtn;
    Activity context;
    int userID;
    public DialogRecordAdd(Activity context) {
        super(context);
        this.context=context;
        userID=context.getSharedPreferences("data",0).getInt("user",0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_add);

        getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;

        dateText=findViewById(R.id.dialogRecordAddDate);
        timeText=findViewById(R.id.dialogRecordAddTime);
        numberText=findViewById(R.id.dialogRecordAddNumber);
        noteText=findViewById(R.id.dialogRecordAddNote);

        cancelBtn=findViewById(R.id.dialogRecordAddCancelBtn);
        saveBtn=findViewById(R.id.dialogRecordAddInsertBtn);

        RepositoryFucntion.setDateText(dateText, context,false);
        RepositoryFucntion.setTimeText(timeText, context,false);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateText.getText().toString().equals("")||timeText.getText().toString().equals("")){
                    Toast.makeText(context, "日期或時間未填寫完成!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String date=dateText.getText().toString()+" "+timeText.getText().toString()+":00.000";
                //database
                /*
                InsertSchedule insertSchedule=new InsertSchedule(String.valueOf(userID), date,
                        numberText.getText().toString(),noteText.getText().toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        if(response.trim().equals("1")){
                            Toast.makeText(context, "這天已經新增過了", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.trim().equals("0")){
                            Toast.makeText(context, "新增成功", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }

                    }
                });
                RequestQueue q= Volley.newRequestQueue(context);
                q.add(insertSchedule);*/
                dismiss();
            }
        });
    }
}
