package com.application.knc.Activities.Profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.Activities.Login.LoginActivity
import com.application.knc.Dailogs.ImageZoomer
import com.application.knc.R
import com.application.knc.Utils.MyApp
import com.application.knc.databinding.ActivityProfileBinding
import com.application.knc.model.UserData

class ProfileActivity : AppCompatActivity(),View.OnClickListener {
    private val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    private fun init()
    {
        binding.inclTitleBar.imgBack.setOnClickListener(this)
        binding.loginUser = MyLocalMemory.getObject(MyApp.MySingleton.USER, UserData())
        binding.cvProfile.setOnClickListener(this)
        binding.cvLogout.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.imgBack-> finish()
            R.id.cvLogout->{
                MyLocalMemory.clearMemory()
                startActivity(Intent(this,LoginActivity::class.java))
                finishAffinity()
            }
            R.id.cvProfile->{
                binding.loginUser?.profile_image_url?.let { ImageZoomer(this, it).show() }
            }
        }
    }
}