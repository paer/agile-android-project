package com.example.paer.agileproject;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by paer on 2015-04-28.
 */
public class TestVolley extends Activity{

    public void getJson() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://www.pivotaltracker.com/services/v5/projects/1333710/stories/93419454?token=8d97e27f6cad31c7c90febef830bb957";

        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                       // mTextView.setText("Response is: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
// Add the request to the RequestQueue.
//        queue.add(stringRequest);
    }

}
