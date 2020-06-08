package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.AdapterClasses.EducationAdapter;
import com.example.mypregnant.DatabaseClasses.GetEducationList;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.MedicalEducation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EducationList extends AppCompatActivity {
    ListView educationListView;
    ArrayList<MedicalEducation> educationList;
    TextView subContentTextView,titleNameTextView;
    String category="";
    String titleName;
    String subContent;
    int parentID=0;
    ArrayList<MedicalEducation> siftEducation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_list);
        ToolBarFunction.setToolBarInit(this,"衛教資訊");
        educationListView=findViewById(R.id.educationAllListView);
        subContentTextView=findViewById(R.id.educationListSubContent);
        titleNameTextView=findViewById(R.id.educationListName);
        educationList=(ArrayList<MedicalEducation>) getIntent().getExtras().getSerializable("AllEducationList");
        category=getIntent().getStringExtra("Category");
        parentID=getIntent().getIntExtra("ParentID",0);
        titleName=getIntent().getStringExtra("TitleName");
        subContent=getIntent().getStringExtra("SubContent");
        siftEducation=new ArrayList<>();
        Log.e("ssss",educationList.size()+"");
        if(parentID!=0)
        {
            titleNameTextView.setText(titleName);
            subContentTextView.setText(subContent);
        }


        for(int i=0;i<educationList.size();i++)
        {
            if(educationList.get(i).getParentID()==parentID)
            {
                siftEducation.add(educationList.get(i));
            }
        }




        educationListView.setAdapter(new EducationAdapter(EducationList.this,siftEducation));

        educationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(siftEducation.get(i).getContent()==""||siftEducation.get(i).getContent()==null)
                {
                    Intent intent=new Intent();
                    intent.setClass(EducationList.this,EducationList.class);
                    Bundle b=new Bundle();
                    b.putSerializable("AllEducationList",educationList);
                    intent.putExtras(b);
                    intent.putExtra("Category",category);
                    intent.putExtra("ParentID",siftEducation.get(i).getEducationID());
                    intent.putExtra("TitleName",siftEducation.get(i).getEducationName());
                    intent.putExtra("SubContent",siftEducation.get(i).getSubContent());
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent();
                    intent.setClass(EducationList.this,EducationDetail.class);
                    Bundle b=new Bundle();
                    b.putSerializable("EducationNode",siftEducation.get(i));
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });
    }

}
