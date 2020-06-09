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
    {/*
        GetVideos getVideos=new GetVideos(new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {*/
                videoList=new ArrayList<>();
                weekVideo=new ArrayList<>();
                try {
                    String response="[{\"VideoID\":8,\"Category\":\"\\u4ea7\\u524d\\u4fdd\\u5065\",\"Content\":\"\\u6392\\u5375\\u8bd5\\u7eb8\\u4f60\\u7528\\u5bf9\\u4e86\\u5417\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=AAZ4l0SsJL4\",\"PregnantWeek\":1},{\"VideoID\":27,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"Content\":\"\\u6211\\u8981\\u751f\\u4e86\\u55ce?\\u8a8d\\u8b58\\u751f\\u7522\\u524d\\u5146\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=hDOu1ehQZzQ\",\"PregnantWeek\":1},{\"VideoID\":7,\"Category\":\"\\u4ea7\\u524d\\u4fdd\\u5065\",\"Content\":\"\\u6000\\u5b55\\u7684\\u5f81\\u5146\\u6709\\u54ea\\u4e9b\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=isSgdszQeQw\",\"PregnantWeek\":2},{\"VideoID\":9,\"Category\":\"\\u4ea7\\u524d\\u4fdd\\u5065\",\"Content\":\"\\u6000\\u5b55\\u521d\\u671f\\u6ce8\\u610f\\u4e8b\\u9879\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=ilktAZcnCJs\",\"PregnantWeek\":3},{\"VideoID\":6,\"Category\":\"\\u4ea7\\u524d\\u4fdd\\u5065\",\"Content\":\"\\u5b55\\u671f\\u51fa\\u8840\\u7b49\\u4e8e\\u6d41\\u4ea7?\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=O3eLbJOl5Go\",\"PregnantWeek\":4},{\"VideoID\":11,\"Category\":\"\\u4ea7\\u524d\\u4fdd\\u5065\",\"Content\":\"\\u6000\\u5b55\\u4f53\\u91cd\\u589e\\u957f\\u591a\\u5c11\\u624d\\u5408\\u7406?\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=GhswYtGgAQ8&list=PL_EF5jNgnyDlOEvKda6PF7BOpDva1nOVV&index=5\",\"PregnantWeek\":4},{\"VideoID\":22,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u6000\\u5b55\\u671f\\u95f4\\u600e\\u6837\\u5408\\u7406\\u996e\\u98df\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=A0POQwzHkM8\",\"PregnantWeek\":4},{\"VideoID\":18,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u53f6\\u9178\\u5230\\u5e95\\u600e\\u4e48\\u8865\\u624d\\u6700\\u9760\\u8c31?\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=hs0G7aHpFNA\",\"PregnantWeek\":5},{\"VideoID\":20,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u5b55\\u671f\\u6c34\\u679c\\u63a8\\u85a6\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=eg-S5p6bdz4\",\"PregnantWeek\":5},{\"VideoID\":19,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u5b55\\u671f\\u71df\\u990a\\u600e\\u9ebc\\u5403\\uff1f\\u61f7\\u5b55\\u521d\\u3001\\u4e2d\\u3001\\u5f8c\\u9019\\u6a23\\u5403\\u5bf6\\u5bf6\\u5065\\u5eb7\\u9577\\u5927\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=iYnNeHtA81s\",\"PregnantWeek\":6},{\"VideoID\":1,\"Category\":\"\\u4ea7\\u524d\\u4fdd\\u5065\",\"Content\":\"\\u7761\\u4e0d\\u9192\\u7684\\u5b55\\u5988\\u4eec \\u5982\\u4f55\\u5e94\\u5bf9\\u5b55\\u671f\\u55dc\\u7761\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=SDEDCHFC1pw\",\"PregnantWeek\":7},{\"VideoID\":24,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u5b55\\u671f\\u8a72\\u88dc\\u5145\\u7684\\u71df\\u990a\\u54c1\\u7433\\u746f\\u6eff\\u76ee,\\u8a72\\u5982\\u4f55\\u9078\\u64c7\\u5462?\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=3CaFbWs_8j8\",\"PregnantWeek\":8},{\"VideoID\":16,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u5b55\\u671f\\u71df\\u990a\\u600e\\u9ebc\\u5403\\uff0c\\u624d\\u80fd\\u8b93\\u5bf6\\u5bf6\\u5065\\u5eb7\\u9577\\u5927\\uff1f\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=6XifM0F_kP4\",\"PregnantWeek\":9},{\"VideoID\":14,\"Category\":\"\\u4ea7\\u524d\\u4fdd\\u5065\",\"Content\":\"\\u5b55\\u671f\\u80c3\\u90e8\\u4e0d\\u9002\\u5982\\u4f55\\u6218\\u80dc\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=BndB0ORAJPQ&list=PL_EF5jNgnyDlOEvKda6PF7BOpDva1nOVV&index=19\",\"PregnantWeek\":10},{\"VideoID\":21,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u5b55\\u671f\\u996e\\u98df\\u7981\\u5fcc\\u975e\\u5c0f\\u4e8b! \",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=r_FsotJyp0U\",\"PregnantWeek\":12},{\"VideoID\":17,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u5b55\\u671f\\u5982\\u4f55\\u8865\\u5145DHA\\uff1f\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=LfbXtM_R1zk\",\"PregnantWeek\":13},{\"VideoID\":10,\"Category\":\"\\u4ea7\\u524d\\u4fdd\\u5065\",\"Content\":\"3\\u5206\\u949f\\u770b\\u61c2\\u5b55\\u65e9\\u671fB\\u8d85\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=CS0TxeBRFUg\",\"PregnantWeek\":13},{\"VideoID\":13,\"Category\":\"\\u4ea7\\u524d\\u4fdd\\u5065\",\"Content\":\"\\u600e\\u6837\\u7f13\\u89e3\\u5b55\\u671f\\u62bd\\u7b4b\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=nieN-6pQrts&list=PL_EF5jNgnyDlOEvKda6PF7BOpDva1nOVV&index=11\",\"PregnantWeek\":14},{\"VideoID\":15,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u62ef\\u6551\\u5b55\\u5988\\u4fbf\\u79d8\\u5927\\u4f5c\\u6218\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=sz-uZfPc6Wg&list=PL_EF5jNgnyDlOEvKda6PF7BOpDva1nOVV&index=8\",\"PregnantWeek\":15},{\"VideoID\":23,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u751f\\u4e2a\\u5065\\u5eb7\\u5b9d\\u5b9d,\\u5b55\\u671f\\u8865\\u9499\\u4e0d\\u53ef\\u5c11\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=dURmH1g1GaI&list=PL_EF5jNgnyDnQz80Phh7oh9XgRCGnEquZ&index=110\",\"PregnantWeek\":17},{\"VideoID\":12,\"Category\":\"\\u4ea7\\u524d\\u4fdd\\u5065\",\"Content\":\"\\u51c6\\u5988\\u5988\\u5982\\u4f55\\u5e94\\u5bf9\\u6625\\u590f\\u79cb\\u51ac \\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=GhswYtGgAQ8&list=PL_EF5jNgnyDlOEvKda6PF7BOpDva1nOVV&index=5\",\"PregnantWeek\":18},{\"VideoID\":32,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"Content\":\"\\u751f\\u5b69\\u5b50\\u8981\\u987a\\u4ea7\\u8fd8\\u662f\\u5256\\u8179\\u4ea7 \\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=6yY_Eh7qFxs\",\"PregnantWeek\":21},{\"VideoID\":33,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"Content\":\"\\u5b9d\\u5b9d\\u662f\\u600e\\u6837\\u51fa\\u751f\\u7684 \\u76f4\\u51fb\\u5206\\u5a29\\u5168\\u8fc7\\u7a0b\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=u88e4_tgpGc\",\"PregnantWeek\":21},{\"VideoID\":31,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"Content\":\"\\u51c6\\u5988\\u505a\\u597d\\u4ea7\\u524d\\u51cf\\u538b,\\u7f13\\u89e3\\u5206\\u5a29\\u6050\\u60e7\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=rvTxVoyJzi4\",\"PregnantWeek\":22},{\"VideoID\":34,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"Content\":\"\\u5b55\\u5a66\\u745c\\u73c8\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=Mfs3DDlC4r0\",\"PregnantWeek\":24},{\"VideoID\":28,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"Content\":\"\\u4ec0\\u4e48\\u662f\\u5206\\u5a29\\u9547\\u75db\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=CzF2tf00xOc\",\"PregnantWeek\":25},{\"VideoID\":41,\"Category\":\"\\u4ea7\\u540e\\u62a4\\u7406\",\"Content\":\"\\u4e00\\u5b55\\u771f\\u7684\\u50bb\\u4e09\\u5e74\\u5417\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=4Yp0l4hVpfE&list=PL_EF5jNgnyDlOEvKda6PF7BOpDva1nOVV&index=34\",\"PregnantWeek\":26},{\"VideoID\":25,\"Category\":\"\\u5b55\\u671f\\u8425\\u517b\",\"Content\":\"\\u7121\\u75db\\u5206\\u5a29\\u6253\\u4e0d\\u6253?\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=v2XSzixcVck\",\"PregnantWeek\":27},{\"VideoID\":26,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"Content\":\"\\u62c9\\u6885\\u8332\\u547c\\u5438\\u6cd5\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=Du_Hi43MMfI\",\"PregnantWeek\":28},{\"VideoID\":29,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"Content\":\"\\u5206\\u5a29\\u4f53\\u64cd\\u505a\\u8d77\\u6765 \\u987a\\u4ea7so easy \\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=9pKvDde5OZc\",\"PregnantWeek\":29},{\"VideoID\":30,\"Category\":\"\\u5206\\u5a29\\u77e5\\u8bc6\",\"Content\":\"\\u5047\\u6027\\u5bab\\u7f29\\u662f\\u4ec0\\u4e48\\u611f\\u89c9- \\u5982\\u4f55\\u5e94\\u5bf9\\u5462\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=9jMwVNNfy64\",\"PregnantWeek\":29},{\"VideoID\":37,\"Category\":\"\\u4ea7\\u540e\\u62a4\\u7406\",\"Content\":\"\\u65b0\\u624b\\u5988\\u54aa,\\u54fa\\u4e73\\u5185\\u8863\\u4f60\\u9009\\u5bf9\\u4e86\\u5417 \\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=asjjrVDgtbk&list=PL_EF5jNgnyDnQz80Phh7oh9XgRCGnEquZ&index=152\",\"PregnantWeek\":30},{\"VideoID\":40,\"Category\":\"\\u4ea7\\u540e\\u62a4\\u7406\",\"Content\":\"\\u5b9d\\u5b9d\\u8981\\u5403\\u591a\\u5c11\\u5976\\u6211\\u7684\\u5976\\u591f\\u5403\\u5417\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=_72bdUjyw5g&list=PL_EF5jNgnyDlOEvKda6PF7BOpDva1nOVV&index=29\",\"PregnantWeek\":32},{\"VideoID\":43,\"Category\":\"\\u4ea7\\u540e\\u62a4\\u7406\",\"Content\":\"\\u4ea7\\u540e\\u6291\\u90c1\\u4e86\\u600e\\u4e48\\u529e\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=URiy1yHC6fE\",\"PregnantWeek\":34},{\"VideoID\":38,\"Category\":\"\\u4ea7\\u540e\\u62a4\\u7406\",\"Content\":\"\\u7238\\u7238\\u4e5f\\u4f1a\\u5f97\\u4ea7\\u540e\\u6291\\u90c1\\u5417\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=55mI3bZRAsk&list=PL_EF5jNgnyDnQz80Phh7oh9XgRCGnEquZ&index=111\",\"PregnantWeek\":35},{\"VideoID\":36,\"Category\":\"\\u4ea7\\u540e\\u62a4\\u7406\",\"Content\":\"\\u6708\\u5b50\\u671f\\u600e\\u4e48\\u5403\\u50ac\\u4e73\\u4e0b\\u5976\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=V575cKnU_dk&list=PL_EF5jNgnyDnQz80Phh7oh9XgRCGnEquZ&index=150\",\"PregnantWeek\":35},{\"VideoID\":39,\"Category\":\"\\u4ea7\\u540e\\u62a4\\u7406\",\"Content\":\"\\u5b9d\\u5b9d\\u8981\\u5403\\u591a\\u5c11\\u5976\\u6211\\u7684\\u5976\\u591f\\u5403\\u5417\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=FX0ci3qiXKw&list=PL_EF5jNgnyDnQz80Phh7oh9XgRCGnEquZ&index=92\",\"PregnantWeek\":36},{\"VideoID\":42,\"Category\":\"\\u4ea7\\u540e\\u62a4\\u7406\",\"Content\":\"\\u5750\\u6708\\u5b50\\u90a3\\u4e9b\\u4e0d\\u5f97\\u4e0d\\u77e5\\u7684\\u771f\\u76f8\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=3R4CIqe2i3Y&list=PL_EF5jNgnyDlOEvKda6PF7BOpDva1nOVV&index=89\",\"PregnantWeek\":36},{\"VideoID\":35,\"Category\":\"\\u4ea7\\u540e\\u62a4\\u7406\",\"Content\":\"\\u6708\\u5b50\\u671f\\u8865\\u5145\\u8425\\u517b,\\u65b0\\u5988\\u5988\\u4eec\\u8865\\u5bf9\\u4e86\\u5417 \\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=6SJfIu35MeQ&list=PL_EF5jNgnyDnQz80Phh7oh9XgRCGnEquZ&index=149\",\"PregnantWeek\":37},{\"VideoID\":44,\"Category\":\"\\u4ea7\\u540e\\u62a4\\u7406\",\"Content\":\"\\u7522\\u5f8c\\u5bae\\u7e2e\\u66f4\\u75db\\u82e6\\uff01\\u60e1\\u9732\\u6392\\u4e0d\\u76e1\\uff01\\u7522\\u5f8c\\u5b50\\u5bae\\u6062\\u5fa9\\u6709\\u5999\\u62db\\uff01\\r\\n\",\"Link\":\"https:\\/\\/www.youtube.com\\/watch?v=RooioVXtijs\",\"PregnantWeek\":38}]";
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

          /*  }
        });
        RequestQueue q= Volley.newRequestQueue(this);
        q.add(getVideos);*/
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
        //videoImage.setTag("https://img.youtube.com/vi/"+v+"/0.jpg");
        videoImage.setImageDrawable(getDrawable(R.drawable.photo0));
        //new DownloadImageTask().execute(videoImage);

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
