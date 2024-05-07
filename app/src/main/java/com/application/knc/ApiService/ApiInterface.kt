package com.application.knc.ApiService
import com.application.knc.model.Carpenters
import com.application.knc.model.Dealers
import com.application.knc.model.Login
import com.application.knc.model.StatusMessage
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email_or_mobile") mobile: String,
        @Field("password") password: String,
        @Field("role") role: String = "sales_executive"
    ) : Call<Login>



    @Multipart
    @POST("register")
    fun register(
        @Part("business_name") businessName: RequestBody,
        @Part("name") name: RequestBody,
        @Part("mobile") mobile: RequestBody,
        @Part("email") email: RequestBody,
        @Part("role") role: RequestBody,
        @Part("password") password: RequestBody,
        @Part("created_by_id") createdById: RequestBody,
        @Part("created_by") createdBy: RequestBody,
        @Part profile_image: MultipartBody.Part,
        @Part govtIdImages:ArrayList<MultipartBody.Part>,
    ): Call<StatusMessage>



    @GET("dealers")
    fun dealers(@Query("page")page:String):Call<Dealers>


    @GET("carpenters")
    fun carpenters(@Query("page")page:String,@Query("dealer_id")dealerId:String,):Call<Carpenters>



    @GET("support")
    fun getCustomerSupport():Call<Login>


}

