package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetDetailsRecord;
import com.example.mypregnant.DatabaseClasses.UpdatePhysical;
import com.example.mypregnant.Function.ToolBarFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PhysicalEdit extends AppCompatActivity {
    String category="";
    String weekStartDate;
    int userID;
    LinearLayout listLayout;
    ImageButton okBtn;
    boolean needTime,needSession;
    int sessionArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_edit);
        weekStartDate=getSharedPreferences("data",0).getString("WeekStartDate","1990-01-01");
        userID=getSharedPreferences("data",0).getInt("user",0);
        category=getIntent().getStringExtra("Category");
        needTime=getIntent().getBooleanExtra("NeedTime",false);
        needSession=getIntent().getBooleanExtra("NeedSession",false);
        sessionArray=getIntent().getIntExtra("SessionArray",0);
        listLayout=findViewById(R.id.physicalEditListLayout);
        okBtn=findViewById(R.id.physicalEditOKBtn);
        ToolBarFunction.setToolBarInit(this,"更改生理數值");
        updateData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        GetDetailsRecord getDetailsRecord=new GetDetailsRecord(String.valueOf(userID), weekStartDate,category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    listLayout.removeAllViews();
                    JSONArray jValues=new JSONArray(response);
                    for(int i=0;i<jValues.length();i++){
                        JSONObject jValue=jValues.getJSONObject(i);
                        View outerLayer=getLayoutInflater().inflate(R.layout.layout_physical_edit,null);
                        TextView dateText=outerLayer.findViewById(R.id.physicalEditDate);
                        dateText.setText(jValue.getString("date"));
                        LinearLayout detailsLayout=outerLayer.findViewById(R.id.physicalEditDetailsLayout);
                        ImageButton addBtn=outerLayer.findViewById(R.id.physicalEditAddBtn);

                        addBtn.setTag(jValue.getString("date"));
                        addBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogPhysialAdd dialogBuilder = new DialogPhysialAdd(PhysicalEdit.this,category,needTime,needSession,sessionArray,view.getTag().toString());

                                dialogBuilder.show();
                                dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialogInterface) {
                                        Log.e("insie","nsda");
                                        getData();
                                    }
                                });
                            }
                        });

                        //把細項目加進去
                        JSONArray jdetails=jValue.getJSONArray("details");
                        for(int j=0;j<jdetails.length();j++)
                        {
                            JSONObject eachDetail=jdetails.getJSONObject(j);
                            View innerLayer=getLayoutInflater().from(PhysicalEdit.this).inflate(R.layout.layout_physical_edit_details,detailsLayout,false);
                            TextView timeText=innerLayer.findViewById(R.id.physicalEditDetailsTime);
                            LinearLayout singleLayout=innerLayer.findViewById(R.id.physicalEditSingleLayout);
                            TextView itemText=innerLayer.findViewById(R.id.physicalEditSingleDetailsItem);
                            EditText valueText=innerLayer.findViewById(R.id.physicalEditSingleDetailsValue);
                            LinearLayout doubleLayout=innerLayer.findViewById(R.id.physicalEditDoubleLayout);
                            TextView itemText1=innerLayer.findViewById(R.id.physicalEditDoubleDetailsItem1);
                            TextView itemText2=innerLayer.findViewById(R.id.physicalEditDoubleDetailsItem2);
                            EditText valueText1=innerLayer.findViewById(R.id.physicalEditDoubleDetailsValue1);
                            EditText valueText2=innerLayer.findViewById(R.id.physicalEditDoubleDetailsValue2);

                            if(jValue.getString("type").equals("single"))
                            {
                                itemText.setText(eachDetail.getString("Item"));
                                valueText.setText(eachDetail.getString("Value"));
                                valueText.setTag(eachDetail.getString("Id"));
                                timeText.setText(eachDetail.getString("Time"));
                                singleLayout.setVisibility(View.VISIBLE);
                                doubleLayout.setVisibility(View.GONE);
                            }
                            else if(jValue.getString("type").equals("double"))
                            {
                                itemText1.setText(eachDetail.getString("Item1"));
                                itemText2.setText(eachDetail.getString("Item2"));
                                valueText1.setText(eachDetail.getString("Value1"));
                                valueText2.setText(eachDetail.getString("Value2"));
                                valueText1.setTag(eachDetail.getString("Id"));
                                valueText2.setTag(eachDetail.getString("Id"));
                                timeText.setText(eachDetail.getString("Time"));
                                singleLayout.setVisibility(View.GONE);
                                doubleLayout.setVisibility(View.VISIBLE);
                            }
                            if(eachDetail.getString("Time").equals("00:00"))
                            {
                                timeText.setVisibility(View.GONE);
                            }
                            detailsLayout.addView(innerLayer);
                        }
                        listLayout.addView(outerLayer);

                    }






                } catch (JSONException e) {
                    Toast.makeText(PhysicalEdit.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        RequestQueue q= Volley.newRequestQueue(PhysicalEdit.this);
        q.add(getDetailsRecord);


    }

    public void updateData(){
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray jchangeArray=new JSONArray();
                for(int i=0;i<listLayout.getChildCount();i++){
                    try {

                        View outerChild=listLayout.getChildAt(i);
                        LinearLayout innerChildLayout=outerChild.findViewById(R.id.physicalEditDetailsLayout);
                        for(int j=0;j<innerChildLayout.getChildCount();j++)
                        {
                            JSONObject jeach=new JSONObject();
                            View innerChild=innerChildLayout.getChildAt(j);
                            if(innerChild.findViewById(R.id.physicalEditSingleLayout).getVisibility()==View.VISIBLE)
                            {
                                jeach.put("id",((TextView)innerChild.findViewById(R.id.physicalEditSingleDetailsValue)).getTag().toString());
                                jeach.put("value",((TextView)innerChild.findViewById(R.id.physicalEditSingleDetailsValue)).getText().toString());
                            }
                            else if(innerChild.findViewById(R.id.physicalEditSingleLayout).getVisibility()==View.GONE)
                            {
                                jeach.put("id",((TextView)innerChild.findViewById(R.id.physicalEditDoubleDetailsValue1)).getTag().toString());
                                jeach.put("value1",((TextView)innerChild.findViewById(R.id.physicalEditDoubleDetailsValue1)).getText().toString());
                                jeach.put("value2",((TextView)innerChild.findViewById(R.id.physicalEditDoubleDetailsValue2)).getText().toString());
                            }

                            jchangeArray.put(jeach);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("sdvsdv",jchangeArray.toString());
                UpdatePhysical updatePhysical=new UpdatePhysical(jchangeArray.toString(),category, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PhysicalEdit.this, "修改完成", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                RequestQueue q= Volley.newRequestQueue(PhysicalEdit.this);
                q.add(updatePhysical);
            }
        });

    }

}
