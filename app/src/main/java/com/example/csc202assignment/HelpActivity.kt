package com.example.csc202assignment

import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class HelpActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        Log.d("web", "Created")

        webView = findViewById(R.id.help_webview)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        //webView.loadUrl("https://www.queensland.com/au/en/places-to-see/destinations/brisbane/where-to-find-street-art-in-brisbane")
        webView.loadUrl("https://www.usc.edu.au/")
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }

    }

}