package com.fit2081.labweek2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    EditText editId;
    EditText editTitle;
    EditText editIsbn;
    EditText editAuthor;
    EditText editDescription;
    EditText editPrice;

    public static final String TAG = "WEEK_3_TAG";
    public static final String ID_KEY = "ID_KEY";
    public static final String TITLE_KEY = "TITLE_KEY";
    public static final String ISBN_KEY = "ISBN_KEY";
    public static final String AUTHOR_KEY = "AUTHOR_KEY";
    public static final String DESCRIPTION_KEY = "DESCRIPTION_KEY";
    public static final String PRICE_KEY = "PRICE_KEY";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putString(TITLE_KEY, String.valueOf(editTitle.getText()));
        outState.putString(ISBN_KEY, String.valueOf(editIsbn.getText()));
        save();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle inState) {
        Log.d(TAG, "onRestoreInstanceState");
        editId.setText(inState.getString(ID_KEY));
        editTitle.setText(inState.getString(TITLE_KEY));
        editIsbn.setText(inState.getString(ISBN_KEY));
        editAuthor.setText(inState.getString(AUTHOR_KEY));
        editDescription.setText(inState.getString(DESCRIPTION_KEY));
        editPrice.setText(inState.getString(PRICE_KEY));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        editId = findViewById(R.id.inputId);
        editTitle = findViewById(R.id.inputTitle);
        editIsbn = findViewById(R.id.inputIsbn);
        editAuthor = findViewById(R.id.inputAuthor);
        editDescription = findViewById(R.id.inputDescription);
        editPrice = findViewById(R.id.inputPrice);

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

    public void save() {
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
        editId.setText(sharedPreferences.getString(ID_KEY, ""));
        editTitle.setText(sharedPreferences.getString(TITLE_KEY, ""));
        editIsbn.setText(sharedPreferences.getString(ISBN_KEY, ""));
        editAuthor.setText(sharedPreferences.getString(AUTHOR_KEY, ""));
        editDescription.setText(sharedPreferences.getString(DESCRIPTION_KEY, ""));
        editPrice.setText(sharedPreferences.getString(PRICE_KEY, ""));
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

    public void reset(View view) {
        editId.setText("Default ID");
        editTitle.setText("Default Title");
        editIsbn.setText("Default ISBN");
        editAuthor.setText("Default Author");
        editDescription.setText("Default Description");
        editPrice.setText("123");
        save();
    }
}