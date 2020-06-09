package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.mypregnant.AdapterClasses.AdvertiseMainAdapter;
import com.example.mypregnant.AdapterClasses.OnSwipeListener;
import com.example.mypregnant.DatabaseClasses.GetAdvertiseData;
import com.example.mypregnant.DatabaseClasses.GetEducationList;
import com.example.mypregnant.DatabaseClasses.GetEventFlow;
import com.example.mypregnant.DatabaseClasses.GetUserInfo;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.ObjectClasses.EducationVideo;
import com.example.mypregnant.ObjectClasses.EventFlow;
import com.example.mypregnant.ObjectClasses.MedicalEducation;
import com.example.mypregnant.ObjectClasses.ShopProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView videoBtn,questionnaireBtn,educationBtn,physicalBtn,shopBtn,scheduleBtn,recordBtn,hospitalInfoBtn,ufoBtn;
    RecyclerView mainRecycleView;

    LinearLayout mainOptionLinearLayout;
    SharedPreferences sharedPreferences;
    int currentNumber=1;
    int bigIconWidth=200;
    int smallIconHeight=80;
    int TIME_TO_SLIDE=150;
    int SPOTLIGHT_DURATION=300;
    int []pressedIcon={R.drawable.main_questionnaire_pressed,R.drawable.main_education_pressed,R.drawable.main_video_pressed,
            R.drawable.main_physical_pressed,R.drawable.main_record_pressed,R.drawable.main_calendar_pressed,
            R.drawable.main_shop_pressed,R.drawable.main_hospital_pressed};
    int []unpressIcon={R.drawable.main_questionnaire_unpress,R.drawable.main_education_unpress,R.drawable.main_video_unpress,
            R.drawable.main_physical_unpress,R.drawable.main_record_unpress,R.drawable.main_calendar_unpress,
            R.drawable.main_shop_unpress,R.drawable.main_hospital_unpress};
    ImageView spotLight;
    ImageView ufo;
    ImageView settingBtn;
    ImageView returnCurrentWeek;
    LayoutInflater inflater;
    int currentWeek;
    int chooseWeek;
    TextView mainWeekText;
    ViewPager mainViewPager;
    LinearLayout mainPagerLayout;
    ArrayList<EventFlow> eventFlows;
    ArrayList<Fragment> totalFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);


        inflater = LayoutInflater.from(this);
        mainOptionLinearLayout=findViewById(R.id.mainOptionsLayout);
        ufo=findViewById(R.id.main_ufo);
        videoBtn=findViewById(R.id.videoBtn);
        questionnaireBtn=findViewById(R.id.questionnaireBtn);
        educationBtn=findViewById(R.id.educationBtn);
        physicalBtn=findViewById(R.id.physicalBtn);;
        shopBtn=findViewById(R.id.shopBtn);
        scheduleBtn=findViewById(R.id.scheduleBtn);
        recordBtn=findViewById(R.id.recordBtn);
        spotLight=findViewById(R.id.mainSpotLight);
        ufoBtn=findViewById(R.id.main_ufo);
        mainWeekText=findViewById(R.id.mainWeekText);
        hospitalInfoBtn=findViewById(R.id.hospitalInfoBtn);
        mainViewPager=findViewById(R.id.mainViewPager);
        settingBtn=findViewById(R.id.mainSettingBtn);
        returnCurrentWeek=findViewById(R.id.mainReturnCurrentWeek);
        mainPagerLayout=findViewById(R.id.mainPagerLayout);

        //mainViewPager.setAdapter(new AdvertiseMainAdapter(getSupportFragmentManager()));
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setHeartPager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });








        returnCurrentWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferences.edit().putInt("PregnantWeek",currentWeek).commit();
                chooseWeek=currentWeek;
                Animation a = AnimationUtils.loadAnimation(MainActivity.this, R.anim.edit_fab_open);
                mainWeekText.startAnimation(a);
                mainWeekText.setText(chooseWeek+"周");
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });


        videoBtn.setOnTouchListener(new OnSwipeListener(this,VideoTotalV2.class){
            @Override
            public void onSwipeLeft() {
                swipeLeft();
            }

            @Override
            public void onSwipeRight() {
                swipeRight();
            }

        });

        questionnaireBtn.setOnTouchListener(new OnSwipeListener(this, QuestionnaireMain.class){
            @Override
            public void onSwipeLeft() {
                swipeLeft();
            }

            @Override
            public void onSwipeRight() {
                swipeRight();
            }

        });
        educationBtn.setOnTouchListener(new OnSwipeListener(this, EducationMain2.class){
            @Override
            public void onSwipeLeft() {
                swipeLeft();
            }

            @Override
            public void onSwipeRight() {
                swipeRight();
            }

        });
        physicalBtn.setOnTouchListener(new OnSwipeListener(this,PhysicalMain.class){
            @Override
            public void onSwipeLeft() {
                swipeLeft();
            }

            @Override
            public void onSwipeRight() {
                swipeRight();
            }

        });
        shopBtn.setOnTouchListener(new OnSwipeListener(this,ShopTotalV2.class){
            @Override
            public void onSwipeLeft() {
                swipeLeft();
            }

            @Override
            public void onSwipeRight() {
                swipeRight();
            }

        });
        scheduleBtn.setOnTouchListener(new OnSwipeListener(this,ScheduleCalendar.class){
            @Override
            public void onSwipeLeft() {
                swipeLeft();
            }

            @Override
            public void onSwipeRight() {
                swipeRight();
            }

        });
        recordBtn.setOnTouchListener(new OnSwipeListener(this,RecordMain.class){
            @Override
            public void onSwipeLeft() {
                swipeLeft();
            }

            @Override
            public void onSwipeRight() {
                swipeRight();
            }

        });
        hospitalInfoBtn.setOnTouchListener(new OnSwipeListener(this, HospitalEducationMain.class){
            @Override
            public void onSwipeLeft() {
                swipeLeft();
            }

            @Override
            public void onSwipeRight() {
                swipeRight();
            }

        });


        ufoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogListView weekList=new DialogListView(MainActivity.this,currentWeek);
                weekList.show();

                LinearLayout listLinearLayout=weekList.findViewById(R.id.dialogListLayout);
                for(int i=1;i<=40;i++){
                    View eachView=inflater.inflate(R.layout.layout_each_list,listLinearLayout,false);
                    TextView week=eachView.findViewById(R.id.eachListWeek);
                    final LinearLayout outterLayout=eachView.findViewById(R.id.eachListLayout);
                    ImageView currentWeekImage=eachView.findViewById(R.id.eachListCurrentWeekImage);
                    outterLayout.setTag(i);
                    if(currentWeek==i){
                        currentWeekImage.setVisibility(View.VISIBLE);
                    }
                    if(chooseWeek==i){
                        outterLayout.setBackgroundResource(R.drawable.note_choose_background);
                        week.setTextColor(Color.parseColor("#F7B52C"));
                    }
                    week.setText(i+" 周");
                    outterLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            chooseWeek=(int)outterLayout.getTag();
                            mainWeekText.setText(chooseWeek+"周");
                            weekList.dismiss();

                        }
                    });
                    listLinearLayout.addView(eachView);
                }

            }
        });


        /*************取得螢幕寬度 給每一個選項寬高******************/
        int leftWidth=RepositoryFucntion.getScreenWidth(this);
        bigIconWidth=leftWidth*5/9;
        smallIconHeight=leftWidth*2/9;

        for(int i=0;i<mainOptionLinearLayout.getChildCount();i++){
            if (i == currentNumber) {
                ImageView image = (ImageView) mainOptionLinearLayout.getChildAt(i);
                image.getLayoutParams().height = bigIconWidth;
                image.getLayoutParams().width = bigIconWidth;
                image.setImageResource(pressedIcon[i]);
                image.requestLayout();
                image.setEnabled(true);
            } else {
                ImageView image = (ImageView) mainOptionLinearLayout.getChildAt(i);
                image.getLayoutParams().height =smallIconHeight;
                image.getLayoutParams().width = smallIconHeight;
                image.setImageResource(unpressIcon[i]);
                image.requestLayout();
                image.setEnabled(false);
            }
        }
        /*************取得螢幕寬度 給每一個選項寬高******************/


        /*************功能表左滑右滑*******************/
        mainOptionLinearLayout.setOnTouchListener(new OnSwipeListener(this){

            @Override
            public void onSwipeLeft() {
                swipeLeft();
            }
            @Override
            public void onSwipeRight() {
                swipeRight();
            }

        });
        /** ***********功能表左滑右滑*******************/

        /******************利用recyleview 做滑動 但是沒辦法定住*********************/

        /******************利用recyleview 做滑動 但是沒辦法定住*********************/

        getPersonalInfo();

    }
    public void swipeLeft(){



        if(currentNumber>=0&&currentNumber<mainOptionLinearLayout.getChildCount()-1){
            currentNumber++;
            for(int i=0;i<mainOptionLinearLayout.getChildCount();i++){
                if (i == currentNumber) {
                    final ImageView image = (ImageView) mainOptionLinearLayout.getChildAt(i);
                    image.requestLayout();
                    image.setImageResource(pressedIcon[i]);
                    ValueAnimator animation = ValueAnimator.ofInt(image.getLayoutParams().height, bigIconWidth);
                    animation.setDuration(TIME_TO_SLIDE);
                    animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            image.getLayoutParams().height=Integer.parseInt(valueAnimator.getAnimatedValue().toString());
                            image.getLayoutParams().width=Integer.parseInt(valueAnimator.getAnimatedValue().toString());
                        }
                    });
                    animation.start();
                    image.setEnabled(true);
                } else {
                    final ImageView image = (ImageView) mainOptionLinearLayout.getChildAt(i);
                    image.requestLayout();
                    image.setImageResource(unpressIcon[i]);
                    ValueAnimator animation = ValueAnimator.ofInt(image.getLayoutParams().height, smallIconHeight);
                    animation.setDuration(TIME_TO_SLIDE);
                    animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            image.getLayoutParams().height=Integer.parseInt(valueAnimator.getAnimatedValue().toString());
                            image.getLayoutParams().width=Integer.parseInt(valueAnimator.getAnimatedValue().toString());
                        }
                    });
                    animation.start();
                    image.setEnabled(false);
                }



            }
            ValueAnimator animation = ValueAnimator.ofInt(mainOptionLinearLayout.getPaddingLeft(), mainOptionLinearLayout.getPaddingLeft()-smallIconHeight);
            animation.setDuration(TIME_TO_SLIDE);
            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mainOptionLinearLayout.setPadding( Integer.parseInt(valueAnimator.getAnimatedValue().toString()), 0, 0, 0);
                }
            });
            animation.start();
        }
    }
    public void swipeRight(){


        if(currentNumber>0&&currentNumber<mainOptionLinearLayout.getChildCount()){
            currentNumber--;
            for(int i=0;i<mainOptionLinearLayout.getChildCount();i++){
                if (i == currentNumber) {
                    final ImageView image = (ImageView) mainOptionLinearLayout.getChildAt(i);
                    image.requestLayout();
                    image.setImageResource(pressedIcon[i]);
                    ValueAnimator animation = ValueAnimator.ofInt(image.getLayoutParams().height, bigIconWidth);
                    animation.setDuration(TIME_TO_SLIDE);
                    animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            image.getLayoutParams().height=Integer.parseInt(valueAnimator.getAnimatedValue().toString());
                            image.getLayoutParams().width=Integer.parseInt(valueAnimator.getAnimatedValue().toString());
                        }
                    });
                    animation.start();
                    image.setEnabled(true);

                } else {
                    final ImageView image = (ImageView) mainOptionLinearLayout.getChildAt(i);
                    image.requestLayout();
                    image.setImageResource(unpressIcon[i]);
                    ValueAnimator animation = ValueAnimator.ofInt(image.getLayoutParams().height, smallIconHeight);
                    animation.setDuration(TIME_TO_SLIDE);
                    animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            image.getLayoutParams().height=Integer.parseInt(valueAnimator.getAnimatedValue().toString());
                            image.getLayoutParams().width=Integer.parseInt(valueAnimator.getAnimatedValue().toString());
                        }
                    });
                    animation.start();
                    image.setEnabled(false);

                }
            }
            ValueAnimator animation = ValueAnimator.ofInt(mainOptionLinearLayout.getPaddingLeft(), mainOptionLinearLayout.getPaddingLeft()+smallIconHeight);
            animation.setDuration(TIME_TO_SLIDE);
            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mainOptionLinearLayout.setPadding( Integer.parseInt(valueAnimator.getAnimatedValue().toString()), 0, 0, 0);
                }
            });
            animation.start();
        }

    }
    public void getPersonalInfo()
    {
        //database
        /*
        GetUserInfo getUserInfo=new GetUserInfo(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="{\"UserID\":1,\"LastPeriodDate\":\"2019-08-10\",\"PregnantWeek\":18,\"WeekStartDate\":\"2019-12-07\",\"Weight\":70,\"Height\":180,\"BirthdayDate\":\"2018-10-10\",\"Room\":\"2772\",\"MedicalID\":\"2782\",\"Doctor\":\"\\u53ef\\u53ef\",\"Nurse\":\"\\u55da\\u55da\\u55da\",\"Name\":\"Tom\",\"Account\":\"aa\"}";
                    JSONObject jUserInfo = new JSONObject(response);
                    sharedPreferences.edit().putInt("user", jUserInfo.getInt("UserID")).commit();
                    sharedPreferences.edit().putInt("PregnantWeek", jUserInfo.getInt("PregnantWeek")).commit();
                    sharedPreferences.edit().putInt("CurrentPregnantWeek", jUserInfo.getInt("PregnantWeek")).commit();
                    sharedPreferences.edit().putString("WeekStartDate", jUserInfo.getString("WeekStartDate")).commit();
                    sharedPreferences.edit().putString("LastPeriodDate", jUserInfo.getString("LastPeriodDate")).commit();
                    sharedPreferences.edit().putFloat("Height", (float)jUserInfo.getDouble("Height")).commit();
                    sharedPreferences.edit().putFloat("Weight", (float)jUserInfo.getDouble("Weight")).commit();
                    currentWeek=jUserInfo.getInt("PregnantWeek");
                    chooseWeek=currentWeek;
                    mainWeekText.setText(chooseWeek+"周");
                    GetEventFlowChart();
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }/*
            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getUserInfo);*/
    }
    public void GetEventFlowChart()
    {
        //database
        /*
        GetEventFlow getEventFlow=new GetEventFlow("1",new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="[{\"EventFlowID\":39,\"Content\":\"\\u5efa\\u5361\\u5b55\\u5987\\u6302\\u5efa\\u5361\\u53f7 \",\"Order\":1,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":40,\"Content\":\"\\u81f35\\u697c\\u4ea7\\u79d1\\u95e8\\u8bca\\u8bca\\u533a \",\"Order\":2,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":45,\"Content\":\"\\u81ea\\u4e3b\\u6d4b\\u91cf\\u4f53\\u91cd\\u3001\\u8840\\u538b,\\u5e76\\u8bb0\\u5f55\\u5728\\u6302\\u53f7\\u5355\\u4e0a\",\"Order\":3,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":46,\"Content\":\"\\u6839\\u636e\\u9884\\u7ea6\\u5355\\u4fe1\\u606f\\u627e\\u5230\\u76f8\\u5e94\\u8bca\\u5ba4\\u548c\\u4e00\\u5bf9\\u4e00\\u62a4\\u5e08\",\"Order\":4,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":47,\"Content\":\"\\u4ea4\\u6302\\u53f7\\u5355\\u7ed9\\u8bca\\u5ba4\\u62a4\\u5e08,\\u6392\\u961f\\u5019\\u8bca\",\"Order\":5,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":48,\"Content\":\"\\u8bca\\u5ba4\\u62a4\\u58eb\\u767b\\u8bb0\\u6838\\u5bf9\\u4fe1\\u606f\",\"Order\":6,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":49,\"Content\":\"\\u533b\\u751f\\u63a5\\u8bca\\u3001\\u4ea7\\u68c0\\u3001\\u5f00\\u5177\\u76f8\\u5e94\\u68c0\\u67e5\",\"Order\":7,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":50,\"Content\":\"\\u4ea4\\u8d39 2-8\\u697c\\u6302\\u53f7\\u6536\\u8d39\\u5904\",\"Order\":8,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":51,\"Content\":\"\\u67e5\\u767d\\u5e263\\u697c\\u68c0\\u9a8c\\u7a97\\u53e3 (\\u534a\\u5c0f\\u65f6\\u540e\\u53d6\\u62a5\\u544a) \",\"Order\":9,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":52,\"Content\":\"\\u9884\\u7ea6B\\u8d85(\\u9075\\u533b\\u5631) \\u95e8\\u8bca4\\u697cB\\u8d85\\u5ba4 \",\"Order\":9,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":53,\"Content\":\"\\u7a7a\\u8179\\u62bd\\u8840 4\\u697c415\\u5ba4\\u62bd\\u8840\\u7a97\\u53e3 \\r\\n(\\u53d6\\u53f7\\u6392\\u961f)\\r\\n\",\"Order\":10,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":54,\"Content\":\"\\u8fdb\\u98df\\u3001\\u996e\\u6c34\\u81f3\\u5c11\\u534a\\u5c0f \\u65f6\\u540e\\u9a8c\\u5c3f4\\u697c\\u201c\\u5c3f\\u6db2 \\r\\n\\u5316\\u9a8c\\u5904\\u201d \\r\\n\",\"Order\":10,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":55,\"Content\":\"\\u4e0d\\u5fc5\\u7b49\\u5019\\u8840\\u3001\\u5c3f\\u5316\\u9a8c\\u7ed3\\u679c,\\u8bf7\\u8fd4\\u56de\\u5efa\\u5361\\u8bca\\u5ba4\\u62a4\\u58eb\\u5904(\\u9884\\u7ea6\\u4e0b\\u6b21\\u4ea7\\u68c0\\u65f6\\u95f4\\u53ca\\u76f8\\u5173\\u68c0\\u67e5) \",\"Order\":11,\"PregnantWeek\":1,\"IsEducational\":1,\"EducationID\":95},{\"EventFlowID\":56,\"Content\":\"\\u75c5\\u60c5\\u9700\\u8981\\u65f6\\u6302\\u8425\\u517b\\u95e8\\u8bca\\u53f7\",\"Order\":1,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94},{\"EventFlowID\":57,\"Content\":\"\\u4ea4\\u6302\\u53f7\\u5355\\u7ed9 523 \\u8bca\\u5ba4\\u62a4\\u58eb,\\u586b\\u5199\\u8425\\u517b\\u54a8\\u8be2\\u5355,\\u5019\\u8bca \",\"Order\":2,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94},{\"EventFlowID\":58,\"Content\":\"\\u533b\\u751f\\u63a5\\u8bca,\\u5f00\\u533b\\u5631(\\u5305\\u62ec\\u9aa8\\u5bc6\\u5ea6,\\u5c3f\\u7898,\\u7ef4\\u751f\\u7d20D,\\u4f53\\u6210\\u5206\\u7b49)\",\"Order\":3,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94},{\"EventFlowID\":59,\"Content\":\"\\u51ed\\u5c31\\u8bca\\u5361\\u5361\\u7f34\\u8d39(2-8\\u697c\\u90fd\\u53ef)\",\"Order\":4,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94},{\"EventFlowID\":60,\"Content\":\"\\u9075\\u533b\\u5631\\u505a\\u76f8\\u5e94\\u8425\\u517b\\u8bc4\\u6d4b \",\"Order\":5,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94},{\"EventFlowID\":61,\"Content\":\"\\u56de523\\/522\\u8bca\\u5ba4\\u533b\\u751f\\u5904,\\u9884\\u7ea6\\u4e0b\\u6b21\\u8425\\u517b\\u95e8\\u8bca\\u5c31\\u8bca\\u65f6\\u95f4\",\"Order\":6,\"PregnantWeek\":24,\"IsEducational\":1,\"EducationID\":94}]";
                    JSONArray jEvents = new JSONArray(response);
                    eventFlows=new ArrayList<>();
                    for(int i=0;i<jEvents.length();i++)
                    {
                        JSONObject jEachEvent=jEvents.getJSONObject(i);
                        EventFlow ef=new EventFlow();
                        ef.setContent(jEachEvent.getString("Content"));
                        ef.setOrder(jEachEvent.getInt("Order"));
                        ef.setPregnantWeek(jEachEvent.getInt("PregnantWeek"));
                        //ef.setEducation(jEachEvent.getBoolean("IsEducational"));
                        ef.setEducationID(jEachEvent.getInt("EducationID"));
                        eventFlows.add(ef);
                    }

                    setAdvertiseAdapter();
                } catch (JSONException e) {
                    Log.e("ese",e.getMessage());
                }
            /*}
        });
        RequestQueue q= Volley.newRequestQueue(MainActivity.this);
        q.add(getEventFlow);*/
    }
    public void setHeartPager(int currentItemCount){

        mainPagerLayout.removeAllViews();
        for(int pageCount=0;pageCount<totalFragment.size();pageCount++){
            ImageView imageView=new ImageView(MainActivity.this);
            imageView.setImageResource((pageCount==currentItemCount)?R.drawable.lightheart:R.drawable.unlightheart);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(RepositoryFucntion.dptoPixel(15,MainActivity.this), RepositoryFucntion.dptoPixel(15,MainActivity.this));
            lp.setMargins(10,10,10,10);
            imageView.setLayoutParams(lp);
            mainPagerLayout.addView(imageView);
        }
    }

    public void setAdvertiseAdapter(){
        int userID=getSharedPreferences("data",0).getInt("UserID",0);
        int pregnantWeek=getSharedPreferences("data",0).getInt("CurrentPregnantWeek",0);
        totalFragment=new ArrayList<>();
        //database
        /*
        GetAdvertiseData getAdvertiseData=new GetAdvertiseData(String.valueOf(userID), String.valueOf(pregnantWeek), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="{\"Education\":[{\"EducationID\":44,\"EducationName\":\"\\u4ea7\\u524d\\u8fd0\\u52a8\",\"Content\":\"\\u6000\\u5b55\\u671f\\u95f4\\u5988\\u54aa\\u5343\\u4e07\\u4e0d\\u8981\\u6015\\u8fd0\\u52a8\\u4f1a\\u4f24\\u5230\\u80ce\\u513f\\uff0c\\u9002\\u5f53\\u7684\\u8fd0\\u52a8\\u53cd\\u800c\\u6709\\u52a9\\u51cf\\u7f13\\u5b55\\u671f\\u4e0d\\u9002\\u5e76\\u5e2e\\u52a9\\u987a\\u4ea7\\u5594\\uff01\\u5988\\u5988\\u4e0d\\u59a8\\u591a\\u6e38\\u6cf3\\u3001\\u6563\\u6b65\\uff0c\\u6216\\u662f\\u9a91\\u5ba4\\u5185\\u811a\\u8e0f\\u8f66\\u3001\\u4ea7\\u524d\\u745c\\u73c8\\uff0c\\u8fd9\\u4e9b\\u90fd\\u662f\\u9002\\u5408\\u6000\\u5b55\\u65f6\\u8fdb\\u884c\\u7684\\u953b\\u70bc\\u5594! \",\"IsEducational\":1,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"SubContent\":\"\\u4ea7\\u524d\\u53ef\\u4ee5\\u505a\\u54ea\\u4e9b\\u8fd0\\u52a8\\u5462?\",\"ParentID\":0,\"PregnantWeek\":13,\"Type\":0},{\"EducationID\":47,\"EducationName\":\"\\u4f38\\u5c55\\u4f53\\u64cd\",\"Content\":\"\\u76ae\\u62c9\\u63d0\\u65af\\u662f\\u4e00\\u79cd\\u5f88\\u597d\\u7684\\u4f38\\u5c55\\u8fd0\\u52a8\\uff0c\\u76ee\\u7684\\u5728\\u4e8e\\u6559\\u5bfc\\u5b55\\u5988\\u54aa\\u5982\\u4f55\\u4f7f\\u7528\\u6b63\\u786e\\u7684\\u529b\\u91cf\\u6765\\u63a7\\u5236\\u8eab\\u4f53\\uff0c\\u4ee5\\u5750\\u59ff\\u6216\\u4fa7\\u8eba\\u3001\\u4ef0\\u8eba\\u7684 5 \\u5206\\u949f\\u5185\\u4e4b\\u4f38\\u5c55\\u4f53\\u64cd\\u4e3a\\u5b9c\\u3002\\u6000\\u5b55\\u540e\\u671f\\u8eab\\u4f53\\u4f1a\\u4ea7\\u751f\\u8f83\\u9ad8\\u7684\\u677e\\u5f1b\\u7d20\\u5206\\u6ccc\\u91cf\\uff0c\\u8fd9\\u79cd\\u6fc0\\u7d20\\u4f1a\\u4f7f\\u5173\\u8282\\u97e7\\u5e26\\u53d8\\u5f97\\u6bd4\\u8f83\\u677e\\u5f1b\\uff0c\\u5e2e\\u52a9\\u5b50\\u5bab\\u6269\\u5927\\uff0c\\u8ba9\\u80ce\\u513f\\u987a\\u5229\\u901a\\u8fc7\\u4ea7\\u9053\\u3002\",\"IsEducational\":1,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"SubContent\":\"\\u4f38\\u5c55\\u4f53\\u64cd\\u53ef\\u4ee5\\u505a\\u5417\\uff1f\",\"ParentID\":0,\"PregnantWeek\":13,\"Type\":0}],\"HospitalInfo\":[{\"EducationID\":95,\"EducationName\":\"\\u5efa\\u5361\\u6d41\\u7a0b \",\"Content\":\"\",\"IsEducational\":0,\"Category\":\"\",\"SubContent\":\"\",\"ParentID\":0,\"PregnantWeek\":13,\"Type\":1},{\"EducationID\":96,\"EducationName\":\"\\u6e29\\u99a8\\u63d0\\u793a \",\"Content\":\"1.\\u6bcf\\u6b21\\u4ea7\\u68c0\\u65f6\\u8bf7\\u52a1\\u5fc5\\u5148\\u6302\\u53f7,\\u643a\\u5e26\\u5b55\\u671f\\u4fdd\\u5065\\u624b\\u518c\\u3001\\u9884\\u7ea6\\u624b\\u518c\\u3001\\u533b\\u4fdd\\u5361(\\u5c31\\u8bca\\u5361)\\r\\n\\u6d4b\\u91cf\\u8840\\u538b\\u3001\\u4f53\\u91cd,\\u56de\\u8bca\\u5ba4\\u62a4\\u58eb\\u5904\\u767b\\u8bb0,\\u7b49\\u5f85\\u4ea7\\u68c0\\u3002\\r\\n2.\\u95e8\\u8bca\\u6302\\u53f7\\u7f34\\u8d39\\u65f6\\u95f4:\\r\\n3-8 \\u697c\\r\\n\\u4e0a\\u534808:00-11-30\\r\\n\\u4e0b\\u534814:00\\u201416:30\\r\\n2\\u697c\\r\\n\\u4e0a\\u534807:30 \\u5f00\\u59cb\\r\\n\\u4e0b\\u534813:00 \\u5f00\\u59cb\\r\\n\\u505c\\u6b62\\u6302\\u53f7\\u65f6\\u95f4: \\u4e0a\\u534811:30 \\u4e0b\\u534816:30\\r\\n3.\\u4ea7\\u79d1\\u95e8\\u8bca\\u5b9e\\u884c\\u5206\\u65f6\\u6bb5\\u9884\\u7ea6,\\u8bca\\u5ba4\\u62a4\\u58eb\\u6839\\u636e\\u60a8\\u7684\\u68c0\\u67e5\\u9879\\u76ee,\\u4f1a\\u5206\\u65f6\\u65f6\\u6bb5\\u9884\\u7ea6\\r\\n\\u4f60\\u6bcf\\u6b21\\u7684\\u4ea7\\u68c0\\u65f6\\u95f4,\\u8bf7\\u60a8\\u6309\\u9884\\u7ea6\\u65f6\\u95f4\\u6bb5\\u5c31\\u8bca,\\u7279\\u6b8a\\u60c5\\u51b5,\\u8bf7\\u670d\\u8bca\\u5ba4\\u62a4\\u58eb\\u5b89\\u6392\\r\\n4.\\u6bcf\\u6b21\\u533b\\u9662\\u4ea7\\u68c0\\u7ed3\\u675f,\\u8bf7\\u4e0e\\u60a8\\u7684\\u8bca\\u5ba4\\u62a4\\u58eb\\u9884\\u7ea6\\u4e0b\\u6b21\\u4ea7\\u68c0\\u65f6\\u95f4\\u6bb5,\\u4e3a\\u4e86\\u65b9\\u4fbf\\r\\n\\u60a8\\u7684\\u4e0b\\u6b21\\u68c0\\u67e5,\\u8bf7\\u5148\\u4ea4\\u5b8c\\u4e0b\\u6b21\\u68c0\\u67e5\\u8d39\\u7528\\u540e\\u518d\\u79bb\\u9662\\u3002\\r\\n5:\\u5982\\u5b55\\u671f\\u51fa\\u73b0\\u89c1\\u7ea2\\u3001\\u9634\\u9053\\u6d41\\u8840\\u3001\\u9634\\u9053\\u6d41\\u6db2\\u3001\\u8179\\u90e8\\u75bc\\u75db\\u53ca\\u80ce\\u52a8\\u89c4\\u5f8b\\u53ca\\u52d8\\u544a\\r\\n\\u751f\\u53d8\\u5316\\u7b49\\u7d27\\u6025\\u60c5\\u51b5,\\u8bf7\\u7acb\\u5373\\u6765\\u6025\\u8bca(\\u4e00\\u697c)\\u5c31\\u8bca,\\u6211\\u9662\\u6025\\u8bca24\\u5c0f\\u65f6\\u503c\\u73ed\\u3002\\r\\n\\u643a\\u5e26\\u5b55\\u671f\\u4fdd\\u5065\\u624b\\u518c\\u3001\\u9884\\u7ea6\\u624b\\u518c\\u3001\\u533b\\u4fdd\\u5361(\\u5c31\\u8bca\\u5361)\\r\\n6:\\u6211\\u9662\\u4e3a\\u7231\\u5a74\\u533b\\u9662,\\u63d0\\u5021\\u6bcd\\u4e73\\u5582\\u517b,\\u4f4f\\u9662\\u7981\\u6b62\\u643a\\u5e26\\u5976\\u74f6\\u3001\\u5976\\u7c89\\u3001\\u5976\\u5634\",\"IsEducational\":0,\"Category\":\"\",\"SubContent\":\"\",\"ParentID\":0,\"PregnantWeek\":13,\"Type\":0}],\"Shop\":[{\"ProductID\":6,\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3985481&str_category_code=1204900017\",\"ProductName\":\"\\u3010\\u8d6b\\u800c\\u53f8\\u3011Ferti-500V\\u597d\\u97fb\\u65e5\\u672c\\u808c\\u9187+\\u8449\\u9178\\u690d\\u7269\\u81a0\\u56ca(90\\u9846\\/\\u7f50)\"},{\"ProductID\":29,\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=5385077&str_category_code=1204900017\",\"ProductName\":\"\\u3010BLACKMORES \\u6fb3\\u4f73\\u5bf6\\u3011\\u5b55\\u5bf6\\u591a\\u81a0\\u56ca\\u98df\\u54c1(180\\u9846)\"},{\"ProductID\":48,\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=6561917&str_category_code=1204900028\",\"ProductName\":\"\\u3010\\u5b55\\u54fa\\u5152\\u3011\\u5abd\\u5abd\\u85fb\\u6cb9DHA\\u2605\\u8edf\\u81a0\\u56ca(\\u5bf6\\u5bf6\\u8070\\u660e\\u8d77\\u8dd1)\"},{\"ProductID\":63,\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=1830500&str_category_code=1204900015\",\"ProductName\":\"\\u3010\\u4e09\\u591a\\u3011\\u5065\\u5eb7\\u7cfb\\u5217-T\\u5927\\u8c46\\u5375\\u78f7\\u8102\\u9846\\u7c92(300g\\/\\u7f50)\"},{\"ProductID\":100,\"Link\":\"https:\\/\\/www.momoshop.com.tw\\/goods\\/GoodsDetail.jsp?i_code=3985481&str_category_code=1204900017\",\"ProductName\":\"\\u3010\\u8d6b\\u800c\\u53f8\\u3011Ferti-500V\\u597d\\u97fb\\u65e5\\u672c\\u808c\\u9187+\\u8449\\u9178\\u690d\\u7269\\u81a0\\u56ca(90\\u9846\\/\\u7f50)\"}],\"Videoes\":[{\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=CS0TxeBRFUg\",\"Content\":\"3\\u5206\\u949f\\u770b\\u61c2\\u5b55\\u65e9\\u671fB\\u8d85\"},{\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=LfbXtM_R1zk\",\"Content\":\"\\u5b55\\u671f\\u5982\\u4f55\\u8865\\u5145DHA\\uff1f\"}],\"Schedule\":[]}";
                    JSONObject totalData=new JSONObject(response);

                    //physicalWarning
                    totalFragment.add(new AdvertiseAbnormalFragment());



                    //education
                    JSONArray educationArray=totalData.getJSONArray("Education");
                    ArrayList<MedicalEducation> educationList=new ArrayList<>();
                    if(educationArray.length()>0){
                        for(int i=0;i<educationArray.length();i++){
                            JSONObject eachEducation=educationArray.getJSONObject(i);
                            MedicalEducation me=new MedicalEducation();
                            me.setEducationID(eachEducation.getInt("EducationID"));
                            me.setEducationName(eachEducation.getString("EducationName"));
                            me.setContent(eachEducation.getString("Content"));
                            me.setIsEducational(eachEducation.getInt("IsEducational"));
                            me.setCategory(eachEducation.getString("Category"));
                            me.setSubContent(eachEducation.getString("SubContent"));
                            me.setParentID(eachEducation.getInt("ParentID"));
                            me.setPregnantWeek(eachEducation.getInt("PregnantWeek"));
                            me.setType(eachEducation.getInt("Type"));
                            educationList.add(me);
                            if(educationList.size()>=2){
                                break;
                            }
                        }
                        totalFragment.add(new AdvertiseEducationFragment(educationList,1,eventFlows));
                    }


                    //hopsitalInfo
                    JSONArray hospitalArray=totalData.getJSONArray("HospitalInfo");
                    ArrayList<MedicalEducation> hospitalList=new ArrayList<>();
                    if(hospitalArray.length()>0){
                        for(int i=0;i<hospitalArray.length();i++){
                            JSONObject eachHopsital=hospitalArray.getJSONObject(i);
                            MedicalEducation me=new MedicalEducation();
                            me.setEducationID(eachHopsital.getInt("EducationID"));
                            me.setEducationName(eachHopsital.getString("EducationName"));
                            me.setContent(eachHopsital.getString("Content"));
                            me.setIsEducational(eachHopsital.getInt("IsEducational"));
                            me.setCategory(eachHopsital.getString("Category"));
                            me.setSubContent(eachHopsital.getString("SubContent"));
                            me.setParentID(eachHopsital.getInt("ParentID"));
                            me.setPregnantWeek(eachHopsital.getInt("PregnantWeek"));
                            me.setType(eachHopsital.getInt("Type"));
                            hospitalList.add(me);
                            if(hospitalList.size()>=2){
                                break;
                            }
                        }
                        totalFragment.add(new AdvertiseEducationFragment(hospitalList,0,eventFlows));
                    }

                    //shop
                    JSONArray shopArray=totalData.getJSONArray("Shop");
                    ArrayList<ShopProduct> shopList=new ArrayList<>();
                    if(shopArray.length()>0){
                        for(int i=0;i<shopArray.length();i++){
                            JSONObject eachShop=shopArray.getJSONObject(i);
                            ShopProduct sp=new ShopProduct();
                            sp.setProductName(eachShop.getString("ProductName"));
                            sp.setLink(eachShop.getString("Link"));
                            sp.setProductID(eachShop.getInt("ProductID"));
                            shopList.add(sp);
                            if(shopList.size()>=2){
                                break;
                            }
                        }
                        totalFragment.add(new AdvertiseShopFragment(shopList));
                    }

                    //video
                    JSONArray videoArray=totalData.getJSONArray("Videoes");
                    ArrayList<EducationVideo> videoList=new ArrayList<>();
                    if(videoArray.length()>0){
                        for(int i=0;i<videoArray.length();i++){
                            JSONObject eachVideo=videoArray.getJSONObject(i);
                            EducationVideo ev=new EducationVideo();
                            ev.setContent(eachVideo.getString("Content"));
                            ev.setLink(eachVideo.getString("Link"));
                            videoList.add(ev);
                            if(videoList.size()>=2){
                                break;
                            }
                        }
                        totalFragment.add(new AdvertiseVideoFragment(videoList));
                    }


                    totalFragment.add(new AdvertiseScheduleFragment());
                    mainViewPager.setAdapter(new AdvertiseMainAdapter(getSupportFragmentManager(),totalFragment));

                    setHeartPager(0);
                } catch (JSONException e) {
                    Log.e("dd",e.getMessage());
                }/*
            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getAdvertiseData);*/
    }


}
