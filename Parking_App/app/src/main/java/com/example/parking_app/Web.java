package com.example.parking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web extends AppCompatActivity {
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        web = findViewById(R.id.web_web);
        web.setWebViewClient(new WebViewClient());
        web.loadUrl("https://www.google.com/maps");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(web.canGoBack()){
            web.goBack();
        }
    }
}