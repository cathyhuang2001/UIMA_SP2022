package com.cs250.joanne.bookshare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.material.snackbar.Snackbar;

public class ItemDetail extends AppCompatActivity {

    private ToggleButton toggle;
    private boolean avail;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        avail = true;
        sharedPreferences = getSharedPreferences("item", MODE_PRIVATE);
        TextView text1 = findViewById(R.id.text1);
        text1.setText(sharedPreferences.getString("itemTitle", "title"));
        TextView text2 = findViewById(R.id.text2);
        text2.setText(sharedPreferences.getString("itemAuthor", "author"));
        toggle = (ToggleButton) findViewById(R.id.togbtn);
        String status = sharedPreferences.getString("itemAvail", "IN");
        toggle.setChecked(status.equals("IN"));
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                avail = isChecked; // whether or not the toggle is enabled
            }
        });
    }

    /** Called when the user clicks the close button */
    public void close(View view) {
        // use the state of the ToggleButton as the
        // result to return to the fragment that launched this
       // explicitly pop this activity off the back-stack
        Intent returnIntent = new Intent();
        if (avail) {
            returnIntent.putExtra("result", "IN");
        } else {
            returnIntent.putExtra("result", "OUT");
        }
        setResult(Activity.RESULT_OK, returnIntent);
        getFragmentManager().popBackStack();
        finish();
    }

}