package com.example.mypregnant.AdapterClasses;

import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mypregnant.AdvertiseAbnormalFragment;
import com.example.mypregnant.AdvertiseEducationFragment;
import com.example.mypregnant.AdvertiseHosptialInfoFragment;
import com.example.mypregnant.AdvertiseQuestionnaireFragment;
import com.example.mypregnant.AdvertiseScheduleFragment;
import com.example.mypregnant.AdvertiseShopFragment;
import com.example.mypregnant.AdvertiseVideoFragment;

import java.util.ArrayList;

public class AdvertiseMainAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> allFragment = new SparseArray<>();
    ArrayList<Fragment> totalFragment;
    public AdvertiseMainAdapter(FragmentManager fm,ArrayList<Fragment> totalFragment) {
        super(fm);
        this.totalFragment=totalFragment;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment ff;
        if(position<totalFragment.size()){
            return totalFragment.get(position);
        }
        else{
            return null;
        }
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        allFragment.put(position, fragment);
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        allFragment.remove(position);
        super.destroyItem(container, position, object);
    }
    @Override
    public int getCount() {
        return totalFragment.size();
    }
}
