package com.application.knc.Activities

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.Activities.Login.LoginActivity
import com.application.knc.Adapters.IntroConnectionsAdapter
import com.application.knc.AppInterface.DialogClickListener
import com.application.knc.Dailogs.LanguageChooser
import com.application.knc.Utils.MyApp
import com.application.knc.databinding.ActivityLanguageSelectionBinding

class LanguageSelectionActivity : AppCompatActivity(), View.OnClickListener,DialogClickListener {

    private val binding by lazy {
        ActivityLanguageSelectionBinding.inflate(layoutInflater)
    }

    private val connectionAdapter by lazy {
        IntroConnectionsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        binding.rvConnection.adapter = connectionAdapter
        binding.inclContinue.btn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.cbLanguage.btn.setOnClickListener{
           if(MyLocalMemory.getString(MyApp.MySingleton.LANGUAGE)=="")
           {
               MyLocalMemory.putString(MyApp.MySingleton.LANGUAGE,"en")
           }
            LanguageChooser(this,this).show()
        }
    }

    override fun onClick(p0: View?) {

    }

    override fun onClick(clickCode: Int, data: Any?, callBack: DialogClickListener?)
    {
        val refreshLayout=Intent(this,LanguageSelectionActivity::class.java)
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(data as String)
        AppCompatDelegate.setApplicationLocales(appLocale)
        startActivity(refreshLayout)
        finish()
    }

}