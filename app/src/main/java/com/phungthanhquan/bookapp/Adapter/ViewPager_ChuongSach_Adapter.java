package com.phungthanhquan.bookapp.Adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ViewPager_ChuongSach_Adapter extends FragmentStatePagerAdapter {

    private  List<Fragment> mFragmentList ;
    private  List<String> mFragmentTitleList;

    public ViewPager_ChuongSach_Adapter(FragmentManager fm, List<Fragment> mFragmentList, List<String> mFragmentTitleList) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.mFragmentTitleList = mFragmentTitleList;
    }


    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
