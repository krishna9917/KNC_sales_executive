package com.application.knc.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.LocaleListCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.Activities.CustomerSupport.CustomerSupportActivity
import com.application.knc.Activities.Profile.ProfileActivity
import com.application.knc.Activities.WebView.WebViewActivity
import com.application.knc.Adapters.HomeFunctionalityOptionsAdapter
import com.application.knc.AppInterface.AdapterViewClickListener
import com.application.knc.AppInterface.DialogClickListener
import com.application.knc.Dailogs.LanguageChooser
import com.application.knc.Fragments.HomeFragment
import com.application.knc.R
import com.application.knc.Utils.MyApp
import com.application.knc.Utils.UtilsFunction
import com.application.knc.databinding.ActivityHomeBinding
import com.application.knc.model.NameIcon
import com.application.knc.model.UserData

class HomeActivity : AppCompatActivity(), AdapterViewClickListener, DialogClickListener {
    private  val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializer()
    }

    private fun initializer()
    {
        binding.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        showFragment(HomeFragment())
        binding.nav.setOnItemSelectedListener { item ->
            when(item.itemId)
            {
               R.id.home -> showFragment(HomeFragment())
            }
            return@setOnItemSelectedListener true
        }


        val functionalityTabs: ArrayList<NameIcon> = ArrayList()
        functionalityTabs.add(
            NameIcon(
                id = 1,
                name = getString(R.string.my_profile), icon = ResourcesCompat.getDrawable(
                    resources, R.drawable.svg_profile, null
                )!!
            )
        )

        functionalityTabs.add(
            NameIcon(
                id = 5,
                name = getString(R.string.change_language), icon = ResourcesCompat.getDrawable(
                    resources, R.drawable.img_language_change, null
                )!!
            )
        )

        functionalityTabs.add(
            NameIcon(
                id = 2,
                name = getString(R.string.customer_support), icon = ResourcesCompat.getDrawable(
                    resources, R.drawable.img_color_support, null
                )!!
            )
        )
        functionalityTabs.add(
            NameIcon(
                id = 3,
                name = getString(R.string.privacy_policy), icon = ResourcesCompat.getDrawable(
                    resources, R.drawable.img_privacy, null
                )!!
            )
        )

        functionalityTabs.add(
            NameIcon(
                id = 4,
                name = getString(R.string.invite_your_friend), icon = ResourcesCompat.getDrawable(
                    resources, R.drawable.svg_app_share, null
                )!!
            )
        )

        binding.rvOptions.adapter = HomeFunctionalityOptionsAdapter(functionalityTabs, this, layout = 1)

    }



    private fun showFragment(fragment:Fragment)
    {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flContainer,fragment , "changeFragment")
            .commit()
    }

    override fun onClick(
        data: Any,
        selectedPosition: Int,
        clickLayoutCode: Int,
        callBack: AdapterViewClickListener?
    ) {
        binding.mDrawerLayout.closeDrawer(GravityCompat.START)
        when (data as Int) {
            1 ->  startActivity(Intent(this,ProfileActivity::class.java))
            2 -> startActivity(Intent(this, CustomerSupportActivity::class.java))
            3 ->  {
                val goToTermConditionUrl = Intent(this, WebViewActivity::class.java)
                goToTermConditionUrl.putExtra("title", getString(R.string.term_conditions))
                goToTermConditionUrl.putExtra("url","https://www.termsfeed.com/blog/sample-terms-and-conditions-template/")
                startActivity(goToTermConditionUrl)
            }
            4 -> UtilsFunction.shareText("Sharing App link - https://play.google.com/store/apps/",this)
            5-> {
                if(MyLocalMemory.getString(MyApp.MySingleton.LANGUAGE)=="")
                {
                    MyLocalMemory.putString(MyApp.MySingleton.LANGUAGE,"en")
                }
                LanguageChooser(this,this).show()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        binding.loginUser = MyLocalMemory.getObject(MyApp.MySingleton.USER, UserData())
    }

    override fun onClick(clickCode: Int, data: Any?, callBack: DialogClickListener?) {
        val refreshLayout=Intent(this,HomeActivity::class.java)
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(data as String)
        AppCompatDelegate.setApplicationLocales(appLocale)
        startActivity(refreshLayout)
        finish()
    }
}