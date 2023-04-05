package com.fit2081.labweek2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editId;
    EditText editTitle;
    EditText editIsbn;
    EditText editAuthor;
    EditText editDescription;
    EditText editPrice;
    TextView viewAuthorTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editId = findViewById(R.id.inputId);
        editTitle = findViewById(R.id.inputTitle);
        editIsbn = findViewById(R.id.inputIsbn);
        editAuthor = findViewById(R.id.inputAuthor);
        editDescription = findViewById(R.id.inputDescription);
        editPrice = findViewById(R.id.inputPrice);
        viewAuthorTitle = findViewById(R.id.viewAuthorTitle);
    }

    public void addBook(View view) {
        System.out.println("Add Button Clicked");

        String title = String.valueOf(editTitle.getText());
        int price = Integer.parseInt(String.valueOf(editPrice.getText()));

        Toast.makeText(this, "Book: " + title + ", Price: " + price, Toast.LENGTH_LONG).show();
    }

    public void clearFields(View view) {
        System.out.println("Clear Button Clicked");
        editId.setText("");
        editTitle.setText("");
        editIsbn.setText("");
        editAuthor.setText("");
        editDescription.setText("");
        editPrice.setText("");
    }

    public void combine(View view) {
        System.out.println("Combine Button Clicked");

        String title = String.valueOf(editTitle.getText());
        String author = String.valueOf(editAuthor.getText());

        viewAuthorTitle.setText(title + " by " + author);
    }

}