package com.example.mypregnant.AdapterClasses;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mypregnant.ObjectClasses.EducationVideo;
import com.example.mypregnant.ObjectClasses.MedicalEducation;
import com.example.mypregnant.R;

import java.util.ArrayList;

public class EducationAdapter extends BaseAdapter {

    Context context;
    ArrayList<MedicalEducation> educations;
    public EducationAdapter(Context context, ArrayList<MedicalEducation> educations)
    {
        this.context=context;
        this.educations=educations;
    }
    @Override
    public int getCount() {
        return educations.size();
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
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.layout_education,viewGroup,false);
            holder=new AdapterHolder();
            holder.Title=view.findViewById(R.id.educationEachTitle);
            holder.SubContent=view.findViewById(R.id.educationEachSubContent);
            holder.EducationLayout=view.findViewById(R.id.educationEachLayout);
            view.setTag(holder);
        }
        else{
            holder=(AdapterHolder) view.getTag();
        }
        holder.Title.setText(educations.get(position).getEducationName());
        holder.SubContent.setText(educations.get(position).getSubContent());

        return view;
    }
    class AdapterHolder
    {
        TextView Title;
        TextView SubContent;
        LinearLayout EducationLayout;
    }
}
