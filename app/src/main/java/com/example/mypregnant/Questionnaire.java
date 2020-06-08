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


        GetQuestionnaireQuestions getQuestionnaireQuestions=new GetQuestionnaireQuestions(String.valueOf(questionnaireID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
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
            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getQuestionnaireQuestions);
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
