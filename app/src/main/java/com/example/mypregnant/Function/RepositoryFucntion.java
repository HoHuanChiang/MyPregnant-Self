package com.example.mypregnant.Function;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mypregnant.MainActivity;
import com.example.mypregnant.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class RepositoryFucntion {
    public static void CustomToast(Context context,String text){
        Toast toast=new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        LayoutInflater inflater = LayoutInflater.from(context);
        View toastView=inflater.inflate(R.layout.layout_toast,null);
        TextView toastText=toastView.findViewById(R.id.toasttext);
        toastText.setText(text);
        toast.setView(toastView);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
    public static void notifyApp(Context context,String title,String content){

        Intent notifyIntent = new Intent(context, MainActivity.class);
        notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent appIntent = PendingIntent.getActivity(context, 0, notifyIntent, 0);



        NotificationManager manager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(R.drawable.red_ghost) // 設置狀態列裡面的圖示（小圖示）　　
                .setContentIntent(appIntent)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.red_ghost)) // 下拉下拉清單裡面的圖示（大圖示）
                .setTicker("notification on status bar.") // 設置狀態列的顯示的資訊
                .setWhen(System.currentTimeMillis())// 設置時間發生時間
                .setAutoCancel(false) // 設置通知被使用者點擊後是否清除  //notification.flags = Notification.FLAG_AUTO_CANCEL;
                .setContentTitle(title) // 設置下拉清單裡的標題
                .setContentText(content)// 設置上下文內容
                .setOngoing(false)      //true使notification變為ongoing，用戶不能手動清除// notification.flags = Notification.FLAG_ONGOING_EVENT; notification.flags = Notification.FLAG_NO_CLEAR;
                .setDefaults(Notification.DEFAULT_ALL) ;//使用所有默認值，比如聲音，震動，閃屏等等
        NotificationChannel channel;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            channel = new NotificationChannel("testesasv"
                    ,"Notify Test"
                    ,NotificationManager.IMPORTANCE_HIGH);
            builder.setChannelId("testesasv");
            manager.createNotificationChannel(channel);
        }else{
            builder.setDefaults(Notification.DEFAULT_ALL)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }
        Random rr=new Random();
        manager.notify(rr.nextInt(100),builder.build());
    }
    public static int getPregnantWeekSpanWeek(Activity activity,String date){
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date periodDate = myFormat.parse(activity.getSharedPreferences("data", 0).getString("LastPeriodDate", ""));
            Date myDate = myFormat.parse(date);
            long diff = myDate.getTime() - periodDate.getTime();
            return (int)Math.ceil(Math.floor(diff / (1000*60*60*24))/7);
        }
        catch(Exception ex){
            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return 0;
    }
    public static int getScreenWidth(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }
    public static int dptoPixel(int dp,Context context)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }
    public static int getSessionByWeek(int week)
    {
        if(week>=1&&week<=12) {
            return 1;
        }
        else if(week>=13&&week<=28)
        {
            return 2;
        }
        else if(week>=29&&week<=40)
        {
            return 3;
        }
        return -1;

    }
    public  static int findSessionBySessionString(String session)
    {
        int pregnantWeek=-1;
        if(session.equals("第一孕期")){
            pregnantWeek=1;
        }
        else if(session.equals("第二孕期")){
            pregnantWeek=2;
        }
        else if(session.equals("第三孕期")){
            pregnantWeek=3;
        }
        else if(session.equals("孕前"))
        {
            pregnantWeek=0;
        }
        else if(session.equals("孕後"))
        {
            pregnantWeek=4;
        }
        return pregnantWeek;
    }
    public  static String findPregnantWeekBySession(String session)
    {
        String pregnantWeek="";
        if(session.equals("第一孕期")){
            pregnantWeek="1-12";
        }
        else if(session.equals("第二孕期")){
            pregnantWeek="13-28";
        }
        else if(session.equals("第三孕期")){
            pregnantWeek="29-40";
        }
        return pregnantWeek;
    }
    public static float findMaxNumber(ArrayList<Float> arrayList){
        float max=-100000;
        for(int i=0;i<arrayList.size();i++){
            if(arrayList.get(i)>max){
                max=arrayList.get(i);
            }
        }
        return max;
    }
    public static float findMinNumber(ArrayList<Float> arrayList){
        float min=99999999;
        for(int i=0;i<arrayList.size();i++){
            if(arrayList.get(i)<min){
                min=arrayList.get(i);
            }
        }
        return min;
    }

    public static void setDateText(final EditText dateEditText, final Activity activity){

        Time now;
        now=new Time(Time.getCurrentTimezone());
        now.setToNow();

        dateEditText.setText(now.year+"-"+String.format("%02d",now.month+1)+"-"+String.format("%02d",now.monthDay));
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year,month,day;
                year=Integer.valueOf(dateEditText.getText().toString().split("-")[0]);
                month=Integer.valueOf(dateEditText.getText().toString().split("-")[1])-1;
                day=Integer.valueOf(dateEditText.getText().toString().split("-")[2]);

                DatePickerDialog timePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateEditText.setText(i+"-"+String.format("%02d",i1+1)+"-"+String.format("%02d",i2));
                    }
                },year,month,day);
                timePickerDialog.show();
            }
        });
    }
    public static void setDateText(final EditText dateEditText, final Activity activity,final boolean needGetCurrent){

        final Time now;
        now=new Time(Time.getCurrentTimezone());
        now.setToNow();

        if(needGetCurrent)
        {
            dateEditText.setText(now.year+"-"+String.format("%02d",now.month+1)+"-"+String.format("%02d",now.monthDay));
        }

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year,month,day;
                if(dateEditText.getText().toString().equals(""))
                {
                    year=now.year;
                    month=now.month;
                    day=now.monthDay;
                }
                else
                {
                    year=Integer.valueOf(dateEditText.getText().toString().split("-")[0]);
                    month=Integer.valueOf(dateEditText.getText().toString().split("-")[1])-1;
                    day=Integer.valueOf(dateEditText.getText().toString().split("-")[2]);
                }


                DatePickerDialog timePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateEditText.setText(i+"-"+String.format("%02d",i1+1)+"-"+String.format("%02d",i2));
                    }
                },year,month,day);
                timePickerDialog.show();
            }
        });
    }
    public static void setTimeText(final EditText timeEditText, final Activity activity){

        Time now;
        now=new Time(Time.getCurrentTimezone());
        now.setToNow();

        timeEditText.setText(String.format("%02d",now.hour)+":"+String.format("%02d",now.minute));
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour,minute;
                hour=Integer.valueOf(timeEditText.getText().toString().split(":")[0]);
                minute=Integer.valueOf(timeEditText.getText().toString().split(":")[1])-1;
                TimePickerDialog timePickerDialog=new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        timeEditText.setText(String.format("%02d",i)+":"+String.format("%02d",i1));
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });
    }
    public static void setTimeText(final EditText timeEditText, final Activity activity,final boolean needGetCurrent){

        final Time now;
        now=new Time(Time.getCurrentTimezone());
        now.setToNow();
        if(needGetCurrent)
        {
            timeEditText.setText(String.format("%02d",now.hour)+":"+String.format("%02d",now.minute));
        }
        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour,minute;
                if(timeEditText.getText().toString().equals(""))
                {
                    hour=now.hour;
                    minute=now.minute;
                }
                else
                {
                    hour=Integer.valueOf(timeEditText.getText().toString().split(":")[0]);
                    minute=Integer.valueOf(timeEditText.getText().toString().split(":")[1])-1;
                }

                TimePickerDialog timePickerDialog=new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        timeEditText.setText(String.format("%02d",i)+":"+String.format("%02d",i1));
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });
    }
}
