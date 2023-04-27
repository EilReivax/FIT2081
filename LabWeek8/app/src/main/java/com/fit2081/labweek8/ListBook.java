package com.fit2081.labweek8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ListBook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout2, new BookFragment()).commit();
    }
}