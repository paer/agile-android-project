package com.example.paer.agileproject;

import android.content.Context;
import android.database.Cursor;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.sql.SQLException;

import database.DBMetaData;
import database.DataBaseHandler;



public class NoteList_Fragment extends Fragment {
    View view;
    Cursor cur;
    DataBaseHandler dbh;
    String[] notes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_note_list, container, false);
        super.onCreate(savedInstanceState);
        dbh = new DataBaseHandler(getActivity());
        try {
            dbh.open(getActivity());
            cur = dbh.selectUserNotesText(1);
            dbh.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (cur.moveToFirst()) {
            notes = new String[cur.getCount()];
            int cntr=0;
            do {
                notes[cntr] = cur.getString(0);
                cntr++;
            } while (cur.moveToNext());
        }

        try{
            ArrayAdapter theAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,notes);
            ListView myList = (ListView) view.findViewById(R.id.NoteList);
            myList.setAdapter(theAdapter);
        }
        catch (Exception ex){
            Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        return view;
    }
    // No idea why it does not work!!!! gives a blank list!
    private void populateListViewFromDB(){
        try{
            String[] fromDB = new String[] {DBMetaData.MetaUserNote.NOTE_TEXT};
            int[] toViewIDs = new int[]{R.layout.row_layout};
            SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                    getActivity(),
                    R.layout.fragment_note_list,
                    cur,
                    fromDB,
                    toViewIDs
            );
            ListView myList = (ListView) view.findViewById(R.id.NoteList);
            myList.setAdapter(myCursorAdapter);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
