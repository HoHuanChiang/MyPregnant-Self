package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.EducationVideo;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class PhysicalMain extends AppCompatActivity {
    TabLayout tab;
    ViewPager viewPager;
    String pages[]={"血壓","血糖","BMI","心跳","胎動","胎心音","活動量"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_main);
        //ToolBarFunction.setToolBarInit(this,"生理數值","本周(4w)趨勢圖");
        ToolBarFunction.setToolBarInit(this,"生理數值");
        tab=(TabLayout)findViewById(R.id.physicalMainTabLayout);
        viewPager=(ViewPager)findViewById(R.id.physicalMainViewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position)
            {
                if(position==0)
                {
                    return new PhysicalLineChartFragment("pressure");
                }
                else if(position==1)
                {
                    return new PhysicalLineChartFragment("sugar");
                }
                else if(position==2)
                {
                    return new PhysicalLineChartFragment("bmi");
                }
                else if(position==3)
                {
                    return new PhysicalLineChartFragment("motherHB");
                }
                else if(position==4)
                {
                    return new PhysicalLineChartFragment("fetalMovement");
                }
                else if(position==5)
                {
                    return new PhysicalLineChartFragment("fetalHB");
                }
                else if(position==6)
                {
                    return new PhysicalStepFragment();
                }
                else
                {
                    return new PhysicalStepFragment();
                }

            }

            @Override
            public int getCount() {
                return pages.length;
            }
        });
        tab.setupWithViewPager(viewPager);
        for(int i=0;i<pages.length;i++)
        {
            tab.getTabAt(i).setText(pages[i]);
        }
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
    public void setAdapter(final String response){


    }
}
