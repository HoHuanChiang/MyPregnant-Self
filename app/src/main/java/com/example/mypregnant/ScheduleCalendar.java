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
        GetDayPhyscialData getDayPhyscialData=new GetDayPhyscialData(String.valueOf(userID), clickedDate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
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

            }
        });
        RequestQueue q= Volley.newRequestQueue(ScheduleCalendar.this);
        q.add(getDayPhyscialData);
    }
    public void GetScheduleData()
    {
        GetSchedule getSchedule=new GetSchedule(String.valueOf(userID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
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
            }
        });
        RequestQueue q= Volley.newRequestQueue(ScheduleCalendar.this);
        q.add(getSchedule);
    }
    public void GetEventLocation()
    {
        GetEventLocation getEventLocation=new GetEventLocation( new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
            }
        });
        RequestQueue q= Volley.newRequestQueue(ScheduleCalendar.this);
        q.add(getEventLocation);
    }
    public void GetEventFlowChart()
    {
        GetEventFlow getEventFlow=new GetEventFlow("0",new Response.Listener<String>() {
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
                        eventFlows.add(ef);
                    }

                    GetEventLocation();
                } catch (JSONException e) {
                    Log.e("ese",e.getMessage());
                }
            }
        });
        RequestQueue q= Volley.newRequestQueue(ScheduleCalendar.this);
        q.add(getEventFlow);
    }


}
