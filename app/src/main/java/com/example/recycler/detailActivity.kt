package com.example.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_detail.*

class detailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //first we will bind the click event of newsItem and then will be redirect to this acitivity
        //with url

        val url = intent.getStringExtra("URL")
        if(url != null){
            // Once we get the url now we have enable some settings with webview.
            // detailWebView is id of our webview.
            detailWebView.settings.javaScriptEnabled = true

            //when we send a request to the server then we have userAgent named header which gives
            // information about the request(coming from desktop or phone, tablet)

            detailWebView.settings.userAgentString  ="Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"




            // Now making changes for settings progress bar
            detailWebView.webViewClient = object: WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    progressBar.visibility = View.GONE
                    detailWebView.visibility = View.VISIBLE
                }
            }
            detailWebView.loadUrl(url)
        }
    }
}