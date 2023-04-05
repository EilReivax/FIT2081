package edu.monash.fit2081.weatherapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String location ="Sydney";

    TextView temp;
    TextView humidity;
    TextView precip;
    TextView wind;

    EditText locationEt;

    final String TAG="WEATHER_APP_TAG";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp=findViewById(R.id.temp_id);
        humidity=findViewById(R.id.humidity_id);
        wind=findViewById(R.id.wind_id);
        precip=findViewById(R.id.precip_id);
        locationEt=findViewById(R.id.location_id);



    }


    public void onGetWeatherBtn(View view){
        String theLocation=locationEt.getText().toString();
        makeRequest(theLocation);
    }


    private void makeRequest(String location) {
        setLoading();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://api.weatherapi.com/v1/current.json?key=cde75afa40ce4179b2781925223003&q=" + location;


        JsonObjectRequest stringRequest =
                new JsonObjectRequest(Request.Method.GET, url,null,
                                      new Response.Listener<JSONObject>() {
                                          @Override
                                          public void onResponse(JSONObject response) {
                                              Log.d(TAG,"on receive");
                                              try {

                                                  JSONObject currentCondition=response.getJSONObject("current");

                                                  String temp=currentCondition.getString("temp_c");
                                                  String precip=currentCondition.getString("precip_mm");
                                                  String humidity=currentCondition.getString("humidity");
                                                  String wind=currentCondition.getString("wind_kph");

                                                  CurrentCondition cc=new CurrentCondition(temp,precip,humidity,wind);

                                                  setWeather(cc);



                                              } catch (Exception e) {
                                                  Log.d(TAG, e.getMessage());
                                              }
                                          }
                                      }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getMessage());
                    }
                });
        // due to long response time, we need to add a long delay time
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void setLoading(){

        temp.setText("Loading...");
        humidity.setText("Loading...");
        wind.setText("Loading...");
        precip.setText("Loading...");
    }

    void setWeather(CurrentCondition cc){
        Log.d(TAG,"on setWeather");

        temp.setText(cc.getTemp());
        humidity.setText(cc.getHumidity());
        wind.setText(cc.getWind());
        precip.setText(cc.getPrecip());
    }
}
