package com.example.labweek8cr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView viewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewCount = findViewById(R.id.viewCount);
    }
    public void countBooks(View view) {
        Uri uri = Uri.parse("content://fit2081.app.michael");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            viewCount.setText(String.valueOf(cursor.getCount()));
        }
        else {
            viewCount.setText("Result is null");
        }
    }

    public void addBook() {
        Uri uri = Uri.parse("content://fit2081.app.michael");
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", "Title");
        contentValues.put("isbn", "ISBN");
        contentValues.put("author", "Author");
        contentValues.put("description", "Description");
        contentValues.put("price", "100");

        getContentResolver().insert(uri, contentValues);
    }
}