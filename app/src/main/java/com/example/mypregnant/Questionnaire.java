package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetQuestionnaireQuestions;
import com.example.mypregnant.DatabaseClasses.InsertQuestionnaireAnswers;
import com.example.mypregnant.Function.RepositoryFucntion;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.QuestionnaireQuestion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Questionnaire extends AppCompatActivity{
    int questionnaireID;
    int documentID;
    Button backBtn,doneBtn;
    //ListView questionnaireListView;
    LinearLayout questionnaireLayout;
    TextView questionnaireNameTextView;
    ArrayList<QuestionnaireQuestion> questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        questionnaireID=getIntent().getIntExtra("QuestionnaireID",0);
        documentID=getIntent().getIntExtra("DocumentID",0);
        doneBtn=findViewById(R.id.questionnaireDoneBtn);
        questionnaireLayout=findViewById(R.id.questionnaireLayout);

        ToolBarFunction.setToolBarInit(this,"問卷",getIntent().getStringExtra("QuestionnaireName"));


        try {
            getData();
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //database
/*
        GetQuestionnaireQuestions getQuestionnaireQuestions=new GetQuestionnaireQuestions(String.valueOf(questionnaireID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="[{\"QuestionID\":1,\"Question\":\"\\u6211\\u80fd\\u770b\\u5230\\u4e8b\\u7269\\u6709\\u8da3\\u7684\\u4e00\\u9762\\uff0c\\u4e26\\u7b11\\u5f97\\u958b\\u5fc3\",\"Options\":\"0-\\u540c\\u4ee5\\u524d\\u4e00\\u6a23;1-\\u6c92\\u6709\\u4ee5\\u524d\\u90a3\\u9ebc\\u591a;2-\\u80af\\u5b9a\\u6bd4\\u4ee5\\u524d\\u5c11;3-\\u5b8c\\u5168\\u4e0d\\u80fd\"},{\"QuestionID\":2,\"Question\":\"\\u6211\\u6b23\\u7136\\u671f\\u5f85\\u672a\\u4f86\\u7684\\u4e00\\u5207\",\"Options\":\"0-\\u540c\\u4ee5\\u524d\\u4e00\\u6a23;1-\\u6c92\\u6709\\u4ee5\\u524d\\u90a3\\u9ebc\\u591a;2-\\u80af\\u5b9a\\u6bd4\\u4ee5\\u524d\\u5c11;3-\\u5b8c\\u5168\\u4e0d\\u80fd\"},{\"QuestionID\":3,\"Question\":\"\\u7576\\u4e8b\\u60c5\\u51fa\\u932f\\u6642\\uff0c\\u6211\\u6703\\u4e0d\\u5fc5\\u8981\\u5730\\u8cac\\u5099\\u81ea\\u5df1\",\"Options\":\"3-\\u5927\\u90e8\\u5206\\u6642\\u5019\\u9019\\u6a23;2-\\u6709\\u6642\\u5019\\u9019\\u6a23;1-\\u4e0d\\u7d93\\u5e38\\u9019\\u6a23;0-\\u6c92\\u6709\\u9019\\u6a23\"},{\"QuestionID\":4,\"Question\":\"\\u6211\\u7121\\u7de3\\u7121\\u6545\\u611f\\u5230\\u7126\\u616e\\u548c\\u64d4\\u5fc3\",\"Options\":\"0-\\u4e00\\u9ede\\u4e5f\\u6c92\\u6709;1-\\u6975\\u5c11\\u6709;2-\\u6709\\u6642\\u5019\\u9019\\u6a23;3-\\u7d93\\u5e38\\u9019\\u6a23\"},{\"QuestionID\":6,\"Question\":\"\\u6211\\u7121\\u7de3\\u7121\\u6545\\u611f\\u5230\\u5bb3\\u6015\\u548c\\u9a5a\\u614c\",\"Options\":\"3-\\u76f8\\u7576\\u591a\\u6642\\u5019\\u9019\\u6a23;2-\\u6709\\u6642\\u5019\\u9019\\u6a23;1-\\u4e0d\\u7d93\\u5e38\\u9019\\u6a23;0-\\u4e00\\u9ede\\u4e5f\\u6c92\\u6709\"},{\"QuestionID\":7,\"Question\":\"\\u5f88\\u591a\\u4e8b\\u60c5\\u885d\\u8457\\u6211\\u800c\\u4f86\\uff0c\\u4f7f\\u6211\\u900f\\u4e0d\\u904e\\u6c23\",\"Options\":\"3-\\u5927\\u591a\\u6578\\u6642\\u5019\\u60a8\\u90fd\\u4e0d\\u80fd\\u61c9\\u4ed8;2-\\u6709\\u6642\\u5019\\u60a8\\u4e0d\\u80fd\\u50cf\\u5e73\\u6642\\u90a3\\u6a23\\u61c9\\u4ed8\\u5f97\\u597d;1-\\u5927\\u90e8\\u5206\\u6642\\u5019\\u60a8\\u90fd\\u80fd\\u50cf\\u5e73\\u6642\\u90a3\\u6a23\\u61c9\\u4ed8\\u5f97\\u597d;0-\\u60a8\\u4e00\\u76f4\\u90fd\\u80fd\\u61c9\\u4ed8\\u5f97\\u597d\"},{\"QuestionID\":8,\"Question\":\"\\u6211\\u5f88\\u4e0d\\u958b\\u5fc3\\uff0c\\u4ee5\\u81f4\\u5931\\u7720\",\"Options\":\"3-\\u5927\\u90e8\\u5206\\u6642\\u5019\\u9019\\u6a23;2-\\u6709\\u6642\\u5019\\u9019\\u6a23;1-\\u4e0d\\u7d93\\u5e38\\u9019\\u6a23;0-\\u4e00\\u9ede\\u4e5f\\u6c92\\u6709\"},{\"QuestionID\":9,\"Question\":\"\\u6211\\u611f\\u5230\\u96e3\\u904e\\u548c\\u60b2\\u50b7\",\"Options\":\"3-\\u5927\\u90e8\\u5206\\u6642\\u5019\\u9019\\u6a23;2-\\u6709\\u6642\\u5019\\u9019\\u6a23;1-\\u4e0d\\u7d93\\u5e38\\u9019\\u6a23;0-\\u4e00\\u9ede\\u4e5f\\u6c92\\u6709\"},{\"QuestionID\":10,\"Question\":\"\\u6211\\u4e0d\\u958b\\u5fc3\\u5230\\u54ed\",\"Options\":\"3-\\u5927\\u90e8\\u5206\\u6642\\u5019\\u9019\\u6a23;2-\\u6709\\u6642\\u5019\\u9019\\u6a23;1-\\u53ea\\u662f\\u5076\\u723e\\u9019\\u6a23;0-\\u6c92\\u6709\\u9019\\u6a23\"},{\"QuestionID\":11,\"Question\":\"\\u6211\\u60f3\\u904e\\u8981\\u50b7\\u5bb3\\u81ea\\u5df1\",\"Options\":\"3-\\u5927\\u90e8\\u5206\\u6642\\u5019\\u9019\\u6a23;2-\\u6709\\u6642\\u5019\\u9019\\u6a23;1-\\u53ea\\u662f\\u5076\\u723e\\u9019\\u6a23;0-\\u6c92\\u6709\\u9019\\u6a23\"}]";
                    JSONArray jQuestions=new JSONArray(response);
                    questions=new ArrayList<>();
                    for(int i=0;i<jQuestions.length();i++)
                    {
                        JSONObject jQuestion=jQuestions.getJSONObject(i);
                        QuestionnaireQuestion eachQuestion=new QuestionnaireQuestion();
                        eachQuestion.setQuestionID(jQuestion.getInt("QuestionID"));
                        eachQuestion.setQuestion(jQuestion.getString("Question"));
                        eachQuestion.setOptions(jQuestion.getString("Options"));
                        questions.add(eachQuestion);
                    }
                    addQuestions();

                } catch (JSONException e) {
                    Toast.makeText(Questionnaire.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
           /* }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getQuestionnaireQuestions);*/
    }
    public void getData()  throws JSONException{
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray jAnswers=new JSONArray();

                for(int i=0;i<questionnaireLayout.getChildCount();i++)
                {
                    RadioGroup rg=(questionnaireLayout.getChildAt(i).findViewById(R.id.questionnaireOptionsRadioGroup));
                    int checkedID=rg.getCheckedRadioButtonId();
                    if(checkedID==-1)
                    {
                        Toast.makeText(Questionnaire.this, "未填寫完成", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        RadioButton selectBtn=((RadioButton)questionnaireLayout.getChildAt(i).findViewById(checkedID));


                        try {
                            JSONObject jAnswer=new JSONObject();
                            jAnswer.put("Answer",selectBtn.getText().toString());
                            jAnswer.put("DocumentID",documentID);
                            jAnswer.put("QuestionID",Integer.valueOf(rg.getTag().toString()));
                            jAnswers.put(jAnswer);
                        } catch (Exception e) {
                            Log.e("error",e.getMessage());
                        }
                    }
                }
                //database
                /*
                InsertQuestionnaireAnswers insertQuestionnaireAnswers=new InsertQuestionnaireAnswers(jAnswers.toString(), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent=new Intent();
                        intent.setClass(Questionnaire.this,QuestionnaireMain.class);
                        startActivity(intent);
                        finish();
                    }
                });

                RequestQueue q=Volley.newRequestQueue(Questionnaire.this);
                q.add(insertQuestionnaireAnswers);
                */

                Intent intent=new Intent();
                intent.setClass(Questionnaire.this,QuestionnaireMain.class);
                startActivity(intent);
                finish();

            }
        });
    }
    public void addQuestions()
    {
        for(int i=0;i<questions.size();i++)
        {
            View view=View.inflate(Questionnaire.this,R.layout.layout_questionnaire,null);
            TextView questionName=view.findViewById(R.id.questionnaireQuestionTextView);
            RadioGroup optionsLayout=view.findViewById(R.id.questionnaireOptionsRadioGroup);
            optionsLayout.setTag(questions.get(i).getQuestionID());
            questionName.setText(questions.get(i).getQuestion());

            /******加入選項********/
            String optionsString=questions.get(i).getOptions();
            String []eachOptions=optionsString.split(";");
            for(int optionCount=0;optionCount<eachOptions.length;optionCount++)
            {
                RadioButton rb=new RadioButton(this);
                rb.setText(eachOptions[optionCount].split("-")[1]);
                rb.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.general_size));
                rb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                optionsLayout.addView(rb);
            }

            questionnaireLayout.addView(view);
        }
    }
}
