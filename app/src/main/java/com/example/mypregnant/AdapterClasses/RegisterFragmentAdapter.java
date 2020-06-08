package com.example.mypregnant.AdapterClasses;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mypregnant.RegisterFragment;

public class RegisterFragmentAdapter extends FragmentStatePagerAdapter {

    SparseArray<Fragment> allFragment = new SparseArray<>();
    public RegisterFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        RegisterFragment oneResiger=new RegisterFragment(position);
        Bundle bundle=new Bundle();
        bundle.putInt("position",(position+1));
        oneResiger.setArguments(bundle);
        return oneResiger;
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
    public Fragment getRegisteredFragment(int position) {
        return allFragment.get(position);
    }
    @Override
    public int getCount() {
        return 6;
    }
}
