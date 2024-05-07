package com.application.knc.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.Activities.Carpenters.CarpenterActivity
import com.application.knc.Activities.Dealers.DealersActivity
import com.application.knc.Activities.Register.RegisterActivity
import com.application.knc.Adapters.HomeFunctionalityOptionsAdapter
import com.application.knc.AppInterface.AdapterViewClickListener
import com.application.knc.model.NameIcon
import com.application.knc.R
import com.application.knc.Utils.MyApp
import com.application.knc.databinding.FragmentHomeBinding
import com.application.knc.model.UserData

class HomeFragment : Fragment(), View.OnClickListener, AdapterViewClickListener {


    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initializer()
        return binding.root
    }

    private fun initializer() {
        val user = MyLocalMemory.getObject(MyApp.MySingleton.USER, UserData())
        binding.loginUser = user

        val functionalityButtons: ArrayList<NameIcon> = ArrayList()
        functionalityButtons.add(
            NameIcon(
                id = 1,
                name = getString(R.string.my_connections),
                icon = ResourcesCompat.getDrawable(resources, R.drawable.img_connections, null)!!
            )
        )
        binding.rvFunctionalityOptions.adapter =
            HomeFunctionalityOptionsAdapter(functionalityButtons, this)
        binding.cvAddCarpenter.setOnClickListener(this)
        binding.cvAddBusinessDealer.setOnClickListener(this)
        binding.imgMenu.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.cvAddCarpenter -> {
                val intent = Intent(context, RegisterActivity::class.java)
                startActivity(intent)
            }

            R.id.cvAddBusinessDealer -> {
                val intent = Intent(context, RegisterActivity::class.java)
                intent.putExtra("isBusinessDealer", true)
                startActivity(intent)
            }

            R.id.imgMenu -> requireActivity().findViewById<DrawerLayout>(R.id.mDrawerLayout)
                .openDrawer(
                    GravityCompat.START
                )
        }
    }

    override fun onClick(
        data: Any,
        selectedPosition: Int,
        clickLayoutCode: Int,
        callBack: AdapterViewClickListener?
    ) {

        when (data as Int) {
            1 -> startActivity(Intent(context, DealersActivity::class.java))
        }

    }

}