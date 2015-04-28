package com.example.paer.agileproject;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {
    // String url ="https://www.pivotaltracker.com/services/v5/projects/1333710/stories/93419454?token=8d97e27f6cad31c7c90febef830bb957";
    String url = "https://www.pivotaltracker.com/services/v5/me?token=";
    String token = "8d97e27f6cad31c7c90febef830bb957";

    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.txtDisplay);
        final Button mButton   = (Button) findViewById(R.id.getJSONButton);
        final EditText mText   = (EditText) findViewById(R.id.ptTokenField);
        final Button defaultButton = (Button) findViewById(R.id.defaultToken);

        defaultButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mText.setText(token);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                url = url + mText.getText();
                displayJsonResponse();
            }
        });
    }

    void displayJsonResponse(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a JSONObject response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display JSON as a string.
                        mTextView.setText("Response is: "+ response.toString());
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
