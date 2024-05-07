package com.application.knc.Activities.CustomerSupport

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.knc.AppInterface.ApiCallListener
import com.application.knc.Utils.MyApp
import com.application.knc.model.Login
import retrofit2.Response

class CustomerSupportViewModel: ViewModel(), ApiCallListener
{


    private  val customerDetailResponse = MutableLiveData<Login>()


    val customerDetailData:LiveData<Login>
        get() = customerDetailResponse



    fun getCustomerDetail(context: Context)
    {
        val customerDetailCall = MyApp.MySingleton.getApiInterface().getCustomerSupport()
        MyApp.MySingleton.callApi(customerDetailCall,MyApp.MySingleton.CUSTOMER_DETAILS,this,context,false)
    }

    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String) {
        when(apiName)
        {
            MyApp.MySingleton.CUSTOMER_DETAILS->{
                val response = callResponse as Response<Login>
                customerDetailResponse.postValue(response.body())
            }
        }

    }

    override fun onFailure(message: String, apiName: String) {
    }


}