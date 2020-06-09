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

        //database
        /*
        GetDetailsRecord getDetailsRecord=new GetDetailsRecord(String.valueOf(userID), weekStartDate,category, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {*/
                try {
                    String response="[{\"date\":\"2019-11-30\",\"details\":[{\"Value1\":120,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":60,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"10:19\",\"Id\":\"20052\"}],\"type\":\"double\"},{\"date\":\"2019-12-01\",\"details\":[{\"Value1\":122,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":61,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"10:19\",\"Id\":\"20053\"}],\"type\":\"double\"},{\"date\":\"2019-12-02\",\"details\":[{\"Value1\":119,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":56,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"10:19\",\"Id\":\"20054\"}],\"type\":\"double\"},{\"date\":\"2019-12-03\",\"details\":[{\"Value1\":150,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":150,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:46\",\"Id\":\"30055\"},{\"Value1\":150,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":150,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:46\",\"Id\":\"30056\"},{\"Value1\":150,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":150,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:47\",\"Id\":\"30057\"},{\"Value1\":150,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":150,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:49\",\"Id\":\"30058\"},{\"Value1\":150,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":150,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:50\",\"Id\":\"30059\"},{\"Value1\":150,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":150,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:53\",\"Id\":\"30060\"},{\"Value1\":150,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":150,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:55\",\"Id\":\"30061\"},{\"Value1\":150,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":150,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:56\",\"Id\":\"30062\"}],\"type\":\"double\"},{\"date\":\"2019-12-04\",\"details\":[],\"type\":\"double\"},{\"date\":\"2019-12-05\",\"details\":[],\"type\":\"double\"},{\"date\":\"2019-12-06\",\"details\":[],\"type\":\"double\"}]";
                    if(category.equals("pressure"))
                    {
                        response="[{\"date\":\"2019-11-30\",\"details\":[{\"Value1\":120,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":60,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"10:19\",\"Id\":\"20052\"}],\"type\":\"double\"},{\"date\":\"2019-12-01\",\"details\":[{\"Value1\":122,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":61,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"10:19\",\"Id\":\"20053\"}],\"type\":\"double\"},{\"date\":\"2019-12-02\",\"details\":[{\"Value1\":119,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":56,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"10:19\",\"Id\":\"20054\"}],\"type\":\"double\"},{\"date\":\"2019-12-03\",\"details\":[{\"Value1\":120,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":60,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:46\",\"Id\":\"30055\"},{\"Value1\":121,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":60,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:46\",\"Id\":\"30056\"},{\"Value1\":120,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":60,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:47\",\"Id\":\"30057\"},{\"Value1\":120,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":60,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:49\",\"Id\":\"30058\"},{\"Value1\":120,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":60,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:50\",\"Id\":\"30059\"},{\"Value1\":120,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":60,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:53\",\"Id\":\"30060\"},{\"Value1\":120,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":60,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:55\",\"Id\":\"30061\"},{\"Value1\":125,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":60,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"02:56\",\"Id\":\"30062\"}],\"type\":\"double\"},{\"date\":\"2019-12-04\",\"details\":[{\"Value1\":125,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":66,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"23:45\",\"Id\":\"30063\"}],\"type\":\"double\"},{\"date\":\"2019-12-05\",\"details\":[{\"Value1\":129,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":69,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"23:46\",\"Id\":\"30064\"}],\"type\":\"double\"},{\"date\":\"2019-12-06\",\"details\":[{\"Value1\":128,\"Item1\":\"\\u6536\\u7e2e\\u58d3\",\"Value2\":70,\"Item2\":\"\\u8212\\u5f35\\u58d3\",\"Time\":\"23:46\",\"Id\":\"30065\"}],\"type\":\"double\"}]";
                    }
                    else if(category.equals("sugar"))
                    {
                        response="[{\"date\":\"2019-11-30\",\"details\":[{\"Item\":\"\\u98ef\\u524d\",\"Value\":45,\"Time\":\"02:51\",\"Id\":\"20037\"},{\"Item\":\"\\u98ef\\u524d\",\"Value\":46,\"Time\":\"11:39\",\"Id\":\"10024\"},{\"Item\":\"\\u98ef\\u5f8c\",\"Value\":88,\"Time\":\"11:39\",\"Id\":\"10031\"},{\"Item\":\"\\u98ef\\u524d\",\"Value\":50,\"Time\":\"17:51\",\"Id\":\"10038\"},{\"Item\":\"\\u98ef\\u5f8c\",\"Value\":89,\"Time\":\"17:52\",\"Id\":\"10039\"},{\"Item\":\"\\u98ef\\u5f8c\",\"Value\":95,\"Time\":\"18:23\",\"Id\":\"10040\"},{\"Item\":\"\\u98ef\\u5f8c\",\"Value\":80,\"Time\":\"18:24\",\"Id\":\"10041\"}],\"type\":\"single\"},{\"date\":\"2019-12-01\",\"details\":[{\"Item\":\"\\u98ef\\u524d\",\"Value\":55,\"Time\":\"11:39\",\"Id\":\"10025\"},{\"Item\":\"\\u98ef\\u5f8c\",\"Value\":97,\"Time\":\"11:39\",\"Id\":\"10032\"}],\"type\":\"single\"},{\"date\":\"2019-12-02\",\"details\":[{\"Item\":\"\\u98ef\\u524d\",\"Value\":42,\"Time\":\"11:39\",\"Id\":\"10026\"},{\"Item\":\"\\u98ef\\u5f8c\",\"Value\":82,\"Time\":\"11:39\",\"Id\":\"10033\"}],\"type\":\"single\"},{\"date\":\"2019-12-03\",\"details\":[{\"Item\":\"\\u98ef\\u524d\",\"Value\":50,\"Time\":\"11:39\",\"Id\":\"10027\"},{\"Item\":\"\\u98ef\\u524d\",\"Value\":56,\"Time\":\"11:40\",\"Id\":\"10035\"}],\"type\":\"single\"},{\"date\":\"2019-12-04\",\"details\":[{\"Item\":\"\\u98ef\\u524d\",\"Value\":53,\"Time\":\"11:39\",\"Id\":\"10028\"},{\"Item\":\"\\u98ef\\u5f8c\",\"Value\":95,\"Time\":\"11:39\",\"Id\":\"10034\"}],\"type\":\"single\"},{\"date\":\"2019-12-05\",\"details\":[{\"Item\":\"\\u98ef\\u524d\",\"Value\":58,\"Time\":\"11:39\",\"Id\":\"10029\"},{\"Item\":\"\\u98ef\\u5f8c\",\"Value\":96,\"Time\":\"11:40\",\"Id\":\"10036\"}],\"type\":\"single\"},{\"date\":\"2019-12-06\",\"details\":[{\"Item\":\"\\u98ef\\u5f8c\",\"Value\":88,\"Time\":\"18:27\",\"Id\":\"10042\"}],\"type\":\"single\"}]";
                    }
                    else if(category.equals("bmi"))
                    {
                        response="[{\"date\":\"2019-11-30\",\"details\":[{\"Item\":\"\\u9ad4\\u91cd\",\"Value\":60,\"Time\":\"00:00\",\"Id\":\"34\"},{\"Item\":\"\\u9ad4\\u91cd\",\"Value\":65,\"Time\":\"00:00\",\"Id\":\"36\"}],\"type\":\"single\"},{\"date\":\"2019-12-01\",\"details\":[{\"Item\":\"\\u9ad4\\u91cd\",\"Value\":65,\"Time\":\"00:00\",\"Id\":\"10034\"}],\"type\":\"single\"},{\"date\":\"2019-12-02\",\"details\":[{\"Item\":\"\\u9ad4\\u91cd\",\"Value\":68,\"Time\":\"00:00\",\"Id\":\"10035\"}],\"type\":\"single\"},{\"date\":\"2019-12-03\",\"details\":[{\"Item\":\"\\u9ad4\\u91cd\",\"Value\":67,\"Time\":\"00:00\",\"Id\":\"10036\"}],\"type\":\"single\"},{\"date\":\"2019-12-04\",\"details\":[{\"Item\":\"\\u9ad4\\u91cd\",\"Value\":68,\"Time\":\"00:00\",\"Id\":\"10037\"}],\"type\":\"single\"},{\"date\":\"2019-12-05\",\"details\":[{\"Item\":\"\\u9ad4\\u91cd\",\"Value\":68.5,\"Time\":\"00:00\",\"Id\":\"10038\"}],\"type\":\"single\"},{\"date\":\"2019-12-06\",\"details\":[{\"Item\":\"\\u9ad4\\u91cd\",\"Value\":70,\"Time\":\"00:00\",\"Id\":\"10039\"}],\"type\":\"single\"}]";
                    }
                    else if(category.equals("motherHB"))
                    {
                        response="[{\"date\":\"2019-11-30\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":60,\"Time\":\"00:00\",\"Id\":\"10017\"},{\"Item\":\"\\u4e2d\",\"Value\":55,\"Time\":\"00:00\",\"Id\":\"20018\"},{\"Item\":\"\\u665a\",\"Value\":67,\"Time\":\"00:00\",\"Id\":\"20019\"}],\"type\":\"single\"},{\"date\":\"2019-12-01\",\"details\":[{\"Item\":\"\\u665a\",\"Value\":51,\"Time\":\"00:00\",\"Id\":\"20017\"},{\"Item\":\"\\u65e9\",\"Value\":54,\"Time\":\"00:00\",\"Id\":\"20020\"},{\"Item\":\"\\u4e2d\",\"Value\":58,\"Time\":\"00:00\",\"Id\":\"20021\"}],\"type\":\"single\"},{\"date\":\"2019-12-02\",\"details\":[{\"Item\":\"\\u4e2d\",\"Value\":59,\"Time\":\"00:00\",\"Id\":\"20022\"},{\"Item\":\"\\u65e9\",\"Value\":54,\"Time\":\"00:00\",\"Id\":\"20023\"},{\"Item\":\"\\u665a\",\"Value\":55,\"Time\":\"00:00\",\"Id\":\"20024\"}],\"type\":\"single\"},{\"date\":\"2019-12-03\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":60,\"Time\":\"00:00\",\"Id\":\"20025\"},{\"Item\":\"\\u4e2d\",\"Value\":68,\"Time\":\"00:00\",\"Id\":\"20026\"},{\"Item\":\"\\u665a\",\"Value\":67,\"Time\":\"00:00\",\"Id\":\"20027\"}],\"type\":\"single\"},{\"date\":\"2019-12-04\",\"details\":[{\"Item\":\"\\u665a\",\"Value\":71,\"Time\":\"00:00\",\"Id\":\"20028\"},{\"Item\":\"\\u4e2d\",\"Value\":62,\"Time\":\"00:00\",\"Id\":\"20029\"},{\"Item\":\"\\u65e9\",\"Value\":66,\"Time\":\"00:00\",\"Id\":\"20030\"}],\"type\":\"single\"},{\"date\":\"2019-12-05\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":64,\"Time\":\"00:00\",\"Id\":\"20031\"},{\"Item\":\"\\u4e2d\",\"Value\":62,\"Time\":\"00:00\",\"Id\":\"20032\"},{\"Item\":\"\\u665a\",\"Value\":55,\"Time\":\"00:00\",\"Id\":\"20033\"}],\"type\":\"single\"},{\"date\":\"2019-12-06\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":58,\"Time\":\"00:00\",\"Id\":\"20034\"},{\"Item\":\"\\u4e2d\",\"Value\":69,\"Time\":\"00:00\",\"Id\":\"20035\"},{\"Item\":\"\\u665a\",\"Value\":70,\"Time\":\"00:00\",\"Id\":\"20036\"}],\"type\":\"single\"}]";
                    }
                    else if(category.equals("fetalMovement"))
                    {
                        response="[{\"date\":\"2019-11-30\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":7,\"Time\":\"00:00\",\"Id\":\"10034\"},{\"Item\":\"\\u4e2d\",\"Value\":6,\"Time\":\"00:00\",\"Id\":\"20034\"},{\"Item\":\"\\u665a\",\"Value\":5,\"Time\":\"00:00\",\"Id\":\"20037\"}],\"type\":\"single\"},{\"date\":\"2019-12-01\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":2,\"Time\":\"00:00\",\"Id\":\"20038\"},{\"Item\":\"\\u4e2d\",\"Value\":4,\"Time\":\"00:00\",\"Id\":\"20039\"},{\"Item\":\"\\u665a\",\"Value\":6,\"Time\":\"00:00\",\"Id\":\"20040\"}],\"type\":\"single\"},{\"date\":\"2019-12-02\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":5,\"Time\":\"00:00\",\"Id\":\"20041\"},{\"Item\":\"\\u4e2d\",\"Value\":6,\"Time\":\"00:00\",\"Id\":\"20042\"},{\"Item\":\"\\u665a\",\"Value\":5,\"Time\":\"00:00\",\"Id\":\"20043\"}],\"type\":\"single\"},{\"date\":\"2019-12-03\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":6,\"Time\":\"00:00\",\"Id\":\"20044\"},{\"Item\":\"\\u4e2d\",\"Value\":3,\"Time\":\"00:00\",\"Id\":\"20045\"},{\"Item\":\"\\u665a\",\"Value\":5,\"Time\":\"00:00\",\"Id\":\"20046\"}],\"type\":\"single\"},{\"date\":\"2019-12-04\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":2,\"Time\":\"00:00\",\"Id\":\"20047\"},{\"Item\":\"\\u4e2d\",\"Value\":1,\"Time\":\"00:00\",\"Id\":\"20048\"},{\"Item\":\"\\u665a\",\"Value\":6,\"Time\":\"00:00\",\"Id\":\"20049\"}],\"type\":\"single\"},{\"date\":\"2019-12-05\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":4,\"Time\":\"00:00\",\"Id\":\"20050\"},{\"Item\":\"\\u4e2d\",\"Value\":8,\"Time\":\"00:00\",\"Id\":\"20051\"},{\"Item\":\"\\u665a\",\"Value\":4,\"Time\":\"00:00\",\"Id\":\"20052\"}],\"type\":\"single\"},{\"date\":\"2019-12-06\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":5,\"Time\":\"00:00\",\"Id\":\"20053\"},{\"Item\":\"\\u4e2d\",\"Value\":4,\"Time\":\"00:00\",\"Id\":\"20054\"},{\"Item\":\"\\u665a\",\"Value\":6,\"Time\":\"00:00\",\"Id\":\"20055\"}],\"type\":\"single\"}]";
                    }
                    else if(category.equals("fetalHB"))
                    {
                        response="[{\"date\":\"2019-11-30\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":40,\"Time\":\"00:00\",\"Id\":\"10033\"},{\"Item\":\"\\u65e9\",\"Value\":45,\"Time\":\"00:00\",\"Id\":\"10034\"},{\"Item\":\"\\u4e2d\",\"Value\":45,\"Time\":\"00:00\",\"Id\":\"10035\"},{\"Item\":\"\\u665a\",\"Value\":42,\"Time\":\"00:00\",\"Id\":\"10036\"}],\"type\":\"single\"},{\"date\":\"2019-12-01\",\"details\":[{\"Item\":\"\\u665a\",\"Value\":20,\"Time\":\"00:00\",\"Id\":\"10032\"},{\"Item\":\"\\u4e2d\",\"Value\":55,\"Time\":\"00:00\",\"Id\":\"10037\"},{\"Item\":\"\\u65e9\",\"Value\":51,\"Time\":\"00:00\",\"Id\":\"10038\"}],\"type\":\"single\"},{\"date\":\"2019-12-02\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":53,\"Time\":\"00:00\",\"Id\":\"10039\"},{\"Item\":\"\\u4e2d\",\"Value\":57,\"Time\":\"00:00\",\"Id\":\"10040\"},{\"Item\":\"\\u665a\",\"Value\":66,\"Time\":\"00:00\",\"Id\":\"10041\"}],\"type\":\"single\"},{\"date\":\"2019-12-03\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":62,\"Time\":\"00:00\",\"Id\":\"10042\"},{\"Item\":\"\\u65e9\",\"Value\":67,\"Time\":\"00:00\",\"Id\":\"10043\"},{\"Item\":\"\\u665a\",\"Value\":69,\"Time\":\"00:00\",\"Id\":\"10044\"},{\"Item\":\"\\u4e2d\",\"Value\":51,\"Time\":\"00:00\",\"Id\":\"10045\"}],\"type\":\"single\"},{\"date\":\"2019-12-04\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":66,\"Time\":\"00:00\",\"Id\":\"10046\"},{\"Item\":\"\\u665a\",\"Value\":63,\"Time\":\"00:00\",\"Id\":\"10047\"},{\"Item\":\"\\u665a\",\"Value\":59,\"Time\":\"00:00\",\"Id\":\"10048\"},{\"Item\":\"\\u4e2d\",\"Value\":67,\"Time\":\"00:00\",\"Id\":\"10049\"}],\"type\":\"single\"},{\"date\":\"2019-12-05\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":56,\"Time\":\"00:00\",\"Id\":\"10050\"},{\"Item\":\"\\u4e2d\",\"Value\":59,\"Time\":\"00:00\",\"Id\":\"10051\"}],\"type\":\"single\"},{\"date\":\"2019-12-06\",\"details\":[{\"Item\":\"\\u65e9\",\"Value\":77,\"Time\":\"00:00\",\"Id\":\"10052\"},{\"Item\":\"\\u665a\",\"Value\":73,\"Time\":\"00:00\",\"Id\":\"10053\"}],\"type\":\"single\"}]";
                    }
                    else if(category.equals("step"))
                    {
                        response="[{\"date\":\"2019-11-30\",\"details\":[{\"Item\":\"\\u6b65\\u6578\",\"Value\":500,\"Time\":\"00:00\",\"Id\":\"10012\"}],\"type\":\"single\"},{\"date\":\"2019-12-01\",\"details\":[{\"Item\":\"\\u6b65\\u6578\",\"Value\":666,\"Time\":\"00:00\",\"Id\":\"20012\"}],\"type\":\"single\"},{\"date\":\"2019-12-02\",\"details\":[{\"Item\":\"\\u6b65\\u6578\",\"Value\":600,\"Time\":\"00:00\",\"Id\":\"20013\"}],\"type\":\"single\"},{\"date\":\"2019-12-03\",\"details\":[{\"Item\":\"\\u6b65\\u6578\",\"Value\":1200,\"Time\":\"00:00\",\"Id\":\"20014\"}],\"type\":\"single\"},{\"date\":\"2019-12-04\",\"details\":[{\"Item\":\"\\u6b65\\u6578\",\"Value\":900,\"Time\":\"00:00\",\"Id\":\"20015\"}],\"type\":\"single\"},{\"date\":\"2019-12-05\",\"details\":[{\"Item\":\"\\u6b65\\u6578\",\"Value\":450,\"Time\":\"00:00\",\"Id\":\"20016\"}],\"type\":\"single\"},{\"date\":\"2019-12-06\",\"details\":[{\"Item\":\"\\u6b65\\u6578\",\"Value\":800,\"Time\":\"00:00\",\"Id\":\"20017\"}],\"type\":\"single\"}]";
                    }
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
/*
            }
        });
        RequestQueue q= Volley.newRequestQueue(PhysicalEdit.this);
        q.add(getDetailsRecord);*/


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
                //database
                /*
                UpdatePhysical updatePhysical=new UpdatePhysical(jchangeArray.toString(),category, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(PhysicalEdit.this, "修改完成", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                RequestQueue q= Volley.newRequestQueue(PhysicalEdit.this);
                q.add(updatePhysical);*/
                finish();
            }
        });

    }

}
