package com.example.paer.agileproject.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.example.paer.agileproject.fragments.CommitsFragment;
import com.example.paer.agileproject.fragments.IssuesFragment;
import com.example.paer.agileproject.fragments.ProjectInfoFragment;

/**
 * DESC
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Proj Info", "Commits", "Issues" };
    private Fragment fragments[] = new Fragment[]{new ProjectInfoFragment(), new CommitsFragment(), new IssuesFragment()};

    public GithubFragmentPagerAdapter(FragmentManager fm) {
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
