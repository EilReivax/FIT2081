package edu.monash.fit2081.countryinfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ImageView;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class CountryDetails extends AppCompatActivity {

    private TextView nameTV, capitalTV, codeTV, populationTV, areaTV, currenciesTv, languagesTV;
    private ImageView flagIV;
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
        currenciesTv = findViewById(R.id.currencies);
        languagesTV = findViewById(R.id.languages);
        flagIV = findViewById(R.id.flag);

        requestQueue = Volley.newRequestQueue(this);

        executor = Executors.newSingleThreadExecutor();
        Executor handler = ContextCompat.getMainExecutor(this);
        uiHandler = new Handler(Looper.getMainLooper());
        jsonParse(selectedCountry);
    }


    private void jsonParse(String selectCountry) {
        String url = "https://restcountries.com/v2/name/"+selectCountry;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject countryObj = response.getJSONObject(0);
                String  name = countryObj.getString("name");
                String capital = countryObj.getString("capital");
                String code2 = countryObj.getString("alpha2Code");
                int population = countryObj.getInt("population");
                int area = countryObj.getInt("area");

                String currencies = "";
                JSONArray currenciesArray = countryObj.getJSONArray("currencies");
                for (int i = 0; i < currenciesArray.length(); i++) {
                    String currency = currenciesArray.getJSONObject(i).getString("code");
                    currencies += currency + ", ";
                }

                String languages = "";
                JSONArray languagesArray = countryObj.getJSONArray("languages");
                for (int i = 0; i < languagesArray.length(); i++) {
                    String language = languagesArray.getJSONObject(i).getString("name");
                    languages += language + ", ";
                }

                nameTV.setText(name);
                capitalTV.setText(capital);
                codeTV.setText(code2);
                populationTV.setText(String.valueOf(population));
                areaTV.setText(String.valueOf(area));
                currenciesTv.setText(currencies);
                languagesTV.setText(languages);

                String flagRequest = "https://flagcdn.com/160x120/" + code2.toLowerCase() + ".png";
                // String flagRequest = countryObj.getJSONObject("flags").getString("name");
                executor.execute(() -> {
                   try {
                        java.net.URL flagUrl = new java.net.URL(flagRequest);
                        HttpsURLConnection connection = (HttpsURLConnection) flagUrl.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();

                        Bitmap bitmap = BitmapFactory.decodeStream(input);

                        uiHandler.post(() -> {
                            flagIV.setImageBitmap(bitmap);
                        });
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);
        requestQueue.add(request);
    }

}
