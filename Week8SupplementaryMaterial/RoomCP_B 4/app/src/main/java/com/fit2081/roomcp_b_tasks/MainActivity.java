package com.fit2081.roomcp_b_tasks;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    TextView tV;
//    public static final String COLUMN_NAME = "taskName";
//    public static final String COLUMN_DESCRIPTION = "taskDescription";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tV = findViewById(R.id.textView_id);
        Uri uri = Uri.parse("content://fit2081.tasks.db.provider/tasks");
        Cursor result = getContentResolver().query(uri, null, null, null);
        if (result != null)
            tV.setText(result.getCount() + "");
        else
            tV.setText("Result is null");
//        ContentValues values= new ContentValues();
//        values.put(COLUMN_NAME,"New Task Name");
//        values.put(COLUMN_DESCRIPTION,"New Desc");
//        Uri uri2= getContentResolver().insert(uri,values);
//        Toast.makeText(this,uri2.toString(),Toast.LENGTH_LONG).show();
    }
}