package com.example.paer.agileproject.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.astuetz.PagerSlidingTabStrip;
import com.example.paer.agileproject.R;
import com.example.paer.agileproject.adapters.PivotalFragmentPagerAdapter;

/**
 * Created by Qi on 2015/5/17.
 */


public class PTStoryFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_pivotal_tab, container, false);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pivotal_viewpager);
        viewPager.setAdapter(new PivotalFragmentPagerAdapter(getFragmentManager()));
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) view.findViewById(R.id.pivotal_tab);
        tabsStrip.setViewPager(viewPager);
        return view;
    }
}
