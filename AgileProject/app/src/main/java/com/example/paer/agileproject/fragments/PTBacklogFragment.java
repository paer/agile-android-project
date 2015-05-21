package com.example.paer.agileproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.paer.agileproject.R;

import java.util.ArrayList;

/**
 * Created by Qi on 2015/5/18.
 */
public class PTBacklogFragment extends Fragment {

    ListView backlogList;
    ArrayList<String> backlogItems = new ArrayList<String>();
    ArrayAdapter<String> backlogAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_pivotal_backlog, container, false);

        backlogList = (ListView) view.findViewById(R.id.ptBacklogList);
        backlogAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, backlogItems);
        displayList();
        backlogList.setAdapter(backlogAdapter);
        return view;
    }

    void displayList(){
        Log.d("Debug","Here?");
        backlogItems.add("hello1");
        for(int i = 0; i < PivotalTrackerFragment.storylist.size(); i++)
        {
            backlogItems.add("hello");
            if ( PivotalTrackerFragment.storylist.get(i).getCurrentState().equals("started") )
                backlogItems.add(PivotalTrackerFragment.storylist.get(i).getName());
        }
    }
}
