package com.application.knc.Activities.Login
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.knc.AppInterface.ApiCallListener
import com.application.knc.Utils.MyApp
import com.application.knc.model.Login
import retrofit2.Response


class LoginViewModel : ViewModel(),ApiCallListener
{
    private val loginData = MutableLiveData<Login>()

    val loginLiveData:LiveData<Login>
        get() =  loginData


    fun login(phone:String,password:String)
    {
        val loginCall = MyApp.MySingleton.getApiInterface().login(phone,password)
        MyApp.MySingleton.callApi(loginCall,MyApp.MySingleton.LOGIN,this,MyApp.MySingleton.getAppContext(), showProgressBar = false)
    }


    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String)
    {
        when(apiName)
        {
            MyApp.MySingleton.LOGIN-> {
                val response = callResponse as Response<Login>
                loginData.postValue(response.body())
            }
        }

    }

    override fun onFailure(message: String, apiName: String)
    {

    }

}