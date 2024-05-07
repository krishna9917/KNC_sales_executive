package com.application.knc.Activities.Dealers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.knc.AppInterface.ApiCallListener
import com.application.knc.Utils.MyApp
import com.application.knc.model.Dealers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DealersViewModel:ViewModel(),ApiCallListener
{
    private val dealers =MutableLiveData<Dealers>()

    val dealersData:LiveData<Dealers>
        get() = dealers

    init {
        viewModelScope.launch (Dispatchers.IO){
            getDealer()
        }
    }


    public fun getDealer(requireNextPage:Boolean=false,page:Int=1,showProgress:Boolean=false)
    {
        var  whichPage=page
        if(requireNextPage)
        {
            whichPage += 1
        }
        val dealersCall = MyApp.MySingleton.getApiInterface().dealers(page.toString())
        MyApp.MySingleton.callApi(dealersCall,MyApp.MySingleton.DEALERS,this,MyApp.MySingleton.getAppContext(),showProgress)

    }

    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String)
    {
        when(apiName)
        {
            MyApp.MySingleton.DEALERS -> {
                val response = callResponse as Response<Dealers>
                dealers.postValue(response.body())
            }
        }
    }

    override fun onFailure(message: String, apiName: String) {
    }


}