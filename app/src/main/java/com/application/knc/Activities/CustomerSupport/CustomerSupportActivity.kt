package com.application.knc.Activities.CustomerSupport

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.R
import com.application.knc.Utils.MyApp
import com.application.knc.Utils.UtilsFunction
import com.application.knc.databinding.ActivityCustomerSupportBinding
import com.application.knc.model.CustomerSupportDetails


class CustomerSupportActivity : AppCompatActivity(), View.OnClickListener {

    private val binding by lazy {
        ActivityCustomerSupportBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<CustomerSupportViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.inclTitleBar.imgBack.setOnClickListener(this)
        binding.txtAddress.setOnClickListener(this)
        setObserver()
    }

    private fun setObserver() {
        viewModel.getCustomerDetail(this)
        viewModel.customerDetailData.observe(this) {
            if (it.status) {
                MyLocalMemory.putObject(MyApp.MySingleton.CUSTOMER_SUPPORT_DETAILS, it.support)
                binding.customerSupportDetails = it.support
            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgBack -> finish()
            R.id.txtAddress->{
                binding.customerSupportDetails?.support_address?.let {
                    UtilsFunction.openMapByAddress(
                        it,this)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.customerSupportDetails = MyLocalMemory.getObject(
            MyApp.MySingleton.CUSTOMER_SUPPORT_DETAILS,
            CustomerSupportDetails()
        )
    }

}