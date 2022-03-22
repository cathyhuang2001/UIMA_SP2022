package com.cs250.joanne.bookshare;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment browse;
    private Fragment messages;
    private Fragment item;
    private Fragment list;
    private Fragment search;
    private FragmentTransaction transaction;
    protected ItemAdapter aa;
    protected ArrayList<Item> myItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // create ArrayList of items
        myItems = new ArrayList<Item>();
        myItems.add(new Item("first item", "author1"));
        myItems.add(new Item("second item", "author2"));
        myItems.add(new Item("third item", "author3"));
        // make array adapter to bind arraylist to listview with custom item layout
        aa = new ItemAdapter(this, R.layout.item_layout, myItems);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        browse = new Browse();
        messages = new Messages();
        search = new Search();
        item = new ItemFrag();
        list = new ListFrag();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, list).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Nav header print welcome message
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        View header = navigationView.getHeaderView(0);
        TextView welcome = (TextView) header.findViewById(R.id.welcome_name);
        String welText = "Welcome, " + sharedPreferences.getString("userName", "Owner");
        welcome.setText(welText);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            transaction = getSupportFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, search);
            transaction.addToBackStack(null);
// Commit the transaction
            transaction.commit();
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_exit) {
            this.finishAffinity();  // exit the app
        }
        else if (id == R.id.browse) {
            transaction = getSupportFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, browse);
            transaction.addToBackStack(null);
// Commit the transaction
            transaction.commit();
        }
        else if (id == R.id.messages) {
            transaction = getSupportFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, messages);
            transaction.addToBackStack(null);
// Commit the transaction
            transaction.commit();
        }
        else if (id == R.id.item_frag) {
            transaction = getSupportFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, this.item);
            transaction.addToBackStack(null);
// Commit the transaction
            transaction.commit();
        } else if (id == R.id.list_frag) {
            transaction = getSupportFragmentManager().beginTransaction();
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, list);
            transaction.addToBackStack(null);
// Commit the transaction
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
