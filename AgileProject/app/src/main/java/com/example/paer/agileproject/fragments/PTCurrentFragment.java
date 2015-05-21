package com.example.paer.agileproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class PTCurrentFragment extends Fragment {

    ListView currentList;
    ArrayList<String> currentItems = new ArrayList<String>();
    ArrayAdapter<String> currentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_pivotal_current, container, false);

        currentList = (ListView) view.findViewById(R.id.ptCorrentList);
        currentAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, currentItems);
        displayList();
        currentList.setAdapter(currentAdapter);

        return view;
    }

    void displayList(){

        for(int i = 0; i < PivotalTrackerFragment.storylist.size(); i++)
        {
            if ( PivotalTrackerFragment.storylist.get(i).getCurrentState().equals("accepted"))
                currentItems.add(PivotalTrackerFragment.storylist.get(i).getName());
        }
    }
}
