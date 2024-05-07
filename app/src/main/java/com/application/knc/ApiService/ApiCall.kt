package com.application.knc.ApiService
import android.content.Context
import android.os.Looper
import com.application.knc.AppInterface.ApiCallListener
import com.application.knc.AppInterface.DialogClickListener
import com.application.knc.Dailogs.AppProgressBar
import com.application.knc.R
import com.application.knc.Utils.MyApp
import com.application.knc.Utils.UtilsFunction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ApiCall<T : Any>(
    private val call: Call<T>,
    val apiName: String,
    private val responseListener: ApiCallListener,
    private val context: Context,
    private val showProgress: Boolean,
) : DialogClickListener {
    init {

        var progressBarDialog: AppProgressBar?=null

        android.os.Handler(Looper.getMainLooper()).post {
            if(showProgress)
             progressBarDialog = AppProgressBar(context)
        }

        if (!UtilsFunction.isInternetConnected(MyApp.MySingleton.getAppContext()))
        {
            android.os.Handler(Looper.getMainLooper()).post {
                responseListener.onFailure(MyApp.MySingleton.getAppContext().getString(R.string.no_internet_connection), apiName)

            }

        } else {
            if (showProgress)
            {
                android.os.Handler(Looper.getMainLooper()).post {
                   progressBarDialog?.show()
                }
            }
            call.enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    progressBarDialog?.dismiss()
                    if (response.isSuccessful && response.code() == 200) {
                        responseListener.onSuccess(response, apiName)

                    } else if (response.code() == 401) {

                    }
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    progressBarDialog?.dismiss()
                    responseListener.onFailure(t.message!!, apiName)
                }
            })
        }
    }




    override fun onClick(clickCode: Int, data: Any?, callBack: DialogClickListener?) {
        MyApp.MySingleton.callApi(call, apiName, responseListener, context, showProgress)
    }

}