package com.application.knc.Activities.WebView
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.application.knc.Dailogs.AppProgressBar
import com.application.knc.R
import com.application.knc.Utils.UtilsFunction
import com.application.knc.databinding.ActivityWebViewBinding


class WebViewActivity : AppCompatActivity(),View.OnClickListener {


    private val binding by lazy {
        ActivityWebViewBinding.inflate(layoutInflater)
    }
    private val progressBar by lazy {
        AppProgressBar(this)
    }
    private lateinit var loadUrl:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        binding.inclTitleBar.imgBack.setOnClickListener(this)
        loadUrl=intent.getStringExtra("url").toString()
        binding.llNoInternet.cvBtn.setOnClickListener(this)
        progressBar.show()
        binding.title = intent.getStringExtra("title").toString()
        binding.webView.getSettings().javaScriptEnabled = true
        binding.webView.loadUrl(loadUrl)
        binding.webView.setWebViewClient(object :WebViewClient(){
            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                loadUrl =url!!
                binding.noInternet = !UtilsFunction.isInternetConnected(this@WebViewActivity)
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.dismiss()
            }
        })

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.cvBtn->{
                progressBar.show()
                binding.webView.loadUrl(loadUrl)
            }
            R.id.imgBack->{
                finish()
            }
        }
    }
}