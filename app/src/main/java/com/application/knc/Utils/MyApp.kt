package com.application.knc.Utils
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.application.discount_medica.LocalMemory.MyLocalMemory
import com.application.knc.AppInterface.ApiCallListener
import com.application.knc.ApiService.ApiCall
import com.application.knc.ApiService.ApiInstance
import com.application.knc.ApiService.ApiInterface
import retrofit2.Call
import java.util.Locale


public class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()
        MySingleton.init(applicationContext)
        val lang = MyLocalMemory.getString(MySingleton.LANGUAGE, "en")
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        createConfigurationContext(config)

    }


    class MySingleton {
        companion object {
            val BASE_URL = "http://knchomedecor.com/knc/api/"
            val SITE_URL = "http://knchomedecor.com/"

            //log tag
            const val ERROR = "ERROR--->"




            //shared preferences
            const val  LANGUAGE = "LANGUAGE"
            const val  USER = "USER"
            const val USER_TOKEN = "USER_TOKEN"
            const val IS_LOGIN="IS_LOGIN"
            const val IS_SECOND_TIME_OPEN=" IS_SECOND_TIME_OPEN"
            const val CUSTOMER_SUPPORT_DETAILS = "CUSTOMERS_SUPPORT_DETAILS"



           // Api end points
            const val LOGIN="login"
            const val REGISTER="register"
            const val DEALERS="dealers"
            const val CARPENTERS="carpenters"
            const val CUSTOMER_DETAILS="support"




            private lateinit var appContext: Context

            fun init(context: Context) {
                appContext = context.applicationContext
            }

            fun getAppContext(): Context {
                return appContext
            }




            public fun getApiInterface(): ApiInterface {
                return ApiInstance.instance().create(ApiInterface::class.java);
            }


            public fun <T : Any> callApi(
                call: Call<T>,
                apiName: String,
                apiCallListener: ApiCallListener,
                context: Context,
                showProgressBar: Boolean = true
            ) {
                ApiCall(call, apiName, apiCallListener, context, showProgressBar)
            }


        }

    }
}