package com.allinone.smartocity.Business.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.allinone.smartocity.Business.fragment.PendingOrderFragment;
import com.allinone.smartocity.Business.fragment.CompleteOrderFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                PendingOrderFragment tab1 = new PendingOrderFragment();
                return tab1;
            case 1:
                CompleteOrderFragment tab2 = new CompleteOrderFragment();
                return tab2;
           
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}