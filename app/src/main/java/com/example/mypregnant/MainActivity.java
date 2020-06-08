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
        GetUserInfo getUserInfo=new GetUserInfo(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
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
                }
            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getUserInfo);
    }
    public void GetEventFlowChart()
    {
        GetEventFlow getEventFlow=new GetEventFlow("1",new Response.Listener<String>() {
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
                        //ef.setEducation(jEachEvent.getBoolean("IsEducational"));
                        ef.setEducationID(jEachEvent.getInt("EducationID"));
                        eventFlows.add(ef);
                    }

                    setAdvertiseAdapter();
                } catch (JSONException e) {
                    Log.e("ese",e.getMessage());
                }
            }
        });
        RequestQueue q= Volley.newRequestQueue(MainActivity.this);
        q.add(getEventFlow);
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
        GetAdvertiseData getAdvertiseData=new GetAdvertiseData(String.valueOf(userID), String.valueOf(pregnantWeek), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
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
                }
            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getAdvertiseData);
    }


}
