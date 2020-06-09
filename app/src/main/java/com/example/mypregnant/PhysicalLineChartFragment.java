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
        //database
        /*
        GetPhysicalData getPhysicalData=new GetPhysicalData(String.valueOf(userID),getActivity().getSharedPreferences("data",0).getString("WeekStartDate",""),category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                ArrayList<ArrayList<Entry>> totalDataSets=new ArrayList<>();
                ArrayList<String> totalDataName=new ArrayList<>();
                try {
                    String response="[{\"item\":\"\\u6536\\u7e2e\\u58d3\",\"details\":[{\"week\":1,\"Value\":120,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":122,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":119,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":120,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":125,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":129,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":128,\"date\":\"2019-12-06\"}]},{\"item\":\"\\u8212\\u5f35\\u58d3\",\"details\":[{\"week\":1,\"Value\":60,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":61,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":56,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":60,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":66,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":69,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":70,\"date\":\"2019-12-06\"}]}]";

                    if(category.equals("pressure"))
                    {
                        response="[{\"item\":\"\\u6536\\u7e2e\\u58d3\",\"details\":[{\"week\":1,\"Value\":120,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":122,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":119,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":120,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":125,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":129,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":128,\"date\":\"2019-12-06\"}]},{\"item\":\"\\u8212\\u5f35\\u58d3\",\"details\":[{\"week\":1,\"Value\":60,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":61,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":56,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":60,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":66,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":69,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":70,\"date\":\"2019-12-06\"}]}]";
                    }
                    else if(category.equals("sugar"))
                    {
                        response="[{\"item\":\"\\u98ef\\u524d\",\"details\":[{\"week\":1,\"Value\":47,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":55,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":42,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":53,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":53,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":58,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":0,\"date\":\"2019-12-06\"}]},{\"item\":\"\\u98ef\\u5f8c\",\"details\":[{\"week\":1,\"Value\":88,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":97,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":82,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":0,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":95,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":96,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":88,\"date\":\"2019-12-06\"}]}]";
                    }
                    else if(category.equals("bmi"))
                    {
                        response="[{\"item\":\"\\u9ad4\\u91cd\",\"details\":[{\"week\":1,\"Value\":62.5,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":65,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":68,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":67,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":68,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":68.5,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":70,\"date\":\"2019-12-06\"}]}]";
                    }
                    else if(category.equals("motherHB"))
                    {
                        response="[{\"item\":\"\\u65e9\",\"details\":[{\"week\":1,\"Value\":60,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":54,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":54,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":60,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":66,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":64,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":58,\"date\":\"2019-12-06\"}]},{\"item\":\"\\u4e2d\",\"details\":[{\"week\":1,\"Value\":55,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":58,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":59,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":68,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":62,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":62,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":69,\"date\":\"2019-12-06\"}]},{\"item\":\"\\u665a\",\"details\":[{\"week\":1,\"Value\":67,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":51,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":55,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":67,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":71,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":55,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":70,\"date\":\"2019-12-06\"}]}]";
                    }
                    else if(category.equals("fetalMovement"))
                    {
                        response="[{\"item\":\"\\u65e9\",\"details\":[{\"week\":1,\"Value\":7,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":2,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":5,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":6,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":2,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":4,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":5,\"date\":\"2019-12-06\"}]},{\"item\":\"\\u4e2d\",\"details\":[{\"week\":1,\"Value\":6,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":4,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":6,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":3,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":1,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":8,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":4,\"date\":\"2019-12-06\"}]},{\"item\":\"\\u665a\",\"details\":[{\"week\":1,\"Value\":5,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":6,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":5,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":5,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":6,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":4,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":6,\"date\":\"2019-12-06\"}]}]";
                    }
                    else if(category.equals("fetalHB"))
                    {
                        response="[{\"item\":\"\\u65e9\",\"details\":[{\"week\":1,\"Value\":42,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":51,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":53,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":64,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":66,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":56,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":77,\"date\":\"2019-12-06\"}]},{\"item\":\"\\u4e2d\",\"details\":[{\"week\":1,\"Value\":45,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":55,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":57,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":51,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":67,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":59,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":0,\"date\":\"2019-12-06\"}]},{\"item\":\"\\u665a\",\"details\":[{\"week\":1,\"Value\":42,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":20,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":66,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":69,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":61,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":0,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":73,\"date\":\"2019-12-06\"}]}]";
                    }
                    else if(category.equals("step"))
                    {
                        response="[{\"item\":\"\\u6d3b\\u52d5\\u91cf\",\"details\":[{\"week\":1,\"Value\":500,\"date\":\"2019-11-30\"},{\"week\":2,\"Value\":666,\"date\":\"2019-12-01\"},{\"week\":3,\"Value\":600,\"date\":\"2019-12-02\"},{\"week\":4,\"Value\":1200,\"date\":\"2019-12-03\"},{\"week\":5,\"Value\":900,\"date\":\"2019-12-04\"},{\"week\":6,\"Value\":450,\"date\":\"2019-12-05\"},{\"week\":7,\"Value\":800,\"date\":\"2019-12-06\"}]}]";
                    }
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
/*
            }
        });
        RequestQueue q= Volley.newRequestQueue(getActivity());
        q.add(getPhysicalData);*/



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
