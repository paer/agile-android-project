package com.example.paer.agileproject.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.paer.agileproject.R;
import com.example.paer.agileproject.adapters.GithubFragmentPagerAdapter;

/**
 * DESC
 *
 * @author Marc
 * @since 2015-05
 */
public class GithubFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_github, container, false);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.github_viewpager);
        viewPager.setAdapter(new GithubFragmentPagerAdapter(getFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) view.findViewById(R.id.github_tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // How to get the values set by config
        // The "Not found" is a default value if they are not set
        SharedPreferences settings = getActivity().getSharedPreferences("SetupActivity", Context.MODE_PRIVATE);
        Log.e("username", settings.getString("username", "Not found"));
        Log.e("password", settings.getString("password", "Not found"));
        Log.e("project", settings.getString("project", "Not found"));
        Log.e("branches", settings.getString("branches", "Not found"));
    }
}
