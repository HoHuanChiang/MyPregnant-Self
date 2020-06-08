package com.example.mypregnant.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mypregnant.ObjectClasses.EducationVideo;
import com.example.mypregnant.ObjectClasses.QuestionnaireDocument;
import com.example.mypregnant.R;

import java.util.ArrayList;

public class QuestionnaireMainAdapter extends BaseAdapter {
    Context context;
    ArrayList<QuestionnaireDocument> questionnaireMainList;
    public QuestionnaireMainAdapter(Context context, ArrayList<QuestionnaireDocument> questionnaire)
    {
        this.context=context;
        this.questionnaireMainList=questionnaire;
    }
    @Override
    public int getCount() {
        return questionnaireMainList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        AdapterHolder holder=null;
        int status=questionnaireMainList.get(position).getStatus();
        String statusString="";
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.layout_document_main,viewGroup,false);
            holder=new AdapterHolder();
            holder.QuestionnaireName=view.findViewById(R.id.questionnaireMainListName);
            holder.QuestionnaireImage=view.findViewById(R.id.questionnaireMainCheckImage);
            //holder.QuestionnaireStatus=view.findViewById(R.id.documentMainListStatus);
            view.setTag(holder);
        }
        else{
            holder=(AdapterHolder) view.getTag();
        }
        holder.QuestionnaireName.setText(questionnaireMainList.get(position).getQuestionnaireName());
        if(status==1)
        {
            statusString="未填寫";
            //holder.QuestionnaireStatus.setTextColor(Color.RED);
            holder.QuestionnaireImage.setImageResource(R.drawable.unlightheart);
        }
        else if(status==0)
        {
            statusString="完成";
            //holder.QuestionnaireStatus.setTextColor(Color.GREEN);
            holder.QuestionnaireImage.setImageResource(R.drawable.lightheart);
        }
        //holder.QuestionnaireStatus.setText(statusString);
        return view;
    }
    class AdapterHolder
    {
        TextView QuestionnaireName;
        TextView QuestionnaireStatus;
        ImageView QuestionnaireImage;
    }
}
