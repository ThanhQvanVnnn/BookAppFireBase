package com.phungthanhquan.bookapp.Adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
