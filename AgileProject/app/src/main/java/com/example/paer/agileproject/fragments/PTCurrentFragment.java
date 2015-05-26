package com.example.paer.agileproject.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.paer.agileproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Qi on 2015/5/18.
 */
public class PTCurrentFragment extends Fragment {

    String urlProject = "https://www.pivotaltracker.com/services/v5/projects/";
    ListView currentView;
    ArrayList<String> currentList = new ArrayList<String>();
    ArrayAdapter<String> currentAdapter;
    ArrayList<Story> storyList = new ArrayList<Story>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_pivotal_current, container, false);

        currentView = (ListView) view.findViewById(R.id.ptCurrentList);
        currentAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, currentList);
        displayJsonResponseProject();
        currentView.setAdapter(currentAdapter);

        currentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                System.out.println(storyList.toString());
                new AlertDialog.Builder(getActivity()) .setTitle("Detail").setMessage(storyList.get((int) arg3).toString()).show();
            }
        });

        return view;
    }


    void displayJsonResponseProject() {
        String url = urlProject + PivotalTrackerFragment.choosenProjectId + "/stories?token=" + PivotalTrackerFragment.token;
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest getRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>()
                {
                    @Override public void onResponse(JSONArray response) {

                        if(response != null) {
                            parseRespon(response);
                            System.out.println("1" + storyList.toString());
                            currentAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, currentList);
                            currentView.setAdapter(currentAdapter);
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );
        queue.add(getRequest);
    }

    void parseRespon(JSONArray response) {
        currentList.clear();
        try {
            int j, i;

            for (i = 0, j = 0; i < response.length(); i++) {
                JSONObject story = response.getJSONObject(i);
                if(story.getString("current_state").equals("accepted"))
                {
                    Story temp = new Story();
                    if (story.getString("kind") == null)
                        temp.setKind(null);
                    else
                        temp.setKind(story.getString("kind"));
                    if (story.getString("id") == null)
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
                         //temp.setAcceptedAt(null);
                    // else
                        // temp.setAcceptedAt(story.getString("accepted_at"));
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
                    if (story.getString("current_state") == null)
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

                    storyList.add(j, temp);
                    currentList.add(temp.getName());
                    System.out.println(storyList.toString());
                    j++;
               }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
