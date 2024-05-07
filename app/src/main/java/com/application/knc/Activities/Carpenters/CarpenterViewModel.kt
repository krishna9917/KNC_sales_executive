package com.application.knc.Activities.Carpenters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.knc.AppInterface.ApiCallListener
import com.application.knc.Utils.MyApp
import com.application.knc.model.Carpenters
import retrofit2.Response

class CarpenterViewModel:ViewModel(),ApiCallListener
{
    private val carpenters =MutableLiveData<Carpenters>()

    val carpenterData:LiveData<Carpenters>
        get() = carpenters


    public fun  getCarpenters(requireNextPage:Boolean=false,page:Int=1,showProgress:Boolean=false,dealerId:String)
    {
        var  whichPage=page
        if(requireNextPage)
        {
            whichPage += 1
        }
        val carpentersCall = MyApp.MySingleton.getApiInterface().carpenters(page.toString(), dealerId = dealerId)
        MyApp.MySingleton.callApi(carpentersCall,MyApp.MySingleton.CARPENTERS,this,MyApp.MySingleton.getAppContext(),showProgress)

    }

    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String)
    {
        when(apiName)
        {
            MyApp.MySingleton.CARPENTERS -> {
                val response = callResponse as Response<Carpenters>
                carpenters.postValue(response.body())
            }
        }
    }

    override fun onFailure(message: String, apiName: String) {
    }


}