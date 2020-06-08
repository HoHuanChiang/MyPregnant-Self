package com.example.mypregnant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.AdapterClasses.EducationAdapter;
import com.example.mypregnant.DatabaseClasses.GetEventFlow;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.EventFlow;
import com.example.mypregnant.ObjectClasses.MedicalEducation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EducationList2 extends AppCompatActivity {
    ListView educationListView;
    ArrayList<MedicalEducation> educationList;
    ArrayList<MedicalEducation> siftEducation;
    ArrayList<EventFlow> eventFlows;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_list2);
        ToolBarFunction.setToolBarInit(this,"衛教資訊");
        educationListView=findViewById(R.id.educationAllListView);
        educationList=(ArrayList<MedicalEducation>) getIntent().getExtras().getSerializable("AllEducationList");
        siftEducation=new ArrayList<>();

        for(int i=0;i<educationList.size();i++)
        {
                siftEducation.add(educationList.get(i));
        }
        eventFlows=new ArrayList<>();
        GetEventFlowChart();



        educationListView.setAdapter(new EducationAdapter(EducationList2.this,siftEducation));


        educationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(siftEducation.get(i).getType()==0){
                    Intent intent=new Intent();
                    intent.setClass(EducationList2.this,EducationDetail.class);
                    Bundle b=new Bundle();
                    b.putSerializable("EducationNode",siftEducation.get(i));
                    intent.putExtras(b);
                    startActivity(intent);
                }
                else if(siftEducation.get(i).getType()==1){
                    ArrayList<EventFlow> siftFlows=new ArrayList<>();
                    for(int flowCount=0;flowCount<eventFlows.size();flowCount++){
                        if(eventFlows.get(flowCount).getEducationID()==siftEducation.get(i).getEducationID()){
                            siftFlows.add(eventFlows.get(flowCount));
                        }
                    }
                    Intent intent=new Intent();
                    intent.setClass(EducationList2.this,ScheduleFlow.class);
                    Bundle b=new Bundle();
                    b.putSerializable("ScheduleFlow",siftFlows);
                    intent.putExtras(b);
                    startActivity(intent);

                }
            }
        });

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
        RequestQueue q= Volley.newRequestQueue(EducationList2.this);
        q.add(getEventFlow);
    }

}
