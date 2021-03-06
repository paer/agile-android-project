package com.example.paer.agileproject.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.paer.agileproject.FragmentNavigationDrawer;
import com.example.paer.agileproject.fragments.NoteList_Fragment;
import com.example.paer.agileproject.R;
import com.example.paer.agileproject.fragments.AddNoteFragment;
import com.example.paer.agileproject.fragments.GithubFragment;
import com.example.paer.agileproject.fragments.PivotalTrackerFragment;
import com.example.paer.agileproject.fragments.PokerFragment;

/**
 * @author Marc
 * @version 1.0
 */
public class MainActivity extends ActionBarActivity {
    private FragmentNavigationDrawer dlDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        dlDrawer = (FragmentNavigationDrawer) findViewById(R.id.drawer_layout);
        // Setup drawer view
        dlDrawer.setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), toolbar,
                R.layout.menu_item, R.id.flContent);

        // TODO: Here you add new navigation items
        dlDrawer.addNavItem("Poker planning game", "Poker planning game", PokerFragment.class);
        dlDrawer.addNavItem("Add notes", "Add notes", AddNoteFragment.class);
        dlDrawer.addNavItem("View notes", "View notes", NoteList_Fragment.class);
        dlDrawer.addNavItem("Pivotal tracker", "Pivotal tracker", PivotalTrackerFragment.class);
        dlDrawer.addNavItem("Github", "Github", GithubFragment.class);
        // Select default
        if (savedInstanceState == null) {
            dlDrawer.selectDrawerItem(0);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content
        if (dlDrawer.isDrawerOpen()) {
            // Uncomment to hide menu items
            // menu.findItem(R.id.mi_test).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Uncomment to inflate menu items to Action Bar
        // inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (dlDrawer.getDrawerToggle().onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        dlDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        dlDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }
}
