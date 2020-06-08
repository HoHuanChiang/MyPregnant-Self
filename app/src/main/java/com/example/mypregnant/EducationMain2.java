package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetEducationList;
import com.example.mypregnant.DatabaseClasses.GetEventFlow;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.EventFlow;
import com.example.mypregnant.ObjectClasses.MedicalEducation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EducationMain2 extends AppCompatActivity {
    LinearLayout weekLayout,firstSessionBtn,secondSessionBtn,thirdSessionBtn,eachLayout;
    ArrayList<ArrayList<MedicalEducation>> sessionData;
    TextView weekTextView;
    ImageView firstImage,secondImage,thirdImage;
    String isEducational;
    ArrayList<EventFlow> eventFlows;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_main2);

        isEducational=getIntent().getStringExtra("IsEducational");
        if(isEducational.equals("1")){
            ToolBarFunction.setToolBarInit(EducationMain2.this,"衛教資訊");
        }
        else if(isEducational.equals("0")){
            ToolBarFunction.setToolBarInit(EducationMain2.this,"醫院資訊");
        }
        weekLayout=findViewById(R.id.educationMainWeekLayout);
        firstSessionBtn=findViewById(R.id.educationMainFirstSessionBtn);
        secondSessionBtn=findViewById(R.id.educationMainSecondSessionBtn);
        thirdSessionBtn=findViewById(R.id.educationMainThirdSessionBtn);
        eachLayout=findViewById(R.id.educationMainEachLayout);
        weekTextView=findViewById(R.id.currentWeekText);
        firstImage=findViewById(R.id.educationMainFirstSessionImage);
        secondImage=findViewById(R.id.educationMainSecondSessionImage);
        thirdImage=findViewById(R.id.educationMainThirdSessionImage);
        weekTextView.setText(getSharedPreferences("data",0).getInt("PregnantWeek",0)+"");

        eventFlows=new ArrayList<>();
        GetEventFlowChart();
        GetEducations();
    }
    public void GetEducations(){
        GetEducationList getEducationList=new GetEducationList(isEducational,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                sessionData=new ArrayList<>();
                ArrayList<MedicalEducation> firstSession=new ArrayList<>();
                ArrayList<MedicalEducation> secondSession=new ArrayList<>();
                ArrayList<MedicalEducation> thirdSession=new ArrayList<>();
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
                                View weekView= LayoutInflater.from(EducationMain2.this).inflate(R.layout.layout_education,weekLayout,false);
                                TextView title=weekView.findViewById(R.id.educationEachTitle);
                                TextView subContent=weekView.findViewById(R.id.educationEachSubContent);
                                LinearLayout layout=weekView.findViewById(R.id.educationEachLayout);
                                title.setText(me.getEducationName());
                                subContent.setText(me.getSubContent());
                                layout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if(finalEducation.getType()==0){
                                            Intent intent=new Intent();
                                            intent.setClass(EducationMain2.this,EducationDetail.class);
                                            Bundle b=new Bundle();
                                            b.putSerializable("EducationNode",finalEducation);
                                            intent.putExtras(b);
                                            startActivity(intent);
                                        }
                                        else if(finalEducation.getType()==1){
                                            ArrayList<EventFlow> siftFlows=new ArrayList<>();
                                            for(int flowCount=0;flowCount<eventFlows.size();flowCount++){
                                                if(eventFlows.get(flowCount).getEducationID()==finalEducation.getEducationID()){
                                                    siftFlows.add(eventFlows.get(flowCount));
                                                }
                                            }
                                            Intent intent=new Intent();
                                            intent.setClass(EducationMain2.this,ScheduleFlow.class);
                                            Bundle b=new Bundle();
                                            b.putSerializable("ScheduleFlow",siftFlows);
                                            intent.putExtras(b);
                                            startActivity(intent);

                                        }
                                    }
                                });
                                weekLayout.addView(weekView);
                            }

                        } catch (JSONException e) {
                            Log.e("errorrrrrrrrrrrrrr",e.getMessage());
                        }
                    }
                    sessionData.add(firstSession);
                    sessionData.add(secondSession);
                    sessionData.add(thirdSession);

                    firstSessionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setEachLayout(0);
                            /*
                            Intent intent=new Intent();
                            intent.setClass(EducationMain2.this,EducationList2.class);
                            Bundle b=new Bundle();
                            b.putSerializable("AllEducationList",firstSession);
                            intent.putExtras(b);
                            startActivity(intent);*/
                        }
                    });
                    secondSessionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setEachLayout(1);
                            /*
                            Intent intent=new Intent();
                            intent.setClass(EducationMain2.this,EducationList2.class);
                            Bundle b=new Bundle();
                            b.putSerializable("AllEducationList",secondSession);
                            intent.putExtras(b);
                            startActivity(intent);*/
                        }
                    });
                    thirdSessionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setEachLayout(2);
                            /*
                            Intent intent=new Intent();
                            intent.setClass(EducationMain2.this,EducationList2.class);
                            Bundle b=new Bundle();
                            b.putSerializable("AllEducationList",thirdSession);
                            intent.putExtras(b);
                            startActivity(intent);*/
                        }
                    });

                    setEachLayout(0);
                } catch (JSONException e) {
                    Toast.makeText(EducationMain2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getEducationList);
    }
    public void setEachLayout(final int session){

        if(session==0){
            firstImage.setImageResource(R.drawable.lightheart);
            secondImage.setImageResource(R.drawable.unlightheart);
            thirdImage.setImageResource(R.drawable.unlightheart);
        }
        else if(session==1){

            firstImage.setImageResource(R.drawable.unlightheart);
            secondImage.setImageResource(R.drawable.lightheart);
            thirdImage.setImageResource(R.drawable.unlightheart);
        }
        else if(session==2){

            firstImage.setImageResource(R.drawable.unlightheart);
            secondImage.setImageResource(R.drawable.unlightheart);
            thirdImage.setImageResource(R.drawable.lightheart);
        }

        eachLayout.removeAllViews();
        for(int i=0;i<sessionData.get(session).size();i++){
            View view= LayoutInflater.from(EducationMain2.this).inflate(R.layout.layout_education,eachLayout,false);
            TextView title=view.findViewById(R.id.educationEachTitle);
            TextView subcontent=view.findViewById(R.id.educationEachSubContent);
            LinearLayout layout=view.findViewById(R.id.educationEachLayout);
            title.setText(sessionData.get(session).get(i).getEducationName());
            subcontent.setText(sessionData.get(session).get(i).getSubContent());
            final MedicalEducation finalMeidcal=sessionData.get(session).get(i);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(finalMeidcal.getType()==0){
                        Intent intent=new Intent();
                        intent.setClass(EducationMain2.this,EducationDetail.class);
                        Bundle b=new Bundle();
                        b.putSerializable("EducationNode",finalMeidcal);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                    else if(finalMeidcal.getType()==1){
                        ArrayList<EventFlow> siftFlows=new ArrayList<>();
                        for(int flowCount=0;flowCount<eventFlows.size();flowCount++){
                            if(eventFlows.get(flowCount).getEducationID()==finalMeidcal.getEducationID()){
                                siftFlows.add(eventFlows.get(flowCount));
                            }
                        }
                        Intent intent=new Intent();
                        intent.setClass(EducationMain2.this,ScheduleFlow.class);
                        Bundle b=new Bundle();
                        b.putSerializable("ScheduleFlow",siftFlows);
                        intent.putExtras(b);
                        startActivity(intent);

                    }


                }
            });
            eachLayout.addView(view);
        }
    }
    public void GetEventFlowChart()
    {
        GetEventFlow getEventFlow=new GetEventFlow("1",new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jEvents = new JSONArray(response);
                    eventFlows=new ArrayList<>();
                    for(int i=0;i<jEvents.length();i++)
                    {
                        JSONObject jEachEvent=jEvents.getJSONObject(i);
                        EventFlow ef=new EventFlow();
                        ef.setContent(jEachEvent.getString("Content"));
                        ef.setOrder(jEachEvent.getInt("Order"));
                        ef.setPregnantWeek(jEachEvent.getInt("PregnantWeek"));
                        //ef.setEducation(jEachEvent.getBoolean("IsEducational"));
                        ef.setEducationID(jEachEvent.getInt("EducationID"));
                        eventFlows.add(ef);
                    }

                } catch (JSONException e) {
                    Log.e("ese",e.getMessage());
                }
            }
        });
        RequestQueue q= Volley.newRequestQueue(EducationMain2.this);
        q.add(getEventFlow);
    }
}
