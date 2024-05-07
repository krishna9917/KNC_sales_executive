package com.application.knc.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.Adapters.IntroProductsAdapter
import com.application.knc.Utils.MyApp
import com.application.knc.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity()
{

    private  val binding by lazy {
        ActivityIntroBinding.inflate(layoutInflater)
    }
    private  val productsAdapter by lazy {
        IntroProductsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        binding.rvProducts.adapter = productsAdapter
        Handler(Looper.myLooper()!!).postDelayed({
            MyLocalMemory.putBoolean(MyApp.MySingleton.IS_SECOND_TIME_OPEN,true)
            startActivity(Intent(this,LanguageSelectionActivity::class.java))
            finish()
        },1000)
    }
}