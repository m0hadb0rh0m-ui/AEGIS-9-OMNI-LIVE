package com.rasad.aegis9;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.content.Intent;
import android.net.Uri;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends Activity {
 private WebView web;
 private static final String PREFS="rasad";
 private boolean allowed(String s) {
  try { Uri u=Uri.parse(s); return "https".equalsIgnoreCase(u.getScheme()) && u.getHost()!=null && !u.getHost().isEmpty(); }
  catch(Exception e){return false;}
 }
 @Override public void onCreate(Bundle state) {
  super.onCreate(state);
  String url=getSharedPreferences(PREFS,MODE_PRIVATE).getString("server","");
  if (allowed(url)) showWeb(url); else showSetup();
 }
 private void showSetup() {
  LinearLayout layout=new LinearLayout(this); layout.setOrientation(LinearLayout.VERTICAL); layout.setPadding(30,60,30,20);
  TextView title=new TextView(this); title.setText("رَصَد // AEGIS-9 LIVE\nأدخل عنوان خادم المنصة الآمن (HTTPS)"); title.setTextSize(20); layout.addView(title);
  EditText input=new EditText(this); input.setSingleLine(true); input.setHint("https://your-domain.example"); input.setInputType(17); layout.addView(input);
  TextView status=new TextView(this); layout.addView(status);
  Button connect=new Button(this); connect.setText("اتصال"); layout.addView(connect);
  connect.setOnClickListener(v -> { String url=input.getText().toString().trim(); if(!allowed(url)){status.setText("يجب إدخال رابط HTTPS صالح"); return;} getSharedPreferences(PREFS,MODE_PRIVATE).edit().putString("server",url).apply(); showWeb(url); });
  setContentView(layout);
 }
 private void showWeb(String url) {
  web=new WebView(this); web.getSettings().setJavaScriptEnabled(true); web.getSettings().setDomStorageEnabled(true);
  web.getSettings().setAllowFileAccess(false); web.getSettings().setAllowContentAccess(false);
  web.getSettings().setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_NEVER_ALLOW);
  web.setWebChromeClient(new WebChromeClient());
  final String origin=Uri.parse(url).getHost();
  web.setWebViewClient(new WebViewClient(){
   @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
    Uri uri=request.getUrl();
    if("https".equalsIgnoreCase(uri.getScheme()) && origin.equalsIgnoreCase(uri.getHost())) return false;
    try { startActivity(new Intent(Intent.ACTION_VIEW,uri)); } catch(Exception ignored) {}
    return true;
   }
  });
  setContentView(web); web.loadUrl(url);
 }
 @Override public void onBackPressed(){if(web!=null && web.canGoBack()) web.goBack(); else super.onBackPressed();}
}
