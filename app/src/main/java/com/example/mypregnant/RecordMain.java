package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetRecord;
import com.example.mypregnant.DatabaseClasses.GetSchedule;
import com.example.mypregnant.DatabaseClasses.InsertRecord;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.Record;
import com.example.mypregnant.ObjectClasses.ScheduleEvent;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecordMain extends AppCompatActivity {
    //test
    FlexboxLayout recordCountLinearLayout;
    LinearLayout recordContent;
    int userID;
    ArrayList<Record> records;

    DialogLoading loading;
    TextView recordMainCountText,recordMainDateText;
    ArrayList<ScheduleEvent> events;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_main);
        ToolBarFunction.setToolBarInit(this,"紀錄");
        userID=getSharedPreferences("data",0).getInt("user",0);
        recordCountLinearLayout=findViewById(R.id.recordCountLinearLayout);
        recordContent=findViewById(R.id.recordContent);
        recordMainCountText=findViewById(R.id.recordMainCountText);
        recordMainDateText=findViewById(R.id.recordMainDateText);
        loading=new DialogLoading(this);
        loading.show();
        GetRecordData();

    }
    public void setButtonClick()
    {
        recordContent.removeAllViews();
        recordCountLinearLayout.removeAllViews();
        for(int i=0;i<15;i++)
        {

            Button recordBtn=new Button(this);
            recordBtn.setText(String.valueOf(i+1));
            FlexboxLayout.LayoutParams lp=new FlexboxLayout.LayoutParams(RepositoryFucntion.dptoPixel(50,this), RepositoryFucntion.dptoPixel(50,this));
            lp.setFlexBasisPercent(18);
            recordBtn.setLayoutParams(lp);
            recordBtn.setTextColor(Color.WHITE);


            View recordBtnView=getLayoutInflater().from(RecordMain.this).inflate(R.layout.layout_record_count_btn,recordCountLinearLayout,false);
            ImageButton layoutRecordCountBtn=recordBtnView.findViewById(R.id.layoutRecordCountBtn);
            TextView layoutRecordCountText=recordBtnView.findViewById(R.id.layoutRecordCountText);
            layoutRecordCountText.setText(String.valueOf(i+1));

            if(i< records.size())
            {
                final int count=i;
                Log.e("dssd","sdvsdvsdvdsv");
                layoutRecordCountBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recordMainCountText.setText((count+1)+"");
                        recordMainDateText.setText(GetEventDate(count));
                        recordContent.removeAllViews();
                        View layoutView=View.inflate(RecordMain.this,R.layout.layout_record_view,null);
                        recordContent.addView(layoutView);
                        setRecordData(count);
                    }
                });

                layoutRecordCountBtn.setImageResource((R.drawable.blue_round));
            }
            else if(i==records.size())
            {
                final int count=i;

                recordMainCountText.setText((count+1)+"");
                recordMainDateText.setText(GetEventDate(count));
                layoutRecordCountBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recordMainCountText.setText((count+1)+"");
                        recordMainDateText.setText(GetEventDate(count));
                        recordContent.removeAllViews();
                        View layoutView=View.inflate(RecordMain.this,R.layout.layout_record_add,null);
                        Button saveBtn=layoutView.findViewById(R.id.recordAddSaveBtn);


                        saveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                SaveRecordData();
                            }
                        });
                        recordContent.addView(layoutView);

                    }
                });
                layoutRecordCountBtn.setImageResource((R.drawable.yellow_round));

            }
            else
            {
                final int count=i;
                layoutRecordCountBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        recordMainCountText.setText((count+1)+"");
                        recordMainDateText.setText(GetEventDate(count));
                        recordContent.removeAllViews();
                    }
                });
                layoutRecordCountBtn.setImageResource((R.drawable.gray_round));

            }
            recordCountLinearLayout.addView(recordBtnView);

            recordContent.removeAllViews();


        }

        View layoutView=View.inflate(RecordMain.this,R.layout.layout_record_add,null);
        Button saveBtn=layoutView.findViewById(R.id.recordAddSaveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveRecordData();
            }
        });
        recordContent.addView(layoutView);
        loading.dismiss();
    }
    public String GetEventDate(int count){
        if(count<events.size()){
            return events.get(count).getDate();
        }
        return "未找到訊息";
    }
    public void SaveRecordData(){
        EditText Weight=recordContent.findViewById(R.id.recordAddWeight);
        EditText SBP=recordContent.findViewById(R.id.recordAddSBP);
        EditText DBP=recordContent.findViewById(R.id.recordAddDBP);
        RadioGroup Mouth=recordContent.findViewById(R.id.recordAddMouth);
        RadioGroup HeartLung=recordContent.findViewById(R.id.recordAddHeartLung);
        RadioGroup LowerLimbEdema=recordContent.findViewById(R.id.recordAddLowerLimbEdema);
        EditText FundalHeight=recordContent.findViewById(R.id.recordAddFundalHeight);
        EditText Girth=recordContent.findViewById(R.id.recordAddGirth);
        EditText FHB=recordContent.findViewById(R.id.recordAddFHB);
        RadioGroup Cervix=recordContent.findViewById(R.id.recordAddCervix);
        RadioGroup Electrocardiogram=recordContent.findViewById(R.id.recordAddElectrocardiogram);
        RadioGroup FetalHeartCare=recordContent.findViewById(R.id.recordAddFetalHeartCare);
        EditText BloodRBC=recordContent.findViewById(R.id.recordAddBloodRBC);
        EditText BloodHB=recordContent.findViewById(R.id.recordAddBloodHB);
        EditText BloodWBC=recordContent.findViewById(R.id.recordAddBloodWBC);
        EditText BloodWBCCount=recordContent.findViewById(R.id.recordAddBloodWBCCount);
        EditText BloodPLT=recordContent.findViewById(R.id.recordAddBloodPLT);
        EditText UrinePH=recordContent.findViewById(R.id.recordAddUrinePH);
        EditText UrineSG=recordContent.findViewById(R.id.recordAddUrineSG);
        EditText UrineURO=recordContent.findViewById(R.id.recordAddUrineURO);
        EditText UrineBLD=recordContent.findViewById(R.id.recordAddUrineBLD);
        EditText UrineWBC=recordContent.findViewById(R.id.recordAddUrineWBC);
        EditText UrinePRO=recordContent.findViewById(R.id.recordAddUrinePRO);
        EditText UrineGLU=recordContent.findViewById(R.id.recordAddUrineGLU);
        EditText UrineBIL=recordContent.findViewById(R.id.recordAddUrineBIL);
        EditText UrineKET=recordContent.findViewById(R.id.recordAddUrineKET);
        EditText UrineRBC=recordContent.findViewById(R.id.recordAddUrineRBC);
        EditText UrineSugarEmpty=recordContent.findViewById(R.id.recordAddUrineSugarEmpty);
        EditText UrineSugarOne=recordContent.findViewById(R.id.recordAddUrineSugarOne);
        EditText UrineSugarTwo=recordContent.findViewById(R.id.recordAddUrineSugarTwo);
        EditText LiverTP=recordContent.findViewById(R.id.recordAddLiverTP);
        EditText LiverG=recordContent.findViewById(R.id.recordAddLiverG);
        EditText LiverALB=recordContent.findViewById(R.id.recordAddLiverALB);
        EditText LiverAST=recordContent.findViewById(R.id.recordAddLiverAST);
        EditText LiverALT=recordContent.findViewById(R.id.recordAddLiverALT);
        EditText LiverSTB=recordContent.findViewById(R.id.recordAddLiverSTB);
        EditText LiverDBIL=recordContent.findViewById(R.id.recordAddLiverDBIL);
        EditText LiverCHE=recordContent.findViewById(R.id.recordAddLiverCHE);
        EditText KidneyBUN=recordContent.findViewById(R.id.recordAddKidneyBUN);
        EditText KidneySCR=recordContent.findViewById(R.id.recordAddKidneySCR);
        EditText KidneyB2_MG=recordContent.findViewById(R.id.recordAddKidneyB2_MG);
        EditText KidneyUrineAcid=recordContent.findViewById(R.id.recordAddKidneyUrineAcid);



        Record rr=new Record();
        int selectedId=-1;
        rr.setDate("1990-01-01");
        rr.setWeight((Weight.getText().toString()));
        rr.setSBP((SBP.getText().toString()));
        rr.setDBP((DBP.getText().toString()));
        selectedId = Mouth .getCheckedRadioButtonId();
        rr.setMouth((selectedId!=-1)? ((RadioButton) findViewById(selectedId)).getText().toString():"");
        selectedId = HeartLung .getCheckedRadioButtonId();
        rr.setHeartLung((selectedId!=-1)? ((RadioButton) findViewById(selectedId)).getText().toString():"");
        selectedId = LowerLimbEdema .getCheckedRadioButtonId();
        rr.setLowerLimbEdema((selectedId!=-1)? ((RadioButton) findViewById(selectedId)).getText().toString():"");
        rr.setFundalHeight((FundalHeight.getText().toString()));
        rr.setGirth((Girth.getText().toString()));
        rr.setFHB((FHB.getText().toString()));
        selectedId = Cervix .getCheckedRadioButtonId();
        rr.setCervix((selectedId!=-1)? ((RadioButton) findViewById(selectedId)).getText().toString():"");
        selectedId = Electrocardiogram .getCheckedRadioButtonId();
        rr.setElectrocardiogram((selectedId!=-1)? ((RadioButton) findViewById(selectedId)).getText().toString():"");
        selectedId = FetalHeartCare .getCheckedRadioButtonId();
        rr.setFetalHeartCare((selectedId!=-1)? ((RadioButton) findViewById(selectedId)).getText().toString():"");
        rr.setBloodRBC((BloodRBC.getText().toString()));
        rr.setBloodHB((BloodHB.getText().toString()));
        rr.setBloodWBC((BloodWBC.getText().toString()));
        rr.setBloodWBCCount((BloodWBCCount.getText().toString()));
        rr.setBloodPLT((BloodPLT.getText().toString()));
        rr.setUrinePH((UrinePH.getText().toString()));
        rr.setUrineSG((UrineSG.getText().toString()));
        rr.setUrineURO((UrineURO.getText().toString()));
        rr.setUrineBLD((UrineBLD.getText().toString()));
        rr.setUrineWBC((UrineWBC.getText().toString()));
        rr.setUrinePRO((UrinePRO.getText().toString()));
        rr.setUrineGLU((UrineGLU.getText().toString()));
        rr.setUrineBIL((UrineBIL.getText().toString()));
        rr.setUrineKET((UrineKET.getText().toString()));
        rr.setUrineRBC((UrineRBC.getText().toString()));
        rr.setUrineSugarEmpty((UrineSugarEmpty.getText().toString()));
        rr.setUrineSugarOne((UrineSugarOne.getText().toString()));
        rr.setUrineSugarTwo((UrineSugarTwo.getText().toString()));
        rr.setLiverTP((LiverTP.getText().toString()));
        rr.setLiverG((LiverG.getText().toString()));
        rr.setLiverALB((LiverALB.getText().toString()));
        rr.setLiverAST((LiverAST.getText().toString()));
        rr.setLiverALT((LiverALT.getText().toString()));
        rr.setLiverSTB((LiverSTB.getText().toString()));
        rr.setLiverDBIL((LiverDBIL.getText().toString()));
        rr.setLiverCHE((LiverCHE.getText().toString()));
        rr.setKidneyBUN((KidneyBUN.getText().toString()));
        rr.setKidneySCR((KidneySCR.getText().toString()));
        rr.setKidneyB2_MG((KidneyB2_MG.getText().toString()));
        rr.setKidneyUrineAcid((KidneyUrineAcid.getText().toString()));

        Gson gson=new Gson();
        //database
        /*
        InsertRecord insertRecord=new InsertRecord(String.valueOf(userID), gson.toJson(rr), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GetRecordData();
            }
        });
        RequestQueue q= Volley.newRequestQueue(RecordMain.this);
        q.add(insertRecord);*/
    }
    public void setRecordData(int count){
        TextView Weight=recordContent.findViewById(R.id.recordAddWeight);
        TextView SBP=recordContent.findViewById(R.id.recordAddSBP);
        TextView DBP=recordContent.findViewById(R.id.recordAddDBP);
        TextView Mouth=recordContent.findViewById(R.id.recordAddMouth);
        TextView HeartLung=recordContent.findViewById(R.id.recordAddHeartLung);
        TextView LowerLimbEdema=recordContent.findViewById(R.id.recordAddLowerLimbEdema);
        TextView FundalHeight=recordContent.findViewById(R.id.recordAddFundalHeight);
        TextView Girth=recordContent.findViewById(R.id.recordAddGirth);
        TextView FHB=recordContent.findViewById(R.id.recordAddFHB);
        TextView Cervix=recordContent.findViewById(R.id.recordAddCervix);
        TextView Electrocardiogram=recordContent.findViewById(R.id.recordAddElectrocardiogram);
        TextView FetalHeartCare=recordContent.findViewById(R.id.recordAddFetalHeartCare);
        TextView BloodRBC=recordContent.findViewById(R.id.recordAddBloodRBC);
        TextView BloodHB=recordContent.findViewById(R.id.recordAddBloodHB);
        TextView BloodWBC=recordContent.findViewById(R.id.recordAddBloodWBC);
        TextView BloodWBCCount=recordContent.findViewById(R.id.recordAddBloodWBCCount);
        TextView BloodPLT=recordContent.findViewById(R.id.recordAddBloodPLT);
        TextView UrinePH=recordContent.findViewById(R.id.recordAddUrinePH);
        TextView UrineSG=recordContent.findViewById(R.id.recordAddUrineSG);
        TextView UrineURO=recordContent.findViewById(R.id.recordAddUrineURO);
        TextView UrineBLD=recordContent.findViewById(R.id.recordAddUrineBLD);
        TextView UrineWBC=recordContent.findViewById(R.id.recordAddUrineWBC);
        TextView UrinePRO=recordContent.findViewById(R.id.recordAddUrinePRO);
        TextView UrineGLU=recordContent.findViewById(R.id.recordAddUrineGLU);
        TextView UrineBIL=recordContent.findViewById(R.id.recordAddUrineBIL);
        TextView UrineKET=recordContent.findViewById(R.id.recordAddUrineKET);
        TextView UrineRBC=recordContent.findViewById(R.id.recordAddUrineRBC);
        TextView UrineSugarEmpty=recordContent.findViewById(R.id.recordAddUrineSugarEmpty);
        TextView UrineSugarOne=recordContent.findViewById(R.id.recordAddUrineSugarOne);
        TextView UrineSugarTwo=recordContent.findViewById(R.id.recordAddUrineSugarTwo);
        TextView LiverTP=recordContent.findViewById(R.id.recordAddLiverTP);
        TextView LiverG=recordContent.findViewById(R.id.recordAddLiverG);
        TextView LiverALB=recordContent.findViewById(R.id.recordAddLiverALB);
        TextView LiverAST=recordContent.findViewById(R.id.recordAddLiverAST);
        TextView LiverALT=recordContent.findViewById(R.id.recordAddLiverALT);
        TextView LiverSTB=recordContent.findViewById(R.id.recordAddLiverSTB);
        TextView LiverDBIL=recordContent.findViewById(R.id.recordAddLiverDBIL);
        TextView LiverCHE=recordContent.findViewById(R.id.recordAddLiverCHE);
        TextView KidneyBUN=recordContent.findViewById(R.id.recordAddKidneyBUN);
        TextView KidneySCR=recordContent.findViewById(R.id.recordAddKidneySCR);
        TextView KidneyB2_MG=recordContent.findViewById(R.id.recordAddKidneyB2_MG);
        TextView KidneyUrineAcid=recordContent.findViewById(R.id.recordAddKidneyUrineAcid);

        Weight.setText((records.get(count).getWeight()));
        SBP.setText((records.get(count).getSBP()));
        DBP.setText((records.get(count).getDBP()));
        Mouth.setText((records.get(count).getMouth()));
        HeartLung.setText((records.get(count).getHeartLung()));
        LowerLimbEdema.setText((records.get(count).getLowerLimbEdema()));
        FundalHeight.setText((records.get(count).getFundalHeight()));
        Girth.setText((records.get(count).getGirth()));
        FHB.setText((records.get(count).getFHB()));
        Cervix.setText((records.get(count).getCervix()));
        Electrocardiogram.setText((records.get(count).getElectrocardiogram()));
        FetalHeartCare.setText((records.get(count).getFetalHeartCare()));
        BloodRBC.setText((records.get(count).getBloodRBC()));
        BloodHB.setText((records.get(count).getBloodHB()));
        BloodWBC.setText((records.get(count).getBloodWBC()));
        BloodWBCCount.setText((records.get(count).getBloodWBCCount()));
        BloodPLT.setText((records.get(count).getBloodPLT()));
        UrinePH.setText((records.get(count).getUrinePH()));
        UrineSG.setText((records.get(count).getUrineSG()));
        UrineURO.setText((records.get(count).getUrineURO()));
        UrineBLD.setText((records.get(count).getUrineBLD()));
        UrineWBC.setText((records.get(count).getUrineWBC()));
        UrinePRO.setText((records.get(count).getUrinePRO()));
        UrineGLU.setText((records.get(count).getUrineGLU()));
        UrineBIL.setText((records.get(count).getUrineBIL()));
        UrineKET.setText((records.get(count).getUrineKET()));
        UrineRBC.setText((records.get(count).getUrineRBC()));
        UrineSugarEmpty.setText((records.get(count).getUrineSugarEmpty()));
        UrineSugarOne.setText((records.get(count).getUrineSugarOne()));
        UrineSugarTwo.setText((records.get(count).getUrineSugarTwo()));
        LiverTP.setText((records.get(count).getLiverTP()));
        LiverG.setText((records.get(count).getLiverG()));
        LiverALB.setText((records.get(count).getLiverALB()));
        LiverAST.setText((records.get(count).getLiverAST()));
        LiverALT.setText((records.get(count).getLiverALT()));
        LiverSTB.setText((records.get(count).getLiverSTB()));
        LiverDBIL.setText((records.get(count).getLiverDBIL()));
        LiverCHE.setText((records.get(count).getLiverCHE()));
        KidneyBUN.setText((records.get(count).getKidneyBUN()));
        KidneySCR.setText((records.get(count).getKidneySCR()));
        KidneyB2_MG.setText((records.get(count).getKidneyB2_MG()));
        KidneyUrineAcid.setText((records.get(count).getKidneyUrineAcid()));

    }
    public void GetRecordData(){
        //database
        /*
        GetRecord getRecord=new GetRecord(String.valueOf(userID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="[{\"Date\":\"1900-01-01\",\"Weight\":\"1\",\"SBP\":\"2\",\"DBP\":\"3\",\"Mouth\":\"\\u672a\\u9078\\u64c7\",\"HeartLung\":\"\\u672a\\u9078\\u64c7\",\"LowerLimbEdema\":\"\\u672a\\u9078\\u64c7\",\"FundalHeight\":\"4\",\"Girth\":\"5\",\"FHB\":\"6\",\"Cervix\":\"\\u672a\\u9078\\u64c7\",\"Electrocardiogram\":\"\\u672a\\u9078\\u64c7\",\"FetalHeartCare\":\"\\u672a\\u9078\\u64c7\",\"BloodRBC\":\"7\",\"BloodHB\":\"8\",\"BloodWBC\":\"9\",\"BloodWBCCount\":\"8\",\"BloodPLT\":\"8\",\"UrinePH\":\"7897\",\"UrineSG\":\"87\",\"UrineURO\":\"5\",\"UrineBLD\":\"8\",\"UrineWBC\":\"88\",\"UrinePRO\":\"8\",\"UrineGLU\":\"7877\",\"UrineBIL\":\"87\",\"UrineKET\":\"7\",\"UrineRBC\":\"7\",\"UrineSugarEmpty\":\"7\",\"UrineSugarOne\":\"7\",\"UrineSugarTwo\":\"7\",\"LiverTP\":\"7\",\"LiverG\":\"7\",\"LiverALB\":\"7\",\"LiverAST\":\"7\",\"LiverALT\":\"7\",\"LiverSTB\":\"7\",\"LiverDBIL\":\"7\",\"LiverCHE\":\"7\",\"KidneyBUN\":\"8\",\"KidneySCR\":\"9\",\"KidneyB2_MG\":\"8\",\"KidneyUrineAcid\":\"7\",\"CreateTime\":{\"date\":\"2019-10-04 14:45:28.000000\",\"timezone_type\":3,\"timezone\":\"Asia\\/Taipei\"}},{\"Date\":\"1900-01-01\",\"Weight\":\"\",\"SBP\":\"\",\"DBP\":\"\",\"Mouth\":\"\",\"HeartLung\":\"\",\"LowerLimbEdema\":\"\",\"FundalHeight\":\"\",\"Girth\":\"\",\"FHB\":\"\",\"Cervix\":\"\",\"Electrocardiogram\":\"\",\"FetalHeartCare\":\"\",\"BloodRBC\":\"\",\"BloodHB\":\"\",\"BloodWBC\":\"\",\"BloodWBCCount\":\"\",\"BloodPLT\":\"\",\"UrinePH\":\"\",\"UrineSG\":\"\",\"UrineURO\":\"\",\"UrineBLD\":\"\",\"UrineWBC\":\"\",\"UrinePRO\":\"\",\"UrineGLU\":\"\",\"UrineBIL\":\"\",\"UrineKET\":\"\",\"UrineRBC\":\"\",\"UrineSugarEmpty\":\"\",\"UrineSugarOne\":\"\",\"UrineSugarTwo\":\"\",\"LiverTP\":\"\",\"LiverG\":\"\",\"LiverALB\":\"\",\"LiverAST\":\"\",\"LiverALT\":\"\",\"LiverSTB\":\"\",\"LiverDBIL\":\"\",\"LiverCHE\":\"\",\"KidneyBUN\":\"\",\"KidneySCR\":\"\",\"KidneyB2_MG\":\"\",\"KidneyUrineAcid\":\"\",\"CreateTime\":{\"date\":\"2019-10-04 15:36:38.000000\",\"timezone_type\":3,\"timezone\":\"Asia\\/Taipei\"}},{\"Date\":\"1900-01-01\",\"Weight\":\"\",\"SBP\":\"\",\"DBP\":\"\",\"Mouth\":\"\",\"HeartLung\":\"\",\"LowerLimbEdema\":\"\",\"FundalHeight\":\"\",\"Girth\":\"\",\"FHB\":\"\",\"Cervix\":\"\",\"Electrocardiogram\":\"\",\"FetalHeartCare\":\"\",\"BloodRBC\":\"\",\"BloodHB\":\"\",\"BloodWBC\":\"\",\"BloodWBCCount\":\"\",\"BloodPLT\":\"\",\"UrinePH\":\"\",\"UrineSG\":\"\",\"UrineURO\":\"\",\"UrineBLD\":\"\",\"UrineWBC\":\"\",\"UrinePRO\":\"\",\"UrineGLU\":\"\",\"UrineBIL\":\"\",\"UrineKET\":\"\",\"UrineRBC\":\"\",\"UrineSugarEmpty\":\"\",\"UrineSugarOne\":\"\",\"UrineSugarTwo\":\"\",\"LiverTP\":\"\",\"LiverG\":\"\",\"LiverALB\":\"\",\"LiverAST\":\"\",\"LiverALT\":\"\",\"LiverSTB\":\"\",\"LiverDBIL\":\"\",\"LiverCHE\":\"\",\"KidneyBUN\":\"\",\"KidneySCR\":\"\",\"KidneyB2_MG\":\"\",\"KidneyUrineAcid\":\"\",\"CreateTime\":{\"date\":\"2019-10-04 15:38:39.000000\",\"timezone_type\":3,\"timezone\":\"Asia\\/Taipei\"}},{\"Date\":\"1900-01-01\",\"Weight\":\"\",\"SBP\":\"\",\"DBP\":\"\",\"Mouth\":\"\",\"HeartLung\":\"\",\"LowerLimbEdema\":\"\",\"FundalHeight\":\"\",\"Girth\":\"\",\"FHB\":\"\",\"Cervix\":\"\",\"Electrocardiogram\":\"\",\"FetalHeartCare\":\"\",\"BloodRBC\":\"\",\"BloodHB\":\"\",\"BloodWBC\":\"\",\"BloodWBCCount\":\"\",\"BloodPLT\":\"\",\"UrinePH\":\"\",\"UrineSG\":\"\",\"UrineURO\":\"\",\"UrineBLD\":\"\",\"UrineWBC\":\"\",\"UrinePRO\":\"\",\"UrineGLU\":\"\",\"UrineBIL\":\"\",\"UrineKET\":\"\",\"UrineRBC\":\"\",\"UrineSugarEmpty\":\"\",\"UrineSugarOne\":\"\",\"UrineSugarTwo\":\"\",\"LiverTP\":\"\",\"LiverG\":\"\",\"LiverALB\":\"\",\"LiverAST\":\"\",\"LiverALT\":\"\",\"LiverSTB\":\"\",\"LiverDBIL\":\"\",\"LiverCHE\":\"\",\"KidneyBUN\":\"\",\"KidneySCR\":\"\",\"KidneyB2_MG\":\"\",\"KidneyUrineAcid\":\"\",\"CreateTime\":{\"date\":\"2019-10-04 15:39:26.000000\",\"timezone_type\":3,\"timezone\":\"Asia\\/Taipei\"}},{\"Date\":\"1990-01-01\",\"Weight\":\"30\",\"SBP\":\"\",\"DBP\":\"\",\"Mouth\":\"\",\"HeartLung\":\"\",\"LowerLimbEdema\":\"\",\"FundalHeight\":\"\",\"Girth\":\"\",\"FHB\":\"\",\"Cervix\":\"\",\"Electrocardiogram\":\"\",\"FetalHeartCare\":\"\",\"BloodRBC\":\"\",\"BloodHB\":\"\",\"BloodWBC\":\"\",\"BloodWBCCount\":\"\",\"BloodPLT\":\"\",\"UrinePH\":\"\",\"UrineSG\":\"\",\"UrineURO\":\"\",\"UrineBLD\":\"\",\"UrineWBC\":\"\",\"UrinePRO\":\"\",\"UrineGLU\":\"\",\"UrineBIL\":\"\",\"UrineKET\":\"\",\"UrineRBC\":\"\",\"UrineSugarEmpty\":\"\",\"UrineSugarOne\":\"\",\"UrineSugarTwo\":\"\",\"LiverTP\":\"\",\"LiverG\":\"\",\"LiverALB\":\"\",\"LiverAST\":\"\",\"LiverALT\":\"\",\"LiverSTB\":\"\",\"LiverDBIL\":\"\",\"LiverCHE\":\"\",\"KidneyBUN\":\"\",\"KidneySCR\":\"\",\"KidneyB2_MG\":\"\",\"KidneyUrineAcid\":\"\",\"CreateTime\":{\"date\":\"2019-12-15 22:16:44.000000\",\"timezone_type\":3,\"timezone\":\"Asia\\/Taipei\"}},{\"Date\":\"1990-01-01\",\"Weight\":\"34\",\"SBP\":\"\",\"DBP\":\"\",\"Mouth\":\"\",\"HeartLung\":\"\",\"LowerLimbEdema\":\"\",\"FundalHeight\":\"\",\"Girth\":\"\",\"FHB\":\"\",\"Cervix\":\"\",\"Electrocardiogram\":\"\",\"FetalHeartCare\":\"\",\"BloodRBC\":\"\",\"BloodHB\":\"\",\"BloodWBC\":\"\",\"BloodWBCCount\":\"\",\"BloodPLT\":\"\",\"UrinePH\":\"\",\"UrineSG\":\"\",\"UrineURO\":\"\",\"UrineBLD\":\"\",\"UrineWBC\":\"\",\"UrinePRO\":\"\",\"UrineGLU\":\"\",\"UrineBIL\":\"\",\"UrineKET\":\"\",\"UrineRBC\":\"\",\"UrineSugarEmpty\":\"\",\"UrineSugarOne\":\"\",\"UrineSugarTwo\":\"\",\"LiverTP\":\"\",\"LiverG\":\"\",\"LiverALB\":\"\",\"LiverAST\":\"\",\"LiverALT\":\"\",\"LiverSTB\":\"\",\"LiverDBIL\":\"\",\"LiverCHE\":\"\",\"KidneyBUN\":\"\",\"KidneySCR\":\"\",\"KidneyB2_MG\":\"\",\"KidneyUrineAcid\":\"33\",\"CreateTime\":{\"date\":\"2019-12-15 22:19:55.000000\",\"timezone_type\":3,\"timezone\":\"Asia\\/Taipei\"}},{\"Date\":\"1990-01-01\",\"Weight\":\"\",\"SBP\":\"\",\"DBP\":\"\",\"Mouth\":\"\",\"HeartLung\":\"\",\"LowerLimbEdema\":\"\",\"FundalHeight\":\"\",\"Girth\":\"\",\"FHB\":\"\",\"Cervix\":\"\",\"Electrocardiogram\":\"\",\"FetalHeartCare\":\"\",\"BloodRBC\":\"\",\"BloodHB\":\"\",\"BloodWBC\":\"\",\"BloodWBCCount\":\"\",\"BloodPLT\":\"\",\"UrinePH\":\"\",\"UrineSG\":\"\",\"UrineURO\":\"\",\"UrineBLD\":\"\",\"UrineWBC\":\"\",\"UrinePRO\":\"\",\"UrineGLU\":\"\",\"UrineBIL\":\"\",\"UrineKET\":\"\",\"UrineRBC\":\"\",\"UrineSugarEmpty\":\"\",\"UrineSugarOne\":\"\",\"UrineSugarTwo\":\"\",\"LiverTP\":\"\",\"LiverG\":\"\",\"LiverALB\":\"\",\"LiverAST\":\"\",\"LiverALT\":\"\",\"LiverSTB\":\"\",\"LiverDBIL\":\"\",\"LiverCHE\":\"\",\"KidneyBUN\":\"\",\"KidneySCR\":\"\",\"KidneyB2_MG\":\"\",\"KidneyUrineAcid\":\"\",\"CreateTime\":{\"date\":\"2019-12-18 18:29:44.000000\",\"timezone_type\":3,\"timezone\":\"Asia\\/Taipei\"}}]";
                    JSONArray jRecords = new JSONArray(response);
                    records=new ArrayList<>();
                    for(int i=0;i<jRecords.length();i++)
                    {
                        JSONObject jEachRecord=jRecords.getJSONObject(i);
                        Record rr=new Record();

                        rr.setDate(jEachRecord.getString("Date"));
                        rr.setWeight(jEachRecord.getString("Weight"));
                        rr.setSBP(jEachRecord.getString("SBP"));
                        rr.setDBP(jEachRecord.getString("DBP"));
                        rr.setMouth(jEachRecord.getString("Mouth"));
                        rr.setHeartLung(jEachRecord.getString("HeartLung"));
                        rr.setLowerLimbEdema(jEachRecord.getString("LowerLimbEdema"));
                        rr.setFundalHeight(jEachRecord.getString("FundalHeight"));
                        rr.setGirth(jEachRecord.getString("Girth"));
                        rr.setFHB(jEachRecord.getString("FHB"));
                        rr.setCervix(jEachRecord.getString("Cervix"));
                        rr.setElectrocardiogram(jEachRecord.getString("Electrocardiogram"));
                        rr.setFetalHeartCare(jEachRecord.getString("FetalHeartCare"));
                        rr.setBloodRBC(jEachRecord.getString("BloodRBC"));
                        rr.setBloodHB(jEachRecord.getString("BloodHB"));
                        rr.setBloodWBC(jEachRecord.getString("BloodWBC"));
                        rr.setBloodWBCCount(jEachRecord.getString("BloodWBCCount"));
                        rr.setBloodPLT(jEachRecord.getString("BloodPLT"));
                        rr.setUrinePH(jEachRecord.getString("UrinePH"));
                        rr.setUrineSG(jEachRecord.getString("UrineSG"));
                        rr.setUrineURO(jEachRecord.getString("UrineURO"));
                        rr.setUrineBLD(jEachRecord.getString("UrineBLD"));
                        rr.setUrineWBC(jEachRecord.getString("UrineWBC"));
                        rr.setUrinePRO(jEachRecord.getString("UrinePRO"));
                        rr.setUrineGLU(jEachRecord.getString("UrineGLU"));
                        rr.setUrineBIL(jEachRecord.getString("UrineBIL"));
                        rr.setUrineKET(jEachRecord.getString("UrineKET"));
                        rr.setUrineRBC(jEachRecord.getString("UrineRBC"));
                        rr.setUrineSugarEmpty(jEachRecord.getString("UrineSugarEmpty"));
                        rr.setUrineSugarOne(jEachRecord.getString("UrineSugarOne"));
                        rr.setUrineSugarTwo(jEachRecord.getString("UrineSugarTwo"));
                        rr.setLiverTP(jEachRecord.getString("LiverTP"));
                        rr.setLiverG(jEachRecord.getString("LiverG"));
                        rr.setLiverALB(jEachRecord.getString("LiverALB"));
                        rr.setLiverAST(jEachRecord.getString("LiverAST"));
                        rr.setLiverALT(jEachRecord.getString("LiverALT"));
                        rr.setLiverSTB(jEachRecord.getString("LiverSTB"));
                        rr.setLiverDBIL(jEachRecord.getString("LiverDBIL"));
                        rr.setLiverCHE(jEachRecord.getString("LiverCHE"));
                        rr.setKidneyBUN(jEachRecord.getString("KidneyBUN"));
                        rr.setKidneySCR(jEachRecord.getString("KidneySCR"));
                        rr.setKidneyB2_MG(jEachRecord.getString("KidneyB2_MG"));
                        rr.setKidneyUrineAcid(jEachRecord.getString("KidneyUrineAcid"));
                        rr.setCreateTime(jEachRecord.getString("CreateTime"));
                        records.add(rr);
                    }
                    GetSchedule();
                } catch (JSONException e) {
                    Log.e("ese",e.getMessage());
                }
           /* }
        });
        RequestQueue q= Volley.newRequestQueue(RecordMain.this);
        q.add(getRecord);*/
    }
    public void GetSchedule(){
        /*
        GetSchedule getSchedule=new GetSchedule(String.valueOf(userID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="[{\"ScheduleID\":1,\"Date\":\"2019-11-02\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\\u6211\\u731c\"},{\"ScheduleID\":2,\"Date\":\"2019-11-29\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"xjxjj\\u554a\\u97a5\\u5662\\u5662\\u8a92\\u8a92\\n\"},{\"ScheduleID\":3,\"Date\":\"2019-12-28\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\\u771f2\"},{\"ScheduleID\":4,\"Date\":\"2020-01-25\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"dd\"},{\"ScheduleID\":5,\"Date\":\"2020-02-22\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"yy\"},{\"ScheduleID\":6,\"Date\":\"2020-03-07\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":7,\"Date\":\"2020-03-21\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":8,\"Date\":\"2020-04-04\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":9,\"Date\":\"2020-04-18\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":10,\"Date\":\"2020-04-25\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":11,\"Date\":\"2020-05-02\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":12,\"Date\":\"2020-05-09\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":13,\"Date\":\"2020-05-16\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":1029,\"Date\":\"2020-05-23\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"},{\"ScheduleID\":1030,\"Date\":\"2020-05-30\",\"Time\":\"00:00\",\"Number\":-1,\"Note\":\"\"}]";
                    JSONArray jEvents = new JSONArray(response);
                    events=new ArrayList<>();
                    for(int i=0;i<jEvents.length();i++)
                    {
                        JSONObject jEachEvent=jEvents.getJSONObject(i);
                        ScheduleEvent se=new ScheduleEvent();
                        se.setTime(jEachEvent.getString("Time"));
                        se.setNumber(jEachEvent.getInt("Number"));
                        se.setNote(jEachEvent.getString("Note"));
                        se.setDate(jEachEvent.getString("Date"));
                        se.setScheduleID(jEachEvent.getInt("ScheduleID"));
                        events.add(se);
                    }


                    setButtonClick();
                } catch (JSONException e) {
                    Log.e("ee",e.getMessage());
                }
          /*  }
        });
        RequestQueue q= Volley.newRequestQueue(RecordMain.this);
        q.add(getSchedule);*/
    }
}
