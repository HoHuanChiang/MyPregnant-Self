package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetDayPhyscialData;
import com.example.mypregnant.DatabaseClasses.GetEventFlow;
import com.example.mypregnant.DatabaseClasses.GetEventLocation;
import com.example.mypregnant.DatabaseClasses.GetSchedule;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.EventFlow;
import com.example.mypregnant.ObjectClasses.EventLocation;
import com.example.mypregnant.ObjectClasses.ScheduleEvent;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.zip.Inflater;

public class ScheduleCalendar extends AppCompatActivity {
    ImageButton addBtn,calendarUpdateBtn;
    TextView calendarScheduleMonth,eventDate,eventWeek,eventTime,eventNumber,eventNote;
    Button calendarScheduleDetailsBtn;
    CompactCalendarView calendarScheduleView;
    LinearLayout yesLayout,dateWeekLayout,physicalLayout;
    LinearLayout locationLayout;
    int userID;
    int pregnantWeek;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy - MMM", Locale.getDefault());
    ArrayList<ScheduleEvent> events;
    ArrayList<EventLocation> eventLocations;
    ArrayList<EventFlow> eventFlows;
    ArrayList<EventFlow> siftEventFlows;
    ScheduleEvent currentEvent;
    LayoutInflater inflater;
    Date currentDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_calendar);
        calendarScheduleDetailsBtn=findViewById(R.id.calendarScheduleDetailsBtn);
        calendarScheduleMonth=findViewById(R.id.calendarScheduleMonth);
        calendarScheduleView=findViewById(R.id.calendarScheduleView);
        eventDate=findViewById(R.id.calendarSchdeuleEventDate);
        eventWeek=findViewById(R.id.calendarSchdeuleEventWeek);
        eventTime=findViewById(R.id.calendarSchdeuleEventTime);
        eventNumber=findViewById(R.id.calendarSchdeuleEventNumber);
        eventNote=findViewById(R.id.calendarSchdeuleEventNote);
        yesLayout=findViewById(R.id.calendarSchdeuleEventYesLayout);
        locationLayout=findViewById(R.id.calendarScheduleLocationLayout);
        calendarUpdateBtn=findViewById(R.id.calendarUpdateBtn);
        dateWeekLayout=findViewById(R.id.calendarScheduleDateWeekLayout);
        physicalLayout=findViewById(R.id.calendarSchedulePhysicalLayout);
        inflater=getLayoutInflater().from(this);
        userID=getSharedPreferences("data",0).getInt("user",0);
        pregnantWeek=getSharedPreferences("data",0).getInt("PregnantWeek",0);
        ToolBarFunction.setToolBarInit(this,"產檢行程");
        currentDate=Calendar.getInstance().getTime();
        addBtn=findViewById(R.id.recordAddBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogRecordAdd dialogRecordAdd=new DialogRecordAdd(ScheduleCalendar.this);
                dialogRecordAdd.show();
                dialogRecordAdd.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        GetScheduleData();
                    }
                });
            }
        });

        calendarScheduleView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarScheduleView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarScheduleView.setUseThreeLetterAbbreviation(true);
        calendarScheduleView.displayOtherMonthDays(false);
        calendarScheduleView.setShouldDrawDaysHeader(true);
        calendarScheduleView.setCurrentDayBackgroundColor(getResources().getColor(R.color.myLightBlue));
        calendarScheduleView.setCurrentSelectedDayBackgroundColor(getResources().getColor(R.color.backgroundPink));

        GetEventFlowChart();

        Calendar cal = Calendar.getInstance();
        final String date=cal.get(Calendar.YEAR)+"-"+String.format("%02d",cal.get(Calendar.MONTH)+1)+"-"+String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
        eventDate.setText(date);
        eventWeek.setText(String.valueOf(RepositoryFucntion.getPregnantWeekSpanWeek(ScheduleCalendar.this,date)));


        calendarScheduleDetailsBtn.setVisibility(View.GONE);
        calendarScheduleMonth.setText(dateFormatForMonth.format(calendarScheduleView.getFirstDayOfCurrentMonth()));
        calendarScheduleView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                clickDate(dateClicked);

            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                calendarScheduleMonth.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });
        calendarUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogScheduleUpdate dialogScheduleUpdate=new DialogScheduleUpdate(ScheduleCalendar.this,currentEvent);
                dialogScheduleUpdate.show();
                dialogScheduleUpdate.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        GetScheduleData();
                    }
                });
            }
        });
        calendarScheduleDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(ScheduleCalendar.this,ScheduleFlow.class);
                Bundle b=new Bundle();
                b.putSerializable("ScheduleFlow",siftEventFlows);
                intent.putExtras(b);
                startActivity(intent);
            }
        });



    }
    public void clickDate(Date dateClicked){
        currentDate=dateClicked;
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateClicked);
        String date=cal.get(Calendar.YEAR)+"-"+String.format("%02d",cal.get(Calendar.MONTH)+1)+"-"+String.format("%02d",cal.get(Calendar.DAY_OF_MONTH));
        eventDate.setText(date);
        eventWeek.setText(String.valueOf(RepositoryFucntion.getPregnantWeekSpanWeek(ScheduleCalendar.this,date)));
        calendarScheduleDetailsBtn.setVisibility(View.GONE);
        if(calendarScheduleView.getEvents(dateClicked).size()==0)
        {
            locationLayout.setVisibility(View.GONE);
            yesLayout.setVisibility(View.GONE);
        }
        else
        {
            for(int i=0;i<events.size();i++)
            {
                if(events.get(i).getDate().equals(date))
                {
                    eventTime.setText(events.get(i).getTime());
                    eventNumber.setText(String.valueOf(events.get(i).getNumber()));
                    eventNote.setText(events.get(i).getNote());
                    currentEvent=events.get(i);
                    break;
                }
            }

            locationLayout.removeViewsInLayout(1,locationLayout.getChildCount()-1);
            for(int i=0;i<eventLocations.size();i++)
            {
                if(eventLocations.get(i).getPregnantWeek()==Integer.parseInt(eventWeek.getText().toString()))
                {
                    View locationView= inflater.inflate(R.layout.layout_event_location_table,locationLayout,false);
                    TextView itemText=locationView.findViewById(R.id.layoutTableItem);
                    TextView locationText=locationView.findViewById(R.id.layoutTableLocation);
                    itemText.setText(eventLocations.get(i).getEventName());
                    locationText.setText(eventLocations.get(i).getLocation());
                    locationLayout.addView(locationView);

                }
            }

            siftEventFlows=new ArrayList<>();
            for(int i=0;i<eventFlows.size();i++)
            {
                if(eventFlows.get(i).getPregnantWeek()==Integer.parseInt(eventWeek.getText().toString()))
                {
                    Log.e("in",eventFlows.get(i).getContent());
                    siftEventFlows.add(eventFlows.get(i));
                    calendarScheduleDetailsBtn.setVisibility(View.VISIBLE);
                }
            }
            locationLayout.setVisibility(View.VISIBLE);
            yesLayout.setVisibility(View.VISIBLE);
        }
        GetPhysicalData(date);

    }

    public void GetPhysicalData(final String clickedDate){

        physicalLayout.removeViewsInLayout(1,physicalLayout.getChildCount()-1);
        //database
        /*
        GetDayPhyscialData getDayPhyscialData=new GetDayPhyscialData(String.valueOf(userID), clickedDate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
*/
                try {
                    String response="[{\"name\":\"\\u5e73\\u5747\\u98ef\\u524d\\u8840\\u7cd6\",\"avgValue\":55,\"abnormalExist\":0,\"details\":[{\"time\":\"16:39:00\",\"value\":50,\"isAbnormal\":0},{\"time\":\"12:39:00\",\"value\":60,\"isAbnormal\":0}]},{\"name\":\"\\u5e73\\u5747\\u98ef\\u5f8c\\u8840\\u7cd6\",\"avgValue\":103,\"abnormalExist\":1,\"details\":[{\"time\":\"16:40:00\",\"value\":103,\"isAbnormal\":1}]},{\"name\":\"\\u5e73\\u5747\\u6536\\u7e2e\\u58d3\",\"avgValue\":120,\"abnormalExist\":0,\"details\":[{\"time\":\"18:22:00\",\"value\":120,\"isAbnormal\":0}]},{\"name\":\"\\u5e73\\u5747\\u8212\\u5f35\\u58d3\",\"avgValue\":47,\"abnormalExist\":0,\"details\":[{\"time\":\"18:22:00\",\"value\":47,\"isAbnormal\":0}]},{\"name\":\"\\u5e73\\u5747\\u9ad4\\u91cd\",\"avgValue\":71,\"abnormalExist\":0,\"details\":[{\"time\":\"15:40:31\",\"value\":71,\"isAbnormal\":0}]},{\"name\":\"\\u5e73\\u5747\\u80ce\\u52d5\",\"avgValue\":5,\"abnormalExist\":0,\"details\":[{\"time\":\"\\u65e9\",\"value\":4,\"isAbnormal\":0},{\"time\":\"\\u4e2d\",\"value\":6,\"isAbnormal\":0},{\"time\":\"\\u665a\",\"value\":5,\"isAbnormal\":0}]},{\"name\":\"\\u5e73\\u5747\\u80ce\\u5fc3\\u97f3\",\"avgValue\":5,\"abnormalExist\":0,\"details\":[{\"time\":\"\\u65e9\",\"value\":5,\"isAbnormal\":0},{\"time\":\"\\u4e2d\",\"value\":7,\"isAbnormal\":0},{\"time\":\"\\u665a\",\"value\":9,\"isAbnormal\":0},{\"time\":\"\\u665a\",\"value\":4,\"isAbnormal\":0},{\"time\":\"\\u65e9\",\"value\":6,\"isAbnormal\":0},{\"time\":\"\\u4e2d\",\"value\":4,\"isAbnormal\":0},{\"time\":\"\\u665a\",\"value\":5,\"isAbnormal\":0},{\"time\":\"\\u4e2d\",\"value\":3,\"isAbnormal\":0}]},{\"name\":\"\\u5e73\\u5747\\u5abd\\u5abd\\u5fc3\\u8df3\",\"avgValue\":65,\"abnormalExist\":1,\"details\":[{\"time\":\"\\u65e9\",\"value\":60,\"isAbnormal\":0},{\"time\":\"\\u4e2d\",\"value\":66,\"isAbnormal\":0},{\"time\":\"\\u665a\",\"value\":68,\"isAbnormal\":1}]},{\"name\":\"\\u5e73\\u5747\\u6d3b\\u52d5\\u91cf\",\"avgValue\":50,\"abnormalExist\":0,\"details\":[{\"time\":\"16:15:00\",\"value\":50,\"isAbnormal\":0}]}]";
                    JSONArray jItems = new JSONArray(response);
                    for(int i=0;i<jItems.length();i++){
                        final JSONObject eachItem=jItems.getJSONObject(i);
                        final View physicalView=inflater.inflate(R.layout.layout_physical_list,physicalLayout,false);
                        TextView nameText=physicalView.findViewById(R.id.physicalListName);
                        TextView valueText=physicalView.findViewById(R.id.physicalListValue);
                        nameText.setText(eachItem.getString("name"));
                        if(eachItem.getInt("abnormalExist")==1){
                            valueText.setTextColor(getResources().getColor(R.color.myRed));
                        }
                        valueText.setText(eachItem.getString("avgValue"));
                        physicalView.setTag(eachItem.getString("name"));

                        if(valueText.getText().toString().trim().equals("-1")){
                            valueText.setText("未填寫");
                        }
                        else{
                            physicalView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        DialogPhysicalDayDetails dialogPhysicalDayDetails=new DialogPhysicalDayDetails(ScheduleCalendar.this,
                                                physicalView.getTag().toString(),clickedDate,eachItem.getJSONArray("details"));
                                        dialogPhysicalDayDetails.show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }





                        physicalLayout.addView(physicalView);
                    }
                } catch (JSONException e) {
                    Log.e("phsycalERRO",e.getMessage());
                }
/*
            }
        });
        RequestQueue q= Volley.newRequestQueue(ScheduleCalendar.this);
        q.add(getDayPhyscialData);*/
    }
    public void GetScheduleData()
    {
        //database
        /*
        GetSchedule getSchedule=new GetSchedule(String.valueOf(userID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="[{\"ScheduleID\":1,\"Date\":\"2019-11-02\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\\u6211\\u731c\"},{\"ScheduleID\":2,\"Date\":\"2019-11-29\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"xjxjj\\u554a\\u97a5\\u5662\\u5662\\u8a92\\u8a92\\n\"},{\"ScheduleID\":3,\"Date\":\"2019-12-28\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\\u771f2\"},{\"ScheduleID\":4,\"Date\":\"2020-01-25\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"dd\"},{\"ScheduleID\":5,\"Date\":\"2020-02-22\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"yy\"},{\"ScheduleID\":6,\"Date\":\"2020-03-07\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":7,\"Date\":\"2020-03-21\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":8,\"Date\":\"2020-04-04\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":9,\"Date\":\"2020-04-18\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":10,\"Date\":\"2020-04-25\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":11,\"Date\":\"2020-05-02\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":12,\"Date\":\"2020-05-09\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":13,\"Date\":\"2020-05-16\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":1029,\"Date\":\"2020-05-23\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":1030,\"Date\":\"2020-05-30\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"}]";
                    JSONArray jEvents = new JSONArray(response);
                    events=new ArrayList<>();
                    calendarScheduleView.removeAllEvents();
                    for(int i=0;i<jEvents.length();i++)
                    {
                        JSONObject jEachEvent=jEvents.getJSONObject(i);
                        ScheduleEvent se=new ScheduleEvent();
                        se.setTime(jEachEvent.getString("Time"));
                        se.setNumber(jEachEvent.getInt("Number"));
                        se.setNote(jEachEvent.getString("Note"));
                        se.setDate(jEachEvent.getString("Date"));
                        se.setScheduleID(jEachEvent.getInt("ScheduleID"));
                        Calendar c=Calendar.getInstance();
                        String []dateSplit=jEachEvent.getString("Date").split("-");
                        c.set(Integer.parseInt(dateSplit[0]),Integer.parseInt(dateSplit[1])-1,Integer.parseInt(dateSplit[2]));
                        Event event=new Event(Color.RED,c.getTimeInMillis());
                        calendarScheduleView.addEvent(event);
                        calendarScheduleView.invalidate();
                        events.add(se);
                    }

                    clickDate(currentDate);
                    calendarScheduleView.invalidate();
                } catch (JSONException e) {
                    Log.e("ee",e.getMessage());
                }
           /* }
        });
        RequestQueue q= Volley.newRequestQueue(ScheduleCalendar.this);
        q.add(getSchedule);*/
    }
    public void GetEventLocation()
    {
        //database
        /*
        GetEventLocation getEventLocation=new GetEventLocation( new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                String response="[{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":28},{\"EventName\":\"\\u71df\\u990a\\u8aee\\u8a62\",\"Location\":\"523\\u5ba4\",\"PregnantWeek\":28},{\"EventName\":\"\\u80ce\\u5152\\u5927\\u5b78\",\"Location\":\"7\\u6a13\",\"PregnantWeek\":28},{\"EventName\":\"\\u52a9\\u7522\\u9580\\u8a3a\\u8aee\\u8a62\",\"Location\":\"521\\u52a9\\u7522\\u9580\\u8a3a\",\"PregnantWeek\":28},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":4},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":8},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":12},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":16},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":20},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":24},{\"EventName\":\"1.\\u62bd\\u8840(\\u8840\\u5e38\\u898f\\u3001\\u809d\\u529f\\u3001\\u7532\\u529f\\u7b49)2.\\u5316\\u9a57\\u5c0f\\u4fbf(\\u9032\\u98df\\u5f8c\\u67e5)\",\"Location\":\"4\\u6a13415\\u62bd\\u8840\\u5ba4(\\u7576\\u5929\\u53d6\\u865f)\",\"PregnantWeek\":32},{\"EventName\":\"\\u5fc3\\u96fb\\u5716\",\"Location\":\"4\\u6a13\\u5fc3\\u96fb\\u5716\\u5ba4(\\u7576\\u5929\\u53d6\\u865f)\",\"PregnantWeek\":32},{\"EventName\":\"B\\u8d85\",\"Location\":\"4\\u6a13B\\u8d85\\u5ba4(\\u7576\\u5929\\u7e73\\u8cbb\\u5f8c\\u9810\\u7d04)\",\"PregnantWeek\":32},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":32},{\"EventName\":\"\\u6bcd\\u4e73\\u9935\\u990a\\u3001\\u52a9\\u7522\\u9580\\u8a3a\\u8aee\\u8a62\",\"Location\":\"\\u4e0a\\u5348520\\u8aee\\u8a62\\u8a55\\u4f30\\uff0c\\u4e0b\\u53485\\u6a13(\\u5ba3\\u6559\\u5ba4)\\u623f\\u9593\\u6280\\u80fd\\u6307\\u5c0e\",\"PregnantWeek\":32},{\"EventName\":\"\\u80ce\\u5152\\u5927\\u5b78\",\"Location\":\"7\\u6a13\",\"PregnantWeek\":32},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":34},{\"EventName\":\"520\\u6bcd\\u4e73\\u9935\\u990a\\u3001521\\u52a9\\u7522\\u9580\\u8a3a\\u8aee\\u8a62\",\"Location\":\"\\u4e0a\\u5348\\u8aee\\u8a62\\u8a55\\u4f30\\uff0c\\u4e0b\\u53485\\u6a13(\\u5ba3\\u6559\\u5ba4)\\u623f\\u9593\\u6280\\u80fd\\u6307\\u5c0e\",\"PregnantWeek\":34},{\"EventName\":\"\\u80ce\\u5152\\u5927\\u5b78\",\"Location\":\"7\\u6a13\",\"PregnantWeek\":34},{\"EventName\":\"B\\u8d85\",\"Location\":\"4\\u6a13B\\u8d85\\u5ba4\",\"PregnantWeek\":36},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":36},{\"EventName\":\"\\u80ce\\u5fc3\\u76e3\\u8b77(\\u5fc5\\u8981\\u6642)(\\u8a3a\\u5ba4\\u8b77\\u58eb\\u8655\\u9810\\u7d04)\",\"Location\":\"510\\u5ba4(\\u5e36\\u5c31\\u8a3a\\u5361)\",\"PregnantWeek\":36},{\"EventName\":\"\\u80ce\\u5152\\u5927\\u5b78\",\"Location\":\"7\\u6a13\",\"PregnantWeek\":36},{\"EventName\":\"520\\u6bcd\\u4e73\\u9935\\u990a\\u3001521\\u52a9\\u7522\\u9580\\u8a3a\\u8aee\\u8a62\",\"Location\":\"\\u4e0a\\u5348\\u8aee\\u8a62\\u8a55\\u4f30\\uff0c\\u4e0b\\u53485\\u6a13(\\u5ba3\\u6559\\u5ba4)\\u623f\\u9593\\u6280\\u80fd\\u6307\\u5c0e\",\"PregnantWeek\":36},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":37},{\"EventName\":\"\\u80ce\\u5fc3\\u76e3\\u8b77(\\u8a3a\\u5ba4\\u8b77\\u58eb\\u9810\\u7d04)\",\"Location\":\"510\\u5ba4(\\u5e36\\u5c31\\u8a3a\\u5361)\",\"PregnantWeek\":37},{\"EventName\":\"\\u52a9\\u7522\\u3001\\u6bcd\\u4e73\\u9935\\u990a\\u8b77\\u7406\\u9580\\u8a3a\",\"Location\":\"521(520)\\u5ba4\",\"PregnantWeek\":37},{\"EventName\":\"\\u80ce\\u5152\\u5927\\u5b78\",\"Location\":\"7\\u6a13\",\"PregnantWeek\":37},{\"EventName\":\"\\u6bcd\\u4e73\\u9935\\u990a(\\u6280\\u80fd\\u6307\\u5c0e)\",\"Location\":\"\\u4e94\\u6a13\\u6bcd\\u4e73\\u9935\\u990a\\u5ba3\\u6559\\u5ba4\",\"PregnantWeek\":37},{\"EventName\":\"1.\\u62bd\\u8840(\\u8840\\u5e38\\u898f\\u3001\\u809d\\u529f\\u3001\\u7532\\u529f\\u7b49)2.\\u5316\\u9a57\\u5c0f\\u4fbf(\\u9032\\u98df\\u5f8c\\u67e5)\",\"Location\":\"4\\u6a13415\\u62bd\\u8840\\u5ba4(\\u7576\\u5929\\u53d6\\u865f)\",\"PregnantWeek\":38},{\"EventName\":\"\\u5fc3\\u96fb\\u5716\",\"Location\":\"4\\u6a13\\u5fc3\\u96fb\\u5716\\u5ba4(\\u7576\\u5929\\u53d6\\u865f)\",\"PregnantWeek\":38},{\"EventName\":\"\\u80ce\\u5fc3\\u76e3\\u8b77(\\u8a3a\\u5ba4\\u8b77\\u58eb\\u9810\\u7d04)\",\"Location\":\"510\\u5ba4(\\u5e36\\u5c31\\u8a3a\\u5361)\",\"PregnantWeek\":38},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":38},{\"EventName\":\"\\u52a9\\u7522\\u3001\\u6bcd\\u4e73\\u9935\\u990a\\u8b77\\u7406\\u9580\\u8a3a\",\"Location\":\"521(520)\\u5ba4\",\"PregnantWeek\":38},{\"EventName\":\"\\u80ce\\u5152\\u5927\\u5b78\",\"Location\":\"7\\u6a13\",\"PregnantWeek\":38},{\"EventName\":\"\\u9ebb\\u9189\\u9580\\u8a3a(530\\u5ba4)\",\"Location\":\"5\\u6a13(\\u5ba3\\u6559\\u5ba4)\\u623f\\u9593(14:30)\",\"PregnantWeek\":38},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":39},{\"EventName\":\"B\\u8d85\",\"Location\":\"4\\u6a13B\\u8d85\\u5ba4\",\"PregnantWeek\":39},{\"EventName\":\"\\u80ce\\u5fc3\\u76e3\\u8b77(\\u8a3a\\u5ba4\\u8b77\\u58eb\\u9810\\u7d04)\",\"Location\":\"510\\u5ba4(\\u5e36\\u5c31\\u8a3a\\u5361)\",\"PregnantWeek\":39},{\"EventName\":\"\\u52a9\\u7522\\u3001\\u6bcd\\u4e73\\u9935\\u990a\\u8b77\\u7406\\u9580\\u8a3a\",\"Location\":\"521(520)\\u5ba4\",\"PregnantWeek\":39},{\"EventName\":\"\\u9ebb\\u9189\\u9580\\u8a3a\",\"Location\":\"530\",\"PregnantWeek\":39},{\"EventName\":\"\\u80ce\\u5152\\u5927\\u5b78\",\"Location\":\"7\\u6a13\",\"PregnantWeek\":39},{\"EventName\":\"\\u6bcd\\u4e73\\u9935\\u990a(\\u6280\\u80fd\\u6307\\u5c0e)\",\"Location\":\"5\\u6a13(\\u5ba3\\u6559\\u5ba4)\\u623f\\u959314:30\",\"PregnantWeek\":39},{\"EventName\":\"\\u7522\\u6aa2\\u3001\\u6e2c\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\\u3001\\u807d\\u5fc3\\u97f3\\u3001\\u6e2c\\u5bae\\u9ad8\\u3001\\u8179\\u570d\",\"Location\":\"\\u672c\\u8a3a\\u5ba4\",\"PregnantWeek\":40}]";
                try {
                    JSONArray jEvents = new JSONArray(response);
                    eventLocations=new ArrayList<>();
                    for(int i=0;i<jEvents.length();i++)
                    {
                        JSONObject jEachEvent=jEvents.getJSONObject(i);
                        EventLocation el=new EventLocation();
                        el.setEventName(jEachEvent.getString("EventName"));
                        el.setLocation(jEachEvent.getString("Location"));
                        el.setPregnantWeek(jEachEvent.getInt("PregnantWeek"));
                        eventLocations.add(el);
                    }

                    GetScheduleData();
                } catch (JSONException e) {
                    Log.e("ese",e.getMessage());
                }
           /* }
        });
        RequestQueue q= Volley.newRequestQueue(ScheduleCalendar.this);
        q.add(getEventLocation);*/
    }
    public void GetEventFlowChart()
    {
        //database
        /*
        GetEventFlow getEventFlow=new GetEventFlow("0",new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="[{\"EventFlowID\":1,\"Content\":\"\\u7a7a\\u8179\\u4f86\\u9662(\\u7981\\u98df\\u3001\\u7981\\u98f2)\",\"Order\":1,\"PregnantWeek\":24,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":3,\"Content\":\"\\u53d6\\u5831\\u544a\\uff1a\\u81ea\\u52a9\\u53d6\\u5831\\u544a\\u6a5f(\\u4e00\\u3001\\u4e09\\u3001\\u4e94\\u6a13)\\u3001\\u53d6\\u4ef6\\u5361\\u6642\\u7684\\u62bd\\u8840\\u3001\\u9a57\\u5c3f\\u5831\\u544a\",\"Order\":2,\"PregnantWeek\":24,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":4,\"Content\":\"\\u639b\\u865f\\u5f8c\\u770b\\u8a3a\",\"Order\":3,\"PregnantWeek\":24,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":5,\"Content\":\"\\u8a3a\\u8996\\u91ab\\u751f\\u8b80\\u5831\\u544a\\uff0c\\u958b\\u7cd6\\u8010\\u6aa2\\u67e5\\u8cbb\",\"Order\":4,\"PregnantWeek\":24,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":6,\"Content\":\"\\u6536\\u8cbb\\u8655\\u7e73\\u8cbb\",\"Order\":5,\"PregnantWeek\":24,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":7,\"Content\":\"\\u52304\\u6a13(415\\u5ba4)\\u53d6\\u865f\\uff0c\\u62bd\\u7b2c\\u4e00\\u7ba1\\u7a7a\\u8179\\u8840\",\"Order\":6,\"PregnantWeek\":24,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":8,\"Content\":\"\\u56de5\\u6a13\\u5ba3\\u6559\\u5ba4(\\u9760\\u8fd1\\u624b\\u6276\\u68af\\u7684\\u73bb\\u7483\\u623f)\\u623f\\u9593\\u559d\\u7cd6\\u6c34(\\u5168\\u7a0b\\u53ea\\u559d\\u4e00\\u676f)\",\"Order\":7,\"PregnantWeek\":24,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":10,\"Content\":\"\\u5b55\\u671f\\u5f9e\\u559d\\u7b2c\\u4e00\\u53e3\\u7cd6\\u6c34\\u958b\\u59cb\\u8a08\\u6642\\uff0c\\u4e00\\u5c0f\\u6642\\u5f8c\\u5230415\\u5ba4\\u62bd\\u7b2c\\u4e8c\\u7ba1\\u8840(\\u6e2c\\u559d\\u7cd6\\u6c34\\u5f8c\\u7b2c1\\u5c0f\\u6642\\u8840\\u7cd6)\\uff0c\\u7b49\\u5f85\\u904e\\u7a0b\\u4e2d\\u7981\\u98df\\u3001\\u7981\\u6c34\\uff0c\\u76e1\\u91cf\\u4fdd\\u6301\\u975c\\u5750\",\"Order\":8,\"PregnantWeek\":24,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":11,\"Content\":\"\\u5728\\u9593\\u96941\\u5c0f\\u6642\\u5019\\uff0c\\u5230415\\u5ba4\\u62bd\\u7b2c\\u4e09\\u7ba1\\u8840(\\u6e2c\\u559d\\u7cd6\\u6c34\\u5f8c\\u7b2c2\\u5c0f\\u6642\\u8840\\u7cd6)\\uff0c\\u7b49\\u5f85\\u904e\\u7a0b\\u4e2d\\u7981\\u98df\\u3001\\u7981\\u6c34\\u3001\\u76e1\\u91cf\\u4fdd\\u6301\\u975c\\u5750\",\"Order\":9,\"PregnantWeek\":24,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":12,\"Content\":\"\\u75c5\\u60c5\\u9700\\u8981\\u6642\\u639b\\u71df\\u990a\\u9580\\u8a3a\\u865f\",\"Order\":1,\"PregnantWeek\":25,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":13,\"Content\":\"\\u4ea4\\u639b\\u865f\\u55ae\\u7d66523\\u8a3a\\u5ba4\\u8b77\\u58eb\\uff0c\\u586b\\u5beb\\u71df\\u990a\\u8aee\\u8a62\\u55ae\\u3001\\u5019\\u8a3a\",\"Order\":2,\"PregnantWeek\":25,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":14,\"Content\":\"\\u91ab\\u751f\\u63a5\\u8a3a\\uff0c\\u958b\\u91ab\\u56d1(\\u5305\\u62ec\\u9aa8\\u5bc6\\u5ea6\\u3001\\u5c3f\\u7898\\u3001\\u7dad\\u751f\\u7d20D\\u3001\\u9ad4\\u6210\\u5206\\u7b49)\",\"Order\":3,\"PregnantWeek\":25,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":15,\"Content\":\"\\u6191\\u5c31\\u8a3a\\u5361\\u7e73\\u8cbb(2-8\\u6a13\\u90fd\\u53ef)\",\"Order\":4,\"PregnantWeek\":25,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":16,\"Content\":\"\\u9075\\u91ab\\u56d1\\u505a\\u76f8\\u61c9\\u71df\\u990a\\u8a55\\u6e2c\",\"Order\":5,\"PregnantWeek\":25,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":17,\"Content\":\"\\u56de523\\/522\\u8a3a\\u5ba4\\u91ab\\u751f\\u8655\\uff0c\\u9810\\u7d04\\u4e0b\\u6b21\\u71df\\u990a\\u9580\\u8a3a\\u5c31\\u8a3a\\u6642\\u9593\",\"Order\":6,\"PregnantWeek\":25,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":18,\"Content\":\"4\\u6a13415\\u5ba4\\u53d6\\u865f\\u62bd\\u8840\",\"Order\":1,\"PregnantWeek\":32,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":19,\"Content\":\"\\u9032\\u98df\\u3001\\u98f2\\u6c34\\u81f3\\u5c11\\u534a\\u5c0f\\u6642\\u5f8c\\u5316\\u9a57\\u5c0f\\u4fbf\",\"Order\":3,\"PregnantWeek\":32,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":20,\"Content\":\"4\\u6a13\\u5fc3\\u96fb\\u5716(\\u6aa2\\u67e5\\u65e5\\u5fc3\\u96fb\\u5716\\u5ba4\\u9580\\u53e3\\u53d6\\u865f)\",\"Order\":3,\"PregnantWeek\":32,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":21,\"Content\":\"4\\u6a13B\\u8d85\\u6aa2\\u67e5\",\"Order\":3,\"PregnantWeek\":32,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":22,\"Content\":\"\\u639b\\u865f\\uff0c\\u81f3\\u7522\\u79d1\\u8a3a\\u5340\\u6e2c\\u91cf\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\",\"Order\":5,\"PregnantWeek\":32,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":23,\"Content\":\"\\u56de\\u8a3a\\u5ba4\\u7b49\\u5f85\\u7522\\u6aa2\",\"Order\":6,\"PregnantWeek\":32,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":24,\"Content\":\"\\u53d6\\u5316\\u9a57\\u5831\\u544a\",\"Order\":7,\"PregnantWeek\":32,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":26,\"Content\":\"\\u91ab\\u751f\\u770b\\u8a3a\\uff0c\\u9810\\u7d04\\u4e0b\\u6b21\\u7522\\u6aa2\",\"Order\":8,\"PregnantWeek\":32,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":27,\"Content\":\"\\u8a3a\\u5ba4\\u8b77\\u5e2b\\u8655\\u5065\\u5eb7\\u5ba3\\u6559\\uff0c\\u9810\\u7d04\\u4e0b\\u6b21\\u5c31\\u8a3a\\u6642\\u9593\",\"Order\":9,\"PregnantWeek\":32,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":28,\"Content\":\"\\u4ea4\\u4e0b\\u6b21\\u6240\\u9700\\u6aa2\\u67e5\\u7684\\u8cbb\\u7528\",\"Order\":10,\"PregnantWeek\":32,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":29,\"Content\":\"4\\u6a13415\\u5ba4\\u53d6\\u865f\\u62bd\\u8840\",\"Order\":1,\"PregnantWeek\":38,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":30,\"Content\":\"\\u9032\\u98df\\u3001\\u98f2\\u6c34\\u81f3\\u5c11\\u534a\\u5c0f\\u6642\\u5f8c\\u5316\\u9a57\\u5c0f\\u4fbf\",\"Order\":2,\"PregnantWeek\":38,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":31,\"Content\":\"4\\u6a13\\u5fc3\\u96fb\\u5716(\\u6aa2\\u67e5\\u65e5\\u5fc3\\u96fb\\u5716\\u5ba4\\u9580\\u53e3\\u53d6\\u865f)\",\"Order\":3,\"PregnantWeek\":38,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":32,\"Content\":\"4\\u6a13B\\u8d85\\u6aa2\\u67e5\",\"Order\":4,\"PregnantWeek\":38,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":33,\"Content\":\"\\u639b\\u865f\\uff0c\\u81f3\\u7522\\u79d1\\u8a3a\\u5340\\u6e2c\\u91cf\\u9ad4\\u91cd\\u3001\\u8840\\u58d3\",\"Order\":5,\"PregnantWeek\":38,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":34,\"Content\":\"\\u56de\\u8a3a\\u5ba4\\u7b49\\u5f85\\u7522\\u6aa2\",\"Order\":6,\"PregnantWeek\":38,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":35,\"Content\":\"\\u53d6\\u5316\\u9a57\\u5831\\u544a\",\"Order\":7,\"PregnantWeek\":38,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":36,\"Content\":\"\\u91ab\\u751f\\u770b\\u8a3a\\uff0c\\u9810\\u7d04\\u4e0b\\u6b21\\u7522\\u6aa2\",\"Order\":8,\"PregnantWeek\":38,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":37,\"Content\":\"\\u8a3a\\u5ba4\\u8b77\\u5e2b\\u8655\\u5065\\u5eb7\\u5ba3\\u6559\\uff0c\\u9810\\u7d04\\u4e0b\\u6b21\\u5c31\\u8a3a\\u6642\\u9593\",\"Order\":9,\"PregnantWeek\":38,\"IsEducational\":0,\"EducationID\":0},{\"EventFlowID\":38,\"Content\":\"\\u4ea4\\u4e0b\\u6b21\\u6240\\u9700\\u6aa2\\u67e5\\u7684\\u8cbb\\u7528\",\"Order\":10,\"PregnantWeek\":38,\"IsEducational\":0,\"EducationID\":0}]";
                    JSONArray jEvents = new JSONArray(response);
                    eventFlows=new ArrayList<>();
                    for(int i=0;i<jEvents.length();i++)
                    {
                        JSONObject jEachEvent=jEvents.getJSONObject(i);
                        EventFlow ef=new EventFlow();
                        ef.setContent(jEachEvent.getString("Content"));
                        ef.setOrder(jEachEvent.getInt("Order"));
                        ef.setPregnantWeek(jEachEvent.getInt("PregnantWeek"));
                        eventFlows.add(ef);
                    }

                    GetEventLocation();
                } catch (JSONException e) {
                    Log.e("ese",e.getMessage());
                }
            /*}
        });
        RequestQueue q= Volley.newRequestQueue(ScheduleCalendar.this);
        q.add(getEventFlow);*/
    }


}
