package com.example.paer.agileproject.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
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
import com.astuetz.PagerSlidingTabStrip;
import com.example.paer.agileproject.R;
import com.example.paer.agileproject.adapters.PivotalFragmentPagerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PivotalTrackerFragment extends Fragment {
    //String url ="https://www.pivotaltracker.com/services/v5/projects/1313008/stories?token="93419454?token=8d97e27f6cad31c7c90febef830bb957";
    String url = new String();
    String res;
    //String token = "8d97e27f6cad31c7c90febef830bb957";
    static String token = "64ae3fa63bd077840e44e878dc1c905f";
    String choosenProjectId;
    TextView mTextView;
    ListView lvProject;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listProjectId = new ArrayList<String>();
    ArrayAdapter<String> lvAdapter;
    EditText mText;
    String urlProject = "https://www.pivotaltracker.com/services/v5/projects/1313008/stories?token=64ae3fa63bd077840e44e878dc1c905f";
    static ArrayList<Story> storylist = new ArrayList<Story>();

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
        lvAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listItems);

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
                displayJsonResponseProject();
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
                        //new AlertDialog.Builder(getActivity()) .setTitle("That dosen't work!").setMessage(error.toString()).show();
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

    void displayJsonResponseProject() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        // Request a JSONObject response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, urlProject, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //res = response.toString();
                        // parseRespon();
                        new AlertDialog.Builder(getActivity()).setTitle("response")
                                .setMessage(response.toString()).show();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int i;
                        String match = "[";

                        for(i = 0; i < error.toString().length(); i++)
                        {
                            if(error.toString().charAt(i) == match.charAt(0))
                                break;
                        }
                        res = error.toString().substring(i);
                        parseRespon();
                    }
                });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    void parseRespon(){
        try {
            Story temp = new Story();
            JSONArray array = new JSONArray(res);
            for(int i = 0;i < array.length(); i++){
                JSONObject story  = array.getJSONObject(i);
                if(story.getString("kind") == null)
                    temp.setKind(null);
                else
                    temp.setKind(story.getString("kind"));
                if(story.getString("id") == null)
                    temp.setId(null);
                else
                    temp.setId(story.getString("id"));
                if (story.getString("created_at") == null)
                    temp.setCreateAt(null);
                else
                    temp.setCreateAt(story.getString("created_at"));
                if (story.getString("updated_at") == null)
                    temp.setUpdateAt(null);
                else
                    temp.setUpdateAt(story.getString("updated_at"));
                //if (story.getString("accepted_at") == null)
                //     temp.setAcceptedAt(null);
                // else
                //     temp.setAcceptedAt(story.getString("accepted_at"));
                if (story.getString("estimate") == null)
                    temp.setEstimate("No point.");
                else
                    temp.setEstimate(story.getString("estimate"));
                if (story.getString("story_type") == null)
                    temp.setStoryType(null);
                else
                    temp.setStoryType(story.getString("story_type"));
                if (story.getString("name") == null)
                    temp.setName(null);
                else
                    temp.setName(story.getString("name"));
                if (story.getString("current_state") == null )
                    temp.setCurrentState(null);
                else
                    temp.setCurrentState(story.getString("current_state"));
                if (story.getString("url") == null)
                    temp.setUrl(null);
                else
                    temp.setUrl(story.getString("url"));
                if (story.getString("owner_ids") == null)
                    temp.setOwnedById(null);
                else

                    temp.setOwnedById(story.getString("owner_ids"));
                /*if (story.getString("description") != null)
                    temp.setDescriptionescription(story.getString("description"));
                else
                    temp.setDescriptionescription(null);
                storylist.add(temp);*/
            }
            //new AlertDialog.Builder(getActivity()).setTitle("response") .setMessage(storylist.get(0).toString()).show();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
