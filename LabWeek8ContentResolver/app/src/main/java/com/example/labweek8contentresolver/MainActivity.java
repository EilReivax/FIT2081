package com.example.labweek8contentresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView viewBookCount = findViewById(R.id.viewBookCount);
        Uri uri = Uri.parse("content://fit2081.app.michael");

        Cursor cursor = getContentResolver().query(uri, null, null, null);
        if (cursor != null) {
            viewBookCount.setText(String.valueOf(cursor.getCount()));
        }
        else {
            viewBookCount.setText("Null");
        }
    }
}