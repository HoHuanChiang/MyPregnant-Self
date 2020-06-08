package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.EventFlow;
import com.example.mypregnant.ObjectClasses.MedicalEducation;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ScheduleFlow extends AppCompatActivity {
    ArrayList<EventFlow> eventFlows;
    LinearLayout scheduleFlowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_flow);
        ToolBarFunction.setToolBarInit(this,"行程流程");
        scheduleFlowLayout=findViewById(R.id.scheduleFlowLayout);
        eventFlows=(ArrayList<EventFlow>) getIntent().getExtras().getSerializable("ScheduleFlow");




        for(int i=0;i<eventFlows.size();i++)
        {
            View view= LayoutInflater.from(ScheduleFlow.this).inflate(R.layout.layout_event_schedule_flow,scheduleFlowLayout,false);
            TextView boxText1=view.findViewById(R.id.layoutFlowText1);
            TextView boxText2=view.findViewById(R.id.layoutFlowText2);
            TextView boxText3=view.findViewById(R.id.layoutFlowText3);
            ImageView downImage=view.findViewById(R.id.layoutFlowDownImage);

            boxText2.setText(eventFlows.get(i).getContent());
            int addCount=0;
            if(i+1<eventFlows.size()&&eventFlows.get(i).getOrder()==eventFlows.get(i+1).getOrder())
            {
                boxText1.setText(eventFlows.get(i+1).getContent());
                addCount++;
            }
            if(i+2<eventFlows.size()&&eventFlows.get(i).getOrder()==eventFlows.get(i+2).getOrder())
            {
                boxText3.setText(eventFlows.get(i+2).getContent());
                addCount++;
            }
            if(addCount==0)
            {
                boxText1.setVisibility(View.GONE);
                boxText3.setVisibility(View.GONE);
                LinearLayout.LayoutParams parmas = new LinearLayout.LayoutParams
                        (RepositoryFucntion.dptoPixel(300,this), ViewGroup.LayoutParams.WRAP_CONTENT);

                boxText2.setLayoutParams(parmas);
            }
            else if(addCount==1)
            {
                boxText3.setVisibility(View.GONE);
            }
            i+=addCount;

            if(i==eventFlows.size()-1)
            {
                downImage.setVisibility(View.GONE);
            }
            scheduleFlowLayout.addView(view);
        }
    }

}
