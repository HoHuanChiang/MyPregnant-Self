package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetQuestionnaireDocument;
import com.example.mypregnant.DatabaseClasses.GetQuestionnaireViewAnswers;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.QuestionnaireDocument;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionnaireAnswerView extends AppCompatActivity {
    TextView titleText;
    LinearLayout answersLinearLayout;
    int documentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_answer_view);
        titleText=findViewById(R.id.questionnaireViewTitle);
        answersLinearLayout=findViewById(R.id.questionnaireViewLinearLayout);
        titleText.setText(getIntent().getStringExtra("QuestionnaireName"));
        documentID=getIntent().getIntExtra("DocumentID",0);
        ToolBarFunction.setToolBarInit(this,"問卷");
        /*
        GetQuestionnaireViewAnswers getQuestionnaireViewAnswers=new GetQuestionnaireViewAnswers(String.valueOf(documentID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="[{\"Question\":\"\\u6211\\u80fd\\u770b\\u5230\\u4e8b\\u7269\\u6709\\u8da3\\u7684\\u4e00\\u9762\\uff0c\\u4e26\\u7b11\\u5f97\\u958b\\u5fc3\",\"Answer\":\"\\u540c\\u4ee5\\u524d\\u4e00\\u6a23\"},{\"Question\":\"\\u6211\\u6b23\\u7136\\u671f\\u5f85\\u672a\\u4f86\\u7684\\u4e00\\u5207\",\"Answer\":\"\\u540c\\u4ee5\\u524d\\u4e00\\u6a23\"},{\"Question\":\"\\u7576\\u4e8b\\u60c5\\u51fa\\u932f\\u6642\\uff0c\\u6211\\u6703\\u4e0d\\u5fc5\\u8981\\u5730\\u8cac\\u5099\\u81ea\\u5df1\",\"Answer\":\"\\u5927\\u90e8\\u5206\\u6642\\u5019\\u9019\\u6a23\"},{\"Question\":\"\\u6211\\u7121\\u7de3\\u7121\\u6545\\u611f\\u5230\\u7126\\u616e\\u548c\\u64d4\\u5fc3\",\"Answer\":\"\\u4e00\\u9ede\\u4e5f\\u6c92\\u6709\"},{\"Question\":\"\\u6211\\u7121\\u7de3\\u7121\\u6545\\u611f\\u5230\\u5bb3\\u6015\\u548c\\u9a5a\\u614c\",\"Answer\":\"\\u76f8\\u7576\\u591a\\u6642\\u5019\\u9019\\u6a23\"},{\"Question\":\"\\u5f88\\u591a\\u4e8b\\u60c5\\u885d\\u8457\\u6211\\u800c\\u4f86\\uff0c\\u4f7f\\u6211\\u900f\\u4e0d\\u904e\\u6c23\",\"Answer\":\"\\u5927\\u90e8\\u5206\\u6642\\u5019\\u60a8\\u90fd\\u80fd\\u50cf\\u5e73\\u6642\\u90a3\\u6a23\\u61c9\\u4ed8\\u5f97\\u597d\"},{\"Question\":\"\\u6211\\u5f88\\u4e0d\\u958b\\u5fc3\\uff0c\\u4ee5\\u81f4\\u5931\\u7720\",\"Answer\":\"\\u6709\\u6642\\u5019\\u9019\\u6a23\"},{\"Question\":\"\\u6211\\u611f\\u5230\\u96e3\\u904e\\u548c\\u60b2\\u50b7\",\"Answer\":\"\\u4e0d\\u7d93\\u5e38\\u9019\\u6a23\"},{\"Question\":\"\\u6211\\u4e0d\\u958b\\u5fc3\\u5230\\u54ed\",\"Answer\":\"\\u53ea\\u662f\\u5076\\u723e\\u9019\\u6a23\"},{\"Question\":\"\\u6211\\u60f3\\u904e\\u8981\\u50b7\\u5bb3\\u81ea\\u5df1\",\"Answer\":\"\\u53ea\\u662f\\u5076\\u723e\\u9019\\u6a23\"}]";
                    JSONArray jAnswers = new JSONArray(response);
                    for(int i=0;i<jAnswers.length();i++)
                    {
                        JSONObject jeachAnswer=jAnswers.getJSONObject(i);
                        View eachView=View.inflate(QuestionnaireAnswerView.this,R.layout.layout_questionnaire_answer_view,null);
                        TextView questionText=eachView.findViewById(R.id.questionnaireViewLayoutQuestion);
                        TextView answerText=eachView.findViewById(R.id.questionnaireViewLayoutAnswer);
                        questionText.setText(jeachAnswer.getString("Question"));
                        answerText.setText(jeachAnswer.getString("Answer"));
                        answersLinearLayout.addView(eachView);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
/*
            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getQuestionnaireViewAnswers);*/
    }
}
