package com.example.paer.agileproject.fragments;


import android.app.AlertDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.paer.agileproject.R;

import java.util.ArrayList;

public class PivotalTrackerFragment extends Fragment {
    //String url ="https://www.pivotaltracker.com/services/v5/projects/1313008/stories?token="93419454?token=8d97e27f6cad31c7c90febef830bb957";
    String url = new String();
    //String token = "8d97e27f6cad31c7c90febef830bb957";
    static String token = "64ae3fa63bd077840e44e878dc1c905f";
    static String choosenProjectId;
    TextView mTextView;
    ListView lvProject;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listProjectId = new ArrayList<String>();
    ArrayAdapter<String> lvAdapter;
    EditText mText;
   // String urlProject = "https://www.pivotaltracker.com/services/v5/projects/1313008/stories?token=64ae3fa63bd077840e44e878dc1c905f";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_pivotal_tracker, container, false);

        mTextView = (TextView) view.findViewById(R.id.txtDisplay);
        final Button mButton = (Button) view.findViewById(R.id.getJSONButton);
        mText = (EditText) view.findViewById(R.id.ptTokenField);
        final Button defaultButton = (Button) view.findViewById(R.id.defaultToken);
        lvProject = (ListView) view.findViewById(R.id.ptProjectDisplay);
        lvAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listItems);

        defaultButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mText.setText(token);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                token = mText.getText().toString();
                displayJsonResponse();
                lvProject.setAdapter(lvAdapter);
            }
        });

        lvProject.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                choosenProjectId = listProjectId.get((int) arg3);
                System.out.println(choosenProjectId + "/" + token);
                //displayJsonResponseProject();
                PTStoryFragment newFragment = new PTStoryFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.flContent, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    void displayJsonResponse() {
        url = "https://www.pivotaltracker.com/services/v5/me?token=";
        url = url + mText.getText();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        // Request a JSONObject response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        search(response.toString(), "t_name ");
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(getActivity()) .setTitle("That dosen't work!").setMessage(error.toString()).show();
                    }
                });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void search(String str, String match) {
        int i, j, k;
        String comma = ",";
        listItems.clear();
        lvAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);
        lvProject.setAdapter(lvAdapter);
        for(i = 0; i < str.length(); i++){
            if(str.charAt(i) == match.charAt(0)){
                for(j = 1; j < match.length(); j++){
                    if(str.charAt(j + i) != match.charAt(j))
                        break;
                }

                if(j == match.length() - 1){
                    for(k = 8; ;k++) {
                        if (str.charAt(i + k) == comma.charAt(0))
                            break;
                    }
                    listProjectId.add(str.substring(i - 15, i - 8 ));
                    listItems.add(str.substring(i + 9, i + k - 1) + " (" +str.substring(i - 15, i - 8 )+ ")");

                    i+= k;
                }
            }
        }
    }

}
