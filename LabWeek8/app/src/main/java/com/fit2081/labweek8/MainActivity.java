package com.fit2081.labweek8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.fit2081.labweek8.provider.Book;
import com.fit2081.labweek8.provider.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText editTitle, editIsbn, editAuthor, editDescription, editPrice;
    public static final String TAG = "WEEK_8_TAG";
    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String ISBN_KEY = "ISBN_KEY";
    public static final String AUTHOR_KEY = "AUTHOR_KEY";
    public static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";
    public static final String PRICE_KEY = "PRICE_KEY";
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fab;
    BookAdapter adapter;
    private BookViewModel bookViewModel;
    DatabaseReference cloudDatabase;
    DatabaseReference bookTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.drawer_layout);

        editTitle = findViewById(R.id.inputTitle);
        editIsbn = findViewById(R.id.inputIsbn);
        editAuthor = findViewById(R.id.inputAuthor);
        editDescription = findViewById(R.id.inputDescription);
        editPrice = findViewById(R.id.inputPrice);

        // Week 4
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

        // Week 5
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> add());

        // Week 7
        adapter = new BookAdapter();
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new BookFragment()).commit();

        // Week 8
        cloudDatabase = FirebaseDatabase.getInstance().getReference();
        bookTable = cloudDatabase.child("books");

        bookTable.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        load();
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
        editTitle.setText(inState.getString(TITLE_KEY));
        editIsbn.setText(inState.getString(ISBN_KEY));
        editAuthor.setText(inState.getString(AUTHOR_KEY));
        editDescription.setText(inState.getString(DESCRIPTION_KEY));
        editPrice.setText(inState.getString(PRICE_KEY));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        int id = item.getItemId();

        if (id == R.id.optionClear) {
            clear();
        }
        else if (id == R.id.optionLoad) {
            load();
        }

        return true;
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d(TAG, "onNavigationItemSelected");
            int id = item.getItemId();

            if (id == R.id.itemAddBook) {
                add();
            }
            else if (id == R.id.itemRemoveLastBook) {
                bookViewModel.deleteLast();
                adapter.notifyDataSetChanged();
            }
            else if (id == R.id.itemRemoveAllBooks) {
                bookViewModel.deleteAll();
                adapter.notifyDataSetChanged();
                bookTable.removeValue();
            }
            else if (id == R.id.itemListAll) {
                Intent intent = new Intent(MainActivity.this, ListBook.class);
                startActivity(intent);
            }

            drawerLayout.closeDrawers();
            return true;
        }
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");
            String message = intent.getStringExtra(SMSReceiver.SMS_KEY);
            StringTokenizer stringTokenizer = new StringTokenizer(message, "|");

            String title = stringTokenizer.nextToken();
            String isbn = stringTokenizer.nextToken();
            String author = stringTokenizer.nextToken();
            String description = stringTokenizer.nextToken();
            String price = stringTokenizer.nextToken();

            editTitle.setText(title);
            editIsbn.setText(isbn);
            editAuthor.setText(author);
            editDescription.setText(description);
            editPrice.setText(price);
        }
    }

    public void add() {
        String title = String.valueOf(editTitle.getText());
        String isbn = String.valueOf(editIsbn.getText());
        String author = String.valueOf(editAuthor.getText());
        String description = String.valueOf(editDescription.getText());
        String price = String.valueOf(editPrice.getText());

        Book book = new Book(title, isbn, author, description, price);
        bookViewModel.insert(book);
        adapter.notifyDataSetChanged();
        bookTable.push().setValue(book);

        Toast.makeText(this, title + " | " + price, Toast.LENGTH_SHORT).show();

        save();
    }

    public void clear() {
        editTitle.setText("");
        editIsbn.setText("");
        editAuthor.setText("");
        editDescription.setText("");
        editPrice.setText("");
    }

    public void save() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TITLE_KEY, String.valueOf(editTitle.getText()));
        editor.putString(ISBN_KEY, String.valueOf(editIsbn.getText()));
        editor.putString(AUTHOR_KEY, String.valueOf(editAuthor.getText()));
        editor.putString(DESCRIPTION_KEY, String.valueOf(editDescription.getText()));
        editor.putString(PRICE_KEY, String.valueOf(editPrice.getText()));
        editor.apply();
    }

    public void load() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        editTitle.setText(sharedPreferences.getString(TITLE_KEY, ""));
        editIsbn.setText(sharedPreferences.getString(ISBN_KEY, ""));
        editAuthor.setText(sharedPreferences.getString(AUTHOR_KEY, ""));
        editDescription.setText(sharedPreferences.getString(DESCRIPTION_KEY, ""));
        editPrice.setText(sharedPreferences.getString(PRICE_KEY, ""));
    }
}