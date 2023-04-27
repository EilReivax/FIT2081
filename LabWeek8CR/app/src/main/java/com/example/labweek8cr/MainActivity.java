package com.example.labweek8cr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final Uri CONTENT_URI = Uri.parse("content://fit2081.app.michael");
    TextView viewCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // addBook();
        countBooks();
    }
    public void countBooks() {
        viewCount = findViewById(R.id.viewCount);
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            viewCount.setText(String.valueOf(cursor.getCount()));
        }
        else {
            viewCount.setText("Result is null");
        }
    }

    public void addBook() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", "Title");
        contentValues.put("isbn", "ISBN");
        contentValues.put("author", "Author");
        contentValues.put("description", "Description");
        contentValues.put("price", "100");

        getContentResolver().insert(CONTENT_URI, contentValues);
    }
}