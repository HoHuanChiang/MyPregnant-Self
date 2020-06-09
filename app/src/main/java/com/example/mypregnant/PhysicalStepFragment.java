package com.example.mypregnant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetBMI;
import com.example.mypregnant.DatabaseClasses.GetStep;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.ObjectClasses.FloatingFunction;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class PhysicalStepFragment extends Fragment {
    BarChart stepBarChart;

    TextView titleName;
    int pregnantWeek;
    String weekStartDate;
    int userID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_physical_step_history, container, false);
        stepBarChart = view.findViewById(R.id.stepBarChart);

        titleName=view.findViewById(R.id.stepName);
        pregnantWeek=getActivity().getSharedPreferences("data",0).getInt("PregnantWeek",0);
        weekStartDate=getActivity().getSharedPreferences("data",0).getString("WeekStartDate","1990-01-01");
        userID=getActivity().getSharedPreferences("data",0).getInt("user",0);
        titleName.setText("本周("+pregnantWeek+"w)趨勢圖");
        FloatingFunction floating=new FloatingFunction(view,getContext(),"step",false,false,0);
        return view;
    }
    private void setChart(){

        stepBarChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        stepBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        stepBarChart.setPinchZoom(false);

        stepBarChart.setDrawBarShadow(false);
        stepBarChart.setDrawGridBackground(false);
        stepBarChart.setFitBars(true);

        XAxis xAxis = stepBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(true);
        xAxis.setTextSize(8);
        xAxis.setTextColor(Color.BLACK);

        stepBarChart.getAxisLeft().setDrawGridLines(true);

        // add a nice and smooth animation
        stepBarChart.animateY(2500);

        stepBarChart.getLegend().setEnabled(true);
        setData();
    }
    private void setData() {
        //database
        /*
        GetStep getStep=new GetStep(String.valueOf(userID), weekStartDate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                String response="[{\"step\":500,\"date\":\"2019-11-30\"},{\"step\":666,\"date\":\"2019-12-01\"},{\"step\":600,\"date\":\"2019-12-02\"},{\"step\":1200,\"date\":\"2019-12-03\"},{\"step\":900,\"date\":\"2019-12-04\"},{\"step\":450,\"date\":\"2019-12-05\"},{\"step\":800,\"date\":\"2019-12-06\"}]";
                ArrayList<BarEntry> stepArray = new ArrayList<>();
                try {

                    JSONArray jPressures=new JSONArray(response);
                    final ArrayList<String> dateForX=new ArrayList<>();
                    ArrayList<Float> yData=new ArrayList<>();
                    for(int i=0;i<jPressures.length();i++){
                        JSONObject jPressure=jPressures.getJSONObject(i);
                        stepArray.add(new BarEntry(i, (float) jPressure.getDouble("step")));
                        yData.add((float) jPressure.getDouble("step"));
                        dateForX.add(jPressure.getString("date"));
                    }



                    //改y軸最大值 最小值
                    YAxis leftAxis = stepBarChart.getAxisLeft();
                    leftAxis.setAxisMaximum(RepositoryFucntion.findMaxNumber(yData)+20);
                    leftAxis.setAxisMinimum(RepositoryFucntion.findMinNumber(yData)-20>0?RepositoryFucntion.findMinNumber(yData)-20:0);

                    YAxis rightAxis = stepBarChart.getAxisRight();
                    rightAxis.setEnabled(false);




                    XAxis xAxis = stepBarChart.getXAxis();
                    if(dateForX.size()>1)
                    {
                        xAxis.setLabelCount(dateForX.size(),false);
                    }
                    xAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return dateForX.get((int) value).substring(5); // xVal is a string array
                        }
                    });

                    BarDataSet set1;

                    if (stepBarChart.getData() != null &&
                            stepBarChart.getData().getDataSetCount() > 0) {
                        set1 = (BarDataSet) stepBarChart.getData().getDataSetByIndex(0);
                        set1.setValues(stepArray);
                        stepBarChart.getData().notifyDataChanged();
                        stepBarChart.notifyDataSetChanged();
                        stepBarChart.invalidate();
                    } else {
                        set1 = new BarDataSet(stepArray, "活動量");
                        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
                        set1.setDrawValues(true);



                        if(stepArray.size()==0){
                            stepArray.add(new BarEntry(0,0));
                        }

                        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
                        dataSets.add(set1);

                        BarData data = new BarData(dataSets);
                        stepBarChart.setData(data);
                    }

                    stepBarChart.invalidate();
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
/*
            }
        });
        RequestQueue q= Volley.newRequestQueue(getActivity());
        q.add(getStep);*/



    }

    @Override
    public void onResume() {
        super.onResume();
        setChart();
    }

}
