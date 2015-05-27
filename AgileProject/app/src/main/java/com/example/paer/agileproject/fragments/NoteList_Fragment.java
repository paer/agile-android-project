package com.example.paer.agileproject.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paer.agileproject.R;


import java.sql.SQLException;
import java.util.ArrayList;

import database.DBMetaData;
import database.DataBaseHandler;
import database.listViewAdapter;



public class NoteList_Fragment extends Fragment {
    View view;
    Cursor cur;
    DataBaseHandler dbh;
    Button del;
    String[] notes;
    private ArrayList<String> list;
    SimpleCursorAdapter myCursorAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_note_list, container, false);
//
        // populateListViewFromDB();
        //  super.onCreate(savedInstanceState);
        // getActivity().setContentView(R.layout.fragment_note_list);
        ListView lview = (ListView) view.findViewById(R.id.contentlist);
        //ListView lview = (ListView) view.findViewById(R.id.contentlist);
        populateList();
        listViewAdapter adapter = new listViewAdapter(getActivity(), list);
        lview.setAdapter(adapter);



        return view;
    }
    // No idea why it does not work!!!! gives a blank list!
    private void populateListViewFromDB(){
        try{
            dbh = new DataBaseHandler(getActivity());
            try {
                dbh.open(getActivity());
                cur = dbh.selectUserNotesText(1);
                dbh.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String[] fromDB = new String[] {DBMetaData.MetaUserNote.NOTE_TEXT};
            int[] toViewIDs = new int[]{R.id.noteText};
            myCursorAdapter = new SimpleCursorAdapter(
                    getActivity(),
                    R.layout.note_list_item,
                    cur,
                    fromDB,
                    toViewIDs
            );
            // ListView myList = (ListView) view.findViewById(R.id.noteList);
            ListView myList = (ListView) view.findViewById(R.id.contentlist);
            myList.setAdapter(myCursorAdapter);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void populateList() {

        list = new ArrayList<String>();
        try{
            dbh = new DataBaseHandler(getActivity());
            try {
                dbh.open(getActivity());
                cur = dbh.selectUserNotesText(1);
                dbh.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (cur.moveToFirst()) {
                do {
                    // Toast.makeText(getBaseContext(),cur.getInt(0)+"  "+cur.getString(1),Toast.LENGTH_LONG).show();
                    list.add(cur.getString(1));
                } while (cur.moveToNext());
            }


            // ListView myList = (ListView) view.findViewById(R.id.noteList);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}


