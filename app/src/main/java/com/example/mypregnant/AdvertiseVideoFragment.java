package com.example.mypregnant;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mypregnant.Function.DownloadImageTask;
import com.example.mypregnant.ObjectClasses.EducationVideo;
import com.example.mypregnant.ObjectClasses.ShopProduct;

import java.util.ArrayList;

public class AdvertiseVideoFragment extends Fragment {

    ArrayList<EducationVideo> videos;
    LinearLayout advertiseVideoLayout;


    public AdvertiseVideoFragment(ArrayList<EducationVideo> videos){
        this.videos=videos;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_advertise_video, container, false);
        advertiseVideoLayout=view.findViewById(R.id.advertiseVideoLayout);



        for(int i=0;i<videos.size();i++) {

            View childView=getLayoutInflater().from(getActivity()).inflate(R.layout.layout_video_v2,advertiseVideoLayout,false);
            ImageView videoImage=childView.findViewById(R.id.layoutVideoImage);
            TextView weekText=childView.findViewById(R.id.layoutVideoWeekText);
            TextView titleText=childView.findViewById(R.id.layoutVideoTitleText);


            Uri uri = Uri.parse(videos.get(i).getLink());
            String v = uri.getQueryParameter("v");
            videoImage.setTag("https://img.youtube.com/vi/"+v+"/0.jpg");
            new DownloadImageTask().execute(videoImage);
            final int finalI = i;
            videoImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videos.get(finalI).getLink()));
                    startActivity(browserIntent);
                }
            });
            titleText.setText(videos.get(i).getContent());
            weekText.setVisibility(View.GONE);
            advertiseVideoLayout.addView(childView);
        }



        return view;
    }

}
