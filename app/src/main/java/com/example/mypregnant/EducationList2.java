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
        //database
        /*
        GetEventFlow getEventFlow=new GetEventFlow("1",new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="[{\"EventFlowID\":39,\"Content\":\"\\u5efa\\u5361\\u5b55\\u5987\\u6302\\u5efa\\u5361\\u53f7 \",\"Order\":1,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":40,\"Content\":\"\\u81f35\\u697c\\u4ea7\\u79d1\\u95e8\\u8bca\\u8bca\\u533a \",\"Order\":2,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":45,\"Content\":\"\\u81ea\\u4e3b\\u6d4b\\u91cf\\u4f53\\u91cd\\u3001\\u8840\\u538b,\\u5e76\\u8bb0\\u5f55\\u5728\\u6302\\u53f7\\u5355\\u4e0a\",\"Order\":3,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":46,\"Content\":\"\\u6839\\u636e\\u9884\\u7ea6\\u5355\\u4fe1\\u606f\\u627e\\u5230\\u76f8\\u5e94\\u8bca\\u5ba4\\u548c\\u4e00\\u5bf9\\u4e00\\u62a4\\u5e08\",\"Order\":4,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":47,\"Content\":\"\\u4ea4\\u6302\\u53f7\\u5355\\u7ed9\\u8bca\\u5ba4\\u62a4\\u5e08,\\u6392\\u961f\\u5019\\u8bca\",\"Order\":5,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":48,\"Content\":\"\\u8bca\\u5ba4\\u62a4\\u58eb\\u767b\\u8bb0\\u6838\\u5bf9\\u4fe1\\u606f\",\"Order\":6,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":49,\"Content\":\"\\u533b\\u751f\\u63a5\\u8bca\\u3001\\u4ea7\\u68c0\\u3001\\u5f00\\u5177\\u76f8\\u5e94\\u68c0\\u67e5\",\"Order\":7,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":50,\"Content\":\"\\u4ea4\\u8d39 2-8\\u697c\\u6302\\u53f7\\u6536\\u8d39\\u5904\",\"Order\":8,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":51,\"Content\":\"\\u67e5\\u767d\\u5e263\\u697c\\u68c0\\u9a8c\\u7a97\\u53e3 (\\u534a\\u5c0f\\u65f6\\u540e\\u53d6\\u62a5\\u544a) \",\"Order\":9,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":52,\"Content\":\"\\u9884\\u7ea6B\\u8d85(\\u9075\\u533b\\u5631) \\u95e8\\u8bca4\\u697cB\\u8d85\\u5ba4 \",\"Order\":9,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":53,\"Content\":\"\\u7a7a\\u8179\\u62bd\\u8840 4\\u697c415\\u5ba4\\u62bd\\u8840\\u7a97\\u53e3 \\r\\n(\\u53d6\\u53f7\\u6392\\u961f)\\r\\n\",\"Order\":10,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":54,\"Content\":\"\\u8fdb\\u98df\\u3001\\u996e\\u6c34\\u81f3\\u5c11\\u534a\\u5c0f \\u65f6\\u540e\\u9a8c\\u5c3f4\\u697c\\u201c\\u5c3f\\u6db2 \\r\\n\\u5316\\u9a8c\\u5904\\u201d \\r\\n\",\"Order\":10,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":55,\"Content\":\"\\u4e0d\\u5fc5\\u7b49\\u5019\\u8840\\u3001\\u5c3f\\u5316\\u9a8c\\u7ed3\\u679c,\\u8bf7\\u8fd4\\u56de\\u5efa\\u5361\\u8bca\\u5ba4\\u62a4\\u58eb\\u5904(\\u9884\\u7ea6\\u4e0b\\u6b21\\u4ea7\\u68c0\\u65f6\\u95f4\\u53ca\\u76f8\\u5173\\u68c0\\u67e5) \",\"Order\":11,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":56,\"Content\":\"\\u75c5\\u60c5\\u9700\\u8981\\u65f6\\u6302\\u8425\\u517b\\u95e8\\u8bca\\u53f7\",\"Order\":1,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94},{\"EventFlowID\":57,\"Content\":\"\\u4ea4\\u6302\\u53f7\\u5355\\u7ed9 523 \\u8bca\\u5ba4\\u62a4\\u58eb,\\u586b\\u5199\\u8425\\u517b\\u54a8\\u8be2\\u5355,\\u5019\\u8bca \",\"Order\":2,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94},{\"EventFlowID\":58,\"Content\":\"\\u533b\\u751f\\u63a5\\u8bca,\\u5f00\\u533b\\u5631(\\u5305\\u62ec\\u9aa8\\u5bc6\\u5ea6,\\u5c3f\\u7898,\\u7ef4\\u751f\\u7d20D,\\u4f53\\u6210\\u5206\\u7b49)\",\"Order\":3,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94},{\"EventFlowID\":59,\"Content\":\"\\u51ed\\u5c31\\u8bca\\u5361\\u5361\\u7f34\\u8d39(2-8\\u697c\\u90fd\\u53ef)\",\"Order\":4,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94},{\"EventFlowID\":60,\"Content\":\"\\u9075\\u533b\\u5631\\u505a\\u76f8\\u5e94\\u8425\\u517b\\u8bc4\\u6d4b \",\"Order\":5,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94},{\"EventFlowID\":61,\"Content\":\"\\u56de523\\/522\\u8bca\\u5ba4\\u533b\\u751f\\u5904,\\u9884\\u7ea6\\u4e0b\\u6b21\\u8425\\u517b\\u95e8\\u8bca\\u5c31\\u8bca\\u65f6\\u95f4\",\"Order\":6,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94}]";
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
           /* }
        });
        RequestQueue q= Volley.newRequestQueue(EducationList2.this);
        q.add(getEventFlow);*/
    }

}
