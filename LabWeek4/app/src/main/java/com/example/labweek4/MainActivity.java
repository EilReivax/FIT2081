package com.example.labweek4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        editId = findViewById(R.id.inputId);
        editTitle = findViewById(R.id.inputTitle);
        editIsbn = findViewById(R.id.inputIsbn);
        editAuthor = findViewById(R.id.inputAuthor);
        editDescription = findViewById(R.id.inputDescription);
        editPrice = findViewById(R.id.inputPrice);

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));

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

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra(SMSReceiver.SMS_KEY);
            StringTokenizer stringTokenizer = new StringTokenizer(message, "|");

            String firstToken = stringTokenizer.nextToken();
            String id = stringTokenizer.nextToken();
            String title = stringTokenizer.nextToken();
            String isbn = stringTokenizer.nextToken();
            String author = stringTokenizer.nextToken();
            String description = stringTokenizer.nextToken();
            String price = stringTokenizer.nextToken();
            String lastToken = stringTokenizer.nextToken();

            if (firstToken.equals(lastToken)) {
                editId.setText(id);
                editTitle.setText(title);
                editIsbn.setText(isbn);
                editAuthor.setText(author);
                editDescription.setText(description);
                editPrice.setText(price);
            }
        }
    }

    public void addBook(View view) {
        String title = String.valueOf(editTitle.getText());
        String price = String.valueOf(editPrice.getText());
        save();

        Toast.makeText(this, "Book: " + title + ", Price: " + price, Toast.LENGTH_LONG).show();
    }

    public void clearFields(View view) {
        editId.setText("");
        editTitle.setText("");
        editIsbn.setText("");
        editAuthor.setText("");
        editDescription.setText("");
        editPrice.setText("");
    }

    public void loadBook(View view) {
        load();
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