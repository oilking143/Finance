package com.talent.jump.model

import com.google.gson.JsonObject
import com.talent.jump.data.LoginResponse
import com.talent.jump.data.TakeTradeResponse
import com.talent.jump.data.TradeListResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiRequest {
    @POST("t/login")
    fun Login(@Body data: JsonObject): Call<LoginResponse>

    @GET("t/transactions?")
    fun getTransactions(@Query("number") page:String, @Query("type") per_page:String,
                      @Query("before_id") type:String): Call<TradeListResponse>

    @POST("/t/transactions/{id}/take")
    open fun takeTransactions(@Path("id") id: String): Call<TakeTradeResponse>
}