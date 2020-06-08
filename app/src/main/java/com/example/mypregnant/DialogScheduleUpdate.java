package com.example.mypregnant;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.InsertSchedule;
import com.example.mypregnant.DatabaseClasses.UpdateSchedule;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.ObjectClasses.ScheduleEvent;
import com.google.gson.Gson;

public class DialogScheduleUpdate extends Dialog {
    private EditText dateText,timeText,numberText,noteText;
    private ImageButton cancelBtn,saveBtn;
    Activity context;
    int userID;
    ScheduleEvent event;
    public DialogScheduleUpdate(Activity context, ScheduleEvent event) {
        super(context);
        this.context=context;
        this.event=event;
        userID=context.getSharedPreferences("data",0).getInt("user",0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_update);

        getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;

        dateText=findViewById(R.id.dialogRecordAddDate);
        timeText=findViewById(R.id.dialogRecordAddTime);
        numberText=findViewById(R.id.dialogRecordAddNumber);
        noteText=findViewById(R.id.dialogRecordAddNote);

        dateText.setText(event.getDate());
        timeText.setText(event.getTime());
        numberText.setText(String.valueOf(event.getNumber()));
        noteText.setText(event.getNote());

        cancelBtn=findViewById(R.id.dialogRecordAddCancelBtn);
        saveBtn=findViewById(R.id.dialogRecordUpdateBtn);

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
                event.setDate(dateText.getText().toString());
                event.setTime(timeText.getText().toString());
                event.setNumber(Integer.parseInt(numberText.getText().toString()));
                event.setNote(noteText.getText().toString());
                Gson g=new Gson();
                //Log.e("gons",g.toJson(event));
                UpdateSchedule updateSchedule=new UpdateSchedule(g.toJson(event), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dismiss();
                    }
                });
                RequestQueue q= Volley.newRequestQueue(context);
                q.add(updateSchedule);
            }
        });
    }
}
