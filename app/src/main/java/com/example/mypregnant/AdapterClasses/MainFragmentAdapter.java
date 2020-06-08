package com.example.mypregnant.AdapterClasses;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mypregnant.RegisterFragment;

public class MainFragmentAdapter extends FragmentStatePagerAdapter {

    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //問卷提醒 異常訊息 行程、衛教訊息、衛教影片、醫院資訊、推薦營養品、
        RegisterFragment oneResiger=new RegisterFragment(position);
        Bundle bundle=new Bundle();
        bundle.putInt("position",(position+1));
        oneResiger.setArguments(bundle);
        return oneResiger;
    }
    public int getCount() {
        return 3;
    }
}
