package com.example.labweek8contentresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView viewBookCount;
    public static final Uri CONTENT_URI = Uri.parse("content://fit2081.app.michael");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewBookCount = findViewById(R.id.viewBookCount);
    }

    public void countBooks(View view) {
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            Log.d("countBooks", String.valueOf(cursor.getCount()));
            viewBookCount.setText(String.valueOf(cursor.getCount()));
        }
        else {
            viewBookCount.setText("Null");
        }
    }

    public void countBooksAboveFifty(View view) {
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, "price > 50", null, null);
        if (cursor != null) {
            Log.d("countBooksAboveFifty", String.valueOf(cursor.getCount()));
            viewBookCount.setText(String.valueOf(cursor.getCount()));
        }
        else {
            viewBookCount.setText("Null");
        }
    }
}