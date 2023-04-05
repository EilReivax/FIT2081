package com.example.labweek5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText editId, editTitle, editIsbn, editAuthor, editDescription, editPrice;
    public static final String TAG = "WEEK_3_TAG";
    public static final String ID_KEY = "ID_KEY";
    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String ISBN_KEY = "ISBN_KEY";
    public static final String AUTHOR_KEY = "AUTHOR_KEY";
    public static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";
    public static final String PRICE_KEY = "PRICE_KEY";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Toolbar toolbar;

    ArrayList<String> books = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.drawer_layout);

        editId = findViewById(R.id.inputId);
        editTitle = findViewById(R.id.inputTitle);
        editIsbn = findViewById(R.id.inputIsbn);
        editAuthor = findViewById(R.id.inputAuthor);
        editDescription = findViewById(R.id.inputDescription);
        editPrice = findViewById(R.id.inputPrice);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listView);

        // Week 4
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        // Week 5
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, books);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        load();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putString(TITLE_KEY, String.valueOf(editTitle.getText()));
        outState.putString(ISBN_KEY, String.valueOf(editIsbn.getText()));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle inState) {
        Log.d(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(inState);
        editId.setText(inState.getString(ID_KEY));
        editTitle.setText(inState.getString(TITLE_KEY));
        editIsbn.setText(inState.getString(ISBN_KEY));
        editAuthor.setText(inState.getString(AUTHOR_KEY));
        editDescription.setText(inState.getString(DESCRIPTION_KEY));
        editPrice.setText(inState.getString(PRICE_KEY));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.optionClear) {
            clear();
        }
        else if (id == R.id.optionLoad) {
            load();
        }
        else if (id == R.id.optionAdd) {
            add();
        }

        return true;
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.itemAddBook) {
                add();
            }
            else if (id == R.id.itemRemoveLastBook) {
                books.remove(books.size() - 1);
                adapter.notifyDataSetChanged();
            }
            else if (id == R.id.itemRemoveAllBooks) {
                books.clear();
                adapter.notifyDataSetChanged();
            }
            else if (id == R.id.itemClearAll) {
                clear();
                books.clear();
                adapter.notifyDataSetChanged();
            }

            drawerLayout.closeDrawers();
            return true;
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(SMSReceiver.SMS_KEY);
            StringTokenizer stringTokenizer = new StringTokenizer(message, "|");

            String id = stringTokenizer.nextToken();
            String title = stringTokenizer.nextToken();
            String isbn = stringTokenizer.nextToken();
            String author = stringTokenizer.nextToken();
            String description = stringTokenizer.nextToken();
            String price = stringTokenizer.nextToken();

            editId.setText(id);
            editTitle.setText(title);
            editIsbn.setText(isbn);
            editAuthor.setText(author);
            editDescription.setText(description);
            editPrice.setText(price);
        }
    }

    public void add() {
        String title = String.valueOf(editTitle.getText());
        String price = String.valueOf(editPrice.getText());

        books.add(title + " | " + price);
        adapter.notifyDataSetChanged();

        Toast.makeText(this, title + " | " + price, Toast.LENGTH_SHORT).show();

        save();
    }

    public void clear() {
        editId.setText("");
        editTitle.setText("");
        editIsbn.setText("");
        editAuthor.setText("");
        editDescription.setText("");
        editPrice.setText("");
    }

    public void save() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_KEY, String.valueOf(editId.getText()));
        editor.putString(TITLE_KEY, String.valueOf(editTitle.getText()));
        editor.putString(ISBN_KEY, String.valueOf(editIsbn.getText()));
        editor.putString(AUTHOR_KEY, String.valueOf(editAuthor.getText()));
        editor.putString(DESCRIPTION_KEY, String.valueOf(editDescription.getText()));
        editor.putString(PRICE_KEY, String.valueOf(editPrice.getText()));
        editor.apply();
    }

    public void load() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        editId.setText(sharedPreferences.getString(ID_KEY, ""));
        editTitle.setText(sharedPreferences.getString(TITLE_KEY, ""));
        editIsbn.setText(sharedPreferences.getString(ISBN_KEY, ""));
        editAuthor.setText(sharedPreferences.getString(AUTHOR_KEY, ""));
        editDescription.setText(sharedPreferences.getString(DESCRIPTION_KEY, ""));
        editPrice.setText(sharedPreferences.getString(PRICE_KEY, ""));
    }
}