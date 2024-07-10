package com.example.billing_expertz.Activity

import android.net.http.SslError
import android.os.Bundle
import android.print.PrintManager
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.billing_expertz.R
import com.example.billing_expertz.databinding.ActivityWebviewBinding


class WebviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview)
        val url=intent.getStringExtra("invoiceUrl")
        binding.webview.loadUrl(url!!)

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.setSupportZoom(true)
        binding.webview.settings.setAllowContentAccess(true)
        binding.webview.settings.setAllowFileAccess(true)
        binding.webview.settings.setDatabaseEnabled(true)
        binding.webview.settings.setDomStorageEnabled(true)

        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                Log.d("zvczxc",request?.url.toString())
                return true
            }
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Code to execute when page starts loading
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Code to execute when page finishes loading
            }
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                // Code to handle error
            }
            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler,
                error: SslError?
            ) {
                Log.d("aasdad", error?.toString()!!)
                handler.proceed() // Ignore SSL certificate errors
            }
        }

        binding.fbPrint.setOnClickListener {
            createWebPagePrint(binding.webview)
        }

        binding.webview.setWebChromeClient(object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String,
                result: JsResult
            ): Boolean {
                return super.onJsAlert(view, url, message, result)
            }
        })
    }
    private fun createWebPagePrint(webView: WebView) {
        val printManager = getSystemService(PRINT_SERVICE) as PrintManager
        val jobName = "${getString(R.string.app_name)} Document"

        val printAdapter = webView.createPrintDocumentAdapter(jobName)
        printManager.print(jobName, printAdapter, null)
    }

    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (binding.webview.canGoBack())
            binding.webview.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}