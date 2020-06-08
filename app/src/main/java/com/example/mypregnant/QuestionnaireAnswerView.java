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
        GetQuestionnaireViewAnswers getQuestionnaireViewAnswers=new GetQuestionnaireViewAnswers(String.valueOf(documentID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
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

            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getQuestionnaireViewAnswers);
    }
}
