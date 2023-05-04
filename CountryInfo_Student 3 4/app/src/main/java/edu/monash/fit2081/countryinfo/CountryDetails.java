package edu.monash.fit2081.countryinfo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class CountryDetails extends AppCompatActivity {

    private TextView nameTV;
    private TextView capitalTV;
    private TextView codeTV;
    private TextView populationTV;
    private TextView areaTV;

    private RequestQueue requestQueue;
    ExecutorService executor;
    Handler uiHandler;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        getSupportActionBar().setTitle(R.string.title_activity_country_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String selectedCountry = getIntent().getStringExtra("country");

        nameTV = findViewById(R.id.country_name);
        capitalTV = findViewById(R.id.capital);
        codeTV = findViewById(R.id.country_code);
        populationTV = findViewById(R.id.population);
        areaTV = findViewById(R.id.area);

        requestQueue = Volley.newRequestQueue(this);




        executor = Executors.newSingleThreadExecutor();
        //Executor handler = ContextCompat.getMainExecutor(this);
        uiHandler = new Handler(Looper.getMainLooper());
        jsonParse(selectedCountry);
    }


    private void jsonParse(String selectCountry) {
        String url = "https://restcountries.com/v2/name/"+selectCountry;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject countryObj = response.getJSONObject(0);
                    String  name = countryObj.getString("name");
                    String capital = countryObj.getString("capital");
                    String code2 = countryObj.getString("alpha2Code");
                    String code3 = countryObj.getString("alpha3Code");
                    int population = countryObj.getInt("population");
                    int area = countryObj.getInt("area");

                    nameTV.setText(name);
                    capitalTV.setText(capital);
                    codeTV.setText(code2);
                    populationTV.setText(population + "");
                    areaTV.setText(area + "");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

}
