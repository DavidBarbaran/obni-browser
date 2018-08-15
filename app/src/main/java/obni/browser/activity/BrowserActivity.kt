package obni.browser.activity

import android.graphics.Bitmap
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_browser.*
import obni.browser.R
import obni.browser.config.Setting
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import obni.browser.util.fadeAnimation
import obni.browser.widget.SearchBar

class BrowserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browser)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.gray)

        val search = intent.extras.getString(Setting.SEARCH)
        val url = "https://www.google.com/search?q=$search"
        searchBar.setSearch(search)
        searchBar.onSearch = object : SearchBar.OnSearch{
            override fun search(q: String) {
                webView.loadUrl("https://www.google.com/search?q=$q")
            }
        }
        webView.webViewClient = MyWebViewClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    progressBar.setProgress(progress, true)
                } else {
                    progressBar.progress = progress
                }

                if(progress == 100){
                    fadeAnimation(progressBar, View.GONE)
                }
            }
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webView.settings.setAppCacheEnabled(true)
        webView.settings.domStorageEnabled = true
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    inner class MyWebViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            //super.onPageStarted(view, url, favicon)
            fadeAnimation(progressBar, View.VISIBLE)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
           super.onPageFinished(view, url)
            //view?.scrollTo(0,0)
        }
    }
}
