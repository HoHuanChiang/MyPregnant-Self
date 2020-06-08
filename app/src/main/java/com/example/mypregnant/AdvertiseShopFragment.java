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
import com.example.mypregnant.ObjectClasses.MedicalEducation;
import com.example.mypregnant.ObjectClasses.ShopProduct;

import java.util.ArrayList;

public class AdvertiseShopFragment extends Fragment {

    ArrayList<ShopProduct> products;
    LinearLayout advertiseShopLayout;

    public AdvertiseShopFragment(ArrayList<ShopProduct> products){
        this.products=products;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_advertise_shop, container, false);
        advertiseShopLayout=view.findViewById(R.id.advertiseShopLayout);




        for(int i=0;i<products.size();i++) {

            View childView=getLayoutInflater().from(getActivity()).inflate(R.layout.layout_video_v2,advertiseShopLayout,false);
            ImageView videoImage=childView.findViewById(R.id.layoutVideoImage);
            TextView weekText=childView.findViewById(R.id.layoutVideoWeekText);
            TextView titleText=childView.findViewById(R.id.layoutVideoTitleText);



            videoImage.setTag("http://163.25.101.128/pregnant/shop/"+products.get(i).getProductID()+".jpg");
            new DownloadImageTask().execute(videoImage);
            final int finalI = i;
            videoImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(products.get(finalI).getLink()));
                    startActivity(browserIntent);
                }
            });
            titleText.setText(products.get(i).getProductName());
            weekText.setVisibility(View.GONE);
            advertiseShopLayout.addView(childView);
        }




        return view;
    }

}
