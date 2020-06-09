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
        //database
        /*
        GetSummaryPhysical getSummaryPhysical=new GetSummaryPhysical(String.valueOf(userID),context.getSharedPreferences("data",0).getString("LastPeriodDate",""),category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                ArrayList<ArrayList<Entry>> totalDataSets=new ArrayList<>();
                ArrayList<String> totalDataName=new ArrayList<>();
                try {

                    String response="[{\"item\":\"\\u6d3b\\u52d5\\u91cf\",\"details\":[{\"week\":1,\"Value\":48},{\"week\":2,\"Value\":90},{\"week\":3,\"Value\":500},{\"week\":4,\"Value\":466},{\"week\":5,\"Value\":0},{\"week\":6,\"Value\":0},{\"week\":7,\"Value\":0},{\"week\":8,\"Value\":0},{\"week\":9,\"Value\":0},{\"week\":10,\"Value\":0},{\"week\":11,\"Value\":500},{\"week\":12,\"Value\":373},{\"week\":13,\"Value\":650},{\"week\":14,\"Value\":0},{\"week\":15,\"Value\":0},{\"week\":16,\"Value\":500},{\"week\":17,\"Value\":730},{\"week\":18,\"Value\":0},{\"week\":19,\"Value\":0},{\"week\":20,\"Value\":0},{\"week\":21,\"Value\":0},{\"week\":22,\"Value\":0},{\"week\":23,\"Value\":0},{\"week\":24,\"Value\":0},{\"week\":25,\"Value\":0},{\"week\":26,\"Value\":0},{\"week\":27,\"Value\":0},{\"week\":28,\"Value\":0},{\"week\":29,\"Value\":0},{\"week\":30,\"Value\":0},{\"week\":31,\"Value\":0},{\"week\":32,\"Value\":0},{\"week\":33,\"Value\":0},{\"week\":34,\"Value\":0},{\"week\":35,\"Value\":0},{\"week\":36,\"Value\":0},{\"week\":37,\"Value\":0},{\"week\":38,\"Value\":0},{\"week\":39,\"Value\":0},{\"week\":40,\"Value\":0}]}]";
                    if(category.equals("pressure"))
                    {
                        response="[{\"item\":\"\\u6536\\u7e2e\\u58d3\",\"details\":[{\"week\":1,\"Value\":130},{\"week\":2,\"Value\":125},{\"week\":3,\"Value\":117},{\"week\":4,\"Value\":155},{\"week\":5,\"Value\":0},{\"week\":6,\"Value\":0},{\"week\":7,\"Value\":120},{\"week\":8,\"Value\":140},{\"week\":9,\"Value\":147},{\"week\":10,\"Value\":270},{\"week\":11,\"Value\":560},{\"week\":12,\"Value\":491},{\"week\":13,\"Value\":133},{\"week\":14,\"Value\":0},{\"week\":15,\"Value\":0},{\"week\":16,\"Value\":120},{\"week\":17,\"Value\":122},{\"week\":18,\"Value\":46},{\"week\":19,\"Value\":0},{\"week\":20,\"Value\":0},{\"week\":21,\"Value\":0},{\"week\":22,\"Value\":0},{\"week\":23,\"Value\":0},{\"week\":24,\"Value\":0},{\"week\":25,\"Value\":0},{\"week\":26,\"Value\":0},{\"week\":27,\"Value\":0},{\"week\":28,\"Value\":0},{\"week\":29,\"Value\":0},{\"week\":30,\"Value\":0},{\"week\":31,\"Value\":0},{\"week\":32,\"Value\":0},{\"week\":33,\"Value\":0},{\"week\":34,\"Value\":0},{\"week\":35,\"Value\":0},{\"week\":36,\"Value\":0},{\"week\":37,\"Value\":0},{\"week\":38,\"Value\":0},{\"week\":39,\"Value\":0},{\"week\":40,\"Value\":0}]},{\"item\":\"\\u8212\\u5f35\\u58d3\",\"details\":[{\"week\":1,\"Value\":64},{\"week\":2,\"Value\":58},{\"week\":3,\"Value\":44},{\"week\":4,\"Value\":47},{\"week\":5,\"Value\":0},{\"week\":6,\"Value\":0},{\"week\":7,\"Value\":60},{\"week\":8,\"Value\":56},{\"week\":9,\"Value\":54},{\"week\":10,\"Value\":118},{\"week\":11,\"Value\":257},{\"week\":12,\"Value\":94},{\"week\":13,\"Value\":81},{\"week\":14,\"Value\":0},{\"week\":15,\"Value\":0},{\"week\":16,\"Value\":60},{\"week\":17,\"Value\":61},{\"week\":18,\"Value\":53},{\"week\":19,\"Value\":0},{\"week\":20,\"Value\":0},{\"week\":21,\"Value\":0},{\"week\":22,\"Value\":0},{\"week\":23,\"Value\":0},{\"week\":24,\"Value\":0},{\"week\":25,\"Value\":0},{\"week\":26,\"Value\":0},{\"week\":27,\"Value\":0},{\"week\":28,\"Value\":0},{\"week\":29,\"Value\":0},{\"week\":30,\"Value\":0},{\"week\":31,\"Value\":0},{\"week\":32,\"Value\":0},{\"week\":33,\"Value\":0},{\"week\":34,\"Value\":0},{\"week\":35,\"Value\":0},{\"week\":36,\"Value\":0},{\"week\":37,\"Value\":0},{\"week\":38,\"Value\":0},{\"week\":39,\"Value\":0},{\"week\":40,\"Value\":0}]}]";
                    }
                    else if(category.equals("sugar"))
                    {
                        response="[{\"item\":\"\\u98ef\\u524d\",\"details\":[{\"week\":1,\"Value\":54},{\"week\":2,\"Value\":62},{\"week\":3,\"Value\":52},{\"week\":4,\"Value\":47},{\"week\":5,\"Value\":0},{\"week\":6,\"Value\":0},{\"week\":7,\"Value\":0},{\"week\":8,\"Value\":0},{\"week\":9,\"Value\":100},{\"week\":10,\"Value\":80},{\"week\":11,\"Value\":203},{\"week\":12,\"Value\":203},{\"week\":13,\"Value\":73},{\"week\":14,\"Value\":0},{\"week\":15,\"Value\":0},{\"week\":16,\"Value\":47},{\"week\":17,\"Value\":50},{\"week\":18,\"Value\":0},{\"week\":19,\"Value\":0},{\"week\":20,\"Value\":0},{\"week\":21,\"Value\":0},{\"week\":22,\"Value\":0},{\"week\":23,\"Value\":0},{\"week\":24,\"Value\":0},{\"week\":25,\"Value\":0},{\"week\":26,\"Value\":0},{\"week\":27,\"Value\":0},{\"week\":28,\"Value\":0},{\"week\":29,\"Value\":0},{\"week\":30,\"Value\":0},{\"week\":31,\"Value\":0},{\"week\":32,\"Value\":0},{\"week\":33,\"Value\":0},{\"week\":34,\"Value\":0},{\"week\":35,\"Value\":0},{\"week\":36,\"Value\":0},{\"week\":37,\"Value\":0},{\"week\":38,\"Value\":0},{\"week\":39,\"Value\":0},{\"week\":40,\"Value\":0}]},{\"item\":\"\\u98ef\\u5f8c\",\"details\":[{\"week\":1,\"Value\":105},{\"week\":2,\"Value\":100},{\"week\":3,\"Value\":52},{\"week\":4,\"Value\":48},{\"week\":5,\"Value\":0},{\"week\":6,\"Value\":0},{\"week\":7,\"Value\":0},{\"week\":8,\"Value\":0},{\"week\":9,\"Value\":0},{\"week\":10,\"Value\":0},{\"week\":11,\"Value\":0},{\"week\":12,\"Value\":90},{\"week\":13,\"Value\":90},{\"week\":14,\"Value\":0},{\"week\":15,\"Value\":0},{\"week\":16,\"Value\":88},{\"week\":17,\"Value\":90},{\"week\":18,\"Value\":0},{\"week\":19,\"Value\":0},{\"week\":20,\"Value\":0},{\"week\":21,\"Value\":0},{\"week\":22,\"Value\":0},{\"week\":23,\"Value\":0},{\"week\":24,\"Value\":0},{\"week\":25,\"Value\":0},{\"week\":26,\"Value\":0},{\"week\":27,\"Value\":0},{\"week\":28,\"Value\":0},{\"week\":29,\"Value\":0},{\"week\":30,\"Value\":0},{\"week\":31,\"Value\":0},{\"week\":32,\"Value\":0},{\"week\":33,\"Value\":0},{\"week\":34,\"Value\":0},{\"week\":35,\"Value\":0},{\"week\":36,\"Value\":0},{\"week\":37,\"Value\":0},{\"week\":38,\"Value\":0},{\"week\":39,\"Value\":0},{\"week\":40,\"Value\":0}]}]";
                    }
                    else if(category.equals("bmi"))
                    {
                        response="[{\"item\":\"\\u9ad4\\u91cd\",\"details\":[{\"week\":1,\"Value\":70.8},{\"week\":2,\"Value\":71.3},{\"week\":3,\"Value\":71.6},{\"week\":4,\"Value\":71.544444444444},{\"week\":5,\"Value\":0},{\"week\":6,\"Value\":0},{\"week\":7,\"Value\":0},{\"week\":8,\"Value\":0},{\"week\":9,\"Value\":72},{\"week\":10,\"Value\":70.2375},{\"week\":11,\"Value\":54.333333333333},{\"week\":12,\"Value\":63.75},{\"week\":13,\"Value\":75.5},{\"week\":14,\"Value\":0},{\"week\":15,\"Value\":0},{\"week\":16,\"Value\":62.5},{\"week\":17,\"Value\":66.4375},{\"week\":18,\"Value\":0},{\"week\":19,\"Value\":0},{\"week\":20,\"Value\":0},{\"week\":21,\"Value\":0},{\"week\":22,\"Value\":0},{\"week\":23,\"Value\":0},{\"week\":24,\"Value\":0},{\"week\":25,\"Value\":0},{\"week\":26,\"Value\":0},{\"week\":27,\"Value\":0},{\"week\":28,\"Value\":0},{\"week\":29,\"Value\":0},{\"week\":30,\"Value\":0},{\"week\":31,\"Value\":0},{\"week\":32,\"Value\":0},{\"week\":33,\"Value\":0},{\"week\":34,\"Value\":0},{\"week\":35,\"Value\":0},{\"week\":36,\"Value\":0},{\"week\":37,\"Value\":0},{\"week\":38,\"Value\":0},{\"week\":39,\"Value\":0},{\"week\":40,\"Value\":0}]}]";
                    }
                    else if(category.equals("motherHB"))
                    {
                        response="[{\"item\":\"\\u5fc3\\u8df3\\u6b21\\u6578\",\"details\":[{\"week\":1,\"Value\":60},{\"week\":2,\"Value\":69},{\"week\":3,\"Value\":50},{\"week\":4,\"Value\":42},{\"week\":5,\"Value\":0},{\"week\":6,\"Value\":0},{\"week\":7,\"Value\":0},{\"week\":8,\"Value\":0},{\"week\":9,\"Value\":0},{\"week\":10,\"Value\":0},{\"week\":11,\"Value\":50},{\"week\":12,\"Value\":50},{\"week\":13,\"Value\":0},{\"week\":14,\"Value\":0},{\"week\":15,\"Value\":0},{\"week\":16,\"Value\":60},{\"week\":17,\"Value\":61},{\"week\":18,\"Value\":0},{\"week\":19,\"Value\":0},{\"week\":20,\"Value\":0},{\"week\":21,\"Value\":0},{\"week\":22,\"Value\":0},{\"week\":23,\"Value\":0},{\"week\":24,\"Value\":0},{\"week\":25,\"Value\":0},{\"week\":26,\"Value\":0},{\"week\":27,\"Value\":0},{\"week\":28,\"Value\":0},{\"week\":29,\"Value\":0},{\"week\":30,\"Value\":0},{\"week\":31,\"Value\":0},{\"week\":32,\"Value\":0},{\"week\":33,\"Value\":0},{\"week\":34,\"Value\":0},{\"week\":35,\"Value\":0},{\"week\":36,\"Value\":0},{\"week\":37,\"Value\":0},{\"week\":38,\"Value\":0},{\"week\":39,\"Value\":0},{\"week\":40,\"Value\":0}]}]";
                    }
                    else if(category.equals("fetalMovement"))
                    {
                        response="[{\"item\":\"\\u80ce\\u52d5\\u6b21\\u6578\",\"details\":[{\"week\":1,\"Value\":4},{\"week\":2,\"Value\":5},{\"week\":3,\"Value\":0},{\"week\":4,\"Value\":6},{\"week\":5,\"Value\":0},{\"week\":6,\"Value\":0},{\"week\":7,\"Value\":0},{\"week\":8,\"Value\":0},{\"week\":9,\"Value\":4},{\"week\":10,\"Value\":4},{\"week\":11,\"Value\":4},{\"week\":12,\"Value\":0},{\"week\":13,\"Value\":0},{\"week\":14,\"Value\":0},{\"week\":15,\"Value\":0},{\"week\":16,\"Value\":6},{\"week\":17,\"Value\":4},{\"week\":18,\"Value\":0},{\"week\":19,\"Value\":0},{\"week\":20,\"Value\":0},{\"week\":21,\"Value\":0},{\"week\":22,\"Value\":0},{\"week\":23,\"Value\":0},{\"week\":24,\"Value\":0},{\"week\":25,\"Value\":0},{\"week\":26,\"Value\":0},{\"week\":27,\"Value\":0},{\"week\":28,\"Value\":0},{\"week\":29,\"Value\":0},{\"week\":30,\"Value\":0},{\"week\":31,\"Value\":0},{\"week\":32,\"Value\":0},{\"week\":33,\"Value\":0},{\"week\":34,\"Value\":0},{\"week\":35,\"Value\":0},{\"week\":36,\"Value\":0},{\"week\":37,\"Value\":0},{\"week\":38,\"Value\":0},{\"week\":39,\"Value\":0},{\"week\":40,\"Value\":0}]}]";
                    }
                    else if(category.equals("fetalHB"))
                    {
                        response="[{\"item\":\"\\u80ce\\u5fc3\\u97f3\\u6b21\\u6578\",\"details\":[{\"week\":1,\"Value\":5},{\"week\":2,\"Value\":5},{\"week\":3,\"Value\":3},{\"week\":4,\"Value\":4},{\"week\":5,\"Value\":0},{\"week\":6,\"Value\":0},{\"week\":7,\"Value\":0},{\"week\":8,\"Value\":0},{\"week\":9,\"Value\":200},{\"week\":10,\"Value\":200},{\"week\":11,\"Value\":0},{\"week\":12,\"Value\":5},{\"week\":13,\"Value\":5},{\"week\":14,\"Value\":0},{\"week\":15,\"Value\":0},{\"week\":16,\"Value\":43},{\"week\":17,\"Value\":56},{\"week\":18,\"Value\":0},{\"week\":19,\"Value\":0},{\"week\":20,\"Value\":0},{\"week\":21,\"Value\":0},{\"week\":22,\"Value\":0},{\"week\":23,\"Value\":0},{\"week\":24,\"Value\":0},{\"week\":25,\"Value\":0},{\"week\":26,\"Value\":0},{\"week\":27,\"Value\":0},{\"week\":28,\"Value\":0},{\"week\":29,\"Value\":0},{\"week\":30,\"Value\":0},{\"week\":31,\"Value\":0},{\"week\":32,\"Value\":0},{\"week\":33,\"Value\":0},{\"week\":34,\"Value\":0},{\"week\":35,\"Value\":0},{\"week\":36,\"Value\":0},{\"week\":37,\"Value\":0},{\"week\":38,\"Value\":0},{\"week\":39,\"Value\":0},{\"week\":40,\"Value\":0}]}]";
                    }
                    else if(category.equals("step"))
                    {
                        response="[{\"item\":\"\\u6d3b\\u52d5\\u91cf\",\"details\":[{\"week\":1,\"Value\":48},{\"week\":2,\"Value\":90},{\"week\":3,\"Value\":500},{\"week\":4,\"Value\":466},{\"week\":5,\"Value\":0},{\"week\":6,\"Value\":0},{\"week\":7,\"Value\":0},{\"week\":8,\"Value\":0},{\"week\":9,\"Value\":0},{\"week\":10,\"Value\":0},{\"week\":11,\"Value\":500},{\"week\":12,\"Value\":373},{\"week\":13,\"Value\":650},{\"week\":14,\"Value\":0},{\"week\":15,\"Value\":0},{\"week\":16,\"Value\":500},{\"week\":17,\"Value\":730},{\"week\":18,\"Value\":0},{\"week\":19,\"Value\":0},{\"week\":20,\"Value\":0},{\"week\":21,\"Value\":0},{\"week\":22,\"Value\":0},{\"week\":23,\"Value\":0},{\"week\":24,\"Value\":0},{\"week\":25,\"Value\":0},{\"week\":26,\"Value\":0},{\"week\":27,\"Value\":0},{\"week\":28,\"Value\":0},{\"week\":29,\"Value\":0},{\"week\":30,\"Value\":0},{\"week\":31,\"Value\":0},{\"week\":32,\"Value\":0},{\"week\":33,\"Value\":0},{\"week\":34,\"Value\":0},{\"week\":35,\"Value\":0},{\"week\":36,\"Value\":0},{\"week\":37,\"Value\":0},{\"week\":38,\"Value\":0},{\"week\":39,\"Value\":0},{\"week\":40,\"Value\":0}]}]";
                    }
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
/*
            }
        });
        RequestQueue q= Volley.newRequestQueue(context);
        q.add(getSummaryPhysical);
*/
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
