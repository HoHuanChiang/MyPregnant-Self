package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.mypregnant.DatabaseClasses.GetVideos;
import com.example.mypregnant.Function.DownloadImageTask;
import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.EducationVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoTotalV2 extends AppCompatActivity {
    LinearLayout videoCurrentWeekLayout,videoBeforeLayout,videoPregnantLayout,videoBirthLayout,videoAfterLayout;
    ArrayList<EducationVideo> videoList;
    ArrayList<EducationVideo> weekVideo;
    LayoutInflater inflater;
    View pareintView;
    TextView videoWeekTextView;
    int pregnantWeek;
    DialogLoading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_total_v2);
        ToolBarFunction.setToolBarInit(this,"衛教影片");
        loading=new DialogLoading(this);
        inflater = LayoutInflater.from(this);
        pregnantWeek=getSharedPreferences("data",0).getInt("PregnantWeek",0);
        pareintView=inflater.inflate(R.layout.activity_video_total_v2,null);
        videoCurrentWeekLayout=findViewById(R.id.videoCurrentWeekLayout);
        videoBeforeLayout=findViewById(R.id.videoBeforeLayout);
        videoPregnantLayout=findViewById(R.id.videoPregnantLayout);
        videoBirthLayout=findViewById(R.id.videoBirthLayout);
        videoAfterLayout=findViewById(R.id.videoAfterLayout);
        videoWeekTextView=findViewById(R.id.currentWeekText);
        videoWeekTextView.setText(String.valueOf(pregnantWeek));

        loading.show();
        GetVideos();




    }
    public void GetVideos()
    {
        GetVideos getVideos=new GetVideos(new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                videoList=new ArrayList<>();
                weekVideo=new ArrayList<>();
                try {
                    //給Listview
                    JSONArray jVideos=new JSONArray(response);
                    for(int i=0;i<jVideos.length();i++)
                    {
                        try {
                            JSONObject jvideo=jVideos.getJSONObject(i);
                            EducationVideo ev=new EducationVideo();
                            ev.setVideoID(jvideo.getInt("VideoID"));
                            ev.setCategory(jvideo.getString("Category"));
                            ev.setContent(jvideo.getString("Content"));
                            ev.setPregnantWeek(jvideo.getInt("PregnantWeek"));
                            ev.setLink(jvideo.getString("Link"));
                            videoList.add(ev);

                            if(jvideo.getInt("PregnantWeek")==pregnantWeek)
                            {
                                weekVideo.add(ev);
                            }

                        } catch (JSONException e) {
                            Toast.makeText(VideoTotalV2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                    SetWeekVideo();
                    SetCategoryVideo();
                    loading.dismiss();
                } catch (JSONException e) {
                    Toast.makeText(VideoTotalV2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getVideos);
    }
    public void SetCategoryVideo()
    {
        for(int i=0;i<videoList.size();i++)
        {
            View view=inflater.inflate(R.layout.layout_video_v2,(ViewGroup) pareintView,false);
            view=setViewData(view,videoList.get(i));
            String category=videoList.get(i).getCategory();
            switch(category){
                case "产前保健":
                    videoBeforeLayout.addView(view);
                    break;
                case "孕期营养":
                    videoPregnantLayout.addView(view);
                    break;
                case "分娩知识":
                    videoBirthLayout.addView(view);
                    break;
                case "产后护理":
                    videoAfterLayout.addView(view);
                    break;
            }
        }
    }
    public void SetWeekVideo()
    {
        for(int i=0;i<weekVideo.size();i+=2)
        {
            LinearLayout outterLayout=new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            outterLayout.setOrientation(LinearLayout.HORIZONTAL);
            outterLayout.setLayoutParams(params);

            View view=inflater.inflate(R.layout.layout_video_v2,(ViewGroup) pareintView,false);
            /******把寬度設為等比*************/
            LinearLayout innerLayout=view.findViewById(R.id.layoutVideoOutterLayout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.weight=1;
            innerLayout.setLayoutParams(lp);

            view=setViewData(view,weekVideo.get(i));

            outterLayout.addView(view);

            view=inflater.inflate(R.layout.layout_video_v2,(ViewGroup) pareintView,false);
            /******把寬度設為等比*************/
            innerLayout=view.findViewById(R.id.layoutVideoOutterLayout);
            lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.weight=1;
            innerLayout.setLayoutParams(lp);

            if(i+1<weekVideo.size())
            {
                view=setViewData(view,weekVideo.get(i+1));
            }
            else
            {
                innerLayout.setVisibility(View.INVISIBLE);
            }



            outterLayout.addView(view);
            videoCurrentWeekLayout.addView(outterLayout);
        }
    }
    public View setViewData(View view, final EducationVideo ev){
        TextView titleText=view.findViewById(R.id.layoutVideoTitleText);
        TextView weekText=view.findViewById(R.id.layoutVideoWeekText);
        titleText.setText(ev.getContent());
        weekText.setText("第 "+ev.getPregnantWeek()+" 週");

        ImageView videoImage=view.findViewById(R.id.layoutVideoImage);
        Uri uri = Uri.parse(ev.getLink());
        String v = uri.getQueryParameter("v");
        videoImage.setTag("https://img.youtube.com/vi/"+v+"/0.jpg");
        new DownloadImageTask().execute(videoImage);

        videoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ev.getLink()));
                startActivity(browserIntent);
            }
        });
        return view;
    }
}
