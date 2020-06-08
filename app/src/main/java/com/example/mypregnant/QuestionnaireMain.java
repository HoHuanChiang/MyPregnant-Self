package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.AdapterClasses.QuestionnaireMainAdapter;
import com.example.mypregnant.DatabaseClasses.GetQuestionnaireDocument;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.QuestionnaireDocument;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionnaireMain extends AppCompatActivity {
    TextView questionnaireMainWeek;
    ListView questionnaireMainListView;
    int userID;
    ArrayList<QuestionnaireDocument> documentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_main);
        ToolBarFunction.setToolBarInit(this,"問卷");
        userID=getSharedPreferences("data",0).getInt("user",0);
        questionnaireMainWeek=findViewById(R.id.currentWeekText);
        questionnaireMainListView=findViewById(R.id.questionnaireMainListView);
        questionnaireMainWeek.setText(getSharedPreferences("data",0).getInt("PregnantWeek",0)+"");
        GetQuestionnaireDocument getQuestionnaireDocument=new GetQuestionnaireDocument(String.valueOf(userID),
                String.valueOf(getSharedPreferences("data",0).getInt("PregnantWeek",0)), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                documentList=new ArrayList<>();
                try {
                    JSONArray jDocument=new JSONArray(response);
                    for(int i=0;i<jDocument.length();i++)
                    {
                        JSONObject jDoc=jDocument.getJSONObject(i);
                        QuestionnaireDocument qd=new QuestionnaireDocument();
                        qd.setDocumentID(jDoc.getInt("DocumentID"));
                        qd.setQuestionnaireID(jDoc.getInt("QuestionnaireID"));
                        qd.setQuestionnaireName(jDoc.getString("QuestionnaireName"));
                        qd.setStatus(jDoc.getInt("Status"));
                        documentList.add(qd);
                    }
                    questionnaireMainListView.setAdapter(new QuestionnaireMainAdapter(QuestionnaireMain.this,documentList));
                    questionnaireMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if(documentList.get(i).getStatus()==1)
                            {
                                Intent intent=new Intent();
                                intent.setClass(QuestionnaireMain.this,Questionnaire.class);
                                intent.putExtra("QuestionnaireID",documentList.get(i).getQuestionnaireID());
                                intent.putExtra("DocumentID",documentList.get(i).getDocumentID());
                                intent.putExtra("QuestionnaireName",documentList.get(i).getQuestionnaireName());
                                startActivity(intent);
                                finish();
                            }
                            else if(documentList.get(i).getStatus()==0)
                            {
                                Intent intent=new Intent();
                                intent.setClass(QuestionnaireMain.this,QuestionnaireAnswerView.class);
                                intent.putExtra("DocumentID",documentList.get(i).getDocumentID());
                                intent.putExtra("QuestionnaireName",documentList.get(i).getQuestionnaireName());
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(QuestionnaireMain.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getQuestionnaireDocument);

    }
}
