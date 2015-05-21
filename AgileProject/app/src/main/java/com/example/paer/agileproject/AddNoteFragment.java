package com.example.paer.agileproject;

import android.content.Context;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import database.DataBaseHandler;


public class AddNoteFragment extends Fragment {

    EditText edittxt;
    Button mButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        mButton   = (android.widget.Button) view.findViewById(R.id.addNoteBtn);
        edittxt = (EditText)view.findViewById(R.id.editText);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edittxt.getText().toString().equals("")) {
                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(edittxt.getWindowToken(), 0);
                    DataBaseHandler dbh = new DataBaseHandler(getActivity());
                    try {
                        dbh.open(getActivity());
                        dbh.insertNote(dbh, 1, edittxt.getText().toString());
                        dbh.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), "Note is added", Toast.LENGTH_LONG).show();
                    edittxt.setText("");
                } else {
                    Toast.makeText(getActivity(), "Cannot add empty note", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
