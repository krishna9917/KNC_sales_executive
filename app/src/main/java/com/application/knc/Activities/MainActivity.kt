package com.application.knc.Activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.Utils.MyApp
import com.application.knc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
     ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializer()
    }

    private fun initializer()
    {
       Handler(Looper.myLooper()!!).postDelayed({
           if(MyLocalMemory.getBoolean(MyApp.MySingleton.IS_LOGIN))
           {
               startActivity(Intent(this,HomeActivity::class.java))
           }else
           {
               if(MyLocalMemory.getBoolean(MyApp.MySingleton.IS_SECOND_TIME_OPEN))
               {
                   startActivity(Intent(this,LanguageSelectionActivity::class.java))
               }else
               {
                   startActivity(Intent(this,IntroActivity::class.java))
               }

           }
           finish()
       },1000)
    }


}