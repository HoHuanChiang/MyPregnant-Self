package com.example.mypregnant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetEducationList;
import com.example.mypregnant.ObjectClasses.EventFlow;
import com.example.mypregnant.ObjectClasses.MedicalEducation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AdvertiseEducationFragment extends Fragment {

    LinearLayout advertiseEducationLayout;
    TextView advertiseEducationTitle;
    int pregnantWeek;
    ArrayList<MedicalEducation> myEducations;
    ArrayList<EventFlow> eventFlows;
    int isEducational;
    public AdvertiseEducationFragment(ArrayList<MedicalEducation> myEducations, int isEducational, ArrayList<EventFlow> eventFlows){
        this.myEducations=myEducations;
        this.isEducational=isEducational;
        this.eventFlows=eventFlows;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_advertise_edcation, container, false);
        advertiseEducationLayout=view.findViewById(R.id.advertiseEducationLayout);
        advertiseEducationTitle=view.findViewById(R.id.advertiseEducationTitle);
        pregnantWeek=getActivity().getSharedPreferences("data",0).getInt("CurrentPregnantWeek",0);

        if(isEducational==0){
            advertiseEducationTitle.setText("當周醫院資訊");
        }
        else if(isEducational==1){
            advertiseEducationTitle.setText("當周衛教小知識");
        }

        for(int i=0;i<myEducations.size();i++) {

            View childView=getLayoutInflater().from(getActivity()).inflate(R.layout.layout_education_simple,advertiseEducationLayout,false);
            TextView titleText=childView.findViewById(R.id.educationSimpleTitle);
            titleText.setText(myEducations.get(i).getEducationName());
            advertiseEducationLayout.addView(childView);


            final int finalI = i;
            childView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(myEducations.get(finalI).getType()==0){
                        Intent intent=new Intent();
                        intent.setClass(getActivity(),EducationDetail.class);
                        Bundle b=new Bundle();
                        b.putSerializable("EducationNode",myEducations.get(finalI));
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                    else if(myEducations.get(finalI).getType()==1){
                        ArrayList<EventFlow> siftFlows=new ArrayList<>();
                        for(int flowCount=0;flowCount<eventFlows.size();flowCount++){
                            if(eventFlows.get(flowCount).getEducationID()==myEducations.get(finalI).getEducationID()){
                                siftFlows.add(eventFlows.get(flowCount));
                            }
                        }
                        Intent intent=new Intent();
                        intent.setClass(getContext(),ScheduleFlow.class);
                        Bundle b=new Bundle();
                        b.putSerializable("ScheduleFlow",siftFlows);
                        intent.putExtras(b);
                        startActivity(intent);

                    }
                }
            });

        }


        return view;
    }


}
