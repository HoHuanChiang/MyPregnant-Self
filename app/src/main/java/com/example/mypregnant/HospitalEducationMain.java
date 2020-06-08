package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetEducationList;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.MedicalEducation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HospitalEducationMain extends AppCompatActivity {
    LinearLayout weekLayout,firstSessionBtn,secondSessionBtn,thirdSessionBtn;
    ArrayList<MedicalEducation> firstSession,secondSession,thirdSession;
    TextView weekTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_education_main);

        ToolBarFunction.setToolBarInit(HospitalEducationMain.this,"醫院資訊");
        weekLayout=findViewById(R.id.educationMainWeekLayout);
        firstSessionBtn=findViewById(R.id.educationMainFirstSessionBtn);
        secondSessionBtn=findViewById(R.id.educationMainSecondSessionBtn);
        thirdSessionBtn=findViewById(R.id.educationMainThirdSessionBtn);
        weekTextView=findViewById(R.id.currentWeekText);
        weekTextView.setText(getSharedPreferences("data",0).getInt("PregnantWeek",0)+"");
        GetEducations();
    }
    public void GetEducations(){
        GetEducationList getEducationList=new GetEducationList("0",new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                firstSession=new ArrayList<>();
                secondSession=new ArrayList<>();
                thirdSession=new ArrayList<>();
                try {
                    //給Listview
                    JSONArray jEducations=new JSONArray(response);

                    for(int i=0;i<jEducations.length();i++)
                    {
                        try {
                            JSONObject jEducation=jEducations.getJSONObject(i);
                            final MedicalEducation me=new MedicalEducation();
                            me.setEducationID(jEducation.getInt("EducationID"));
                            me.setEducationName(jEducation.getString("EducationName"));
                            me.setContent(jEducation.getString("Content"));
                            me.setIsEducational(jEducation.getInt("IsEducational"));
                            me.setCategory(jEducation.getString("Category"));
                            me.setSubContent(jEducation.getString("SubContent"));
                            me.setParentID(jEducation.getInt("ParentID"));
                            me.setPregnantWeek(jEducation.getInt("PregnantWeek"));
                            me.setType(jEducation.getInt("Type"));
                            int pregnantWeek=me.getPregnantWeek();
                            if(pregnantWeek>0&&pregnantWeek<=12)
                            {
                                firstSession.add(me);
                            }
                            else if(pregnantWeek>12&&pregnantWeek<=28)
                            {
                                secondSession.add(me);
                            }
                            else if(pregnantWeek>28&&pregnantWeek<=40)
                            {
                                thirdSession.add(me);
                            }


                            if(pregnantWeek==getSharedPreferences("data",0).getInt("PregnantWeek",0)){
                                final MedicalEducation finalEducation=me;
                                View view= LayoutInflater.from(HospitalEducationMain.this).inflate(R.layout.layout_education,weekLayout,false);
                                TextView title=view.findViewById(R.id.educationEachTitle);
                                TextView subcontent=view.findViewById(R.id.educationEachSubContent);
                                LinearLayout layout=view.findViewById(R.id.educationEachLayout);
                                title.setText(me.getEducationName());
                                subcontent.setText(me.getSubContent());
                                layout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent=new Intent();
                                        intent.setClass(HospitalEducationMain.this,EducationDetail.class);
                                        Bundle b=new Bundle();
                                        b.putSerializable("EducationNode",finalEducation);
                                        intent.putExtras(b);
                                        startActivity(intent);
                                    }
                                });
                                weekLayout.addView(view);
                            }

                        } catch (JSONException e) {
                            Log.e("errorrrrrrrrrrrrrrrrr",e.getMessage());
                        }
                    }
                    firstSessionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent();
                            intent.setClass(HospitalEducationMain.this,EducationList2.class);
                            Bundle b=new Bundle();
                            b.putSerializable("AllEducationList",firstSession);
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    });
                    secondSessionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent();
                            intent.setClass(HospitalEducationMain.this,EducationList2.class);
                            Bundle b=new Bundle();
                            b.putSerializable("AllEducationList",secondSession);
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    });
                    thirdSessionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent();
                            intent.setClass(HospitalEducationMain.this,EducationList2.class);
                            Bundle b=new Bundle();
                            b.putSerializable("AllEducationList",thirdSession);
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    });

                } catch (JSONException e) {
                    Toast.makeText(HospitalEducationMain.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getEducationList);
    }
}
