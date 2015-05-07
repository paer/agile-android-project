package com.example.paer.agileproject.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.paer.agileproject.R;

/**
 * DESC
 *
 * @author Marc
 * @since 2015-05
 */
public class IssuesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_github_issues, container, false);
    }
}
