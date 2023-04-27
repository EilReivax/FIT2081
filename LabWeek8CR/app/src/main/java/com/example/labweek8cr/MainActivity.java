package com.example.labweek8cr;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView viewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewCount = findViewById(R.id.viewCount);

        Uri uri = Uri.parse("content://fit2081.app.michael");
        Cursor cursor = getContentResolver().query(uri, null, null, null);
        viewCount.setText(String.valueOf(cursor.getCount()));
    }
}