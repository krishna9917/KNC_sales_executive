package com.application.knc.Activities.Register
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.application.knc.AppInterface.ApiCallListener
import com.application.knc.Utils.MyApp
import com.application.knc.model.StatusMessage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class RegisterViewModel:ViewModel(),ApiCallListener
{

    private  val register=MutableLiveData<StatusMessage>()

    val registerData:LiveData<StatusMessage>
        get() = register



    fun register(businessName: RequestBody,
                 name: RequestBody,
                 mobile: RequestBody,
                 email: RequestBody,
                 role: RequestBody,
                 password: RequestBody,
                 createdById: RequestBody,
                 createdBy: RequestBody,
                 profile_pic: MultipartBody.Part,govtId:ArrayList<MultipartBody.Part>,context: Context
    )
    {

        val registerCall = MyApp.MySingleton.getApiInterface().register(businessName, name, mobile, email, role, password,createdById,createdBy, profile_pic,govtId)
        MyApp.MySingleton.callApi(registerCall,MyApp.MySingleton.REGISTER,this,context,showProgressBar = false)
    }

    override fun <T : Any> onSuccess(callResponse: Response<T>, apiName: String)
    {
        when(apiName)
        {
            MyApp.MySingleton.REGISTER -> {
                val response = callResponse as Response<StatusMessage>
                register.postValue(response.body())
            }
        }
    }

    override fun onFailure(message: String, apiName: String)
    {

    }

}