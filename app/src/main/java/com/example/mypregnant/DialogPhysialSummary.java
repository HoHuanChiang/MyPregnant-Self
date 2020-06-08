package com.example.mypregnant;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetBMI;
import com.example.mypregnant.DatabaseClasses.GetSummaryPhysical;
import com.example.mypregnant.DatabaseClasses.InsertPhysical;
import com.example.mypregnant.Function.RepositoryFucntion;
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
import java.util.List;

public class DialogPhysialSummary extends Dialog {
    private String category;
    Activity context;
    int userID;
    LineChart summaryChart;
    public DialogPhysialSummary(Activity context, String category) {
        super(context);
        this.context=context;
        this.category=category;
        userID=context.getSharedPreferences("data",0).getInt("user",0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_physical_summary);
        getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;

        summaryChart=findViewById(R.id.dialogPhysicalSummaryLineChart);
        chartSetting();

    }
    protected  void chartSetting(){
        summaryChart.getDescription().setEnabled(false);

        // enable touch gestures
        summaryChart.setTouchEnabled(true);

        summaryChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        summaryChart.setDragEnabled(true);
        summaryChart.setScaleEnabled(true);
        summaryChart.setDrawGridBackground(false);
        summaryChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        summaryChart.setPinchZoom(true);

        // set an alternative background color
        summaryChart.setBackgroundColor(Color.WHITE);

        summaryChart.animateX(1500);

        Legend l = summaryChart.getLegend();

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

        XAxis xAxis = summaryChart.getXAxis();
        //xAxis.setTypeface(tfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = summaryChart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setAxisMaximum(30f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = summaryChart.getAxisRight();
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

        GetSummaryPhysical getSummaryPhysical=new GetSummaryPhysical(String.valueOf(userID),context.getSharedPreferences("data",0).getString("LastPeriodDate",""),category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<ArrayList<Entry>> totalDataSets=new ArrayList<>();
                ArrayList<String> totalDataName=new ArrayList<>();
                try {
                    JSONArray totalData=new JSONArray(response);
                    ArrayList<Float> yData=new ArrayList<>();

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

                        }
                        totalDataSets.add(eachData);
                        totalDataName.add(itemName);
                    }


                    if(category.equals("bmi")){
                        ArrayList<Entry> topLine=new ArrayList<>();
                        ArrayList<Entry> bottomLine=new ArrayList<>();
                        float height=context.getSharedPreferences("data",0).getFloat("Height",0);
                        float weight=context.getSharedPreferences("data",0).getFloat("Weight",0);
                        float bmi=weight/(height/100f)*(height/100f);
                        float afterAddTopWeight=0,afterAddBottomWeight=0;
                        if(bmi<18.5){
                            afterAddBottomWeight=0.44f;
                            afterAddTopWeight=0.58f;
                        }
                        else if(bmi>=18.5&&bmi<=24.9){
                            afterAddBottomWeight=0.35f;
                            afterAddTopWeight=0.5f;
                        }
                        else if(bmi>=24.9&&bmi<=29.9){
                            afterAddBottomWeight=0.23f;
                            afterAddTopWeight=0.33f;
                        }
                        else if(bmi>=29.9){
                            afterAddBottomWeight=0.17f;
                            afterAddTopWeight=0.27f;
                        }
                        for(int i=0;i<12;i++){
                            bottomLine.add(new Entry(i,(weight+(i+1)*0.111f)));
                            topLine.add(new Entry(i,(weight+(i+1)*0.3125f)));
                            yData.add((weight+(i+1)*0.111f));
                            yData.add((weight+(i+1)*0.3125f));
                        }
                        for(int i=12;i<40;i++){
                            bottomLine.add(new Entry(i,((weight+(12)*0.111f)+(i-12+1)*afterAddBottomWeight)));
                            topLine.add(new Entry(i,((weight+(12)*0.3125f)+(i-12+1)*afterAddTopWeight)));
                            yData.add((weight+(i+1)*afterAddBottomWeight));
                            yData.add((weight+(i+1)*afterAddTopWeight));
                        }
                        totalDataSets.add(bottomLine);
                        totalDataSets.add(topLine);
                        totalDataName.add("下限");
                        totalDataName.add("上限");
                    }

                    //invisible line
                    ArrayList<Entry> invisibleData=new ArrayList<>();
                    for(int i=0;i<40;i++)
                    {
                        invisibleData.add(new Entry(i,0));
                    }
                    totalDataSets.add(invisibleData);
                    totalDataName.add("");

                    //改y軸最大值 最小值
                    YAxis leftAxis = summaryChart.getAxisLeft();
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
                    XAxis xAxis = summaryChart.getXAxis();
                    xAxis.setLabelCount(40,true);
                    xAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            if(value==0||value==19||value==39)
                            {
                                return String.valueOf((int)value+1);
                            }
                            return "";
                        }
                    });
                    setParameters(totalDataSets,totalDataName);

                } catch (JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        RequestQueue q= Volley.newRequestQueue(context);
        q.add(getSummaryPhysical);

    }
    private void setParameters(ArrayList<ArrayList<Entry>> totalData,ArrayList<String> totalDataName){

        ArrayList<ILineDataSet> listSet=new ArrayList<>();

        if (summaryChart.getData() != null &&
                summaryChart.getData().getDataSetCount() > 0) {
            for(int i=0;i<summaryChart.getData().getDataSetCount();i++)
            {
                LineDataSet lds=(LineDataSet) summaryChart.getData().getDataSetByIndex(i);

                lds.setValues(totalData.get(i));
            }
            summaryChart.getData().notifyDataChanged();
            summaryChart.notifyDataSetChanged();
            summaryChart.invalidate();
        }
        else {
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
                eachDataSet.setDrawCircles(false);
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
            data.setValueTextColor(Color.TRANSPARENT);
            data.setValueTextSize(9f);

            // set data
            summaryChart.setData(data);
        }
    }
}
