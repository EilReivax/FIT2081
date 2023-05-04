package com.example.labweek6task2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "WEEK_6_TAG";
    EditText editFruit;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    ArrayList<Fruit> fruits = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        editFruit = findViewById(R.id.editFruit);
        recyclerView = findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter();
        adapter.setFruits(fruits);
        recyclerView.setAdapter(adapter);
    }

    public void search(View view) {
        String inputFruit = String.valueOf(editFruit.getText());
        makeRequest(inputFruit);
    }

    private void makeRequest(String inputFruit) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://fruityvice.com/api/fruit/" + inputFruit;

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            Log.d(TAG, "onResponse");
            String name = response.getString("name");
            String family = response.getString("family");

            JSONObject nutritions = response.getJSONObject("nutritions");
            String calories = nutritions.getString("calories");
            String sugar = nutritions.getString("sugar");
            String carbohydrates = nutritions.getString("carbohydrates");

            Fruit fruit = new Fruit(name, family, calories, sugar, carbohydrates);
            fruits.add(fruit);
            adapter.notifyDataSetChanged();

        }, error -> Log.d(TAG, error.getMessage()));

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        queue.add(stringRequest);
    }
}