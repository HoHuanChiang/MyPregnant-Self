package com.example.mypregnant;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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
import com.example.mypregnant.DatabaseClasses.GetBloodPressure;
import com.example.mypregnant.DatabaseClasses.GetPhysicalData;
import com.example.mypregnant.DatabaseClasses.GetSummaryPhysical;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.ObjectClasses.FloatingFunction;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhysicalLineChartFragment extends Fragment {

    LineChart eachLineChart;
    TextView titleName;
    String weekStartDate;
    int pregnantWeek;
    int userID;
    String category;

    public PhysicalLineChartFragment(String category)
    {
        this.category=category;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_physical_line_chart, container, false);

        eachLineChart = view.findViewById(R.id.totalLineChart);
        titleName=view.findViewById(R.id.lineChartName);
        pregnantWeek=getActivity().getSharedPreferences("data",0).getInt("PregnantWeek",0);
        weekStartDate=getActivity().getSharedPreferences("data",0).getString("WeekStartDate","1990-01-01");
        userID=getActivity().getSharedPreferences("data",0).getInt("user",0);
        titleName.setText("本周("+pregnantWeek+"w)趨勢圖");

        if(category.equals("pressure"))
        {
            FloatingFunction floating=new FloatingFunction(view,getContext(),category,true,false,0);
        }
        else if(category.equals("sugar"))
        {
            FloatingFunction floating=new FloatingFunction(view,getContext(),category,true,true,R.array.EatStatus);
        }
        else if(category.equals("bmi"))
        {
            FloatingFunction floating=new FloatingFunction(view,getContext(),category,false,false,0);
        }
        else if(category.equals("motherHB"))
        {
            FloatingFunction floating=new FloatingFunction(view,getContext(),category,false,true,R.array.Session);
        }
        else if(category.equals("fetalMovement"))
        {
            FloatingFunction floating=new FloatingFunction(view,getContext(),category,false,true,R.array.Session);
        }
        else if(category.equals("fetalHB"))
        {
            FloatingFunction floating=new FloatingFunction(view,getContext(),category,false,true,R.array.Session);
        }
        else if(category.equals("step"))
        {
            FloatingFunction floating=new FloatingFunction(view,getContext(),category,false,false,0);
        }


        return view;
    }
    private void chartSetting()
    {
        eachLineChart.getDescription().setEnabled(false);

        // enable touch gestures
        eachLineChart.setTouchEnabled(true);

        eachLineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        eachLineChart.setDragEnabled(true);
        eachLineChart.setScaleEnabled(true);
        eachLineChart.setDrawGridBackground(false);
        eachLineChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        eachLineChart.setPinchZoom(true);

        // set an alternative background color
        eachLineChart.setBackgroundColor(Color.WHITE);

        eachLineChart.animateX(1500);

        Legend l = eachLineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(tfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);

        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = eachLineChart.getXAxis();
        //xAxis.setTypeface(tfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = eachLineChart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(30f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = eachLineChart.getAxisRight();
        //rightAxis.setTypeface(tfLight);
        rightAxis.setEnabled(false);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(900);
        rightAxis.setAxisMinimum(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
        setData();
    }
    private void setData() {
        GetPhysicalData getPhysicalData=new GetPhysicalData(String.valueOf(userID),getActivity().getSharedPreferences("data",0).getString("WeekStartDate",""),category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<ArrayList<Entry>> totalDataSets=new ArrayList<>();
                ArrayList<String> totalDataName=new ArrayList<>();
                try {
                    JSONArray totalData=new JSONArray(response);
                    ArrayList<Float> yData=new ArrayList<>();
                    final ArrayList<String> dateStringX=new ArrayList<>();

                    for(int i=0;i<totalData.length();i++){
                        ArrayList<Entry> eachData=new ArrayList<>();
                        JSONObject jData=totalData.getJSONObject(i);
                        String itemName=jData.getString("item");
                        JSONArray jeachWeekData=jData.getJSONArray("details");
                        for(int j=0;j<jeachWeekData.length();j++)
                        {
                            if(jeachWeekData.getJSONObject(j).getInt("Value")!=0)
                            {
                                eachData.add(new Entry(j,Float.parseFloat(jeachWeekData.getJSONObject(j).getString("Value"))));
                                yData.add(Float.parseFloat(jeachWeekData.getJSONObject(j).getString("Value")));
                            }
                            if(i==0)
                            {
                                dateStringX.add(jeachWeekData.getJSONObject(j).getString("date"));
                            }
                        }
                        totalDataSets.add(eachData);
                        totalDataName.add(itemName);
                    }

                    ArrayList<Entry> invisibleData=new ArrayList<>();
                    for(int i=0;i<7;i++)
                    {
                        invisibleData.add(new Entry(i,0));
                    }
                    totalDataSets.add(invisibleData);
                    totalDataName.add("");



                    //改y軸最大值 最小值
                    YAxis leftAxis = eachLineChart.getAxisLeft();
                    if(yData.size()!=0)
                    {
                        leftAxis.setAxisMaximum(RepositoryFucntion.findMaxNumber(yData)+10);
                        leftAxis.setAxisMinimum(RepositoryFucntion.findMinNumber(yData)-10>0?RepositoryFucntion.findMinNumber(yData)-10:0);
                    }
                    else
                    {
                        leftAxis.setAxisMaximum(50);
                        leftAxis.setAxisMinimum(0);
                    }

                    //x軸改成string 日期
                    XAxis xAxis = eachLineChart.getXAxis();
                    xAxis.setLabelCount(7,true);
                    xAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {

                            return dateStringX.get((int)value).substring(5);
                        }
                    });
                    setParameters(totalDataSets,totalDataName);

                } catch (JSONException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        RequestQueue q= Volley.newRequestQueue(getActivity());
        q.add(getPhysicalData);



    }
    private void setParameters(ArrayList<ArrayList<Entry>> totalData,ArrayList<String> totalDataName){

        ArrayList<ILineDataSet> listSet=new ArrayList<>();
/*
        if (eachLineChart.getData() != null &&
                eachLineChart.getData().getDataSetCount() > 0) {
            Log.e("first",category+"first");
            for(int i=0;i<eachLineChart.getData().getDataSetCount();i++)
            {
                LineDataSet lds=(LineDataSet) eachLineChart.getData().getDataSetByIndex(i);

                lds.setValues(totalData.get(i));
            }
            eachLineChart.getData().notifyDataChanged();
            eachLineChart.notifyDataSetChanged();
            eachLineChart.invalidate();
        }
        else {*/
            for (int i = 0; i < totalData.size(); i++) {
                LineDataSet eachDataSet = new LineDataSet(totalData.get(i), totalDataName.get(i));
                eachDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
                eachDataSet.setColor(ColorTemplate.JOYFUL_COLORS[i]);
                eachDataSet.setCircleColor(Color.BLACK);
                eachDataSet.setLineWidth(2f);
                eachDataSet.setCircleRadius(3f);
                eachDataSet.setFillAlpha(65);
                eachDataSet.setFillColor(ColorTemplate.getHoloBlue());
                eachDataSet.setHighLightColor(Color.rgb(244, 117, 117));
                eachDataSet.setDrawCircleHole(false);
                if(i==totalData.size()-1)
                {
                    eachDataSet.setColor(Color.TRANSPARENT);
                    eachDataSet.setVisible(false);
                }
                if(totalData.get(i).size()==0)
                {
                    totalData.get(i).add(new Entry(0,0));
                    eachDataSet.setVisible(false);
                }
                listSet.add(eachDataSet);
            }
            LineData data = new LineData(listSet);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);

            // set data
            eachLineChart.setData(data);

        }
    //}

    @Override
    public void onResume() {
        super.onResume();
        chartSetting();
    }

}
