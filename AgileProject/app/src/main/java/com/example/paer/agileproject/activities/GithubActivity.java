package com.example.paer.agileproject.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import com.astuetz.PagerSlidingTabStrip;
import com.example.paer.agileproject.R;
import com.example.paer.agileproject.adapters.GithubFragmentPagerAdapter;

/**
 * DESC
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.github_viewpager);
        viewPager.setAdapter(new GithubFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.github_tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }
}
