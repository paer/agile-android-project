package com.example.paer.agileproject.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.paer.agileproject.fragments.PTCurrentFragment;
import com.example.paer.agileproject.fragments.PTBacklogFragment;
import com.example.paer.agileproject.fragments.PTIceboxFragment;

/**
 * Created by Qi on 2015/5/18.
 */
public class PivotalFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Current", "Backlog", "Icebox" };
    private Fragment fragments[] = new Fragment[]{new PTCurrentFragment(), new PTBacklogFragment(), new PTIceboxFragment()};

    public PivotalFragmentPagerAdapter (FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
