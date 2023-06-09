package edu.monash.fit2081.countryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebWiki extends AppCompatActivity {
    private WebView wikiWV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_wiki);

        getSupportActionBar().setTitle(R.string.title_activity_wiki);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wikiWV = findViewById(R.id.wiki);

        final String country = getIntent().getStringExtra("country");
        String url = "https://en.wikipedia.org/wiki/" + country;
        wikiWV.setWebViewClient(new WebViewClient());
        wikiWV.loadUrl(url);
    }
}
