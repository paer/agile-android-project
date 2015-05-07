package com.example.paer.agileproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.paer.agileproject.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by alex on 03.05.15.
 */
public class SetupFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_setup_github, container, false);

        Button next = (Button) view.findViewById(R.id.setup_github_next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GithubFragment newFragment = new GithubFragment();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.flContent, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        });
        return view;
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
*/
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
