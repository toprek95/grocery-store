package com.example.grocerystore;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebsiteActivity extends AppCompatActivity {

	public static final String URL_KEY = "url";
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_website);

		String url = getString(R.string.google_url);
		Intent urlIntent = getIntent();
		if (null != urlIntent) {
			url = urlIntent.getStringExtra(URL_KEY);
		}

		webView = findViewById(R.id.web_view);
		webView.setWebViewClient(new WebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(url);
	}

	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
		}
	}
}