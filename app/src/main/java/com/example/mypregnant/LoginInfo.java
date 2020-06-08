package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.AdapterClasses.RegisterFragmentAdapter;
import com.example.mypregnant.DatabaseClasses.CheckAccountExist;
import com.example.mypregnant.DatabaseClasses.InsertUserInfo;
import com.example.mypregnant.Function.RepositoryFucntion;

public class LoginInfo extends AppCompatActivity {
    Button okBtn;
    EditText birthdayText,accountText,lastPeriodText,heightText,weightText,passwordText,confirmPasswordText;
    ViewPager loginInfoViewPager;
    RegisterFragmentAdapter registerAdapter;
    int currentItemCount=0;
    ImageButton nextBtn,lastBtn;
    String account,name,password,birthday,lastPeriod,weight,height,room,medicalID,doctor,nurse;
    LinearLayout pagerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_info);
        loginInfoViewPager=findViewById(R.id.loginInfoViewPager);
        registerAdapter=new RegisterFragmentAdapter(getSupportFragmentManager());
        loginInfoViewPager.setAdapter(registerAdapter);
        loginInfoViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        nextBtn=findViewById(R.id.registerNextBtn);
        lastBtn=findViewById(R.id.registerLastBtn);
        pagerLayout=findViewById(R.id.registerPagerLayout);
        lastBtn.setVisibility(View.INVISIBLE);
        lastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentItemCount=(currentItemCount<6&&currentItemCount>0)?currentItemCount-1:currentItemCount;
                lastBtn.setVisibility((currentItemCount==0)? View.INVISIBLE:View.VISIBLE);
                nextBtn.setVisibility((currentItemCount==5)? View.INVISIBLE:View.VISIBLE);
                nextBtn.setImageResource((currentItemCount==5)? R.drawable.ok:R.drawable.nextbtn);

                setHeartPager();


                loginInfoViewPager.setCurrentItem(currentItemCount,true);



            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean nextPageAvaliable=false;
                int position = loginInfoViewPager.getCurrentItem();
                RegisterFragment fragment =(RegisterFragment)registerAdapter.getRegisteredFragment(position);
                EditText firstEditText=fragment.getView().findViewById(R.id.registerFirstEditText);
                EditText secondEditText=fragment.getView().findViewById(R.id.registerSecondEditText);

                if(position==0){

                    account=firstEditText.getText().toString();
                    name=secondEditText.getText().toString();
                    if(account.equals("")||name.equals("")){
                        Log.e("sdvsdv","sdvsv");
                        RepositoryFucntion.CustomToast(LoginInfo.this,"請填寫完整!");
                    }
                    else{
                        CheckAccountExist checkAccountExist=new CheckAccountExist(account, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.trim().equals("1")){
                                    Toast.makeText(LoginInfo.this, "帳號已有人使用", Toast.LENGTH_SHORT).show();
                                }
                                else if(response.trim().equals("0")){
                                    currentItemCount=(currentItemCount<5&&currentItemCount>=0)?currentItemCount+1:currentItemCount;
                                    nextBtn.setVisibility((currentItemCount==5)? View.INVISIBLE:View.VISIBLE);
                                    lastBtn.setVisibility((currentItemCount==0)? View.INVISIBLE:View.VISIBLE);
                                    setHeartPager();
                                    loginInfoViewPager.setCurrentItem(currentItemCount,true);
                                }
                                else{
                                    Toast.makeText(LoginInfo.this, "系統錯誤", Toast.LENGTH_SHORT).show();
                                    Log.e("res",response);
                                }
                            }
                        });
                        RequestQueue q= Volley.newRequestQueue(LoginInfo.this);
                        q.add(checkAccountExist);
                    }
                }
                else if(position==1){
                    password=firstEditText.getText().toString();
                    String confrimPassword=secondEditText.getText().toString();
                    if(password.equals("")){
                        Toast.makeText(LoginInfo.this, "請填寫完整", Toast.LENGTH_SHORT).show();
                    }
                    else if(!password.equals(confrimPassword)){
                        Toast.makeText(LoginInfo.this, "密碼和確認密碼不一致", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        nextPageAvaliable=true;
                    }
                }
                else if(position==2){
                    birthday=firstEditText.getText().toString();
                    lastPeriod=secondEditText.getText().toString();
                    if(birthday.equals("")||lastPeriod.equals("")){
                        Toast.makeText(LoginInfo.this, "請填寫完整", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        nextPageAvaliable=true;
                    }

                }
                else if(position==3){
                    height=firstEditText.getText().toString();
                    weight=secondEditText.getText().toString();
                    if(height.equals("")||weight.equals("")){
                        Toast.makeText(LoginInfo.this, "請填寫完整", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        nextPageAvaliable=true;
                    }

                }
                else if(position==4){
                    room=firstEditText.getText().toString();
                    medicalID=secondEditText.getText().toString();
                    if(room.equals("")||medicalID.equals("")){
                        Toast.makeText(LoginInfo.this, "請填寫完整", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        nextPageAvaliable=true;
                    }

                }
                else if(position==5){
                    doctor=firstEditText.getText().toString();
                    nurse=secondEditText.getText().toString();
                    if(doctor.equals("")||nurse.equals("")){
                        Toast.makeText(LoginInfo.this, "請填寫完整", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        InsertUserInfo insertUserInfo=new InsertUserInfo(account,name,birthday,password,lastPeriod,weight,height,room,medicalID,doctor,nurse, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(LoginInfo.this, "註冊成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                        RequestQueue q= Volley.newRequestQueue(LoginInfo.this);
                        q.add(insertUserInfo);
                    }
                }

                if(nextPageAvaliable){
                    currentItemCount=(currentItemCount<5&&currentItemCount>=0)?currentItemCount+1:currentItemCount;
                    nextBtn.setImageResource((currentItemCount==5)? R.drawable.ok:R.drawable.nextbtn);
                    lastBtn.setVisibility((currentItemCount==0)? View.INVISIBLE:View.VISIBLE);

                    setHeartPager();


                    loginInfoViewPager.setCurrentItem(currentItemCount,true);
                }
            }
        });
        /*
        birthdayText=findViewById(R.id.registerBirthday);
        accountText=findViewById(R.id.registerAccount);
        lastPeriodText=findViewById(R.id.registerLastPeriodDate);
        heightText=findViewById(R.id.registerHeight);
        weightText=findViewById(R.id.registerWeight);
        passwordText=findViewById(R.id.registerPassword);
        confirmPasswordText=findViewById(R.id.registerConfirmPassword);
        okBtn=findViewById(R.id.registerOKBtn);
        RepositoryFucntion.setDateText(birthdayText,this,false);
        RepositoryFucntion.setDateText(lastPeriodText,this,false);
*/


/*
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("periodDate",lastPeriodText.getText().toString());
                if(!passwordText.getText().toString().equals(confirmPasswordText.getText().toString()))
                {
                    Toast.makeText(LoginInfo.this, "密碼和密碼確認不相同!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(accountText.getText().toString().equals("")|| birthdayText.getText().toString().equals("")|| passwordText.getText().toString().equals("")||
                        lastPeriodText.getText().toString().equals("")|| weightText.getText().toString().equals("")|| heightText.getText().toString().equals("")){
                    Toast.makeText(LoginInfo.this, "填寫不完整!", Toast.LENGTH_SHORT).show();
                    return;
                }
                InsertUserInfo insertUserInfo=new InsertUserInfo(accountText.getText().toString(), birthdayText.getText().toString(), passwordText.getText().toString(),
                        lastPeriodText.getText().toString(), weightText.getText().toString(), heightText.getText().toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response=response.trim();
                        if(response.equals("1"))
                        {
                            Toast.makeText(LoginInfo.this, "帳號已經存在，請更換", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.equals("0"))
                        {
                            Toast.makeText(LoginInfo.this, "註冊成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Log.e("ddd",response);

                        }
                    }
                });
                RequestQueue q= Volley.newRequestQueue(LoginInfo.this);
                q.add(insertUserInfo);

            }
        });*/
    }

    public void setHeartPager(){

        pagerLayout.removeAllViews();
        for(int pageCount=0;pageCount<6;pageCount++){
            ImageView imageView=new ImageView(LoginInfo.this);
            imageView.setImageResource((pageCount==currentItemCount)?R.drawable.lightheart:R.drawable.unlightheart);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(RepositoryFucntion.dptoPixel(20,LoginInfo.this), RepositoryFucntion.dptoPixel(20,LoginInfo.this));
            lp.setMargins(10,10,10,10);
            imageView.setLayoutParams(lp);
            pagerLayout.addView(imageView);
        }


    }
}
