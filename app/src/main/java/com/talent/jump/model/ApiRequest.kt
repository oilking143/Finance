package com.talent.jump.model

import com.google.gson.JsonObject
import com.talent.jump.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiRequest {
    @POST("t/login")
    fun Login(@Body data: JsonObject): Call<LoginResponse>

    @POST("t/payments")
    fun CreatePayments(@Body data: JsonObject): Call<CreatePaymentResponse>

    @GET("t/me")
    fun getMe(): Call<GetMeDataResponse>

    @GET("t/transactions/history")
    fun getHistory(@Query("before_id") bank_name:String, @Query("status") account_number:String,
                    @Query("number") status:String): Call<HistoryResponse>

    @GET("t/transactions/conduct")
    fun getConduct(): Call<JsonObject>

    @GET("t/payments?")
    fun getPayments(@Query("channel_id") channel_id:String, @Query("is_withdraw") is_withdraw:String,
                    @Query("status") status:String): Call<PayMentsResponse>

    @GET("t/transactions?")
    fun getTransactions(@Query("number") page:String, @Query("type") per_page:String,
                      @Query("before_id") type:String): Call<TradeListResponse>

    @GET("t/transactions/{id}")
    fun getHistoryTransaction(@Path("id") id: String): Call<HistorySingleData>

    @GET("t/transactions/{id}")
    fun getSingleTransaction(@Path("id") id: String): Call<SingleTradeResponse>

    @FormUrlEncoded
    @POST("t/transactions/{id}/take")
    fun takeTransactions(@Path("id") id: String,@Field("payment_id") payment_id:String): Call<JsonObject>

    @FormUrlEncoded
    @POST("p/transactions/pay")
    fun payTrasation(@Field("tid") id:String,@Field("merchant_id") merchant_id:String,@Field("merchant_order_no") merchant_order_no:String
        ,@Field("amount") amount:Int,@Field("check_value") check_value:String): Call<JsonObject>


    @Multipart
    @Headers("Accept:multipart/formdata")
    @POST("t/transactions/{id}/report")
    fun ReportTrade(@Path("id") id: String,
                     @Part("comment") comment: RequestBody, @Part("is_success") is_success: RequestBody,@Part("payment_id") payment_id: RequestBody): Call<ReportResponse>
}