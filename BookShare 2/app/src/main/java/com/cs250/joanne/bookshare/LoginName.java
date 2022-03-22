package com.cs250.joanne.bookshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginName extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_name);
        sharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        editText = findViewById(R.id.editText);
        editText.setText(sharedPreferences.getString("userName", "Owner"));
    }

    /** Called when the user clicks the login button */
    public void launchMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String name = editText.getText().toString();
        // permanently store name for future app launches and for nav header
        sharedPreferences = getApplicationContext().getSharedPreferences("user", MODE_PRIVATE);
        sharedPreferences.edit().putString("userName", name).apply();
        startActivity(intent);
    }
}