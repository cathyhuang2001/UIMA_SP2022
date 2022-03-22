package com.cs250.joanne.bookshare;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetIterator;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class ListFrag extends Fragment {

    public static final int MENU_ITEM_EDITVIEW = Menu.FIRST;
    public static final int MENU_ITEM_DELETE = Menu.FIRST + 1;
    public static final int MENU_ITEM_LEND = Menu.FIRST + 2;

    private ListView myList;
    private CardView myCard;
    private MainActivity myact;
    SharedPreferences sharedPreferences;

    Context cntx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.list_frag, container, false);

        cntx = getActivity().getApplicationContext();

        myact = (MainActivity) getActivity();
        myact.getSupportActionBar().setTitle("My Bookshelf");
        myList = (ListView) myview.findViewById(R.id.mylist);
        myCard = (CardView) myview.findViewById(R.id.card_view);
        // connect listview to the array adapter in MainActivity
        myList.setAdapter(myact.aa);
        registerForContextMenu(myList);
        // refresh view
        myact.aa.notifyDataSetChanged();

        // program a short click on the list item
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Selected #" + id, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                // create intent to launch ItemDetail activity with the item's details
                // startActivity for result so that you know the status of the toggle button when you return
                Item curr = myact.myItems.get(position);
                sharedPreferences = getContext().getSharedPreferences("item", Context.MODE_PRIVATE);
                sharedPreferences.edit().putInt("itemID", position).apply();
                sharedPreferences.edit().putString("itemTitle", curr.getTitle()).apply();
                sharedPreferences.edit().putString("itemAuthor", curr.getAuthor()).apply();
                sharedPreferences.edit().putString("itemAvail", curr.getAvailability()).apply();
                Intent intent = new Intent(myact, ItemDetail.class);
                startActivityForResult(intent, 1);
            }
        });

        return myview;
    }

    // for a long click on a menu item use ContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // create menu in code instead of in xml file (xml approach preferred)
        menu.setHeaderTitle("Select Item");

        // Add menu items
        menu.add(0, MENU_ITEM_EDITVIEW, 0, R.string.menu_editview);
        menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete);
        menu.add(0, MENU_ITEM_LEND, 0, "Lend");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        AdapterView.AdapterContextMenuInfo menuInfo;
        menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = menuInfo.position; // position in array adapter

        switch (item.getItemId()) {
            case MENU_ITEM_EDITVIEW: {

                Toast.makeText(cntx, "edit request",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            case MENU_ITEM_DELETE: {
                myact.myItems.remove(index);
                Toast.makeText(cntx, "book " + index + " deleted",
                        Toast.LENGTH_SHORT).show();
                // refresh view
                myact.aa.notifyDataSetChanged();
                return true;
            }
            case MENU_ITEM_LEND: {
                Item curr = myact.myItems.get(index);
                if (curr.getAvailability().equals("OUT")) {
                    Toast.makeText(cntx, "book " + index + " not available",
                            Toast.LENGTH_SHORT).show();
                } else {
                    curr.setAvailability("OUT");
                    Toast.makeText(cntx, "Lending book " + index,
                            Toast.LENGTH_SHORT).show();
                    myact.aa.notifyDataSetChanged();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode== Activity.RESULT_OK){
            String avail = data.getStringExtra("result");
            int i = sharedPreferences.getInt("itemID", -1);
            if (i != -1) {
                Item curr = myact.myItems.get(i);
                curr.setAvailability(avail);
                myact.aa.notifyDataSetChanged();
            }
        }
    }


    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Other Fragment2", "onStart");
        // Apply any required UI change now that the Fragment is visible.
    }

    // Called at the start of the active lifetime.
    @Override
    public void onResume(){
        super.onResume();
        Log.d ("Other Fragment", "onResume");
        // Resume any paused UI updates, threads, or processes required
        // by the Fragment but suspended when it became inactive.
    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
        Log.d ("Other Fragment", "onPause");
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground activity.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
        super.onPause();
    }

    // Called to save UI state changes at the
    // end of the active lifecycle.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d ("Other Fragment", "onSaveInstanceState");
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate, onCreateView, and
        // onCreateView if the parent Activity is killed and restarted.
        super.onSaveInstanceState(savedInstanceState);
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
        Log.d ("Other Fragment", "onStop");
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Fragment isn't visible.
        super.onStop();
    }

    // Called when the Fragment's View has been detached.
    @Override
    public void onDestroyView() {
        Log.d ("Other Fragment", "onDestroyView");
        // Clean up resources related to the View.
        super.onDestroyView();
    }

    // Called at the end of the full lifetime.
    @Override
    public void onDestroy(){
        Log.d ("Other Fragment", "onDestroy");
        // Clean up any resources including ending threads,
        // closing database connections etc.
        super.onDestroy();
    }

    // Called when the Fragment has been detached from its parent Activity.
    @Override
    public void onDetach() {
        Log.d ("Other Fragment", "onDetach");
        super.onDetach();
    }
}
