package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetUserInfo;
import com.example.mypregnant.DatabaseClasses.UpdatePersonalInfo;
import com.example.mypregnant.Function.ToolBarFunction;

import org.json.JSONException;
import org.json.JSONObject;

public class BasicInfoEdit extends AppCompatActivity {
    EditText roomText,medicalIDText,doctorText,nurseText;
    TextView accountText, birthdayText,lastPeriodText,heightText,weightText,nameText,weekText;
    Button saveBtn;
    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info_edit);

        ToolBarFunction.setToolBarInit(this,"個人資訊");
        roomText=findViewById(R.id.basicEditRoom);
        medicalIDText=findViewById(R.id.basicEditMedicalID);
        doctorText=findViewById(R.id.basicEditDoctor);
        nurseText=findViewById(R.id.basicEditNurse);
        accountText=findViewById(R.id.basicEditAccount);
        birthdayText=findViewById(R.id.basicEditBirthday);
        lastPeriodText=findViewById(R.id.basicEditLastPeriodDate);
        heightText=findViewById(R.id.basicEditHeight);
        weightText=findViewById(R.id.basicEditWeight);
        nameText=findViewById(R.id.basicEditName);
        weekText=findViewById(R.id.basicEditCurrentWeek);
        saveBtn=findViewById(R.id.basicEditSaveBtn);
        userID=getSharedPreferences("data",0).getInt("user",0);
        GetPersonalData();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatePersonalInfo updatePersonalInfo=new UpdatePersonalInfo(String.valueOf(userID), roomText.getText().toString(), medicalIDText.getText().toString(), doctorText.getText().toString(), nurseText.getText().toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(BasicInfoEdit.this, "更改完成!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                RequestQueue q= Volley.newRequestQueue(BasicInfoEdit.this);
                q.add(updatePersonalInfo);
            }
        });
    }
    public void GetPersonalData(){
        GetUserInfo getUserInfo=new GetUserInfo(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jUserInfo = new JSONObject(response);
                    nameText.setText(jUserInfo.getString("Name"));
                    accountText.setText(jUserInfo.getString("Account"));
                    birthdayText.setText(jUserInfo.getString("BirthdayDate"));
                    lastPeriodText.setText(jUserInfo.getString("LastPeriodDate"));
                    heightText.setText(jUserInfo.getInt("Height")+"");
                    weightText.setText(jUserInfo.getInt("Weight")+"");
                    weekText.setText(jUserInfo.getInt("PregnantWeek")+"");

                    roomText.setText(jUserInfo.getString("Room"));
                    medicalIDText.setText(jUserInfo.getString("MedicalID"));
                    doctorText.setText(jUserInfo.getString("Doctor"));
                    nurseText.setText(jUserInfo.getString("Nurse"));
                } catch (JSONException e) {
                    Toast.makeText(BasicInfoEdit.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getUserInfo);
    }
}
