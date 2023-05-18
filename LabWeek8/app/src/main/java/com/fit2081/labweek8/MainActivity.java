package com.fit2081.labweek8;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.fit2081.labweek8.provider.Book;
import com.fit2081.labweek8.provider.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    EditText editTitle, editIsbn, editAuthor, editDescription, editPrice;
    public static final String TAG = "WEEK_10_TAG";
    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String ISBN_KEY = "ISBN_KEY";
    public static final String AUTHOR_KEY = "AUTHOR_KEY";
    public static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";
    public static final String PRICE_KEY = "PRICE_KEY";
    public static final int MIN_SWIPE_DISTANCE = 300;
    public static final int MIN_SWIPE_VELOCITY = 1000;
    private float dx, dy, x1, x2, y1, y2;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fab;
    BookAdapter adapter;
    private BookViewModel bookViewModel;
    DatabaseReference cloudDatabase;
    DatabaseReference bookTable;
    private GestureDetectorCompat gestureDetector;

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

        // Week 10
        /*
        View view = findViewById(R.id.touchLayout);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getRawX();
                        y1 = motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = motionEvent.getRawX();
                        y2 = motionEvent.getRawY();

                        dx = x2 - x1;
                        dy = y2 - y1;

                        if (Math.abs(dx) > MIN_SWIPE_DISTANCE && Math.abs(dy) > MIN_SWIPE_DISTANCE) {
                            if (dx < 0 && dy < 0) {
                                int editPriceInt = Integer.parseInt(String.valueOf(editPrice.getText()));
                                editPriceInt *= 2;
                                 editPrice.setText(String.valueOf(editPriceInt));
                            }
                            else if (dx < 0 && dy > 0) {
                                String editTitleString = String.valueOf(editTitle.getText());
                                editTitle.setText(editTitleString.toUpperCase());
                            }
                        }
                        else {
                            if (dx > 0 && Math.abs(dy) < MIN_SWIPE_DISTANCE) {
                                int editPriceInt = Integer.parseInt(String.valueOf(editPrice.getText()));
                                editPriceInt += 1;
                                editPrice.setText(String.valueOf(editPriceInt));
                            }
                            else if (dx < 0 && Math.abs(dy) < MIN_SWIPE_DISTANCE) {
                                add();
                            }
                            else if (dy > 0 && Math.abs(dx) < MIN_SWIPE_DISTANCE) {
                                load();
                            }
                            else if (dy < 0 && Math.abs(dx) < MIN_SWIPE_DISTANCE) {
                                clear();
                            }
                        }
                        break;
                }
                return true;
            }
        });
        */

        // Week 11
        gestureDetector = new GestureDetectorCompat(this, new MyGestureListener());
        View view = findViewById(R.id.touchLayout);
        view.setOnTouchListener(this);

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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
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

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            String randomString = RandomString.generateNewRandomString(10);
            editIsbn.setText(randomString);
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            clear();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int editPriceInt = Integer.parseInt(String.valueOf(editPrice.getText()));
            editPriceInt += distanceX;
            editPrice.setText(String.valueOf(editPriceInt));

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityX) > MIN_SWIPE_VELOCITY || Math.abs(velocityY) > MIN_SWIPE_VELOCITY) {
                moveTaskToBack(true);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            load();
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