package com.example.paer.agileproject;

import android.util.Log;

import com.android.volley.*;
import com.android.volley.toolbox.*;

/**
 * Created by paer on 2015-04-28.
 */



public class PTHandler {


String pt_URL;
String token;
String projectID;
String storyID;

    public PTHandler(String token, String projectID, String storyID){
        this.token = token;
        this.projectID = projectID;
        this.storyID = storyID;
        this.pt_URL = getURL();
        Log.d("PL", pt_URL);
    }

    private String getURL() {
        return "https://www.pivotaltracker.com/services/v5/projects/"+projectID+"/stories/"+storyID+"?token="+token;
    }
}
