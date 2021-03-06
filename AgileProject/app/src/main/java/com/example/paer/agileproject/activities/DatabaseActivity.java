package com.example.paer.agileproject.activities;

import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.paer.agileproject.R;

import java.sql.SQLException;

import database.DataBaseHandler;


public class DatabaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Cursor cur = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pivotal_tracker);
        DataBaseHandler dbh = new DataBaseHandler(this);
        try {
            dbh.open(this);
            dbh.insertUser(dbh,"Mazen","Rachid");
            dbh.insertNote(dbh, 1, "The first note");
            dbh.insertNote(dbh, 1, "MetaUser Notesssssssssssssssssssssss");
            cur = dbh.selectUserNotes(1);
            dbh.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        if (cur.moveToFirst()) {
            do {
                Toast.makeText(getBaseContext(),cur.getInt(0)+"  "+cur.getString(1),Toast.LENGTH_LONG).show();
            } while (cur.moveToNext());
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
